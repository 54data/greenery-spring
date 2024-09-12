package com.mycompany.miniproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.service.NoticeService;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private NoticeService noticeService;
	
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
		
		if(!prdAddDto.getProductMainImage().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "main", prdAddDto.getProductMainImage());
		}
		if(!prdAddDto.getProductSub1Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub1", prdAddDto.getProductSub1Image());
		}
		if(!prdAddDto.getProductSub2Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub2", prdAddDto.getProductSub2Image());
		}
		if(!prdAddDto.getProductSub3Image().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "sub3", prdAddDto.getProductSub3Image());
		}
		if(!prdAddDto.getProductDetailImage().isEmpty()) {
			insertProduct(prdAddDto.getCategory(), productId, "detail", prdAddDto.getProductDetailImage());
		}
		return "redirect:/admin/mainadmin";
	}
	
	private void insertProduct(String category, int productId, String usage, MultipartFile mf) throws Exception {
		ProductImageDto imgDto = new ProductImageDto();
		imgDto.setProductId(productId);
		imgDto.setProductImgUsage(usage);
		imgDto.setProductImgName(category + "_" + productId + "_" + usage);
		imgDto.setProductImg(mf.getBytes());
		imgDto.setProductImgType(mf.getContentType());
		productService.insertProductImg(imgDto);
	}
	
	@PostMapping("/addNotice") 
	public String addNotice(NoticeDto noticeForm, HttpSession session) {
		NoticeDto notice = new NoticeDto();
		notice.setNoticeWriter("greenery_admin");
		notice.setNoticeTitle(noticeForm.getNoticeTitle());
		notice.setNoticeContent(noticeForm.getNoticeContent());
		notice.setNoticeHitcount(0);
		noticeService.addNoticeContent(notice);
		return "redirect:/admin/mainadmin";
	}
}
