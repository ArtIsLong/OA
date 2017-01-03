package com.gnd.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.ModelDrivenBaseAction;
import com.gnd.oa.domain.Forum;
import com.gnd.oa.domain.Reply;
import com.gnd.oa.domain.Topic;
import com.gnd.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

/**
 * 主题
 */
@Controller
@Scope("prototype")
public class TopicAction extends ModelDrivenBaseAction<Topic> {

	private static final long serialVersionUID = 1L;

	private Long forumId;
	
	/** 显示单个主题（主贴+回帖列表） */
	public String show() throws Exception {
		Topic topic = topicService.getById(model.getId());
		ActionContext.getContext().put("topic", topic);
		
//		List<Reply> replyList = replyService.findByTopic(topic);
//		ActionContext.getContext().put("replyList", replyList);
		new QueryHelper(Reply.class, "t")//
			.addCondition("t.topic=?", topic)//
			.addOrderProperty("t.postTime", true)//
			.preparePageBean(replyService, pageNum, pageSize);
		return "show";
	}
	
	/** 发表新帖页面 */
	public String addUI() throws Exception {
		Forum forum = forumService.getById(forumId);
		ActionContext.getContext().put("forum", forum);
		return "addUI";
	}

	/** 发表新帖 */
	public String add() throws Exception {
		model.setForum(forumService.getById(forumId));
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());
		topicService.save(model);
		return "toShow";
	}

	public Long getForumId() {
		return forumId;
	}

	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}
}
