package com.mycompany.miniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.UserDao;
import com.mycompany.miniproject.dto.CouponDto;
import com.mycompany.miniproject.dto.UserDto;

@Service
public class UserService {
	public enum JoinResult {
		SUCCESS,
		FAIL_DUPLICATED_USERID
	}

	@Autowired
	private UserDao userDao;
		
	public JoinResult userIdCheck(String userID) {
		boolean userExist = isMid(userID);
		if (userExist) {
			return JoinResult.FAIL_DUPLICATED_USERID;
		}
		return JoinResult.SUCCESS;
	}
	
	public JoinResult join(UserDto user) {
		userDao.insert(user);
		return JoinResult.SUCCESS;
	}

	public boolean isMid(String mid) {
		return userDao.selectByUserId(mid) != null;
	}

	public String getUserName(String userId) {
		String userName = userDao.selectByUserId(userId).getUserName();
		return userName;
	}

	public int getUserCouponStatus(String userId) {
		int couponStatus = userDao.getUserCouptonStatus(userId);
		return couponStatus;
	}

	public void updateCouponStatus(int couponStatus, String userId) {
		CouponDto coupon = new CouponDto();
		coupon.setCouponStatus(couponStatus);
		coupon.setUserId(userId);
		userDao.updateCouponStatus(coupon);
	}

	public UserDto getUserInfo(String userId) {
		UserDto user = userDao.selectByUserId(userId);
		return user;
	}

	public void updateUserInfo(UserDto user) {
		userDao.updateByUserId(user);
	}

	public Boolean updatePwd(UserDto user) {
		return userDao.updateNewPwd(user) != 0;
	}

	public void deactivateUser(String userId) {	
		userDao.deactivateUserById(userId);
	}
}
