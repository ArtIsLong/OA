package com.gnd.oa.service;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;

public interface ProcessDefinitionService {

	/**
	 * @Function findAllLatestVersions 
	 * @Description 查询所有最新版本的流程定义列表
	 *
	 * @return List<ProcessDefinition>
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-4-29下午3:13:23
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-29      chenmin          v1.0.0                                    修改原因<br>
	 */
	public List<ProcessDefinition> findAllLatestVersions();
	
	/**
	 * @Function deleteByKey 
	 * @Description 删除指定key的所有版本的流程定义
	 *
	 * @param key
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-4-29下午3:14:28
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-29      chenmin          v1.0.0                                    修改原因<br>
	 */
	public void deleteByKey(String key);
	
	/**
	 * @Function deploy 
	 * @Description 部署流程定义（文件格式ZIP）
	 *
	 * @param zipInputStream
	 * @return void
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-4-29下午3:15:22
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-29      chenmin          v1.0.0                                    修改原因<br>
	 */
	public void deploy(ZipInputStream zipInputStream);
	
	/**
	 * @Function getProcessImageResourceAsStream 
	 * @Description 获取指定流程定义的流程图片
	 *
	 * @param processDefinitionId
	 * @return InputStream
	 * @throws 
	 *
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-4-29下午3:16:39
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date         Author          Version            Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-4-29      chenmin          v1.0.0                                    修改原因<br>
	 */
	public InputStream getProcessImageResourceAsStream(String processDefinitionId);
}
