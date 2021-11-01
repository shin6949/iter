package com.cos.iter.domain.post;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class PostTest {
    @Autowired
    PostRepository postRepository;

    @Test
    public void findAllPagingTest() {
        PageRequest pageRequest = PageRequest.of(0, 2);

        Page<Post> result = postRepository.findAll(pageRequest);
        log.info("Total Page: " + result.getTotalPages());
        log.info("List Size: " + result.getContent().size());
        log.info("Contents: " + result.getContent());
    }

    @Test
    public void getNonFollowPostsWithPagingTest() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<Post> result = postRepository.getNonFollowPosts(2, pageRequest);

        log.info("List Size: " + result.size());
        log.info("Contents: " + result);
    }
}
