package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDto {
	private int orderId;
	private String userId;
	private Date orderDate;
	private int orderTotalPrice;
}
