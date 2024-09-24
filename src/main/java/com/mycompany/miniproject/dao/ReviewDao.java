package com.mycompany.miniproject.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	public List<ReviewDto> getReviewsById(@Param("productId") int productId, @Param("pager") PagerDto pager);
	
	public ReviewDto getReviewImgById(int reviewId);

	public int countRows(@Param("productId") int productId);

	public int insertReview(ReviewDto reviewDto);
	
	public int deleteReview(int reviewId);

	public int countUserReviewForProduct(Map<String, Object> params);

	public void updateReview(ReviewDto reviewDto);

	public ReviewDto getReview(Map<String, Object> params);

	public ReviewDto getReviewByReviewId(int reviewId);

}
