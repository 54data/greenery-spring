package com.mycompany.miniproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.OrderDetailDao;
import com.mycompany.miniproject.dto.OrderDetailDto;

@Service
public class OrderDetailService {

	@Autowired
	private OrderDetailDao orderDetailDao;

	public OrderDetailDto getOrderDetailById(int orderId, int productId) {
		Map<String, Object> params = new HashMap<>();
	    params.put("orderId", orderId);
	    params.put("productId", productId);
	    
	    return orderDetailDao.getOrderDetail(params);	    
	}

	public List<OrderDetailDto> getOrderDetails(String userId, String sortOrder, String searchQuery) {
		Map<String, Object> params = new HashMap<>();
		params.put("userId", userId);
		params.put("sortOrder", Integer.parseInt(sortOrder)); 
		params.put("searchQuery", searchQuery);
		
		List<OrderDetailDto> orderDetailList = orderDetailDao.getOrderDetails(params);
		return orderDetailList;
	}

	
}
