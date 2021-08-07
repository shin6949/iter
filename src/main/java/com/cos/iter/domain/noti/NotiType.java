package com.cos.iter.domain.noti;

import lombok.Getter;

@Getter
public enum NotiType {
	LIKE("doLike"), COMMENT("댓글작성"), FOLLOW("doFollow");
	
	NotiType(String key) {
		this.key = key;
	}
	
	private String key;
}
