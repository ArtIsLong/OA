package com.gnd.oa.view.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.jbpm.api.ProcessDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class ProcessDefinitionAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String id;
	private String key;

	private File upLoad; // 上传文件
	private InputStream inputStream; // 下载文件

	/** 流程列表 */
	public String list() throws Exception {
		List<ProcessDefinition> processDefinitionList = processDefinitionService.findAllLatestVersions();
		ActionContext.getContext().put("processDefinitionList", processDefinitionList);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		key = URLDecoder.decode(key, "utf-8");
		processDefinitionService.deleteByKey(key);
		return "toList";
	}

	/** 部署页面 */
	public String addUI() throws Exception {
		return "addUI";
	}

	/** 部署 */
	public String add() throws Exception {
		ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(upLoad));
		try {
			processDefinitionService.deploy(zipInputStream);
		} finally {
			zipInputStream.close();
		}
		return "toList";
	}

	/** 查看流程图 */
	public String downloadProcessImage() throws Exception {
		id = URLDecoder.decode(id, "utf-8");
		inputStream = processDefinitionService.getProcessImageResourceAsStream(id);
		return "downloadProcessImage";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public File getUpLoad() {
		return upLoad;
	}

	public void setUpLoad(File upLoad) {
		this.upLoad = upLoad;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}
