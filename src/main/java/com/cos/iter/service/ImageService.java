package com.cos.iter.service;

import com.cos.iter.domain.comment.Comment;
import com.cos.iter.domain.image.Image;
import com.cos.iter.domain.image.ImageRepository;
import com.cos.iter.domain.like.Likes;
import com.cos.iter.domain.tag.Tag;
import com.cos.iter.domain.tag.TagRepository;
import com.cos.iter.domain.user.User;
import com.cos.iter.domain.user.UserRepository;
import com.cos.iter.util.Logging;
import com.cos.iter.util.Utils;
import com.cos.iter.web.dto.ImageReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class ImageService {
	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;
	private final AzureService azureService;
	private final Logging logging;
	
	@Transactional(readOnly = true)
	public List<Image> feedPhoto(int loginUserId, String tag){
		List<Image> images = null;
		if(tag == null || tag.equals("")) {
			images = imageRepository.mFeeds(loginUserId);
		} else {
			images = imageRepository.mFeeds(tag);
		}

		log.info(logging.getClassName() + " / " + logging.getMethodName());
		for (Image image : images) {
			image.setLikeCount(image.getLikes().size());
			log.info(image.getCreateDateString());
			
			// doLike 상태 여부 등록
			for (Likes like : image.getLikes()) {
				if(like.getUser().getId() == loginUserId) {
					image.setLikeState(true);
				}
			}
			// 댓글 주인 여부 등록
			for (Comment comment : image.getComments()) {
				if(comment.getUser().getId() == loginUserId) {
					comment.setCommentHost(true);
				}
			}
		}

		return images;
	}
	
	@Transactional(readOnly = true)
	public List<Image> popularPhoto(int loginUserId) {
		return imageRepository.mNonFollowImage(loginUserId);
	}

	@Transactional
	public void photoUpload(ImageReqDto imageReqDto, int userId) {
		User userEntity = userRepository.findById(userId).
				orElseThrow(null);

		String imageFilename = "";

		try {
			imageFilename = azureService.uploadToCloudAndReturnFileName(imageReqDto.getFile(), "photo");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 1. Image 저장
		Image image = imageReqDto.toEntity(imageFilename, userEntity);
		Image imageEntity = imageRepository.save(image);
		
		// 2. Tag 저장
		List<String> tagNames = Utils.tagParse(imageReqDto.getTags());
		for (String name : tagNames) {
			Tag tag = Tag.builder()
					.image(imageEntity)
					.name(name)
					.build();
			tagRepository.save(tag);
		}
	}
}