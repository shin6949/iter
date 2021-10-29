package com.cos.iter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfilePostRespDto {
	private int id;
	private String imageUrl;
	private int likeCount;
	private int commentCount;

	public String getImageUrl() {
		final String blogStorageUrl = System.getenv("AZURE_BLOB_URL");

		return blogStorageUrl + "/photo/" + imageUrl;
	}
}
