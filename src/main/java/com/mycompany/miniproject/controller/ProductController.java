package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {
	@RequestMapping("/detailpage")
	public String detailpage() {
		log.info("실행");
		return "product/detailpage";
	}
	
	@RequestMapping("/search")
	public String search() {
		log.info("실행");
		return "product/search";
	}
}
