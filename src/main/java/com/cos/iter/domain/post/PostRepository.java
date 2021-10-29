package com.cos.iter.domain.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByUserId(int userId);

    // 내가 doFollow 하지 않은 사람들의 이미지들(최대 20개)
    @Query(value = "SELECT *, (((SELECT count(*) FROM likes li WHERE li.post_id = po.id) * 2) + ((SELECT count(*) FROM comment comm WHERE comm.post_id = po.id) * 5) + po.view_count) as popular_rate FROM post po WHERE po.user_id IN (SELECT id FROM user WHERE id != ?1 AND id NOT IN (SELECT to_user_id FROM follow WHERE from_user_id = ?1)) ORDER BY popular_rate DESC"
            , nativeQuery = true)
    List<Post> getNonFollowPosts(int loginUserId, PageRequest pageRequest);

    @Query(value = "SELECT * FROM post po WHERE po.user_id IN (SELECT id FROM user WHERE id != ?1 AND id NOT IN (SELECT to_user_id FROM follow WHERE from_user_id = ?1)) ORDER BY create_date DESC"
            , nativeQuery = true)
    Page<Post> getNonFollowPostsPaging(int loginUserId, PageRequest pageRequest);

    @Query(value="select * from post where user_id in (select to_user_id from follow where from_user_id = ?1) OR user_id = ?1 ORDER BY create_date DESC", nativeQuery = true)
    Page<Post> getFeeds(int loginUserId, PageRequest pageRequest);

    @Query(value="select * from post where id in (select post_id from tag where name LIKE CONCAT('%', ?1, '%')) ORDER BY create_date DESC", nativeQuery = true)
    Page<Post> getFeeds(String tag, PageRequest pageRequest);


}