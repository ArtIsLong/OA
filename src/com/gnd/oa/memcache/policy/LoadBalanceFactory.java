package com.gnd.oa.memcache.policy;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;

import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.memcache.impl.SocketObjectPool;
import com.gnd.oa.memcache.impl.SocketPoolableObjectFactory;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class LoadBalanceFactory {
	private static transient Log log = LogFactory
			.getLog(LoadBalanceFactory.class);
	private static LoadBalanceFactory instance = null;
	private IPolicy objIPolicy = null;
	private List servers = new ArrayList();
	private static Boolean isInit = Boolean.FALSE;
	private String serverName = null;

	public static LoadBalanceFactory getInstance() throws Exception {
		if (instance == null)
			synchronized (isInit) {
				if (isInit.equals(Boolean.FALSE)) {
					instance = new LoadBalanceFactory();
					isInit = Boolean.TRUE;
				}
			}

		return instance;
	}

	private LoadBalanceFactory() throws Exception {
		synchronized (this) {
			try {
				this.objIPolicy = ((IPolicy) Class.forName(
						PropertiesConfigure.getProperties().getProperty(
								"server.policy")).newInstance());
			} catch (Exception ex) {
				log.error("转换出错,取默认配置", ex);
				this.objIPolicy = new RoundRobinPolicy();
			}

			int timeoutSeconds = 30;
			String strTimeoutSeconds = PropertiesConfigure.getProperties()
					.getProperty("server.conn.timeout");
			if ((!(StringUtils.isBlank(strTimeoutSeconds)))
					&& (StringUtils.isNumeric(strTimeoutSeconds))) {
				timeoutSeconds = Integer.parseInt(strTimeoutSeconds);
			}

			String list = PropertiesConfigure.getProperties().getProperty(
					"server.list");
			String[] tmp = list.split(",");
			for (int i = 0; i < tmp.length; ++i) {
				String[] tmp2 = tmp[i].split(":");
				Server server = new Server(tmp2[0], Integer.parseInt(tmp2[1]),
						timeoutSeconds);
				this.servers.add(server);
				addPool(makePool(server));
			}

			this.serverName = PropertiesConfigure.getProperties().getProperty(
					"server.name");
			if (StringUtils.isBlank(this.serverName))
				this.serverName = "";
		}
	}

	public ObjectPool makePool(Server server) throws Exception {
		SocketPoolableObjectFactory factory = new SocketPoolableObjectFactory(
				server.getHost() + ":" + server.getPort(),
				server.getTimeoutSeconds());
		ObjectPool pool = new SocketObjectPool(factory);
		return pool;
	}

	public SocketObjectPool getSocketObjectPool() throws Exception {
		if (this.objIPolicy.size() == 0)
			throw new Exception(this.serverName + "均衡工厂中已经没有可使用的pool了");

		SocketObjectPool rtn = (SocketObjectPool) this.objIPolicy
				.getPolicyObject();
		if (rtn == null)
			throw new Exception(this.serverName + "均衡工厂中已经没有可使用的pool了");

		return rtn;
	}

	public void deletePool(SocketObjectPool objSocketObjectPool) {
		synchronized (this.objIPolicy) {
			if (this.objIPolicy.contains(objSocketObjectPool)) {
				if (log.isErrorEnabled()) {
					log.error("删除连接池:" + objSocketObjectPool);
				}

				this.objIPolicy.remove(objSocketObjectPool);
				objSocketObjectPool.clear();
				objSocketObjectPool = null;
			}
		}
	}

	public void addPool(ObjectPool objectPool) {
		synchronized (this.objIPolicy) {
			this.objIPolicy.add(objectPool);
		}
	}

	public List getAllServers() {
		return this.servers;
	}

	public Object[] getArivableServers() {
		return this.objIPolicy.toArray();
	}
}