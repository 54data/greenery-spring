package com.mycompany.miniproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.miniproject.dto.NoticeDto;
import com.mycompany.miniproject.dto.PagerDto;

@Mapper
public interface NoticeDao {

	public List<NoticeDto> getNoticeAll(PagerDto pager);

	public int countRows();

}
