package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.ProductImageDto;

@Mapper
public interface ProductImageDao {
	public ProductImageDto selectImgByProductId(int productId);
	
	public ProductImageDto getImgByProductId(int productImgId);

	public List<ProductImageDto> getImgsByProductId(int productId);

}
