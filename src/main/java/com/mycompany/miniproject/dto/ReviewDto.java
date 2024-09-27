package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewDto {
	private int reviewId;
	private int productId;
	private int orderId;
	private String userId;
	private String reviewContent;
	private Date reviewRegDate;
	private int reviewScore;
	private byte[] reviewImg;
	private String reviewImgType;
	private String reviewImgName;
	private OrderDetailDto orderDetail;
	private int reviewRecom;
}
