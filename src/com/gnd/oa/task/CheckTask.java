package com.gnd.oa.task;

import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;

import com.danga.MemCached.SockIOPool;
import com.gnd.oa.memcache.ValidateFactory;
import com.gnd.oa.memcache.impl.SocketObjectPool;
import com.gnd.oa.memcache.policy.LoadBalanceFactory;
import com.gnd.oa.memcache.policy.Server;

@SuppressWarnings("rawtypes")
public class CheckTask extends TimerTask {
	private static transient Log log = LogFactory.getLog(CheckTask.class);

	@Resource
	private SockIOPool memCachedPool;
	
	public void run() {
		Object[] ok_servers;
		Iterator iter;
		Thread.currentThread().setName("memcached检查线程");

		if (log.isInfoEnabled())
			log.info("检查线程开始工作");

		try {
			ok_servers = LoadBalanceFactory.getInstance().getArivableServers();
			List all_servers = LoadBalanceFactory.getInstance().getAllServers();
			for (iter = all_servers.iterator(); iter.hasNext();) {
				Server item = (Server) iter.next();
				if (!(isArivableServer(item, ok_servers))) {
					ObjectPool objectPool = LoadBalanceFactory.getInstance()
							.makePool(item);
					try {

						if (ValidateFactory.validate(memCachedPool)) {
							LoadBalanceFactory.getInstance()
									.addPool(objectPool);
							if (log.isInfoEnabled())
								log.info("检查线程发现连接池:" + objectPool.toString()
										+ "验证成功");
						} else {
							throw new Exception("SockIOPool:" + memCachedPool + ",验证失败");
						}
					} catch (Exception ex1) {
						if (objectPool != null) {
							if (log.isInfoEnabled())
								log.info("检查线程发现连接池:" + objectPool.toString()
										+ "验证失败", ex1);

							objectPool.clear();
							objectPool = null;
						}
					} finally {
						if ((memCachedPool != null) && (objectPool != null))
							objectPool.returnObject(memCachedPool);
					}
				}
			}
		} catch (Throwable ex) {
			log.error("检查线程检查时候发现外层异常", ex);
		}

		if (log.isInfoEnabled())
			log.info("检查线程完成工作");
	}

	private boolean isArivableServer(Server server, Object[] objects) {
		boolean rtn = false;
		for (int i = 0; i < objects.length; ++i) {
			SocketObjectPool objSocketObjectPool = (SocketObjectPool) objects[i];
			if ((objSocketObjectPool.getHost().equalsIgnoreCase(server
					.getHost()))
					&& (objSocketObjectPool.getPort() == server.getPort())) {
				rtn = true;
				break;
			}
		}
		return rtn;
	}

	public static void main(String[] args) throws Exception {
		Timer timer = new Timer();
		timer.schedule(new CheckTask(), 1000L, 5000L);
	}
}