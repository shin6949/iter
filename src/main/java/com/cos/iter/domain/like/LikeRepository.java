package com.cos.iter.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Integer>{

	@Modifying
	@Query(value = "INSERT INTO likes(image_id, user_id) VALUES(?1, ?2)", nativeQuery = true)
	int mSave(int imageId, int loginUserId);
	
	@Modifying
	@Query(value = "DELETE FROM likes WHERE image_id = ?1 AND user_id = ?2", nativeQuery = true)
	int mDelete(int imageId, int loginUserId);
}
