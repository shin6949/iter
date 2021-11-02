package com.cos.iter.web.dto;

import com.cos.iter.domain.post.Post;

import com.cos.iter.domain.image.Image;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

@Data
public class ImageReqDto {
	private MultipartHttpServletRequest file;
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

	public List<MultipartFile> getMultipartFiles() {
		return file.getFiles("input_img");
	}
}





