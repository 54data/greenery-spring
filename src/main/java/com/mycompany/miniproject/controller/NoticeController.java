package com.mycompany.miniproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.PagerDto;
import com.mycompany.miniproject.service.NoticeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/notice")
@Slf4j
public class NoticeController {
	@Autowired 
	private NoticeService noticeService;
	
	@GetMapping("/noticeContentAddHitcount")
	public String noticeContentAddHitcount(int noticeId, Model model) {
		noticeService.addHitcount(noticeId);
		return noticeContent(noticeId, model);
	}

	@GetMapping("/noticeContent")
	public String noticeContent(int noticeId, Model model) {
		NoticeDto notice = noticeService.getNoticeContent(noticeId);
		model.addAttribute("notice", notice);
		return "notice/noticeContent";
	}
	
	@GetMapping("/notices")
	public String notices(Model model,
			@RequestParam(defaultValue="1") int pageNo,
			HttpSession session) {
		int totalRows = noticeService.getTotalRows();
		PagerDto pager = new PagerDto(7, 5, totalRows, pageNo);
		session.setAttribute("pager", pager);
		
		List<NoticeDto> noticeList = noticeService.getNotices(pager); // notice테이블의 각 행을 담은 객체를 담은 리스트
		model.addAttribute("noticeList", noticeList);
		
		model.addAttribute("noticeSize", totalRows); // notice 데이터 총 개수 반환
		return "notice/notices";
	}
}
