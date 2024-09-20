package com.mycompany.miniproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.WishlistDto;

@Mapper
public interface WishlistDao {

	public void insertWishlist(WishlistDto wishlist);

	public WishlistDto getWishlist(WishlistDto wishlistDto);

	public int deleteWishlist(WishlistDto wishlist);

}
