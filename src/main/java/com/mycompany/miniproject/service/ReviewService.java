package com.mycompany.miniproject.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.ReviewDao;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ReviewDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;
	
	public ReviewDto getReviewImgByReviewId(int reviewId) {
		ReviewDto reviewImg = reviewDao.getReviewImgById(reviewId);
		return reviewImg;
	}

	public int getTotalRows(@Param("productId") int productId) {
		int totalRows = reviewDao.countRows(productId);
		return totalRows;
	}

	public List<ReviewDto> getReviewsByProductId(int productId, PagerDto pager) {
		List<ReviewDto> reviewList = reviewDao.getReviewsById(productId, pager);
		return reviewList;
	}

	public void insertReview(ReviewDto reviewDto){
		reviewDao.insertReview(reviewDto);
	}
	
	public void deleteReview(int orderId){
		reviewDao.deleteReview(orderId);
	}

	public boolean hasReviewForProduct(int orderId, int productId) {
		Map<String, Object> params = new HashMap<>();
	    params.put("orderId", orderId);
	    params.put("productId", productId);
	    
		int count = reviewDao.countUserReviewForProduct(params);
        return count > 0;
	}

	public void updateReview(ReviewDto reviewDto) {
		reviewDao.updateReview(reviewDto);	
	}

	public ReviewDto getReview(int orderId, int productId){
		Map<String, Object> params = new HashMap<>();
	    params.put("orderId", orderId);
	    params.put("productId", productId);
	    
	    return reviewDao.getReview(params);
	}
	
	public ReviewDto getReviewByReviewId(int reviewId){	    
	    return reviewDao.getReviewByReviewId(reviewId);
	}
	
}
