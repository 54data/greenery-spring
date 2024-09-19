package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.service.ProductService;
import com.mycompany.miniproject.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
	private ProductService productService;
	@Autowired
	private UserService userService;
	
	@RequestMapping("")
	public String main(Model model) {
		List<ProductDto> recProducts = productService.getRecList();
		List<ProductDto> newProducts = productService.getNewList();
		log.info("실행");
		model.addAttribute("recProducts", recProducts);
		model.addAttribute("newProducts", newProducts);
		
		return "main";
	}
	
	@GetMapping("loadMainImg")
	public void loadMainImg(int productId, HttpServletResponse response) throws Exception {
		ProductImageDto productImage = productService.getMainImg(productId);
		
		String contentType = productImage.getProductImgType();
		response.setContentType(contentType);
		
		String fileName = productImage.getProductImgName();
		String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodingFileName + "\"");		

		OutputStream out = response.getOutputStream();
		out.write(productImage.getProductImg());
		out.flush();
		out.close();
	}
	
	@Secured("ROLE_user")
	@GetMapping("recieveCoupon")
	public String recieveCoupon(Authentication authentication) {
		int couponStatus = userService.getUserCouponStatus(authentication.getName());
		if(couponStatus == 0) {
			userService.updateCouponStatus(1, authentication.getName());
			log.info("쿠폰발급");
		}
		if(couponStatus == 1) {
			log.info("이미발급 받음");
		}
		if(couponStatus == -1) {			
			log.info("이미 사용함");
		}
		return "redirect:/";
	}

}
