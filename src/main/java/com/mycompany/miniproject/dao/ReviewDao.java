package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.dto.ReviewDto;

@Mapper
public interface ReviewDao {

	public List<ReviewDto> getReviewsById(@Param("productId") int productId, @Param("pager") PagerDto pager);
	
	public ReviewDto getReviewImgById(int reviewId);

	public int countRows(@Param("productId") int productId);


}
