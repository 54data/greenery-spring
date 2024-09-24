package com.mycompany.miniproject.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductAddDto {
	private int productId;
	private String productName;
	private int productPrice;
	private int productStock;
	private String productSummary;
	private String productDetailSummary;
	private String category;
	private MultipartFile productMainImage;
	private MultipartFile productSub1Image;
	private MultipartFile productSub2Image;
	private MultipartFile productSub3Image;
	private MultipartFile productDetailImage;
	private String userId;
}
