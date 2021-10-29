package com.cos.iter.web;

import com.cos.iter.util.Logging;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/location")
@Log4j2
public class LocationController {
    private final Logging logging;

    @Value("${azure.blob.url}")
    private String blobStorageUrl;
    
    @GetMapping("/find")
    public String findLocation(Model model) {
        log.info(logging.getClassName() + " / " + logging.getMethodName());
        model.addAttribute("storageUrl", blobStorageUrl);

        return "location/find";
    }
}
