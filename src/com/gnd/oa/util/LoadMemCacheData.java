package com.gnd.oa.util;

import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.gnd.oa.cfg.PropertiesConfigure;

/**
 * 根据memcache.properties文件中的IS_USE_DB判读是否使用缓存服务器，从而提前加载缓存数据
 */
public class LoadMemCacheData implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce) {
		Properties properties = PropertiesConfigure.getProperties();
		String IS_USE_DB = (String) properties.get("is_use_db");
		if("false".equals(IS_USE_DB)){
			
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
