package com.cos.iter.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
public class TagParserTest {
    @Test
    @DisplayName("Tag Parsing Test")
    public void tagParseTest() {
        final TagParser tagParser = new TagParser();
        final String tags = "#cube, #테스트 안녕하세요 #후후";

        List<String> expectedResult = new ArrayList<>(Arrays.asList("cube", "테스트", "후후"));
        assertEquals(tagParser.tagParse(tags).toString(), expectedResult.toString());
    }
}
