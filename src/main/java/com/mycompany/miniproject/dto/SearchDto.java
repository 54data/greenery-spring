package com.mycompany.miniproject.dto;

import lombok.Data;

@Data
public class SearchDto {
	private String category;
	private String searchContent;
	private String sort;
}
