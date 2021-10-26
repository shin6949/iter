package com.cos.iter.web;

import com.cos.iter.config.auth.LoginUserAnnotation;
import com.cos.iter.config.auth.dto.LoginUser;
import com.cos.iter.domain.user.User;
import com.cos.iter.service.UserService;
import com.cos.iter.util.Logging;
import com.cos.iter.web.dto.UserProfileRespDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Log4j2
public class UserController {
	private final UserService userService;
	private final Logging logging;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("loginUser : " + loginUser);

		UserProfileRespDto userProfileRespDto = userService.memberProfile(id, loginUser);
		model.addAttribute("respDto", userProfileRespDto);
		log.info("UserProfileRespDto: " + userProfileRespDto);

		return "user/profile";
	}
	
	@GetMapping("/user/profileEdit")
	public String profileEdit(@LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		User userEntity = userService.getUser(loginUser);
		model.addAttribute("user", userEntity);
		return "user/profile-edit";
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> userUpdate(User user) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		userService.modifyUser(user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 원래는 put으로 하는게 맞는데 편하게 하기 위해
	@PostMapping("/user/profileUpload")
	public String userProfileUpload(@RequestParam("profileImage") MultipartFile file, int userId,
									@LoginUserAnnotation LoginUser loginUser) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		if(userId == loginUser.getId()) {
			userService.uploadProfile(loginUser, file);
		}
		
		return "redirect:/user/" + userId;
	}
}


