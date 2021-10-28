package com.cos.iter.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(int userId);

    // 내가 doFollow 하지 않은 사람들의 이미지들(최대 20개)
    @Query(value = "select * from post where user_id in (select id from user where id != ?1 and id not in (select to_user_id from follow where from_user_id = ?1)) ORDER BY create_date DESC", nativeQuery = true)
    Page<Post> getNonFollowPosts(int loginUserId, PageRequest pageRequest);

    @Query(value="select * from post where user_id in (select to_user_id from follow where from_user_id = ?1) OR user_id = ?1 ORDER BY create_date DESC", nativeQuery = true)
    Page<Post> getFeeds(int loginUserId, PageRequest pageRequest);

    @Query(value="select * from post where id in (select post_id from tag where name LIKE CONCAT('%', ?1, '%')) ORDER BY create_date DESC", nativeQuery = true)
    Page<Post> getFeeds(String tag, PageRequest pageRequest);


}