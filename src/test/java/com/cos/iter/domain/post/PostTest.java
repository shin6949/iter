package com.cos.iter.domain.post;

import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.user.User;
import com.cos.iter.domain.user.UserRepository;
import com.cos.iter.web.dto.JoinReqDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
public class PostTest {
    @Autowired
    private PostRepository postRepository;

    @LocalServerPort
    private int port; //8080

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private final int sizePerPage = 2;
    private final int pageNum = 0;

    @BeforeAll
    public void saveMockData() throws Exception {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        final String username = "test1234";
        final String password = "user6949";
        final String email = "user@gmail.com";
        final String name = "testusername";

        String url = "http://localhost:" + port + "/auth/join";

        String formData = "username=" + username + "&" +
                "password=" + password + "&" +
                "email=" + email + "&" +
                "name=" + name;

        try {
            mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(formData)
            ).andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            log.info("Already Exists");
        }

        final List<Tag> tags = new ArrayList<>(Arrays.asList(
                new Tag("cube"),
                new Tag("테스트"),
                new Tag("후후"))
        );

        User mockUser = userRepository.findByUsername(username).get();

        Post mockPost = Post.builder()
                .content("맛있는 쉐이크쉑")
                .user(mockUser)
                .tags(tags)
                .build();

        postRepository.save(mockPost);

        mockPost = Post.builder()
                .content("어디에나 있는 롯데리아")
                .user(mockUser)
                .tags(tags)
                .build();
        postRepository.save(mockPost);

        mockPost = Post.builder()
                .content("감튀가 맛있는 맥도날드")
                .user(mockUser)
                .tags(tags)
                .build();
        postRepository.save(mockPost);
    }

    @Test
    public void findAllPagingTest() {
        final PageRequest pageRequest = PageRequest.of(pageNum, sizePerPage);

        final Page<Post> result = postRepository.findAll(pageRequest);
        final List<Post> expectedResult = postRepository.findAll();

        assertEquals(result.getTotalElements(), expectedResult.size());
        final double calculatedPage = (double) expectedResult.size() / sizePerPage;

        assertEquals(result.getTotalPages(), calculatedPage % 1 > 0 ? (int) calculatedPage + 1 : (int) calculatedPage);
    }

    @Test
    public void getNonFollowPostsWithPagingTest() {
        final int findUserId = 2;
        final PageRequest pageRequest = PageRequest.of(pageNum, sizePerPage);

        final List<Post> result = postRepository.getNonFollowPosts(findUserId, pageRequest);
        final Page<Post> expectedResult = postRepository.getNonFollowPostsPaging(findUserId, pageRequest);

        assertEquals(result.size(), expectedResult.getContent().size());
    }
}
