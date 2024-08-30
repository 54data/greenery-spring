package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@RequestMapping("/mainadmin")
	public String mainAdmin() {
		log.info("실행");
		return "admin/mainadmin";
	}
	
	@RequestMapping("/noticeadd")
	public String noticeAdd() {
		log.info("실행");
		return "admin/noticeadd";
	}
	
	@RequestMapping("/noticeselect")
	public String noticeSelect() {
		log.info("실행");
		return "admin/noticeselect";
	}
	
	@RequestMapping("/productadd")
	public String productAdd() {
		log.info("실행");
		return "admin/productadd";
	}
	
	@RequestMapping("/productselect")
	public String productSelect() {
		log.info("실행");
		return "admin/productselect";
	}
}
