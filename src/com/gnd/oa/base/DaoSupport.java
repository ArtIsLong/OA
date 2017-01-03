package com.gnd.oa.base;

import java.util.List;

import com.gnd.oa.domain.PageBean;
import com.gnd.oa.util.HqlHelper;
import com.gnd.oa.util.QueryHelper;

public interface DaoSupport<T> {

	/**
	 * 保存实体
	 * 
	 * @param entity
	 */
	void save(T entity);

	/**
	 * 删除实体
	 * 
	 * @param id
	 */
	void delete(Long id);

	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	void update(T entity);

	/**
	 * 按id查询
	 * 
	 * @param id
	 * @return
	 */
	T getById(Long id);

	/**
	 * 按id查询
	 * 
	 * @param ids
	 * @return
	 */
	List<T> getByIds(Long[] ids);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	List<T> findAll();

	/**
	 * @Function getPageBean 
	 * @Description 公共的查询分页信息的方法
	 *
	 * @param @param pageNum 
	 * @param @param pageSize
	 * @param @param queryHelper
	 * @param @return
	 * @return PageBean
	 * @throws 
	 *
	@Deprecated
	PageBean getPageBean(int pageNum, int pageSize, String hql, List<Object> parameters);
	 * @date :2016-4-18上午10:45:43
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-18      chenmin          v1.0.0                                    修改原因<br>
	 */
	PageBean getPageBean(int pageNum, int pageSize, QueryHelper queryHelper);
	
	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param queryListHQL
	 *            查询数据列表的HQL语句，如果在前面加上“select count(*) ”就变成了查询总数量的HQL语句了
	 * 
	 * @param parameters
	 *            参数列表，顺序与HQL中的'?'的顺序一一对应。
	 * @return
	 */
	@Deprecated
	PageBean getPageBean(int pageNum, String queryListHQL, Object[] parameters);

	/**
	 * 公共的查询分页信息的方法
	 * 
	 * @param pageNum
	 * @param hqlHelper
	 *            查询条件（HQL语句与参数列表）
	 * @return
	 */
	PageBean getPageBean(int pageNum, HqlHelper hqlHelper);


}
