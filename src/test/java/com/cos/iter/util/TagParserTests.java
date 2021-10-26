package com.cos.iter.util;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Log4j2
public class TagParserTests {
    @Autowired
    private TagParser tagParser;

    @Test
    public void tagParseTest() {
        final String tags = "#cube, #테스트 안녕하세요 #후후";
        log.info(tagParser.tagParse(tags));
    }
}
