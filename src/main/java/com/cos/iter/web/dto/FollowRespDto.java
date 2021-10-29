package com.cos.iter.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowRespDto {
	private int id;
	private String username;
	private String name;
	private String profileImage;
	private boolean followState;
	private boolean equalUserState;

	public String getProfileImage() {
		final String blogStorageUrl = System.getenv("AZURE_BLOB_URL");

		return blogStorageUrl + "/profile/" + profileImage;
	}
}
