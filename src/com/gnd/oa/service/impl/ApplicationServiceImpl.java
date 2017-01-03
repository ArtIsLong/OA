package com.gnd.oa.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.task.Task;
import org.springframework.stereotype.Service;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.Application;
import com.gnd.oa.domain.ApproveInfo;
import com.gnd.oa.domain.TaskView;
import com.gnd.oa.domain.User;
import com.gnd.oa.service.ApplicationService;

@Service
public class ApplicationServiceImpl extends DaoSupportImpl<Application>
		implements ApplicationService {

	@Resource
	private ProcessEngine processEngine;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public void submit(Application application) {
		application.setApplyTime(new Date()); // 申请时间，当前时间
		application.setStatus(Application.STATUS_RUNNING);
		application.setTitle(// 格式为：模板名称_申请人姓名_申请时间
				application.getApplicationTemplate().getName()//
						+ "_" + application.getApplicant().getName()//
						+ "_" + sdf.format(application.getApplyTime()));

		getSession().save(application);

		// 启动流程实例，开始流转
		// >> 准备流程变量
		Map<String, Object> variablesMap = new HashMap<String, Object>();
		variablesMap.put("application", application);

		// >> 启动流程实例,并带上流程变量（当前的申请信息）
		String pdKey = application.getApplicationTemplate()
				.getProcessDefinitionKey();
		ProcessInstance startProcessInstanceByKey = processEngine
				.getExecutionService().startProcessInstanceByKey(pdKey,
						variablesMap);

		// >> 办理完第一个任务“提交申请”，并带上流程变量（当前的申请信息）
		Task task = processEngine.getTaskService()// 查询出本流程实例中当前仅有的一个任务“提交申请”
				.createTaskQuery()//
				.processInstanceId(startProcessInstanceByKey.getId())//
				.uniqueResult();
		processEngine.getTaskService().completeTask(task.getId());
	}

	public List<TaskView> getMyTaskViewList(User currentUser) {
		// 查询我的任务列表
		String userId = currentUser.getLoginName();
		List<Task> taskList = processEngine.getTaskService().findPersonalTasks(
				userId);

		// 找出每个任务对应的申请信息
		List<TaskView> resultList = new ArrayList<TaskView>();
		for (Task task : taskList) {
			Application application = (Application) processEngine
					.getTaskService().getVariable(task.getId(), "application");
			resultList.add(new TaskView(task, application));
		}

		// 返回“任务--申请信息”的结果
		return resultList;
	}

	public Set<String> getOutcomesByTaskId(String taskId) {
		// 获取指定任务活动中所有流出的连线名称
		return processEngine.getTaskService().getOutcomes(taskId);
	}

	public void approve(ApproveInfo approveInfo, String taskId, String outcome) {
		// 1，保存本次审批信息
		getSession().save(approveInfo);

		// 2，办理完任务
		Task task = processEngine.getTaskService().getTask(taskId); // 一定要先取出Task对象，再完成任务，否则拿不到，因为执行完就变成历史信息了。
		if (outcome == null) {
			processEngine.getTaskService().completeTask(taskId);
		} else {
			processEngine.getTaskService().completeTask(taskId, outcome);
		}

		// >> 取出所属的流程实例，如果取出的为null，说明流程实例执行完成了，已经变成了历史记录
		ProcessInstance pi = processEngine.getExecutionService().findProcessInstanceById(task.getExecutionId());

		// 3，维护申请的状态
		Application application = approveInfo.getApplication();
		if (!approveInfo.isApproval()) {
			// 如果本环节不同意，则流程实例直接结束，申请的状态为：未通过
			if (pi != null) { // 如果流程还未结束
				processEngine.getExecutionService().endProcessInstance(task.getExecutionId(), ProcessInstance.STATE_ENDED);
			}
			application.setStatus(Application.STATUS_REJECTED);
		} else {
			// 如果本环节同意，而且本环节是最后一个环节，则流程实例正常结束，申请的状态为：已通过
			if (pi == null) { // 本环节是最后一个环节，即流程已经结束了
				application.setStatus(Application.STATUS_APPROVED);
			}
		}
		getSession().update(application);
	}

}
