package com.mycompany.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.ProductDao;
import com.mycompany.miniproject.dao.ProductImageDao;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImageDao productImageDao;
	
	public List<ProductDto> getProducts() {
		List<ProductDto> products = productDao.getProductAll();
		return products;
	}

	public ProductImageDto getMainImg(int productId) {
		ProductImageDto productImage = productImageDao.selectImgByProductId(productId);
		return productImage;
	}
}
