package com.mycompany.miniproject.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductAddDto {
	private String productName;
	private int productPrice;
	private int productStock;
	private MultipartFile productMainImage;
	private MultipartFile productSub1Image;
	private MultipartFile productSub2Image;
	private MultipartFile productSub3Image;
	private String productSummary;
	private String productDetailSummary;
	private MultipartFile productDetailImage;
	private String category;
}
