package com.gnd.oa.cfg;

import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gnd.oa.util.Resource;


@SuppressWarnings({"rawtypes"})
public final class PropertiesConfigure
{
  private static transient Log log = LogFactory.getLog(PropertiesConfigure.class);
  private static Properties prop = null;

  public static Properties getProperties()
  {
    return prop;
  }

  public static void setProperties(Properties newProperties)
  {
    prop.clear();
	Set keys = newProperties.keySet();
    for (Iterator iter = keys.iterator(); iter.hasNext(); ) {
      Object item = iter.next();
      prop.put(item, newProperties.get(item));
    }
  }

  public static Properties getProperties(String prefix, boolean isDiscardPrefix)
    throws Exception
  {
    Properties rtn = new Properties();
    Set key = prop.keySet();
    for (Iterator iter = key.iterator(); iter.hasNext(); ) {
      String element = (String)iter.next();
      if (StringUtils.indexOf(element, prefix) != -1)
        if (isDiscardPrefix == true) {
          rtn.put(StringUtils.replace(element, prefix + ".", "").trim(), prop.get(element));
        }
        else
          rtn.put(element, prop.get(element));

    }

    return rtn;
  }

  static
  {
    try
    {
      String config = System.getProperty("memcached.configure");
      if (StringUtils.isBlank(config)) {
        log.info("没有发现-Dmemcached.configure属性配置,尝试获取默认的memcache.properties");
        prop = Resource.loadPropertiesFromClassPath("memcache.properties");
      }
      else {
        prop = Resource.loadPropertiesFromClassPath(config);
      }
    }
    catch (Exception ex) {
      throw new RuntimeException("初始化失败", ex);
    }
  }
}