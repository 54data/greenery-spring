package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.miniproject.dto.CartDto;
import com.mycompany.miniproject.dto.OrderDetailDto;
import com.mycompany.miniproject.dto.OrderDto;
import com.mycompany.miniproject.dto.PagerDto;

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

	public List<OrderDto> getOrdersByUserId(@Param("pager")PagerDto pager, @Param("userId")String userId);

	public int totalOrderNum(String userId);

}
