package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class OrderDetailDto {
	private int orderId;
	private int productId;
	private int productQty;
	private int productPrice;
}
