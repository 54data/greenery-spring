package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	public List<ReviewDto> getReviewById(int productId);

	public List<ReviewDto> getReviewsById(@Param("productId") int productId, @Param("startRowNo") int startRowNo, @Param("endRowNo") int endRowNo);
	
	public ReviewDto getReviewImgById(int reviewId);

	public int countRows();


}
