package com.cos.iter.util;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log4j2
@Repository
public class TagParser {
	// 참고: https://bluepoet.me/2012/02/15/java%EC%A0%95%EA%B7%9C%EC%8B%9D-%EC%82%AC%EC%9A%A9%ED%95%98%EC%97%AC-%ED%8A%B8%EC%9C%84%ED%84%B0-%ED%95%B4%EC%8B%9C%ED%83%9C%EA%B7%B8-%EC%B6%94%EC%B6%9C%EA%B8%B0%EB%8A%A5-%EB%A7%8C%EB%93%A4%EA%B8%B0
	public List<String> tagParse(String tags){
		Pattern pattern = Pattern.compile("\\#([0-9a-zA-Z가-힣-_]*)");
		Matcher matcher = pattern.matcher(tags);
		List<String> extractHashTag = new ArrayList<>();

		while(matcher.find()) {
			extractHashTag.add(specialCharacterReplace(matcher.group()));
		}

		log.info("Result: " + extractHashTag);
		return extractHashTag;
	}

	private String specialCharacterReplace(String str) {
		str = StringUtils.replaceChars(str, "-+=!@#$%^&*()[]{}|\\;:'\"<>,.?/~`） ","");

		if(str.length() < 1) {
			return null;
		}

		return str;
	}
}
