package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductDto;
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
	public String productAdd(String pageUsage) {
		log.info("실행");
		return "admin/productadd";
	}
	
	@GetMapping("/productselect")
	public String productselect(Model model,
				@RequestParam(defaultValue="1")int pageNo,
				HttpSession session) {
		
		int totalRows = productService.getTotalRows();
		PagerDto pager = new PagerDto(10, 5, totalRows, pageNo);
		session.setAttribute("pager", pager);
		
		List<ProductDto> productList;
		productList = productService.getProducts(pager);
				
		model.addAttribute("productList", productList);
		log.info("실행");
		 log.info("Product List: " + productList);
		
		return "admin/productselect";
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
	
	@GetMapping("/updateForm")
	public String updateForm(int productId, String pageUsage, Model model) {
		ProductDto product = productService.getProductByProductId(productId);
		model.addAttribute("product", product);
		return "admin/productadd";
	}
	
	@PostMapping("/updateProduct")
	public String updateProduct(ProductAddDto prdAddDto) {
		log.info("실행");
		if(prdAddDto.getProductMainImage().isEmpty()) {
			productService.updateProduct(prdAddDto);
			
		}
		return "redirect:/admin/mainadmin";
	}
	
	@GetMapping("/deleteProduct")
	public String deleteProduct(int productId) {
		productService.deleteProduct(productId);
		return "redirect:/admin/mainadmin";
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
