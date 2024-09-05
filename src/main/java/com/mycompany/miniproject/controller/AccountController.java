package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/account")
@Controller
public class AccountController {
	@RequestMapping("/login")
	public String login() {
		log.info("로그인 성공");
		return "account/login";
	}
	
	@RequestMapping("/signup")
	public String signup() {
		log.info("회원가입");
		return "account/signup";
	}
}
