package com.cos.iter.web;

import com.cos.iter.util.Logging;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.iter.config.auth.LoginUserAnnotation;
import com.cos.iter.config.auth.dto.LoginUser;
import com.cos.iter.service.LikesService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@Log4j2
public class LikeController {
	private final LikesService likesService;
	private final Logging logging;
	
	@PostMapping("/likes/{imageId}")
	public ResponseEntity<?> like(@PathVariable int imageId, @LoginUserAnnotation LoginUser loginUser) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		likesService.doLike(imageId, loginUser.getId());
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@DeleteMapping("/likes/{imageId}")
	public ResponseEntity<?> unLike(@PathVariable int imageId, @LoginUserAnnotation LoginUser loginUser) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		likesService.doUnlike(imageId, loginUser.getId());
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}
