package com.fangwu.dao.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.fangwu.entity.admin.Menu;

/**
 * ≤Àµ•π‹¿Ìdao
 * 
 *
 */
@Repository
public interface MenuDao {
	public int add(Menu menu);
	public List<Menu> findList(Map<String, Object> queryMap);
	public List<Menu> findTopList();
	public int getTotal(Map<String, Object> queryMap);
	public int edit(Menu menu);
	public int delete(Long id);
	public List<Menu> findChildernList(Long parentId);
	public List<Menu> findListByIds(String ids);
}
