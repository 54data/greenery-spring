package com.mycompany.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.ReviewDao;
import com.mycompany.miniproject.dto.ReviewDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReviewService {
	
	@Autowired
	private ReviewDao reviewDao;
	
	public List<ReviewDto> getReviewsByProductId(int productId) {
		List<ReviewDto> reviewList = reviewDao.getReviewById(productId);
		return reviewList;
	}

	public ReviewDto getReviewImgByReviewId(int reviewId) {
		ReviewDto reviewImg = reviewDao.getReviewImgById(reviewId);
		return reviewImg;
	}


}
