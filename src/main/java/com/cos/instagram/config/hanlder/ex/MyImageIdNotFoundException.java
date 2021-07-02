package com.cos.instagram.config.hanlder.ex;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MyImageIdNotFoundException extends RuntimeException{
	private final String message;
	
	public MyImageIdNotFoundException() {
		this.message = "해당 이미지의 id를 찾을 수 없습니다.";
	}

	@Override
	public String getMessage() {
		return message;
	}
	
}
