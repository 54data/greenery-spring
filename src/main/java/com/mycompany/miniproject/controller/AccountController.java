package com.mycompany.miniproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.miniproject.dto.UserDto;
import com.mycompany.miniproject.service.UserService;
import com.mycompany.miniproject.service.UserService.JoinResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/account")
@Controller
public class AccountController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/loginForm")
	public String loginForm() {
		return "account/loginForm";
	}
	
	@GetMapping("/signupForm")
	public String signupForm() {
		return "account/signupForm";
	}
	
	@PostMapping("/userIdCheck")
	@ResponseBody
	public boolean userIdCheck(@RequestParam("userId") String userId, Model model) {
		JoinResult joinResult = userService.userIdCheck(userId);
	    return joinResult != JoinResult.FAIL_DUPLICATED_USERID;
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public boolean signup(@ModelAttribute UserDto user) {
		user.setUserStatus(true);
		PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		user.setUserPwd(passwordEncoder.encode(user.getUserPwd()));
		JoinResult joinResult = userService.join(user);
		return joinResult == JoinResult.SUCCESS;
	}
}