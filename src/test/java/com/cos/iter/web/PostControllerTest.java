package com.cos.iter.web;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Log4j2
class PostControllerTest {
    @LocalServerPort
    private int port; //8080

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @BeforeTransaction
    public void saveMockData() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        final String username = "cocotest12344";
        final String password = "user6949";
        final String email = "coco@blue.com";
        final String name = "cocotest";

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

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void feedTestWithAnonymous() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is3xxRedirection());
    }

    @Test
    @WithMockUser
    public void feedTestWithUser() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful());
    }
}