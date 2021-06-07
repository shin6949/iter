package com.cos.instagram.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.cos.instagram.domain.comment.Comment;
import com.cos.instagram.domain.image.Image;
import com.cos.instagram.domain.image.ImageRepository;
import com.cos.instagram.domain.like.Likes;
import com.cos.instagram.domain.tag.Tag;
import com.cos.instagram.domain.tag.TagRepository;
import com.cos.instagram.domain.user.User;
import com.cos.instagram.domain.user.UserRepository;
import com.cos.instagram.util.Utils;
import com.cos.instagram.web.dto.ImageReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ImageService {

	private final ImageRepository imageRepository;
	private final TagRepository tagRepository;
	private final UserRepository userRepository;
	private final AzureService azureService;
	
	@Transactional(readOnly = true)
	public List<Image> feedPhoto(int loginUserId, String tag){
		List<Image> images = null;
		if(tag == null || tag.equals("")) {
			images = imageRepository.mFeeds(loginUserId);
		}else {
			images = imageRepository.mFeeds(tag);
		}
		
		for (Image image : images) {
			image.setLikeCount(image.getLikes().size());
			
			// 좋아요 상태 여부 등록
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

//		Path imageFilepath = Paths.get(uploadFolder + imageFilename);
//		try {
//			Files.write(imageFilepath, imageReqDto.getFile().getBytes());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

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