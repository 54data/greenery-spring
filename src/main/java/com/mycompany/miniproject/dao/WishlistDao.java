package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.WishlistDto;

@Mapper
public interface WishlistDao {

	public void insertWishlist(WishlistDto wishlist);

	public WishlistDto getWishlist(WishlistDto wishlistDto);

	public int deleteWishlist(WishlistDto wishlist);

	public List<Integer> getWishlistAll(String userId);

	public int deleteWishlistByProductId(int productId);

}
