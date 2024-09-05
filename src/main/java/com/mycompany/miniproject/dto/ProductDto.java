package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProductDto {
	private int productId;
	private String productName;
	private int productPrice;
	private int productStock;
	private String productSummary;
	private String productDetailSummary;
	private Date productRegDate;
	private String category;
	private String test;
}
