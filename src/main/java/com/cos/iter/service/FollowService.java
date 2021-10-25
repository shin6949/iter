package com.cos.iter.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.iter.domain.follow.FollowRepository;
import com.cos.iter.domain.noti.NotiRepository;
import com.cos.iter.domain.noti.NotiType;
import com.cos.iter.web.dto.FollowRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FollowService {
	
	@PersistenceContext
	private EntityManager em;
	private final FollowRepository followRepository;
	private final NotiRepository notiRepository;
	
	public List<FollowRespDto> getFollowingList(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		String queryString = "select u.id,u.username,u.name,u.profile_image, " +
				"if(u.id = ?, true, false) equal_user_state," +
				"if((select true from follow where from_user_id = ? and to_user_id = u.id), true, false) as follow_state " +
				"from follow f inner join user u on f.to_user_id = u.id " +
				"and f.from_user_id = ?";
		System.out.println("getFollowingList : " + queryString);
		Query query = em.createNativeQuery(queryString, "FollowRespDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);

		return (List<FollowRespDto>) query.getResultList();
	}
	
	public List<FollowRespDto> getFollowerList(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		String queryString = "select u.id,u.username,u.name,u.profile_image, " +
				"if(u.id = ?, true, false) equal_user_state," +
				"if((select true from follow where from_user_id = ? and to_user_id = u.id), true, false) as follow_state " +
				"from follow f inner join user u on f.from_user_id = u.id " +
				"and f.to_user_id = ?";
		
		Query query = em.createNativeQuery(queryString, "FollowRespDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);
		return (List<FollowRespDto>) query.getResultList();
	}
	
	// 서비스단에서 롤백하려면 throw를 runtimeException으로 던져야됨.
	@Transactional
	public void doFollow(int loginUserId, int pageUserId) {
		int result = followRepository.mFollow(loginUserId, pageUserId);
		
		notiRepository.mSave(loginUserId, pageUserId, NotiType.FOLLOW.name());
		System.out.println("doFollow result : "+result);
	}
	
	@Transactional
	public void doUnFollow(int loginUserId, int pageUserId) {
		int result = followRepository.mUnFollow(loginUserId, pageUserId);
		System.out.println("doUnFollow result : "+result);
	}
}
