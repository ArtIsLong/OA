package com.gnd.oa.cfg;

import java.util.Properties;

import com.gnd.oa.util.Resource;

/**
 * 对应配置的对象（对应default.properties）
 * 
 * @author chenmin
 * 
 */
public class Configuration {

	private static int pageSize;

	static {
		// TODO 读取配置default.properties文件，并初始化所有配置
		try {
			Properties prop = Resource.loadPropertiesFromClassPath("default.properties");
			pageSize = Integer.parseInt(String.valueOf(prop.get("pageSize")));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static int getPageSize() {
		return pageSize;
	}

}
