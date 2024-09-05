package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserDto {
	private String userId;
	private String userPwd;
	private String userName;
	private String userTel;
	private String userEmail;
	private String zipcode;
	private String roadAddress;
	private String detailedAddress;
	private String userRole;
	private int couponStatus;
	private Date userRegDate;
	private int userStatus;
}
