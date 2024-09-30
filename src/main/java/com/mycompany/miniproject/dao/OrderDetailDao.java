package com.mycompany.miniproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.OrderDetailDto;

@Mapper
public interface OrderDetailDao {

/*	List<OrderDetailDto> getOrderDetailByOd(String userId);
*/
	OrderDetailDto getOrderDetail(Map<String, Object> params);

	List<OrderDetailDto> getOrderDetails(Map<String, Object> params);

}
