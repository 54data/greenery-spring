package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@RequestMapping("/mainAdmin")
	public String mainAdmin() {
		log.info("실행");
		return "admin/mainAdmin";
	}
	
	@RequestMapping("/noticeAdd")
	public String noticeAdd() {
		log.info("실행");
		return "admin/noticeAdd";
	}
	
	@RequestMapping("/noticeSelect")
	public String noticeSelect() {
		log.info("실행");
		return "admin/noticeSelect";
	}
	
	@RequestMapping("/productAdd")
	public String productAdd() {
		log.info("실행");
		return "admin/productAdd";
	}
	
	@RequestMapping("/productSelect")
	public String productSelect() {
		log.info("실행");
		return "admin/productSelect";
	}
}
