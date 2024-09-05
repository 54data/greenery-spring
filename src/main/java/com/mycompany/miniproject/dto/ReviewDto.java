package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ReviewDto {
	private int review_id;
	private int product_id;
	private String user_id;
	private String review_content;
	private Date review_reg_date;
	private int review_score;
	private String review_img;
	private String review_img_type;
	private String review_img_name;
}
