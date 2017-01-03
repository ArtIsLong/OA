package com.gnd.oa.memcache.impl;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.PoolableObjectFactory;

public class SocketPoolableObjectFactory implements PoolableObjectFactory {
	private static transient Log log = LogFactory
			.getLog(SocketPoolableObjectFactory.class);
	private String host = null;
	private int port = 0;
	private int timeoutSeconds = 0;

	public SocketPoolableObjectFactory(String ip, int timeoutSeconds) {
		this.host = ip.split(":")[0];
		this.port = Integer.parseInt(ip.split(":")[1]);
		this.timeoutSeconds = timeoutSeconds;
		log.info("memcached连接池工厂初始化......");
	}

	public String getHost() {
		return this.host;
	}

	public int getPort() {
		return this.port;
	}

	public int getTimeoutSeconds() {
		return this.timeoutSeconds;
	}

	public Object makeObject() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("开始构造对象");
		}

		Socket socket = null;
		try {
			socket = new Socket();
			socket.setTcpNoDelay(true);
			socket.setKeepAlive(true);
			socket.connect(
					new InetSocketAddress(InetAddress.getByName(this.host),
							this.port), this.timeoutSeconds * 1000);
			socket.setSoTimeout(this.timeoutSeconds * 1000);
		} catch (Exception ex) {
			if(log.isErrorEnabled()){
				log.error("构造socket对象失败", ex);
			}
			throw ex;
		}

		try {
//			if (!(ValidateFactory.validate(socket))){
//				throw new Exception("Socket:" + socket + ",验证失败");
//			}
		} catch (Exception ex) {
			if(log.isErrorEnabled()){
				log.error(ex.getMessage(), ex);
			}
			throw ex;
		} finally {
			if (socket != null) {
				socket.close();
				socket = null;
			}
		}

		return socket;
	}

	public void destroyObject(Object object) throws Exception {
		if (log.isDebugEnabled())
			log.debug("销毁对象:" + object);

		if ((object != null) && (object instanceof Socket))
			((Socket) object).close();
	}

	public boolean validateObject(Object object) {
		if (log.isDebugEnabled())
			log.debug("验证对象");

		return true;
	}

	public void activateObject(Object object) throws Exception {
		if (log.isDebugEnabled())
			log.debug("激活对象:" + object);
	}

	public void passivateObject(Object object) throws Exception {
		if (log.isDebugEnabled())
			log.debug("去激活对象:" + object);
	}
}