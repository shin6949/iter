package com.cos.iter.web.dto;

import com.cos.iter.domain.post.Post;
import org.springframework.web.multipart.MultipartFile;

import com.cos.iter.domain.image.Image;

import lombok.Data;

@Data
public class ImageReqDto {
	private MultipartFile file;
	private String content;
	private float latitude;
	private float longitude;
	private String tags;
	
	public Image toImageEntity(String imageUrl, Post postEntity) {
		return Image.builder()
				.latitude(latitude)
				.longitude(longitude)
				.url(imageUrl)
				.post(postEntity)
				.build();
	}

	public Post toPostEntity() {
		return Post.builder()
				.content(content)
				.build();
	}
}





