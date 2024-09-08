package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.ProductDto;

@Mapper
public interface ProductDao {

	public List<ProductDto> getProductAll();

}
