package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
	@RequestMapping("/noticeContent")
	public String noticeContent() {
		log.info("실행");
		return "notice/noticeContent";
	}
	
	@RequestMapping("/notices")
	public String notices() {
		log.info("실행");
		return "notice/notices";
	}
}
