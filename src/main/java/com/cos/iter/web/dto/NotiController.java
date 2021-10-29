package com.cos.iter.web.dto;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.iter.domain.noti.Noti;
import com.cos.iter.service.NotiService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class NotiController {
	private final NotiService notiService;

	@Value("${azure.blob.url}")
	private String blobStorageUrl;

	@GetMapping("/noti/{loginUserId}")
	public String noti(@PathVariable int loginUserId, Model model) {
		model.addAttribute("notis", notiService.notificationList(loginUserId));
		model.addAttribute("storageUrl", blobStorageUrl);

		return "noti/noti";
	}
	
	@GetMapping("/test/noti/{loginUserId}")
	public @ResponseBody List<Noti> testNoti(@PathVariable int loginUserId, Model model) {
		
		return notiService.notificationList(loginUserId);
	}
}
