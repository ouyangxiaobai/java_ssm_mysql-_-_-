package com.fangwu.service.admin;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fangwu.entity.admin.Role;

/**
 * ��ɫrole service
 * 
 *
 */
@Service
public interface RoleService {
	public int add(Role role);
	public int edit(Role role);
	public int delete(Long id);
	public List<Role> findList(Map<String, Object> queryMap);
	public int getTotal(Map<String, Object> queryMap);
	public Role find(Long id);
}
