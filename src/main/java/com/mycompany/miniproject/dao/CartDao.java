package com.mycompany.miniproject.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CartDao {

	public int deleteCart(int productId);

}
