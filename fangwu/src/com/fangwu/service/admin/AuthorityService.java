package com.fangwu.service.admin;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fangwu.entity.admin.Authority;

/**
 * Ȩ��service�ӿ�
 * 
 *
 */
@Service
public interface AuthorityService {
	public int add(Authority authority);
	public int deleteByRoleId(Long roleId);
	public List<Authority> findListByRoleId(Long roleId);
}
