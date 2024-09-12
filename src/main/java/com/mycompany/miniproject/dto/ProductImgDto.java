package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class ProductImgDto {
	private int productImgId;
	private int productId;
	private String imgName;
	private byte[] productImg;
	private String productImgType;
	private String productImgUsage;
	
}
