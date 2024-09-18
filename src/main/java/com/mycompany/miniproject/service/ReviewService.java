package com.mycompany.miniproject.service;

import java.util.List;

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


}
