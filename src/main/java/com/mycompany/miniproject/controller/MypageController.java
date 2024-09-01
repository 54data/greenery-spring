package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.interceptor.LoginCheck;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@Slf4j
public class MypageController {
	@RequestMapping("/editMyInfo")
	public String editMyInfo() {
		log.info("실행");
		return "mypage/editMyInfo";
	}
	
	@RequestMapping("/likedProducts")
	public String likedProducts() {
		log.info("실행");
		return "mypage/likedProducts";
	}
	
	@LoginCheck
	@RequestMapping("/mypage")
	public String mypage() {
		log.info("실행");
		return "mypage/mypage";
	}
	
	@RequestMapping("/orderList")
	public String orderList() {
		log.info("실행");
		return "mypage/orderList";
	}
	
	@RequestMapping("/reviews")
	public String reviews() {
		log.info("실행");
		return "mypage/reviews";
	}
}
