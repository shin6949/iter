package com.cos.iter.web;

import com.cos.iter.service.UserService;
import com.cos.iter.util.Logging;
import com.cos.iter.web.dto.JoinReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
@Log4j2
public class AuthController {
	private final UserService userService;
	private final Logging logging;

	@Value("${azure.blob.url}")
	private String blobStorageUrl;
	
	@GetMapping("/auth/loginForm")
	public String loginForm(Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("/auth/loginForm 진입");

		// 이미 로그인 된 상태라면 리다이렉트
		if(SecurityContextHolder.getContext().getAuthentication().getPrincipal() != "anonymousUser") {
			return "redirect:/";
		}

		model.addAttribute("storageUrl", blobStorageUrl);
		return "auth/loginForm"; 
	}

	@GetMapping("/auth/joinForm")
	public String joinForm(Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("/auth/joinForm 진입");

		model.addAttribute("storageUrl", blobStorageUrl);
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



