package com.cos.instagram.web;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.service.UserService;
import com.cos.instagram.web.dto.UserProfileRespDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser, Model model) {
		UserProfileRespDto userProfileRespDto = userService.회원프로필(id, loginUser);
		model.addAttribute("respDto", userProfileRespDto);
		return "user/profile";
	}
	
	@GetMapping("/user/profileEdit")
	public String profileEdit(
			@LoginUserAnnotation LoginUser loginUser,
			Model model) {
		User userEntity = userService.getUser(loginUser);
		model.addAttribute("user", userEntity);
		return "user/profile-edit";
	}
	
	@PutMapping("/user")
	public ResponseEntity<?> userUpdate(User user){
		userService.modifyUser(user);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	// 원래는 put으로 하는게 맞는데 편하게 하기 위해
	@PostMapping("/user/profileUpload")
	public String userProfileUpload(@RequestParam("profileImage") MultipartFile file,
			int userId,
			@LoginUserAnnotation LoginUser loginUser){
		if(userId == loginUser.getId()) {
			userService.uploadProfile(loginUser, file);
		}
		
		return "redirect:/user/"+userId;
	}
}


