package com.cos.iter.service;

import com.cos.iter.domain.comment.Comment;
import com.cos.iter.domain.like.Like;
import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.post.PostRepository;
import com.cos.iter.util.Logging;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final Logging logging;

    @Transactional(readOnly = true)
    public List<Post> getFeedPhoto(int loginUserId, String tag, Integer page){
        if(page == null) {
            page = 1;
        }

        List<Post> posts;
        if(tag == null || tag.equals("")) {
            posts = postRepository.getFeeds(loginUserId, getStartLimitNum(page), getEmdLimitNum(page));
        } else {
            posts = postRepository.getFeeds(tag, getStartLimitNum(page), getEmdLimitNum(page));
        }

        log.info(logging.getClassName() + " / " + logging.getMethodName());
        for (Post post : posts) {
            post.setLikeCount(post.getLikes().size());

            // doLike 상태 여부 등록
            for (Like like : post.getLikes()) {
                if(like.getUser().getId() == loginUserId) {
                    post.setLikeState(true);
                }
            }
            // 댓글 주인 여부 등록
            for (Comment comment : post.getComments()) {
                if(comment.getUser().getId() == loginUserId) {
                    comment.setCommentHost(true);
                }
            }
        }

        return posts;
    }

    @Transactional(readOnly = true)
    public List<Post> getPopularPost(int loginUserId, Integer page) {
        if(page == null) {
            page = 1;
        }

        List<Post> nonFollowPosts = postRepository.getNonFollowPosts(loginUserId, getStartLimitNum(page), getEmdLimitNum(page));
        log.info("nonFollowPosts: " + nonFollowPosts);

        return nonFollowPosts;
    }

    private int getStartLimitNum(Integer page) {
        return (page - 1) * 10;
    }

    private int getEmdLimitNum(Integer page) {
        return (page * 10) - 1;
    }
}
