package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.SearchDto;

@Mapper
public interface ProductDao {

	public List<ProductDto> getProductAll(PagerDto pager);
	public List<ProductDto> getSearchProduct(SearchDto searchDto);
	public int countRows();
	public int countSearchedProduct(SearchDto searchDto);
	public ProductDto getProductDetailInfo(int productId);
	public List<ProductDto> getRecProduct();
	public List<ProductDto> getNewProduct();
	public int insertProduct(ProductAddDto prdAddDto);
	public int getProductIdByName(String productName);
	public int insertProductImg(ProductImageDto imgDto);
	public int deleteProduct(int productId);
	public int deleteProductImage(int productId);

}
