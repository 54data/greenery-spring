package com.mycompany.miniproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.CouponDto;
import com.mycompany.miniproject.dto.UserDto;

@Mapper
public interface UserDao {

	public UserDto selectByUserId(String username);

	public int insert(UserDto user);

	public int getUserCouptonStatus(String userId);
	
	public int updateCouponStatus(CouponDto coupon);

	public int updateByUserId(UserDto user);

	public int updateNewPwd(UserDto user);

	public int deactivateUserById(String userId);
}
