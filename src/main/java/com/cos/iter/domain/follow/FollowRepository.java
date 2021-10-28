package com.cos.iter.domain.follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FollowRepository extends JpaRepository<Follow, Integer>{

	@Query(value = "SELECT count(*) FROM follow WHERE to_user_id = ?1", nativeQuery = true)
	int mCountByFollower(int toUserId);
	
	@Query(value = "SELECT count(*) FROM follow WHERE from_user_id = ?1", nativeQuery = true)
	int mCountByFollowing(int fromUserId); 
	
	@Query(value = "SELECT count(*) FROM follow WHERE from_user_id = ?1 AND to_user_id = ?2", nativeQuery = true)
	int mFollowState(int loginUserId, int pageUserId);
	
	// 수정,삭제,추가시에는 모디파이 어노테이션 필요 @Modifying
	// 수정,삭제,추가시에 return 값으로 변경된 행의 개수를 받는다.

	@Modifying
	@Query(value = "INSERT INTO follow(from_user_id, to_user_id) VALUES(?1, ?2)", nativeQuery = true)
	int mFollow(int loginUserId, int pageUserId);
	
	@Modifying
	@Query(value = "DELETE FROM follow WHERE from_user_id = ?1 AND to_user_id = ?2", nativeQuery = true)
	int mUnFollow(int loginUserId, int pageUserId);
}
