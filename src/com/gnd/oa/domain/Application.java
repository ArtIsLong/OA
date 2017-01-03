package com.gnd.oa.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @ClassName: Application
 * @Description: 流转的申请
 * @author 陈敏
 * @date 2016-5-2 下午1:52:11
 * 
 */
public class Application {

	/** 状态常量：审批中 */
	public static final String STATUS_RUNNING = "审批中";

	/** 状态常量：已通过 */
	public static final String STATUS_APPROVED = "已通过";

	/** 状态常量：未通过 */
	public static final String STATUS_REJECTED = "未通过";

	private Long id;
	private ApplicationTemplate applicationTemplate; // 所使用的申请模板
	private Set<ApproveInfo> approveInfos = new HashSet<ApproveInfo>();
	private User applicant; // 申请人

	private String title; // 标题
	private Date applyTime; // 申请时间
	private String path; // 文档的存储路径
	private String status; // 当前的状态

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ApplicationTemplate getApplicationTemplate() {
		return applicationTemplate;
	}

	public void setApplicationTemplate(ApplicationTemplate applicationTemplate) {
		this.applicationTemplate = applicationTemplate;
	}

	public Set<ApproveInfo> getApproveInfos() {
		return approveInfos;
	}

	public void setApproveInfos(Set<ApproveInfo> approveInfos) {
		this.approveInfos = approveInfos;
	}

	public User getApplicant() {
		return applicant;
	}

	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
