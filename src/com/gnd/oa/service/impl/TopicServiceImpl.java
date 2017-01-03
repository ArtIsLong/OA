package com.gnd.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Forum;
import com.gnd.oa.domain.PageBean;
import com.gnd.oa.domain.Topic;
import com.gnd.oa.service.TopicService;

@Service
@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public class TopicServiceImpl extends DaoSupportImpl<Topic> implements
		TopicService {

	public List<Topic> findByForum(Forum forum) {
		// 排序：所有的置顶帖在最上面，并按照最后更新时间排序，让新状态的在上面
		return getSession()//
				.createQuery(
						"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.list();
	}

	@Override
	public void save(Topic topic) {
		// 1、设置属性并保存
		topic.setType(Topic.TYPE_NORMAL); // 模型为普通帖
		topic.setReplyCount(0);
		topic.setLastReply(null); // XXX
		topic.setLastUpdateTime(topic.getPostTime());
		getSession().save(topic); // 保存

		// 2、维护相关的特殊属性
		Forum forum = topic.getForum();
		// 版块下的主题数量
		forum.setTopicCount(forum.getTopicCount() + 1);
		forum.setArticleCount(forum.getArticleCount() + 1);
		forum.setLastTopic(topic); // 最后发表的主题
		getSession().update(forum);
	}

	public PageBean getPageBeanByForum(int pageNum, int pageSize, Forum forum) {

		// 查询本页的数据列表
		List list = getSession()
				.createQuery(//
						"FROM Topic t WHERE t.forum=? ORDER BY (CASE t.type WHEN 2 THEN 2 ELSE 0 END) DESC, t.lastUpdateTime DESC")//
				.setParameter(0, forum)//
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();

		// 查询总记录数量
		Long count = (Long) getSession().createQuery(//
				"SELECT COUNT(*) FROM Topic t WHERE t.forum=?")//
				.setParameter(0, forum)//
				.uniqueResult();

		return new PageBean(pageNum, pageSize, list, count.intValue());
	}

}
