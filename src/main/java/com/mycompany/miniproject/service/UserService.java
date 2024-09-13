package com.mycompany.miniproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.UserDao;
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
}
