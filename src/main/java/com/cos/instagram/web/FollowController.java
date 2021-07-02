package com.cos.instagram.web;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.instagram.config.auth.LoginUserAnnotation;
import com.cos.instagram.config.auth.dto.LoginUser;
import com.cos.instagram.service.FollowService;
import com.cos.instagram.web.dto.FollowRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@Log4j2
public class FollowController {
	private final FollowService followService;

	private final String controllerName = "FollowController / ";
	
	@GetMapping("/follow/followingList/{pageUserId}")
	public String followingList(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(controllerName + "followingList");

		model.addAttribute("followingList", followService.getFollowingList(loginUser.getId(), pageUserId));
		return "follow/following-list";
	}
	
	@GetMapping("/follow/followerList/{pageUserId}")
	public String followerList(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(controllerName + "followerList");

		model.addAttribute("followerList", followService.getFollowerList(loginUser.getId(), pageUserId));
		return "follow/follower-list";
	}
	
	@GetMapping("test/follow/followingList/{pageUserId}")
	public @ResponseBody List<FollowRespDto> testFollowingList(@PathVariable int pageUserId, @LoginUserAnnotation LoginUser loginUser) {
		log.info(controllerName + "testFollowingList");

		return followService.getFollowingList(loginUser.getId(), pageUserId);
	}
	
	@PostMapping("/follow/{id}")
	public ResponseEntity<?> follow(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser) {
		log.info(controllerName + "follow");

		followService.doFollow(loginUser.getId(), id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/follow/{id}")
	public ResponseEntity<?> unFollow(@PathVariable int id, @LoginUserAnnotation LoginUser loginUser) {
		log.info(controllerName + "unFollow");
		
		followService.doUnFollow(loginUser.getId(), id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
