package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class CartDto {
	private String userId;
	private int productId;
	private int productQty;
	private String productName;
	private int productPrice;
	private String productSummary;
	private int productStock;
}
