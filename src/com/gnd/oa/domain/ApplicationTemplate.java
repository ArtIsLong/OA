package com.gnd.oa.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: ApplicationTemplate
 * @Description: 申请模板
 * @author 陈敏
 * @date 2016-4-29 下午5:15:33
 * 
 */
public class ApplicationTemplate {

	private Long id;
	private String name;
	private String processDefinitionKey;
	private String path; // 文件在服务器上存储的路径
	private Set<Application> applications = new HashSet<Application>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Set<Application> getApplications() {
		return applications;
	}

	public void setApplications(Set<Application> applications) {
		this.applications = applications;
	}
}
