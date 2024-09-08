package com.mycompany.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
	ProductService productService;
	@RequestMapping("")
	public String main(Model model) {
		List<ProductDto> productList = productService.getProducts();
		model.addAttribute("productList", productList);
		log.info("실행");
		
		return "main";
	}
}
