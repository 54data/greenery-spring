package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.OrderDetailDto;

@Mapper
public interface OrderDetailDao {

	List<OrderDetailDto> getOrderDetailByOd(String userId);

}
