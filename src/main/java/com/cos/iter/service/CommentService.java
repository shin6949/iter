package com.cos.iter.service;

import java.util.function.Supplier;

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
public class CommentService {
	private final CommentRepository commentRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	
	@Transactional
	public void writeComment(CommentRespDto commentRespDto) {
		commentRepository.mSave( 
				commentRespDto.getUserId(), 
				commentRespDto.getImageId(), 
				commentRespDto.getContent());
		Image imageEntity = imageRepository.findById(commentRespDto.getImageId()).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(commentRespDto.getUserId(), imageEntity.getUser().getId(), NotiType.COMMENT.name());
	}
	
	@Transactional
	public void deleteComment(int id) {
		commentRepository.deleteById(id);
	}
}
