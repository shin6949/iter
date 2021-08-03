package com.cos.iter.web.dto;

import lombok.Data;

@Data
public class CommentRespDto {
	private String content;
	private int userId;
	private int imageId;
}
