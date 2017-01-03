package com.gnd.oa.task;

import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.SockIOPool;
import com.gnd.oa.memcache.ValidateFactory;

/**
 * 实时检测memcached状态
 */
public class CheckMemcachedTask extends TimerTask {
	private static transient Log log = LogFactory
			.getLog(CheckMemcachedTask.class);

	@Resource
	private SockIOPool memCachedPool;
	
	@Override
	public void run() {
		Thread.currentThread().setName("memcached检查线程");
		if (log.isInfoEnabled()){
			log.info("检查线程开始工作");
		}
		try {
			boolean rtn = ValidateFactory.validate(memCachedPool);
			if(rtn){
				log.info("memcached连接正常！");
			}
		} catch (Exception e) {
			if(log.isErrorEnabled()){
				log.error(e.getMessage(), e);
			}
		}
	}

}
