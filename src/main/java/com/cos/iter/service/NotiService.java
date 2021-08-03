package com.cos.iter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.iter.domain.noti.Noti;
import com.cos.iter.domain.noti.NotiRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class NotiService {
	private final NotiRepository notiRepository;
	
	@Transactional(readOnly = true)
	public List<Noti> notificationList(int loginUserId){
		return notiRepository.findByToUserId(loginUserId);
	}
}
