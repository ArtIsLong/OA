package com.gnd.oa.service;

import java.util.Collection;
import java.util.List;

import com.gnd.oa.base.DaoSupport;
import com.gnd.oa.domain.Privilege;

public interface PrivilegeService extends DaoSupport<Privilege> {
	/**
	 * 查询所有的顶级权限
	 */
	List<Privilege> findTopList();
	
	/**
	 * 查询所有权限对应的URL集合(不重复)
	 */
	Collection<String> getAllPrivilegeUrls();

}
