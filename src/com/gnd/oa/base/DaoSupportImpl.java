package com.gnd.oa.base;

import java.lang.reflect.ParameterizedType;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.cfg.Configuration;
import com.gnd.oa.domain.PageBean;
import com.gnd.oa.util.HqlHelper;
import com.gnd.oa.util.QueryHelper;

/**
 * 所有DAO层的基类
 */
@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DaoSupportImpl<T> implements DaoSupport<T> {
	private static transient Log log = LogFactory.getLog(DaoSupportImpl.class);
	@Resource
	protected SessionFactory sessionFactory;

	@Resource
	protected ProcessEngine processEngine;

	private Class<T> clazz;

	public DaoSupportImpl() {
		// 使用反射技术得到T的真实类型
		ParameterizedType pt = (ParameterizedType) this.getClass()
				.getGenericSuperclass(); // 获取当前new的对象的 泛型的父类 类型
		this.clazz = (Class<T>) pt.getActualTypeArguments()[0]; // 获取第一个类型参数的真实类型
		log.info("clazz ---> " + clazz);
	}

	/**
	 * 获取当前可用的Session
	 * 
	 * @return
	 */
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public void save(T entity) {
		getSession().save(entity);
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	public void delete(Long id) {
		Object obj = getById(id);
		if (obj != null) {
			getSession().delete(obj);
		}
	}

	public T getById(Long id) {
		if (id == null) {
			return null;
		} else {
			return (T) getSession().get(clazz, id);
		}
	}

	public List<T> getByIds(Long[] ids) {
		if (ids == null || ids.length == 0) {
			return Collections.EMPTY_LIST;
		}
		return getSession().createQuery(//
				"from " + clazz.getSimpleName() + " where id in (:ids)")//
				.setParameterList("ids", ids)//
				.list();
	}

	public List<T> findAll() {
		return getSession().createQuery(//
				"from " + clazz.getSimpleName())//
				.list();
	}

	// 公共的查询分页信息的方法
	@Deprecated
	public PageBean getPageBean(int pageNum, int pageSize, String hql,
			List<Object> parameters) {
		System.out.println("-------> DaoSupportImpl.getPageBean()");

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hql); // 创建查询对象
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数量
		Query countQuery = getSession().createQuery("SELECT COUNT(*) " + hql);
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	// 公共的查询分页信息的方法（最终版）
	public PageBean getPageBean(int pageNum, int pageSize,
			QueryHelper queryHelper) {
		System.out
				.println("-------> DaoSupportImpl.getPageBean( int pageNum, int pageSize, QueryHelper queryHelper )");

		// 参数列表
		List<Object> parameters = queryHelper.getParameters();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(
				queryHelper.getListQueryHql()); // 创建查询对象
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数量
		Query countQuery = getSession().createQuery(
				queryHelper.getCountQueryHql());
		if (parameters != null) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	// 公共的查询分页信息的方法
	public PageBean getPageBean(int pageNum, String queryListHQL,
			Object[] parameters) {
		System.out.println("--------------> BaseDaoImpl.getPageBean()");
		int pageSize = Configuration.getPageSize();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(queryListHQL);
		if (parameters != null && parameters.length > 0) { // 设置参数
			for (int i = 0; i < parameters.length; i++) {
				listQuery.setParameter(i, parameters[i]);
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数
		Query countQuery = getSession().createQuery(
				"SELECT COUNT(*) " + queryListHQL);
		if (parameters != null && parameters.length > 0) { // 设置参数
			for (int i = 0; i < parameters.length; i++) {
				countQuery.setParameter(i, parameters[i]);
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

	// 最终版
	public PageBean getPageBean(int pageNum, HqlHelper hqlHelper) {
		System.out
				.println("--------------> BaseDaoImpl.getPageBean( int pageNum, HqlHelper hqlHelper )");
		int pageSize = Configuration.getPageSize();
		List<Object> parameters = hqlHelper.getParameters();

		// 查询本页的数据列表
		Query listQuery = getSession().createQuery(hqlHelper.getQueryListHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				listQuery.setParameter(i, parameters.get(i));
			}
		}
		listQuery.setFirstResult((pageNum - 1) * pageSize);
		listQuery.setMaxResults(pageSize);
		List list = listQuery.list(); // 执行查询

		// 查询总记录数
		Query countQuery = getSession().createQuery(
				hqlHelper.getQueryCountHql());
		if (parameters != null && parameters.size() > 0) { // 设置参数
			for (int i = 0; i < parameters.size(); i++) {
				countQuery.setParameter(i, parameters.get(i));
			}
		}
		Long count = (Long) countQuery.uniqueResult(); // 执行查询

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

}
