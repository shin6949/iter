package com.cos.instagram.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
public class LocationController {

    @GetMapping("/find")
    public String findLocation() {
        return "location/find";
    }
}
