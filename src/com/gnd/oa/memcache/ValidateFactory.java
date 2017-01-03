package com.gnd.oa.memcache;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.danga.MemCached.SockIOPool.SockIO;
import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.memcache.interfaces.IValidate;
import com.gnd.oa.util.SpringContextHolder;

public class ValidateFactory {
	private static transient Log log = LogFactory.getLog(ValidateFactory.class);

	public static boolean validate(SockIOPool sockIOPool) {
		boolean rtn = true;
		boolean flag = false;
		if (sockIOPool != null && sockIOPool.getInitConn() != 0) {
			String[] servers = sockIOPool.getServers();
			for (int i = 0; i < servers.length; i++) {
				SockIO sockIO = sockIOPool.getConnection(servers[i]);
				if (sockIO == null || sockIO.getHost() == null
						|| "".equals(sockIO.getHost())) {
					log.error(servers[i] + "连接失败");
					flag = true;
				}else{
					sockIO.close();
					flag = false;
				}
			}
			if(flag){
				return rtn;
			}
		} else {
			log.error("SockIOPool连接池已经关闭或者不存在");
			return rtn;
		}
		
		try {
			Properties properties = PropertiesConfigure.getProperties(
					"server.validate", true);
			if ((properties == null) || (properties.size() == 0)) {
				rtn = true;
			} else if (!(StringUtils.isBlank(properties.getProperty("class")))) {
				IValidate objIValidate = (IValidate) Class.forName(
						properties.getProperty("class").trim()).newInstance();
				rtn = objIValidate.validate(
						(MemCachedClient) SpringContextHolder
								.getBean("memCachedClient"), properties);
			} else {
				if (log.isDebugEnabled()){
					log.debug("不需要验证");
				}
				rtn = true;
			}
			log.info("SockIOPool正常");
		} catch (Exception ex) {
			if (log.isErrorEnabled()) {
				log.error("验证发生错误", ex);
			}
			rtn = false;
		}
		return rtn;
	}
}