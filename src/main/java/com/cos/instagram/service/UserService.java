package com.cos.instagram.service;

import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.config.hanlder.ex.MyUserIdNotFoundException;
import com.cos.instagram.domain.follow.FollowRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.web.dto.JoinReqDto;
import com.cos.instagram.web.dto.UserProfileImageRespDto;
import com.cos.instagram.web.dto.UserProfileRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

@RequiredArgsConstructor
@Service
public class UserService {
	
	@PersistenceContext
	private EntityManager em;
	private final UserRepository userRepository;
	private final FollowRepository followRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AzureService azureService;

	@Transactional
	public void uploadProfile(LoginUser loginUser, MultipartFile file) {
		String imageFilename = "";

		try {
			imageFilename = azureService.uploadToCloudAndReturnFileName(file, "profile");
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		User userEntity = userRepository.findById(loginUser.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {
			@Override
			public MyUserIdNotFoundException get() {
				return new MyUserIdNotFoundException();
			}
		});
		
		// 더티체킹
		userEntity.setProfileImage(imageFilename);
	}
	
	@Transactional
	public void modifyUser(User user) {
		// 더티 체킹
		User userEntity = userRepository.findById(user.getId()).orElseThrow(new Supplier<MyUserIdNotFoundException>() {
			@Override
			public MyUserIdNotFoundException get() {
				return new MyUserIdNotFoundException();
			}
		});
		userEntity.setName(user.getName());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
	}
	
	@Transactional(readOnly = true)
	public User getUser(LoginUser loginUser) {
		return userRepository.findById(loginUser.getId())
				.orElseThrow(new Supplier<MyUserIdNotFoundException>() {
					@Override
					public MyUserIdNotFoundException get() {
						return new MyUserIdNotFoundException();
					}
				});
	}
	
	@Transactional
	public void register(JoinReqDto joinReqDto) {
		System.out.println("서비스 회원가입 들어옴");
		System.out.println(joinReqDto);
		String encPassword = bCryptPasswordEncoder.encode(joinReqDto.getPassword());
		System.out.println("encPassword : "+encPassword);
		joinReqDto.setPassword(encPassword);
		userRepository.save(joinReqDto.toEntity());
	}
	
	// 읽기 전용 트랜잭션
	// (1) 변경 감지 연산을 하지 않음.
	// (2) isolation(고립성)을 위해 Phantom read 문제가 일어나지 않음.
	@Transactional(readOnly = true)
	public UserProfileRespDto memberProfile(int id, LoginUser loginUser) {
		int imageCount;
		int followerCount;
		int followingCount;
		boolean followState;
		
		User userEntity = userRepository.findById(id)
				.orElseThrow(new Supplier<MyUserIdNotFoundException>() {
					@Override
					public MyUserIdNotFoundException get() {
						return new MyUserIdNotFoundException();
					}
				});
		
		// 1. 이미지들과 전체 이미지 카운트(dto받기)
		String queryString = "select im.id, im.imageUrl, " +
				"(select count(*) from Likes lk where lk.imageId = im.id) as likeCount, " +
				"(select count(*) from Comment ct where ct.imageId = im.id) as commentCount " +
				"from Image im where im.userId = ? ";
		Query query = em.createNativeQuery(queryString, "UserProfileImageRespDtoMapping").setParameter(1, id);
		List<UserProfileImageRespDto> imagesEntity = query.getResultList();

		imageCount = imagesEntity.size();
		
		// 2. doFollow 수
		followerCount = followRepository.mCountByFollower(id);
		followingCount = followRepository.mCountByFollowing(id);
		
		// 3. doFollow 유무
		followState = followRepository.mFollowState(loginUser.getId(), id) == 1;
		
		// 4. 최종마무리
		return UserProfileRespDto.builder()
		.pageHost(id==loginUser.getId())
		.followState(followState)
		.followerCount(followerCount)
		.followingCount(followingCount)
		.imageCount(imageCount)
		.user(userEntity)
		.images(imagesEntity) // 수정완료(Dto만듬) (댓글수, 좋아요수)
		.build();
	}
}






