package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.SearchDto;

@Mapper
public interface ProductDao {

	public List<ProductDto> getProductAll(PagerDto pager);
	public List<ProductDto> getSearchProduct(SearchDto searchDto);
	public int countRows();
	public ProductDto getProductDetailInfo(int productId);

}
