package com.cos.iter.web;

import java.util.List;

import com.cos.iter.util.Logging;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.iter.config.auth.LoginUserAnnotation;
import com.cos.iter.config.auth.dto.LoginUser;
import com.cos.iter.domain.image.Image;
import com.cos.iter.service.ImageService;
import com.cos.iter.web.dto.ImageReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@Log4j2
public class ImageController {
	private final ImageService imageService;
	private final Logging logging;

	@GetMapping({"", "/", "/image/feed"})
	public String feed(String tag, @LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());
		log.info("loginUser : "+ loginUser);

		model.addAttribute("images", imageService.feedPhoto(loginUser.getId(), tag));
		return "image/feed";
	}
	
	@GetMapping("/test/image/feed")
	public @ResponseBody List<Image> testFeed(String tag, @LoginUserAnnotation LoginUser loginUser) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		return imageService.feedPhoto(loginUser.getId(), tag);
	}
	
	@GetMapping("/image/uploadForm")
	public String imageUploadForm(@RequestParam(name = "location", required = false) String location, Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		if(location == null) {
			log.info("Redirecting to location find page");
			return "redirect:/location/find";
		}

		model.addAttribute("location", location);
		return "image/image-upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(@LoginUserAnnotation LoginUser loginUser, ImageReqDto imageReqDto) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		imageService.photoUpload(imageReqDto, loginUser.getId());
		return "redirect:/user/" + loginUser.getId();
	}
	
	@GetMapping("/image/explore")
	public String imageExplore(@LoginUserAnnotation LoginUser loginUser, Model model) {
		log.info(logging.getClassName() + " / " + logging.getMethodName());

		model.addAttribute("images", imageService.popularPhoto(loginUser.getId()));
		return "image/explore";
		
	}
}




