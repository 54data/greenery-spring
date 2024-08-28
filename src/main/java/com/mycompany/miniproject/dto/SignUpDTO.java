package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class SignUpDTO {
	private String id;
	private String pw;
	private String pw_confirm;
	private String name;
	private String phoneNum;
	private String email;
	private String emailDomain;
	private String zipcode;
	private String load_address;
	private String detail_address;
}