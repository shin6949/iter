package com.cos.iter.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	@Modifying
	@Query(value = "INSERT INTO comment(user_id, post_id, content) VALUES(?1, ?2, ?3)", nativeQuery = true)
	int mSave(int userId, int postId, String content);
}
