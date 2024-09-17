package com.mycompany.miniproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.dto.OrderDetailDto;
import com.mycompany.miniproject.interceptor.LoginCheck;
import com.mycompany.miniproject.service.OrderDetailService;
import com.mycompany.miniproject.service.ProductService;
import com.mycompany.miniproject.service.ReviewService;
import com.mycompany.miniproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/mypage")
@Slf4j
public class MypageController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderDetailService orderDetailService;
	
	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;
	
	@RequestMapping("/editMyInfo")
	public String editMyInfo() {
		log.info("실행");
		return "mypage/editMyInfo";
	}
	
	@RequestMapping("/likedProducts")
	public String likedProducts(HttpSession session) {
		log.info("실행");
		String userId = session.getId();
		log.info("아이디는?" + userId);
		
		return "mypage/likedProducts";
	}
	
	@LoginCheck
	@RequestMapping("/mypage")
	public String mypage() {
		log.info("실행");
		return "mypage/mypage";
	}
	
	@RequestMapping("/orderList")
	public String orderList(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			userId = userDetails.getUsername(); 
		}
		
		List<OrderDetailDto> orderDetails = orderDetailService.getOrderDetails(userId);
		model.addAttribute("orderDetails", orderDetails);

		log.info(userId);
		
		log.info("실행");
		return "mypage/orderList";
	}
	
	@RequestMapping("/reviews")
	public String reviews() {
		log.info("실행");
		return "mypage/reviews";
	}
}
