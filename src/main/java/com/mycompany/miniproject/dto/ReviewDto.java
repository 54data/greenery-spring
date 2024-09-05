package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewDto {
	private int reviewId;
	private int productId;
	private String userid;
	private String reviewContent;
	private Date reviewRegDate;
	private int reviewScore;
	private byte[] reviewImg;
	private String reviewImgType;
	private String reviewImgName;
}
