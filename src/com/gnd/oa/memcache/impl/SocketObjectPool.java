package com.gnd.oa.memcache.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;

import com.gnd.oa.cfg.PropertiesConfigure;

public class SocketObjectPool extends GenericObjectPool
  implements ObjectPool
{
  private static transient Log log = LogFactory.getLog(SocketObjectPool.class);
  private static int maxIdle = 8;
  private static int minIdle = 8;
  private static int maxActive = 8;
  private static int maxWait = -1;
  private SocketPoolableObjectFactory objFactory = null;

  public SocketObjectPool(SocketPoolableObjectFactory objFactory)
  {
    super(objFactory);
    setMaxIdle(maxIdle);
    setMinIdle(minIdle);
    setMaxActive(maxActive);
    setMaxWait(maxWait);

    this.objFactory = objFactory;
    
    log.info("memcached连接池初始化......");
  }

  public String getHost() {
    return this.objFactory.getHost(); }

  public int getPort() {
    return this.objFactory.getPort(); }

  public int getTimeoutSeconds() {
    return this.objFactory.getTimeoutSeconds();
  }

  public String toString() {
    return "[host=" + this.objFactory.getHost() + ",port=" + this.objFactory.getPort() + ",timeoutSeconds=" + this.objFactory.getTimeoutSeconds() + "] SocketObjectPool ";
  }

  static
  {
    try
    {
      String strMin = PropertiesConfigure.getProperties("server.conn", true).getProperty("min");
      String strMax = PropertiesConfigure.getProperties("server.conn", true).getProperty("max");
      minIdle = Integer.parseInt(strMin);
      maxIdle = Integer.parseInt(strMax);
      maxActive = maxIdle;
    }
    catch (Throwable ex) {
      log.error("转换出错,取默认配置", ex);
      maxIdle = 8;
      minIdle = 8;
      maxActive = 8;
      maxWait = -1;
    }
  }
}