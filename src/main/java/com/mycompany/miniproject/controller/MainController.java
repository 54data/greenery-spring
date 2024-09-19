package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@GetMapping("recieveCoupon")
	public ResponseEntity<String> recieveCoupon(Authentication authentication) {
		if (authentication != null) {
			String couponStatus = "" + userService.getUserCouponStatus(authentication.getName());
			if (couponStatus.equals("0")) {
				userService.updateCouponStatus(1, authentication.getName());
				log.info("쿠폰발급");
				return ResponseEntity.ok(couponStatus);
			} else if (couponStatus.equals("1")) {
				log.info("이미발급 받음");
				return ResponseEntity.ok(couponStatus);
			} else if (couponStatus.equals("-1")) {			
				log.info("이미 사용함");
				return ResponseEntity.ok(couponStatus);
			}		
		} else {
			// 인증되지 않은 경우 401 상태 반환
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 하지 않은 상태");
		};
		
		// 기본적으로 처리되지 않은 경우
	    return ResponseEntity.badRequest().body("알 수 없는 오류");
	}
}
