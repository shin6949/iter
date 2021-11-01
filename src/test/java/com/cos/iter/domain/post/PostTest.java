package com.cos.iter.domain.post;

import com.cos.iter.domain.image.ImageRepository;
import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.user.User;
import com.cos.iter.domain.user.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class PostTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @BeforeAll
    public void saveMockData() {
        final User mockUser = User.builder()
                .username("cocotest1234")
                .password("cocotest1234")
                .email("coco@blue.com")
                .build();
        userRepository.saveAndFlush(mockUser);

        // TODO: 추가 작성 필요
        final ArrayList<String> tagStrings = new ArrayList<>(Arrays.asList("cube", "테스트", "후후"));

        final List<Tag> tags = new ArrayList<>();
        final Post mockPost = Post.builder()
                .content("맛있는 쉐이크쉑")
                .user(mockUser)
                .build();
    }

    @Test
    public void findAllPagingTest() {
        final int sizePerPage = 2;
        final int pageNum = 0;
        PageRequest pageRequest = PageRequest.of(pageNum, sizePerPage);

        Page<Post> result = postRepository.findAll(pageRequest);
        List<Post> expectedResult = postRepository.findAll();

        assertEquals(result.getTotalElements(), expectedResult.size());
        assertEquals(result.getTotalPages(), expectedResult.size() / sizePerPage);
    }

    @Test
    public void getNonFollowPostsWithPagingTest() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        List<Post> result = postRepository.getNonFollowPosts(2, pageRequest);

        log.info("List Size: " + result.size());
        log.info("Contents: " + result);
    }
}
