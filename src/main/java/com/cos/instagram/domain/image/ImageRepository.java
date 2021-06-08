package com.cos.instagram.domain.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Integer>{
	
	List<Image> findByUserId(int userId);
	
	// 내가 팔로우 하지 않은 사람들의 이미지들(최대 20개)
	@Query(value = "select * from Image where userId in (select id from User where id != ?1 and id not in (select toUserId from Follow where fromUserId = ?1)) limit 20 ORDER BY createDate DESC", nativeQuery = true)
	List<Image> mNonFollowImage(int loginUserId);
	
	@Query(value="select * from Image where userId in (select toUserId from Follow where fromUserId = ?1) ORDER BY createDate DESC", nativeQuery = true)
	List<Image> mFeeds(int loginUserId);
	
	@Query(value="select * from Image where id in (select imageId from Tag where name=?1) ORDER BY createDate DESC", nativeQuery = true)
	List<Image> mFeeds(String tag);
}
