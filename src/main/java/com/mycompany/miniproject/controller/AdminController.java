package com.mycompany.miniproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductImgDto;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@Autowired
	ProductService productService;
	
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
	
	@PostMapping("/productInsert")
	public String productInsert(ProductAddDto prdAddDto) throws Exception{
		log.info("실행");
		productService.insertProduct(prdAddDto);
		int productId = productService.getProductIdByName(prdAddDto.getProductName());
		
		for(int i=1; i<=5; i++) {
			ProductImgDto imgDto = new ProductImgDto();
			MultipartFile mf = prdAddDto.getProductMainImage();
			imgDto.setProductId(productId);
			imgDto.setImgName(prdAddDto.getCategory() + "_" + productId + "_main");
			imgDto.setProductImg(mf.getBytes());
			imgDto.setProductImgType(mf.getContentType());
			imgDto.setProductImgUsage("main");
			
			productService.insertProductImg(imgDto);
		}
			
		
		return "redirect:/admin/mainadmin";
	}
}
