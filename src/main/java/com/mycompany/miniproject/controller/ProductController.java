package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/product")
@Slf4j
public class ProductController {
	@Autowired
	private ProductService productService;

	
    @GetMapping("/detail-info")
    public String showDetailInfo() {
        return "product/detail-info";
    }

    @GetMapping("/reviews-select")
    public String showReviewsSelect() {
        return "product/reviews-select";
    }
    
	@RequestMapping("/detailpage")
	public String detailpage(@RequestParam int productId, Model model) throws Exception  {
	    ProductDto product = productService.getProductDetail(productId);  
	    ProductImageDto productImage = productService.getMainImg(productId);
	    
	    model.addAttribute("product", product);
	    model.addAttribute("productImage", productImage);
	    
	    return "product/detailpage"; 
	}
	
	@RequestMapping("/search")
	public String search() {
		log.info("실행");
		return "product/search";
	}
	
//	@GetMapping("loadImg")
//	public void loadImg(int productId, HttpServletResponse response) throws Exception {
//		
//		List<ProductImageDto> productImages = productService.getProductImgs(productId);
//		
//		for (ProductImageDto productImage : productImages) {
//	        String contentType = productImage.getProductImgType();
//	        response.setContentType(contentType);
//	        
//	        String fileName = productImage.getProductImgName();
//	        String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
//	        response.setHeader("Content-Disposition", "inline; filename=\"" + encodingFileName + "\"");
//
//	        OutputStream out = response.getOutputStream();
//	        out.write(productImage.getProductImg());
//	        out.flush();
//	        out.close();
//	    }
//	}

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
