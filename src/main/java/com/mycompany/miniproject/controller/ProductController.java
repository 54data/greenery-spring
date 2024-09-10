package com.mycompany.miniproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.dto.ProductDto;
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
	public String detailpage() {
		log.info("실행");
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
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("productList", productList);
		return "product/search";
	}
}
