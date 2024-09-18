package com.mycompany.miniproject.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.UserDto;

@Mapper
public interface UserDao {

	public UserDto selectByUserId(String username);

	public int insert(UserDto user);

}
