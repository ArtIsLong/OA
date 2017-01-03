package com.gnd.oa.view.action;

import java.util.Date;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.ModelDrivenBaseAction;
import com.gnd.oa.domain.Reply;
import com.gnd.oa.domain.Topic;
import com.opensymphony.xwork2.ActionContext;

/**
 * 回复
 */
@Controller
@Scope("prototype")
public class ReplyAction extends ModelDrivenBaseAction<Reply> {

	private static final long serialVersionUID = 1L;

	private Long topicId;

	/** 回帖页面 */
	public String addUI() throws Exception {
		Topic topic = topicService.getById(topicId);
		ActionContext.getContext().put("topic", topic);
		return "addUI";
	}

	/** 回帖 */
	public String add() throws Exception {
		model.setTopic(topicService.getById(topicId));
		model.setAuthor(getCurrentUser());
		model.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
		model.setPostTime(new Date());

		replyService.save(model);

		// 转到新回复所在主题的显示页面
		return "toTopicShow";
	}

	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
}
