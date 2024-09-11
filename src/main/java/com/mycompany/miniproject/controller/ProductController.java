package com.mycompany.miniproject.controller;

import java.io.OutputStream;
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

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.SearchDto;
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
	    List<ProductImageDto> productImages = productService.getProductImgs(productId);
	    
	    List<String> imageUrls = new ArrayList<>();
	    
	    
	    for(ProductImageDto productImage : productImages) {
	    	String imageUrl = "loadImg?productId=" + productId + "&product_img_usage=" + productImage.getProductImgUsage();
	    	imageUrls.add(imageUrl);
	    }
	    
	    model.addAttribute("product", product);
	    model.addAttribute("imageUrls", imageUrls);
	    
	    return "product/detailpage"; 
	}
	
	@GetMapping("/search")
	public String search(String category, String search, String sort, Model model) {
		log.info("실행");
		SearchDto searchDto = new SearchDto();
		searchDto.setCategory(category);
		searchDto.setSearchContent(search);
		searchDto.setSort(sort);
		List<ProductDto> productList = productService.getSearchProduct(searchDto);
		int searchedRows = productService.getSearchedRows(searchDto);
		model.addAttribute("searchedRows", searchedRows);
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("productList", productList);
		return "product/search";
	}
	
	@GetMapping("/searchProductAll")
	public String searchProductAll(Model model,
				@RequestParam(defaultValue="1")int pageNo,
				HttpSession session, String sort) {
		
		int totalRows = productService.getTotalRows();
		PagerDto pager = new PagerDto(15, 5, totalRows, pageNo, sort);
		session.setAttribute("pager", pager);
		
		List<ProductDto> productList;
		productList = productService.getProducts(pager);
				
		model.addAttribute("productList", productList);
		log.info("실행");
		
		return "product/search";
	}
	
	@GetMapping("loadImg")
	public void loadImg(int productId, String product_img_usage, HttpServletResponse response) throws Exception {
		
		List<ProductImageDto> productImages = productService.getProductImgs(productId);
		
		
		for (ProductImageDto productImage : productImages) {
			String fileUsage = productImage.getProductImgUsage();
				        
	        if(!fileUsage.equals(product_img_usage)) {
	        	String contentType = productImage.getProductImgType();
		        response.setContentType(contentType);
	        		       
	        	
	        	String fileName = productImage.getProductImgName();
		        String encodingFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		        response.setHeader("Content-Disposition", "inline; filename=\"" + encodingFileName + "\"");

		        OutputStream out = response.getOutputStream();
		        out.write(productImage.getProductImg());
		        out.flush();
		        out.close();
	        }
	        
	    }
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
