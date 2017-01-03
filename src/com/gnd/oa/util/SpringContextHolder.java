package com.gnd.oa.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 以静态变量Spring ApplicationContext，可在任何代码任何地方任何时候中取出ApplicationContext
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Component
public class SpringContextHolder implements ApplicationContextAware {

	private static ApplicationContext applicationContext;
	
	/**
	 * 实现ApplicationContextAware接口的context注入函数，将其存入静态变量
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.applicationContext = applicationContext;
	}
	
	/**
	 * 取得存储在静态变量中的ApplicationContext
	 * @throws IllegalStateException 
	 */
	public static ApplicationContext getApplicationContext() throws Exception{
		checkApplicationContext();
		return applicationContext;
	}
	
	/**
	 * 从静态变量ApplicationContext中取得Bean，自动转型为所赋值对象的类型
	 * @throws IllegalStateException 
	 */
	public static <T> T getBean(String name) throws Exception{
		checkApplicationContext();
		return (T)applicationContext.getBean(name);
	}
	
	/**
	 * 从静态变量ApplicationContext中取得Bean，自动转型为所赋值对象的类型
	 * 
	 * 如果有多个Bean符合Class，取出第一个
	 * @throws IllegalStateException 
	 */
	public static <T> T getBean(Class<T> clazz) throws Exception{
		checkApplicationContext();
		Map beanMaps = applicationContext.getBeansOfType(clazz);
		
		if(beanMaps != null && !beanMaps.isEmpty()){
			return (T)beanMaps.values().iterator().next();
		}else{
			return null;
		}
	}
	
	private static void checkApplicationContext() throws Exception{
		if(applicationContext == null){
			throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
		}
	}
}
