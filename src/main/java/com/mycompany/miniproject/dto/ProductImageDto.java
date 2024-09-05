package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class ProductImageDto {
	private int productImgId;
	private int productId;
	private String productImgName;
	private String productImgType;
	private byte[] productImg;
	private String productImgUsage;
}
