package com.gnd.oa.service;

import java.util.List;
import java.util.Set;

import com.gnd.oa.base.DaoSupport;
import com.gnd.oa.domain.Application;
import com.gnd.oa.domain.ApproveInfo;
import com.gnd.oa.domain.TaskView;
import com.gnd.oa.domain.User;

public interface ApplicationService extends DaoSupport<Application> {

	/**
	 * @Function submit
	 * @Description 提交申请：<br>
	 *              1、保存申请信息<br>
	 *              2、启动流程开始流转
	 * @param @param application
	 * @return void
	 * @throws
	 * 
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-5-2下午2:13:44
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date Author Version Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-5-2 chenmin v1.0.0 修改原因<br>
	 */
	public void submit(Application application);
	
	/**
	 * @Function getMyTaskViewList 
	 * @Description 查询我的任务信息列表
	 *
	 * @param @param currentUser
	 * @param @return
	 * @return List<TaskView>
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-5-2下午2:15:21
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-5-2      chenmin          v1.0.0                                    修改原因<br>
	 */
	public List<TaskView> getMyTaskViewList(User currentUser);

	public Set<String> getOutcomesByTaskId(String taskId);

	public void approve(ApproveInfo approveInfo, String taskId, String outcome);
}
