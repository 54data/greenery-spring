package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.OrderDto;

@Mapper
public interface OrderDao {

	List<OrderDto> getOrderDetailByOrder(String userId);

	OrderDto getOrderInfo(int orderId);

}
