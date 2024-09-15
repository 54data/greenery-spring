package com.mycompany.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.ProductDao;
import com.mycompany.miniproject.dao.ProductImageDao;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.ProductDto;
import com.mycompany.miniproject.dto.ProductImageDto;
import com.mycompany.miniproject.dto.SearchDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private ProductImageDao productImageDao;
	
	public List<ProductDto> getProducts(PagerDto pager) {
		List<ProductDto> products = productDao.getProductAll(pager);
		return products;
	}
	
	public List<ProductDto> getSearchProduct(SearchDto searchDto){
		List<ProductDto> products = productDao.getSearchProduct(searchDto);
		return products;
	}
	
	public int getSearchedRows(SearchDto searchDto) {
		int searchedRows = productDao.countSearchedProduct(searchDto);
		return searchedRows;
	}

	public ProductImageDto getMainImg(int productId) {
		ProductImageDto productImage = productImageDao.selectImgByProductId(productId);
		return productImage;
	}

	public int getTotalRows() {
		int totalRows = productDao.countRows();
		return totalRows;
	}

	public ProductDto getProductDetail(int productId) {
		ProductDto product = productDao.getProductDetailInfo(productId);
		return product;
	}

	public ProductImageDto getProductImg(int productImgId) {
		ProductImageDto productImage = productImageDao.getImgByProductImgId(productImgId);
		return productImage;
	}
	
	public List<ProductImageDto> getProductImgs(int productId) {
		List<ProductImageDto> productImages = productImageDao.getImgsByProductId(productId);
		return productImages;
	}

	public List<ProductImageDto> getReviewImg(int reviewId) {
		List<ProductImageDto> reviewList = productDao.getReviewImg();
		return reviewList;
	}
	
	public List<ProductDto> getRecList() {
		List<ProductDto> recList = productDao.getRecProduct();
		return recList;
	}

	public List<ProductDto> getNewList() {
		List<ProductDto> newList = productDao.getNewProduct();
		return newList;
	}

	public void insertProduct(ProductAddDto prdAddDto) {
		productDao.insertProduct(prdAddDto);
	}

	public void insertProductImg(ProductImageDto imgDto) {
		productDao.insertProductImg(imgDto);
	}

	public int getProductIdByName(String productName) {
		int productId = productDao.getProductIdByName(productName);
		return productId;
	}

	public void deleteProduct(int productId) {
		productDao.deleteProductImage(productId);
		productDao.deleteProduct(productId);
	}

	public void updateProduct(ProductAddDto prdAddDto) {
		productDao.updateProduct(prdAddDto);
	}

	public ProductAddDto getProductByProductId(int productId) {
		ProductAddDto product = productDao.getProductByProductId(productId);
		return product;
	}

	public int updateProductImage(ProductImageDto imgDto) {
		int result = productDao.updateProductImage(imgDto);
		return result;
	}

	public ProductImageDto getImgByUsage(int productId, String usage) {
		ProductImageDto productImageDto = new ProductImageDto();
		productImageDto.setProductId(productId);
		productImageDto.setProductImgUsage(usage);
		ProductImageDto productImage = productImageDao.getImgByUsage(productImageDto);
		return productImage;
	}

	public void deleteProductImg(int productId, String usage) {
		ProductImageDto productImgDto = new ProductImageDto();
		productImgDto.setProductId(productId);
		productImgDto.setProductImgUsage(usage);
		productImageDao.deleteProductImg(productImgDto);
		
	}
}
