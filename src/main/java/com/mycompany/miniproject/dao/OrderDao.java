package com.mycompany.miniproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.CartDto;
import com.mycompany.miniproject.dto.OrderDetailDto;
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

	public int insertOrderProducts(OrderDto orderDto);

	public int insertOrderDetailProducts(OrderDetailDto orderDetailDto);

	public int getCartNumById(String userId);

	public int totalOrderNum(String userId);

	public List<OrderDto> getOrdersByOrderIds(Map<String, Object> params);


}
