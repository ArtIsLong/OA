package com.gnd.oa.test;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.PoolableObjectFactory;
import org.hibernate.SessionFactory;
import org.jbpm.api.ProcessEngine;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.domain.User;

public class SpringTest {

	private ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");

	@Test
	public void testBean() throws Exception {
		TestAction testAction = (TestAction) ac.getBean("testAction");
		System.out.println(testAction);
	}

	// 测试SessionFactory
	@Test
	public void testSessionFactory() throws Exception {
		SessionFactory sessionFactory = (SessionFactory) ac.getBean("sessionFactory");
		System.out.println(sessionFactory);
	}
	
	@Test
	public void testDataSource() throws Exception {
		System.out.println(ac.getBean("dataSource"));
	}
	
	// 测试事务
	@Test
	public void testTransaction() throws Exception {
		TestService testService = (TestService) ac.getBean("testService");
		testService.saveTwoUsers();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testMemcached() throws Exception{
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		SockIOPool sockIOPool = (SockIOPool) ac.getBean("memCachedPool");
		PoolableObjectFactory socketPoolableObjectFactory = (PoolableObjectFactory) ac.getBean("socketPoolableObjectFactory");
		ObjectPool socketObjectPool = (ObjectPool) ac.getBean("socketObjectPool");
		System.out.println("s=" + sockIOPool.getInitConn());
		System.out.println("s=" + (Socket)socketPoolableObjectFactory.makeObject());
		System.out.println("s=" + socketObjectPool.borrowObject());
		
		MemCachedClient client = (MemCachedClient) ac.getBean("memCachedClient");
		
		//开始设值
		client.set("name", " string ");
		client.set("int", 5);
		client.set("double", 5.5);
		
		User user = new User();
		user.setId(1l);
		user.setName("admin");
		user.setPassword("admin");
		user.setLoginName("admin");
		
		client.set("user", user);
		
		List<User> users = new ArrayList<User>();
		for (long i = 0; i < 3; i++) {
			User uu = new User();
			uu.setId(i);
			uu.setName("admin" + i);
			uu.setPassword("admin" + i);
			uu.setLoginName("admin" + i);
			users.add(uu);
		}
		client.set("users", users);
		try {
			Thread.sleep(50);
			
			//开始取值
			String name = (String)client.get("name");
			int i = (Integer)client.get("int");
			double d = (Double)client.get("double");
			User u = (User)client.get("user");
			users = (List<User>)client.get("users");
			System.out.println(name + i + d+u+users);
			System.out.println(client.get("IsReady"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testProperties() throws IOException{
//		Properties prop = new Properties();
//		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("memcache.properties"));
		System.setProperty("memcached.configure", "jdbc.properties");
		Properties prop = PropertiesConfigure.getProperties();
//		String flag = (String)prop.get("is_use_db");
//		System.out.println(flag);
//		String config = System.getProperty("memcached.configure");
		System.out.println(prop.get("jdbc.driver"));
	}
	
	@Test
	public void test(){
		System.out.println(DigestUtils.md5Hex("21232f297a57a5a743894a0e4a801fc3"));
	}
	
	@Test
	public void testprocessEngine(){
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		ProcessEngine processEngine = (ProcessEngine) ac.getBean("processEngine");
		System.out.println(processEngine);
	}
	
	@Test
	public void testUser(){
		System.out.println(User.class.getSimpleName());
	}
}
