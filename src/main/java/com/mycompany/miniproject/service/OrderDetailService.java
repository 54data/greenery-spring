package com.mycompany.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.OrderDetailDao;
import com.mycompany.miniproject.dto.OrderDetailDto;

@Service
public class OrderDetailService {

	@Autowired
	private OrderDetailDao orderDetailDao;
	
	public List<OrderDetailDto> getOrderDetails(String userId){
		List<OrderDetailDto> orderDetailList = orderDetailDao.getOrderDetailAll(userId);
		return orderDetailList;
	}
	
}
