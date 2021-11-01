package com.cos.iter.web;

import com.cos.iter.domain.user.User;
import com.cos.iter.domain.user.UserRepository;
import com.cos.iter.web.dto.JoinReqDto;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class AuthControllerTest {
    @LocalServerPort
    private int port; //8080

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Register Test")
    public void registerTest() throws Exception{
        String username = "zxcvlksdfoq";
        String password = "1234";
        String email = "kakaofriends@nate.com";
        String name = "카카오프렌즈";

        String url = "http://localhost:" + port + "/auth/join";
        log.info("url : " +  url);
        JoinReqDto user = JoinReqDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .name(name)
                .build();

        String formData = "username=" + username + "&" +
                "password=" + password + "&" +
                "email=" + email + "&" +
                "name=" + name;

        log.info("user : " + user);
        try {
            mockMvc.perform(
                    post(url)
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                            .content(formData)
            ).andExpect(status().is3xxRedirection());
        } catch (Exception e) {
            log.info("Already Exists");

        }
    }

    @Test
    @DisplayName("Login Test")
    public void loginTest() throws Exception {
        final String username = "test1234";
        final String password = "user6949";

        createTestAccount(username, password);

        mockMvc.perform(post("/auth/login")
            .contentType(MediaType.APPLICATION_JSON)
            .param("username", username)
            .param("password", password))
            .andExpect(authenticated());
    }

    private void createTestAccount(String username, String password) {
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
    }
}