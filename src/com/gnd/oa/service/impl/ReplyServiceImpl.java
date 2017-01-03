package com.gnd.oa.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Forum;
import com.gnd.oa.domain.PageBean;
import com.gnd.oa.domain.Reply;
import com.gnd.oa.domain.Topic;
import com.gnd.oa.service.ReplyService;

@Service
@Transactional
@SuppressWarnings({"unchecked","rawtypes"})
public class ReplyServiceImpl extends DaoSupportImpl<Reply> implements
		ReplyService {

	public List<Reply> findByTopic(Topic topic) {
		return getSession()//
				.createQuery(
						"from Reply t where t.topic = ? order by t.postTime asc")//
				.setParameter(0, topic)//
				.list();
	}

	@Override
	public void save(Reply reply) {
		getSession().save(reply);

		// 维护相关信息
		Topic topic = reply.getTopic();
		Forum forum = topic.getForum();
		forum.setArticleCount(forum.getArticleCount() + 1); // 文章数量
		topic.setReplyCount(topic.getReplyCount() + 1); // 回复数量
		topic.setLastReply(reply); // 最后发表的回复
		topic.setLastUpdateTime(reply.getPostTime()); // 最后更新时间（主题发表时间或最后回复时间）
		getSession().update(topic);
		getSession().update(forum);
	}

	public PageBean getPageBeanByTopic(int pageNum, int pageSize, Topic topic) {

		//查询列表
		List list = getSession().createQuery(//
				"from Reply t where t.topic = ? order by t.postTime asc")//
				.setParameter(0, topic)//
				.setFirstResult((pageNum - 1) * pageSize)//
				.setMaxResults(pageSize)//
				.list();
		//查询总数量
		Long count = (Long)getSession().createQuery(//
				"select count(*) from Reply t where t.topic = ?")//
				.setParameter(0, topic)//
				.uniqueResult();
		return new PageBean(pageNum, pageSize, list, count.intValue());
	}
}
