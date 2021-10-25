package com.cos.iter.web;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.cos.iter.web.dto.JoinReqDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Log4j2
public class ImageControllerTest {
	@LocalServerPort
	private int port; //8080
	
	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	
	@Test
	public void registerTest() throws Exception{
		String username = "zxcvlksdfoq";
		String password = "1234";
		String email = "kakaofriends@nate.com";
		String name = "카카오프렌즈";

		String url = "http://localhost:"+port+"/auth/join";
		log.info("url : "+  url);
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
		mvc.perform(
				post(url)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.content(formData)
		).andExpect(status().is3xxRedirection());
	}
}





