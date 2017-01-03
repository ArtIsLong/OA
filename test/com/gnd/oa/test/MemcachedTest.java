package com.gnd.oa.test;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gnd.oa.memcache.interfaces.IMemcachedService;
import com.gnd.oa.util.SpringContextHolder;

public class MemcachedTest {

	@Test
	public void test01() throws Exception{
		new ClassPathXmlApplicationContext("applicationContext.xml");
		IMemcachedService memcachedService = (IMemcachedService) SpringContextHolder.getBean("memcachedService");
		System.out.println(memcachedService.getAllKeys());
	}
	
	@Test
	public void test02() throws Exception {
		String mapsValue = "3054538553";
//		Long maxValue = (long) Integer.MAX_VALUE;
		Long l = Long.parseLong(mapsValue);
//		int limit = Integer.parseInt(mapsValue);
		System.out.println(Integer.MAX_VALUE < l);
	}
}
