package com.gnd.oa.service;

import java.util.List;

import com.gnd.oa.base.DaoSupport;
import com.gnd.oa.domain.PageBean;
import com.gnd.oa.domain.Reply;
import com.gnd.oa.domain.Topic;

public interface ReplyService extends DaoSupport<Reply> {

	/**
	 * 查询指定主题中所有的回复列表，排序：按发表时间升序排列
	 */
	@Deprecated
	public List<Reply> findByTopic(Topic topic);

	/**
	 * @Function getPageBeanByTopic 
	 * @Description 查询分页信息
	 *
	 * @param @param pageNum 页码
	 * @param @param pageSize 每页显示多少条
	 * @param @param topic 回复所属主题
	 * @param @return
	 * @return PageBean
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-4-18上午10:47:56
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-18      chenmin          v1.0.0                                    修改原因<br>
	 */
	@Deprecated
	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic);
}
