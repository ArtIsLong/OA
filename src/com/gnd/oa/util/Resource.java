package com.gnd.oa.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
@SuppressWarnings({"rawtypes"})
public class Resource
{
  public static String loadFileFromClassPath(String filePath)
    throws Exception
  {
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String tmp = null;
    StringBuffer sb = new StringBuffer();
    while (true) {
      tmp = br.readLine();
      if (tmp == null) break;
      sb.append(tmp);
      sb.append("\n");
    }

    return sb.toString();
  }

  public static URL loadURLFromClassPath(String filePath)
    throws Exception
  {
    return Thread.currentThread().getContextClassLoader().getResource(filePath);
  }

  public static InputStream loadInputStreamFromClassPath(String filePath)
    throws Exception
  {
    return Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
  }

  public static PropertiesConfiguration loadPropertiesConfigurationFromClassPath(String filePath)
    throws Exception
  {
    PropertiesConfiguration pc = new PropertiesConfiguration();
    pc.load(loadInputStreamFromClassPath(filePath));
    return pc;
  }

  public static Properties loadPropertiesFromClassPath(String filePath)
    throws Exception
  {
    Properties pc = new Properties();
    pc.load(loadInputStreamFromClassPath(filePath));
    return pc;
  }

  public static Properties loadPropertiesFromClassPath(String filePath, String prefix, boolean isDiscardPrefix)
    throws Exception
  {
    Properties rtn = new Properties();
    Properties pc = loadPropertiesFromClassPath(filePath);
    Set key = pc.keySet();
    for (Iterator iter = key.iterator(); iter.hasNext(); ) {
      String element = (String)iter.next();
      if (StringUtils.indexOf(element, prefix) != -1)
        if (isDiscardPrefix == true) {
          rtn.put(StringUtils.replace(element, prefix + ".", "").trim(), pc.get(element));
        }
        else
          rtn.put(element, pc.get(element));

    }

    return rtn;
  }

  public static void main(String[] args) throws Exception {
    InputStream is = loadInputStreamFromClassPath("config.properties");
    PropertiesConfiguration pc = new PropertiesConfiguration();
    pc.load(is);
  }
}