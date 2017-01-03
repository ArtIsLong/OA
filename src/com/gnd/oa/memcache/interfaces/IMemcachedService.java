package com.gnd.oa.memcache.interfaces;

import java.util.Map;

@SuppressWarnings("rawtypes")
public interface IMemcachedService {

	public boolean delete(String key);
	
	public void set(String key, Object obj);
	
	public Object get(String key);
	
	public boolean deleteWithType(String key, Class clazz);
	
	public void setWithType(String key, Object obj);
	
	public void setWithType(String key, Object obj,Class clazz);
	
	public Object getWithType(String key, Class clazz);
	
	public Object[] getWithType(String[] keys, Class clazz);
	
	public void setWithType(String[] keys, Class clazz, Object[] objs);
	
	public Map<String, Object> getAllKeys();
}
