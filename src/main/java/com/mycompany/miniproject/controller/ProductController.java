package com.mycompany.miniproject.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	    List<ProductImageDto> productImages = productService.getImgsByProductId(productId);
	    
	    model.addAttribute("product", product);
	    model.addAttribute("productImages", productImages);  
	    
	    return "product/detailpage"; 
	}
	
	@GetMapping("/loadProductImage")
    public void loadProductImage(int productImgId, HttpServletResponse response) throws Exception {
		ProductImageDto productImage = productService.getImgByProductId(productImgId);
		
		Map<Integer, byte[]> map = new HashMap<>();
		
		map.put(productImgId, productImage.getProductImg());
		
		OutputStream out = response.getOutputStream();
        out.write(map.get(productImgId));
        out.flush(); 
        out.close();
               
    }

	@GetMapping("/search")
	public String search(String category, String search, String sort, Model model) {
		log.info("실행");
		SearchDto searchDto = new SearchDto();
		searchDto.setCategory(category);
		searchDto.setSearchContent(search);
		searchDto.setSort(sort);
		List<ProductDto> productList = productService.getSearchProduct(searchDto);
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("productList", productList);
		return "product/search";
	}

	

}
