package com.gnd.oa.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Properties;

import com.gnd.oa.cfg.PropertiesConfigure;

public class SocketUtil {

	public static Socket getSocket() throws Exception{
		Socket socket = null;
		//建立缓存服务器连接
		socket = new Socket();
		socket.setTcpNoDelay(true);
		socket.setKeepAlive(true);
		Properties prop = PropertiesConfigure.getProperties();
		String hostIp = prop.getProperty("memcache.server");
		String[] host = hostIp.split(":");
		int timeout = Integer.parseInt(prop.getProperty("memcache.timeout"));
		socket.connect(new InetSocketAddress(InetAddress.getByName(host[0]), Integer.parseInt(host[1])), timeout * 1000);
		socket.setSoTimeout(timeout * 1000);
		return socket;
	}
}
