package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDetailDto {
    private int orderId;
    private String userId;
    private Date orderDate;
    private int productId;
    private String productName;
    private String productSummary;
    private int productQty;
    private int productPrice;
    private boolean hasReview;    
    private ReviewDto review;
    private int orderTotalPrice;
    private int couponStatus;
}
