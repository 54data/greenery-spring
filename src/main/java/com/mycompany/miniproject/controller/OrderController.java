package com.mycompany.miniproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.interceptor.LoginCheck;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/order")
@Slf4j
public class OrderController {
	@LoginCheck
	@RequestMapping("/basket")
	public String basket() {
		log.info("실행");
		return "order/basket";
	}
	
	@RequestMapping("/order")
	public String order() {
		log.info("실행");
		return "order/order";
	}
	
	@RequestMapping("/payment")
	public String payment() {
		log.info("실행");
		return "order/payment";
	}
}
