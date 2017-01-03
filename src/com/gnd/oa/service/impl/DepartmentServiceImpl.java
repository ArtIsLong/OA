package com.gnd.oa.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Department;
import com.gnd.oa.service.DepartmentService;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class DepartmentServiceImpl extends DaoSupportImpl<Department> implements DepartmentService {

	@Resource
	private SessionFactory sessionFactory;
	
	public List<Department> findTopList() {
		return sessionFactory.getCurrentSession().createQuery(//
				"from Department d where d.parent is null")//
				.list();
	}

	public List<Department> findChildren(Long parentId) {
		return sessionFactory.getCurrentSession().createQuery(//
				"from Department d where d.parent.id=?")//
				.setParameter(0, parentId)
				.list();
	}

}
