package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ProductAddDto;
import com.mycompany.miniproject.dto.WishlistDto;

@Mapper
public interface WishlistDao {

	public void insertWishlist(WishlistDto wishlist);

	public WishlistDto getWishlist(WishlistDto wishlistDto);

	public int deleteWishlist(WishlistDto wishlist);

	public List<ProductAddDto> getWishlistAll(PagerDto pager);

	public int deleteWishlistByProductId(int productId);

	public int getTotalWishlistRows(String userId);

	public List<Integer> getUserWishlist(String userId);

}
