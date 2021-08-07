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
		String queryString = "select u.id,u.username,u.name,u.profileImage, " +
				"if(u.id = ?, true, false) equalUserState," +
				"if((select true from Follow where fromUserId = ? and toUserId = u.id), true, false) as followState " +
				"from Follow f inner join User u on f.toUserId = u.id " +
				"and f.fromUserId = ?";
		System.out.println("getFollowingList : "+ queryString);
		Query query = em.createNativeQuery(queryString, "FollowRespDtoMapping")
				.setParameter(1, loginUserId)
				.setParameter(2, loginUserId)
				.setParameter(3, pageUserId);

		return (List<FollowRespDto>) query.getResultList();
	}
	
	public List<FollowRespDto> getFollowerList(int loginUserId, int pageUserId){
		// 첫번째 물음표 loginUserId, 두번째 물음표 pageUserId
		String queryString = "select u.id,u.username,u.name,u.profileImage, " +
				"if(u.id = ?, true, false) equalUserState," +
				"if((select true from Follow where fromUserId = ? and toUserId = u.id), true, false) as followState " +
				"from Follow f inner join User u on f.fromUserId = u.id " +
				"and f.toUserId = ?";
		
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
