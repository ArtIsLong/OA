package com.gnd.oa.memcache.check;

import java.util.Properties;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.danga.MemCached.MemCachedClient;
import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.util.SpringContextHolder;

public class Check {

	public static void main(String[] args) throws Exception {
		new ClassPathXmlApplicationContext("applicationContext.xml");
		MemCachedClient client = (MemCachedClient)SpringContextHolder.getBean("memCachedClient");
		Properties properties = PropertiesConfigure.getProperties();
		client.set(String.valueOf(properties.get("server.validate.key")),properties.get("server.validate.value"));
	}
}
