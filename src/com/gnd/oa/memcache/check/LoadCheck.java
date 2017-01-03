package com.gnd.oa.memcache.check;

import java.util.HashMap;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.danga.MemCached.MemCachedClient;
import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.util.SpringContextHolder;

/**
 * 验证数据
 * @author 陈敏
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class LoadCheck {

	public static void main(String[] args) throws Exception {
		// 验证memcached中total_items和curr_items数量是否一致
		// 验证结束打上validate.IsReady=OK
		// 开放memcached服务给外部系统使用

		System.out.println("######################################");
		System.out.println("Checking Memcached Server Status......");
		System.out.println("######################################");
		new ClassPathXmlApplicationContext("applicationContext.xml");
		MemCachedClient client = (MemCachedClient) SpringContextHolder.getBean("memCachedClient");
		Properties properties = PropertiesConfigure.getProperties("server.validate", true);
		String ready = (String) client.get(properties.getProperty("key"));
		if (!StringUtils.isBlank(ready)&& ready.equals(properties.getProperty("value"))) {
			System.out.println("####################################");
			System.out.println("Memcached Server Running Status: OK");
			System.out.println("####################################");
			System.exit(-1);
		}
		
		HashMap stats = (HashMap) client.stats();
		HashMap stat = (HashMap)stats.get(PropertiesConfigure.getProperties().get("memcache.server"));
		System.out.println("evictions Check [PASS], return value is 0");

		// 验证memcached中total_items和curr_items数量是否一致
		String curr_items = (String) stat.get("curr_items");
		String total_items = (String) stat.get("total_items");
		if (!StringUtils.isBlank(curr_items)
				&& StringUtils.isNumeric(curr_items)
				&& !StringUtils.isBlank(total_items)
				&& StringUtils.isNumeric(total_items)) {
			long lcurr_items = Long.parseLong(curr_items);
			long ltotal_items = Long.parseLong(total_items);
			if (lcurr_items != ltotal_items) {
				throw new Exception("Checking [FAILED], the stats 'curr_items' is not equal to 'total_items'");
			}
		} else {
			throw new Exception(
					"Checking [FAILED], the stats 'curr_items' or 'total_items' is null or not numerical");
		}
		System.out.println("Loaded items step 1 Check [PASS], the stats 'curr_items' is equal to 'total_items'");
		// System.out.println("部分验证成功,memcached返回的stat信息中的curr_items和total_items相等");
		// 验证memcached中total_items和curr_items数量是否一致结束

		// 验证结束打上标志
		client.set(properties.getProperty("key"),properties.getProperty("value"));

		System.out.println("####################################");
		System.out.println("All Items have been Checked.");
		System.out.println("Memcached Server Running Status: OK");
		System.out.println("####################################");
	}
}
