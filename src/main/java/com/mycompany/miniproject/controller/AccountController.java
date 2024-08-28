package com.mycompany.miniproject.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mycompany.miniproject.dto.LoginDTO;
import com.mycompany.miniproject.dto.SignUpDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/account")
@Controller
public class AccountController {

	@RequestMapping("/login")
	public String login() {
		log.info("로그인 성공");
		return "account/login";
	}
	
	@PostMapping("/requestAjax")
	public void requestAjax(LoginDTO dto, HttpServletResponse response) throws IOException {
		log.info(dto.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", dto.getId());
		jsonObject.put("pw", dto.getPw());
		String json = jsonObject.toString();
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println(json);
		pw.flush();
		pw.close();
		
	}
	
	@RequestMapping("/signup")
	public String signup() {
		log.info("회원가입");
		return "account/signup";
	}
	
	@PostMapping("/requestAjaxSignUp")
	public void requestAjaxSignUp(SignUpDTO dto, HttpServletResponse response) throws IOException {
		log.info(dto.toString());
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", dto.getId());
		jsonObject.put("pw", dto.getPw());
		jsonObject.put("pw_confirm",dto.getPw_confirm());
		jsonObject.put("name", dto.getName());
		jsonObject.put("phoneNum", dto.getPhoneNum());
		jsonObject.put("email", dto.getEmail());
		jsonObject.put("emailDomain", dto.getEmailDomain());
		jsonObject.put("zipcode", dto.getZipcode());
		jsonObject.put("load_address", dto.getLoad_address());
		jsonObject.put("detail_address", dto.getDetail_address());
		String json = jsonObject.toString();
		
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter pw = response.getWriter();
		pw.println(json);
		pw.flush();
		pw.close();

	}
}
