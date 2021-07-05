package com.cos.instagram.web;

import com.cos.instagram.service.UserService;
import com.cos.instagram.util.Logging;
import com.cos.instagram.web.dto.JoinReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Log4j2
public class AuthController {
	private final UserService userService;
	private final Logging logging;
	
	@GetMapping("/auth/loginForm")
	public String loginForm() {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("/auth/loginForm 진입");

		// 이미 로그인 된 상태라면 리다이렉트
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			return "redirect:/";
		}

		return "auth/loginForm"; 
	}

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("/auth/joinForm 진입");

		return "auth/joinForm";
	}

	@PostMapping("/auth/join")
	public String join(JoinReqDto joinReqDto) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info(joinReqDto.toString());

		userService.register(joinReqDto);
		return "redirect:/auth/loginForm";
	}
}



