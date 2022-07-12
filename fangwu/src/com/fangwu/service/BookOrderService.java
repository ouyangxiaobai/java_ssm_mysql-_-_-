package com.fangwu.service;
/**
 * ×âÁÞ¶©µ¥service
 */
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fangwu.entity.BookOrder;

@Service
public interface BookOrderService {
	public int add(BookOrder bookOrder);
	public int edit(BookOrder bookOrder);
	public int delete(Long id);
	public List<BookOrder> findList(Map<String, Object> queryMap);
	public Integer getTotal(Map<String, Object> queryMap);
	public BookOrder find(Long id);
}
