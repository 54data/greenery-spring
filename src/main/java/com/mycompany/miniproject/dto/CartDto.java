package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class Cart {
	private String userId;
	private int productId;
	private int productQty;
}
