package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.SearchDto;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class MainController {
	@Autowired
	ProductService productService;
	
	@RequestMapping("")
	public String main(Model model,
				@RequestParam(defaultValue="1")int pageNo,
				HttpSession session, String category) {
		
		int totalRows = productService.getTotalRows();
		PagerDto pager = new PagerDto(15, 5, totalRows, pageNo);
		session.setAttribute("pager", pager);
		
		List<ProductDto> productList;
				if(category == null) {
					productList = productService.getProducts(pager);
				}else {
					SearchDto searchDto = new SearchDto();
					searchDto.setCategory(category);
					productList = productService.getSearchProduct(searchDto);
					model.addAttribute("category", category);
				};
		model.addAttribute("productList", productList);
		log.info("실행");
		
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
}
