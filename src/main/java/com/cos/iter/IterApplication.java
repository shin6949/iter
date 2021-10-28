package com.cos.iter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class IterApplication extends SpringBootServletInitializer {
	public static int POSTS_PER_PAGE = 10;

	public static void main(String[] args) {
		SpringApplication.run(IterApplication.class, args);
	}
}
