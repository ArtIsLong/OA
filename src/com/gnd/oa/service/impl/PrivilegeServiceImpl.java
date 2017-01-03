package com.gnd.oa.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Privilege;
import com.gnd.oa.service.PrivilegeService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PrivilegeServiceImpl extends DaoSupportImpl<Privilege> implements
		PrivilegeService {

	public List<Privilege> findTopList() {
		return getSession().createQuery(//
				"from Privilege p where p.parent is null")//
				.list();
	}

	public Collection<String> getAllPrivilegeUrls() {
		return getSession().createQuery(//
				"select distinct p.url from Privilege p where p.url is not null")//
				.list();
	}
}
