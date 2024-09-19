package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.CartDto;
import com.mycompany.miniproject.dto.OrderDto;

@Mapper
public interface OrderDao {

	public List<OrderDto> getOrderDetailByOrder(String userId);

	public OrderDto getOrderInfo(int orderId);
	
	public int addProductToCart(CartDto cartDto);

	public List<CartDto> getCartProuducts(String userId);

	public int checkCartProduct(CartDto cartDto);

	public int updateProductQty(CartDto cartDto);

	public int deleteCartProduct(CartDto cartDto);

	public CartDto getSeletedProduct(CartDto cartDto);

}
