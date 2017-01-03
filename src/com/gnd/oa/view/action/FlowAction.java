package com.gnd.oa.view.action;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.BaseAction;
import com.gnd.oa.domain.Application;
import com.gnd.oa.domain.ApplicationTemplate;
import com.gnd.oa.domain.ApproveInfo;
import com.gnd.oa.domain.TaskView;
import com.gnd.oa.util.HqlHelper;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class FlowAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private File upLoad; // 上传的文件
	private Long applicationTemplateId;

	private String taskId;
	private Long applicationId;
	private String comment;
	private boolean approval;
	
	private String outcome;
	private String applicationStatus;

	// ======================申请人相关
	
	/** 起草申请（模板列表） */
	public String applicationTemplateList() throws Exception {
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "applicationTemplateList";
	}
	
	/** 提交申请页面 */
	public String submitUI() throws Exception {
		return "submitUI";
	}
	
	/** 提交申请 */
	public String submit() throws Exception {
		Application application = new Application();
		application.setApplicant(getCurrentUser());
		application.setPath(saveUpLoadFile(upLoad));
		application.setApplicationTemplate(applicationTemplateService.getById(applicationTemplateId));
		
		// 调用业务方法（保存申请信息，并启动流程开始流转）
		applicationService.submit(application);
		return "toMyApplicationList";  //成功后转到“我的申请查询”
	}
	
	/** 我的申请查询 */
	public String myApplicationList() throws Exception {
		// 构建查询条件并准备分页信息
		new HqlHelper(Application.class, "a")//
				.addCondition("a.applicant=?", getCurrentUser())//
				.addCondition(applicationTemplateId != null, "a.applicationTemplate.id=?", applicationTemplateId)//
				.addCondition(StringUtils.isNotBlank(applicationStatus), "a.status=?", applicationStatus)//
				.addOrder("a.applyTime", false)//
				.buildPageBeanForStruts2(pageNum, applicationService);
		
		List<ApplicationTemplate> applicationTemplateList = applicationTemplateService.findAll();
		ActionContext.getContext().put("applicationTemplateList", applicationTemplateList);
		return "myApplicationList";
	}
	
	// ======================审批人相关

	/** 待我审批（我的任务列表） */
	public String myTaskList() throws Exception {
		List<TaskView> taskViewList = applicationService
				.getMyTaskViewList(getCurrentUser());
		ActionContext.getContext().put("taskViewList", taskViewList);
		return "myTaskList";
	}

	/** 审批处理页面 */
	public String approveUI() throws Exception {
		Set<String> outcomes = applicationService.getOutcomesByTaskId(taskId);
		ActionContext.getContext().put("outcomes", outcomes);
		return "approveUI";
	}

	/** 审批处理 */
	public String approve() throws Exception {
		// 封装
		ApproveInfo approveInfo = new ApproveInfo();

		approveInfo.setComment(comment);
		approveInfo.setApproval(approval);
		approveInfo.setApplication(applicationService.getById(applicationId));

		approveInfo.setApprover(getCurrentUser()); // 审批人，当前登录用户
		approveInfo.setApproveTime(new Date()); // 当前时间

		// 调用用业务方法（保存本次审批信息，并办理完任务，并维护申请的状态）
		applicationService.approve(approveInfo, taskId, outcome);

		return "toMyTaskList"; // 成功后转到待我审批页面
	}

	/** 查看流转记录 */
	public String approveHistory() throws Exception {
		Application application = applicationService.getById(applicationId);
		ActionContext.getContext().put("approveInfos",application.getApproveInfos());
		return "approveHistory";
	}

	// ---
	public File getUpLoad() {
		return upLoad;
	}

	public void setUpLoad(File upLoad) {
		this.upLoad = upLoad;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}

	public Long getApplicationTemplateId() {
		return applicationTemplateId;
	}

	public void setApplicationTemplateId(Long applicationTemplateId) {
		this.applicationTemplateId = applicationTemplateId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public boolean isApproval() {
		return approval;
	}

	public void setApproval(boolean approval) {
		this.approval = approval;
	}

	public String getOutcome() {
		return outcome;
	}

	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
}
