package com.mycompany.miniproject.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ReviewDataDto {
	private int productId;
	private int orderId;
	private String userId;
	private String reviewContent;
	private int reviewScore;
	private MultipartFile reviewImg;
	private int reviewId;
}
