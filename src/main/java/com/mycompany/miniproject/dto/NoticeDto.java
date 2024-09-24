package com.mycompany.miniproject.dto;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeDto {
	private int noticeNum;
	private int noticeId;
	private String noticeWriter;
	private String noticeTitle;
	private String noticeContent;
	private Date noticeRegDate;
	private int noticeHitcount;
	private int beforeNoticeId;
	private String beforeNoticeTitle;
	private int afterNoticeId;
	private String afterNoticeTitle;
}
