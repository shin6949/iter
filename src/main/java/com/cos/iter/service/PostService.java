package com.cos.iter.service;

import com.cos.iter.IterApplication;
import com.cos.iter.domain.comment.Comment;
import com.cos.iter.domain.like.Like;
import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.post.PostRepository;
import com.cos.iter.domain.user.UserRepository;
import com.cos.iter.util.Logging;
import com.cos.iter.web.dto.ImageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Logging logging;

    @Transactional(readOnly = true)
    public Page<Post> getFeedPhoto(int loginUserId, String tag, Integer page){
        if(page == null) {
            page = 1;
        }

        PageRequest pageRequest = PageRequest.of(page - 1, IterApplication.POSTS_PER_PAGE);

        Page<Post> posts;
        if(tag == null || tag.equals("")) {
            posts = postRepository.getFeeds(loginUserId, pageRequest);
        } else {
            posts = postRepository.getFeeds(tag, pageRequest);
        }

        log.info(logging.getClassName() + " / " + logging.getMethodName());
        for (Post post : posts.getContent()) {
            post.setLikeCount(post.getLikes().size());

            // doLike 상태 여부 등록
            for (Like like : post.getLikes()) {
                if(like.getUser().getId() == loginUserId) {
                    post.setLikeState(true);
                }
            }

            int commentSize = post.getComments().size();
            if(commentSize > 3) {
                post.setComments(post.getComments().subList(commentSize - 3, commentSize));
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

        PageRequest pageRequest = PageRequest.of(page - 1, IterApplication.POSTS_PER_PAGE);
        List<Post> nonFollowPosts = postRepository.getNonFollowPosts(loginUserId, pageRequest);

        log.info("nonFollowPosts: " + nonFollowPosts);

        return nonFollowPosts;
    }

    @Transactional
    public int saveAndReturnId(ImageReqDto imageReqDto, int userId) {
        Post post = imageReqDto.toPostEntity();

        post.setUser(userRepository.getById(userId));
        postRepository.save(post);
        postRepository.flush();

        return post.getId();
    }

    @Transactional(readOnly = true)
    public Post getDetailPost(int loginUserId, int postId) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());
        Post post = postRepository.getById(postId);

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

        return post;
    }

    @Transactional
    @Async
    public void increaseViewCount(Post post) {
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);
    }
}
