package com.cos.iter.service;

import java.util.function.Supplier;

import com.cos.iter.domain.post.Post;
import com.cos.iter.domain.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.iter.config.hanlder.ex.MyImageIdNotFoundException;
import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.image.ImageRepository;
import com.cos.iter.domain.like.LikeRepository;
import com.cos.iter.domain.noti.NotiRepository;
import com.cos.iter.domain.noti.NotiType;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikeRepository likeRepository;
	private final NotiRepository notiRepository;
	private final ImageRepository imageRepository;
	private final PostRepository postRepository;
	
	@Transactional
	public void doLike(int postId, int loginUserId) {
		likeRepository.mSave(postId, loginUserId);
		Post postEntity = postRepository.findById(postId).orElseThrow(new Supplier<MyImageIdNotFoundException>() {
			@Override
			public MyImageIdNotFoundException get() {
				return new MyImageIdNotFoundException();
			}
		});
		notiRepository.mSave(loginUserId, postEntity.getUser().getId(), NotiType.LIKE.name());
	}
	
	@Transactional
	public void doUnlike(int imageId, int loginUserId) {
		likeRepository.mDelete(imageId, loginUserId);
	}
}
