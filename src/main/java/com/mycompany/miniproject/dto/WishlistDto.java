package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class WishlistDto {
	private int wishlistId;
	private String userId;
	private int productId;
}
