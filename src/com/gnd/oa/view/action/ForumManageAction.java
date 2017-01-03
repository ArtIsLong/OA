package com.gnd.oa.view.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.ModelDrivenBaseAction;
import com.gnd.oa.domain.Forum;
import com.gnd.oa.domain.Topic;
import com.gnd.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ForumManageAction extends ModelDrivenBaseAction<Forum> {
	private static final long serialVersionUID = 1L;
	
		/**
	 * 0 表示查看全部主题<br>
	 * 1 表示只看精华帖
	 */
	private int viewType = 0;

	/**
	 * 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)<br>
	 * 1 表示只按最后更新时间排序<br>
	 * 2 表示只按主题发表时间排序<br>
	 * 3 表示只按回复数量排序
	 */
	private int orderBy = 0;

	/**
	 * true 表示升序<br>
	 * false 表示降序
	 */
	private boolean asc = false;


	/** 列表 */
	public String list() throws Exception {
//		List<Forum> forumList = forumService.findAll();
//		ActionContext.getContext().put("forumList", forumList);
		new QueryHelper(Forum.class, "t")//
		.preparePageBean(forumService, pageNum, pageSize);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		forumService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		forumService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(forum);
		return "saveUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		Forum forum = forumService.getById(model.getId());
		forum.setName(model.getName());
		forum.setDescription(model.getDescription());
		forumService.update(forum);
		return "toList";
	}

	/** 上移 */
	public String moveUp() throws Exception {
		forumService.moveUp(model.getId());
		return "toList";
	}

	/** 下移 */
	public String moveDown() throws Exception {
		forumService.moveDown(model.getId());
		return "toList";
	}
	
	/** 显示单个版块（主题列表） */
	public String show() throws Exception {
		//准备数据 ： forum
		Forum forum = forumService.getById(model.getId());
		ActionContext.getContext().put("forum", forum);
		
		//准备数据 ：topicList
		// List<Topic> topicList = topicService.findByForum(forum);
		// ActionContext.getContext().put("topicList", topicList);
		
				// 准备分页信息 ，最终版
		new QueryHelper(Topic.class, "t")//
				// 过滤条件
				.addCondition("t.forum=?", forum)//
				.addCondition((viewType == 1), "t.type=?", Topic.TYPE_BEST) // 1 表示只看精华帖
				// 排序条件
				.addOrderProperty((orderBy == 1), "t.lastUpdateTime", asc) // 1 表示只按最后更新时间排序
				.addOrderProperty((orderBy == 2), "t.postTime", asc) // 2 表示只按主题发表时间排序
				.addOrderProperty((orderBy == 3), "t.replyCount", asc) // 3 表示只按回复数量排序
				.addOrderProperty((orderBy == 0), "(CASE t.type WHEN 2 THEN 2 ELSE 0 END)", false)//
				.addOrderProperty((orderBy == 0), "t.lastUpdateTime", false) // 0 表示默认排序(所有置顶帖在前面，并按最后更新时间降序排列)
				.preparePageBean(topicService, pageNum, pageSize);

		
		return "show";
	}
	
		public int getViewType() {
		return viewType;
	}

	public void setViewType(int viewType) {
		this.viewType = viewType;
	}

	public int getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(int orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isAsc() {
		return asc;
	}

	public void setAsc(boolean asc) {
		this.asc = asc;
	}


}
