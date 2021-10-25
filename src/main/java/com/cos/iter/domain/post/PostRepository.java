package com.cos.iter.domain.post;

import com.cos.iter.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(User user);

    // 내가 doFollow 하지 않은 사람들의 이미지들(최대 20개)
    @Query(value = "select * from post where user_id in (select id from user where id != ?1 and id not in (select to_user_id from follow where from_user_id = ?1)) ORDER BY create_date DESC LIMIT ?2 ?3", nativeQuery = true)
    List<Post> getNonFollowPosts(int loginUserId, int from, int to);

    @Query(value="select * from image where user_id in (select to_user_id from follow where from_user_id = ?1) ORDER BY create_date DESC LIMIT ?2 ?3", nativeQuery = true)
    List<Post> getFeeds(int loginUserId, int from, int to);

    @Query(value="select * from image where id in (select image_id from tag where name LIKE CONCAT('%', ?1, '%')) ORDER BY create_date DESC LIMIT ?2 ?3", nativeQuery = true)
    List<Post> getFeeds(String tag, int from, int to);
}