package com.cos.iter.service;

import java.util.function.Supplier;

import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.post.PostRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.iter.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.iter.domain.comment.CommentRepository;
import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.image.ImageRepository;
import com.cos.iter.domain.noti.NotiRepository;
import com.cos.iter.domain.noti.NotiType;
import com.cos.iter.web.dto.CommentRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Log4j2
public class CommentService {
	private final CommentRepository commentRepository;
	private final NotiRepository notiRepository;
	private final PostRepository postRepository;
	
	@Transactional
	public void writeComment(CommentRespDto commentRespDto) {
		commentRepository.mSave(
				commentRespDto.getUserId(), 
				commentRespDto.getPostId(),
				commentRespDto.getContent());
		Post postEntity = postRepository.findById(commentRespDto.getPostId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(commentRespDto.getUserId(), postEntity.getUser().getId(), NotiType.COMMENT.name());
	}
	
	@Transactional
	public void deleteComment(int id) {
		commentRepository.deleteById(id);
	}
}
