package com.gnd.oa.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.base.DaoSupportImpl;
import com.gnd.oa.domain.ApplicationTemplate;
import com.gnd.oa.service.ApplicationTemplateService;

@Service
@Transactional
public class ApplicationTemplateServiceImpl extends
		DaoSupportImpl<ApplicationTemplate> implements
		ApplicationTemplateService {

	@Override
	public void delete(Long id) {
		//删除数据库记录
		ApplicationTemplate applicationTemplate = getById(id);
		getSession().delete(applicationTemplate);
		
		//删除文件
		File file = new File(applicationTemplate.getPath());
		if(file.exists()){
			file.delete();
		}
	}
}
