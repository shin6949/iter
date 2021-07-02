package com.cos.instagram.web;

import com.cos.instagram.util.Logging;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
@Log4j2
public class LocationController {
    private final Logging logging;

    @GetMapping("/find")
    public String findLocation() {
        log.info(logging.getClassName() + " / " + logging.getMethodName());

        return "location/findlocation";
    }
}
