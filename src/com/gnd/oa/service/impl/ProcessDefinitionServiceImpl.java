package com.gnd.oa.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessDefinitionQuery;
import org.jbpm.api.ProcessEngine;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.service.ProcessDefinitionService;

@Service
@Transactional
public class ProcessDefinitionServiceImpl implements ProcessDefinitionService {

	@Resource
	private ProcessEngine processEngine;
	
	public void deploy(ZipInputStream zipInputStream) {
		processEngine.getRepositoryService()//
					 .createDeployment()//
					 .addResourcesFromZipInputStream(zipInputStream)//
					 .deploy();
	}
	
	public List<ProcessDefinition> findAllLatestVersions() {
		//查询出所有的流程定义列表，把最新的版本都排到最后面
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
					 .createProcessDefinitionQuery()//
					 .orderAsc(ProcessDefinitionQuery.PROPERTY_VERSION)//
					 .list();
		
		//过滤出所有最新的版本
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		for (ProcessDefinition processDefinition : list) {
			map.put(processDefinition.getKey(), processDefinition);
		}
		return new ArrayList<ProcessDefinition>(map.values());
	}

	public void deleteByKey(String key) {
		//查询出指定key的所有版本的流程定义
		List<ProcessDefinition> list = processEngine.getRepositoryService()//
					 .createProcessDefinitionQuery()//
					 .processDefinitionKey(key)//
					 .list();
		
		//循环删除
		for (ProcessDefinition processDefinition : list) {
			processEngine.getRepositoryService().deleteDeploymentCascade(processDefinition.getDeploymentId());
		}
	}

	public InputStream getProcessImageResourceAsStream(
			String processDefinitionId) {
		//根据id取出对应的流程定义对象
		ProcessDefinition processDefinition = processEngine.getRepositoryService()//
					 .createProcessDefinitionQuery()//
					 .processDefinitionId(processDefinitionId)//
					 .uniqueResult();
		//返回图片资源
		return processEngine.getRepositoryService().getResourceAsStream(processDefinition.getDeploymentId(), processDefinition.getImageResourceName());
	}

}
