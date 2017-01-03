package com.gnd.oa.view.listener;

import java.util.Collection;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.gnd.oa.domain.Privilege;
import com.gnd.oa.service.PrivilegeService;

public class InitListener implements ServletContextListener {
	private static transient Log log = LogFactory.getLog(InitListener.class);
	public void contextInitialized(ServletContextEvent sce) {
		// 获取容器与相关的Service对象
		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
		PrivilegeService privilegeService = (PrivilegeService) ac.getBean("privilegeServiceImpl");

		// 准备数据：topPrivilegeList
		List<Privilege> topPrivilegeList = privilegeService.findTopList();
		sce.getServletContext().setAttribute("topPrivilegeList", topPrivilegeList);
		log.info("------------> 已准备数据  topPrivilegeList<------------");
		
		// 准备数据: allprivilegeUrls
		Collection<String> allprivilegeUrls = privilegeService.getAllPrivilegeUrls();
		sce.getServletContext().setAttribute("allprivilegeUrls", allprivilegeUrls);
		log.info("------------> 已准备数据 allprivilegeUrls <------------");
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}
}