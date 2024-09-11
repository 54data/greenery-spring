package com.mycompany.miniproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycompany.miniproject.dao.NoticeDao;
import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.PagerDto;

@Service
public class NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
	public List<NoticeDto> getNotices(PagerDto pager) {
		List<NoticeDto> noticeList = noticeDao.getNoticeAll(pager);
		return noticeList;
	}

	public int getTotalRows() {
		int totalRows = noticeDao.countRows();
		return totalRows;
	}

	public NoticeDto getNoticeContent(int noticeId) {
		NoticeDto notice = noticeDao.getNoticeById(noticeId);
		return notice;
	}
}
