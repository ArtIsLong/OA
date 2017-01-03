package com.gnd.oa.memcache.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.gnd.oa.memcache.interfaces.IMemcachedService;

@SuppressWarnings("rawtypes")
public class MemcachedServiceImpl implements IMemcachedService {

	private static final Log log = LogFactory
			.getLog(MemcachedServiceImpl.class);

	private MemCachedClient memCachedClient;

	public void setMemCachedClient(MemCachedClient memCachedClient) {
		this.memCachedClient = memCachedClient;
		// 序列化
		if (this.memCachedClient != null) {
			this.memCachedClient.setPrimitiveAsString(true);
		}
	}

	public boolean delete(String key) {
		return memCachedClient.delete(key);
	}

	public void set(String key, Object obj) {
		if (obj != null) {
			memCachedClient.set(key, obj);
		} else {
			memCachedClient.delete(key);
		}
	}

	public Object get(String key) {
		return memCachedClient.get(key);
	}

	public boolean deleteWithType(String key, Class clazz) {
		return memCachedClient.delete(getKeyWithType(key, clazz));
	}

	protected String getKeyWithType(String key, Class clazz) {
		return clazz.getSimpleName() + "-" + key;
	}

	public void setWithType(String key, Object obj) {
		memCachedClient.set(getKeyWithType(key, obj.getClass()), obj);
	}

	public void setWithType(String key, Object obj, Class clazz) {
		if (obj != null) {
			memCachedClient.set(getKeyWithType(key, clazz), obj);
		} else {
			memCachedClient.delete(getKeyWithType(key, clazz));
		}
	}

	public Object getWithType(String key, Class clazz) {
		return memCachedClient.get(getKeyWithType(key, clazz));
	}

	public Object[] getWithType(String[] keys, Class clazz) {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = getKeyWithType(keys[i], clazz);
		}
		try {
			return memCachedClient.getMultiArray(keys);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
		}
		return new Object[0];
	}

	public void setWithType(String[] keys, Class clazz, Object[] objs) {
		for (int i = 0; i < objs.length; i++) {
			memCachedClient.set(getKeyWithType(keys[i], clazz), objs[i]);
		}
	}

	/**
	 * 获取服务器上的所有key
	 */
	public Map<String, Object> getAllKeys() {
		log.info("开始获取没有挂掉服务器中所有的key......");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, String>> items = memCachedClient.statsItems();
		for (Iterator<String> itemIt = items.keySet().iterator(); itemIt
				.hasNext();) {
			String itemKey = itemIt.next();
			Map<String, String> maps = items.get(itemKey);
			for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt
					.hasNext();) {
				String mapsKey = mapsIt.next();
				String mapsValue = maps.get(mapsKey);
				Long l = Long.parseLong(mapsValue.trim());

				if (l <= Integer.MAX_VALUE) {
					String[] arr = mapsKey.split(":");
					int slabNumber = Integer.valueOf(arr[1].trim());
					int limit = Integer.valueOf(mapsValue.trim());
					Map<String, Map<String, String>> dumpMaps = memCachedClient
							.statsCacheDump(slabNumber, limit);
					for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt
							.hasNext();) {
						String dumpKey = dumpIt.next();
						Map<String, String> allMap = dumpMaps.get(dumpKey);
						for (Iterator<String> allIt = allMap.keySet()
								.iterator(); allIt.hasNext();) {
							String allKey = allIt.next();
							map.put(allKey.trim(),allKey.trim());
						}
					}
				}
			}
		}

		log.info("获取没有挂掉服务器中所有的key完成");
		return map;
	}
}
