package com.cos.instagram.web;

import com.cos.instagram.util.Logging;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.instagram.service.CommentService;
import com.cos.instagram.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@Log4j2
public class CommentController {
	private final CommentService commentService;
	private final Logging logging;
	
	@PostMapping("/comment")
	public ResponseEntity<?> comment(CommentRespDto commentRespDto) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		commentService.writeComment(commentRespDto);
		return new ResponseEntity<String>("ok", HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		commentService.deleteComment(id);
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
}



