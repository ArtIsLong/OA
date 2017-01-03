package com.gnd.oa.memcache.check;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gnd.oa.cfg.PropertiesConfigure;
import com.gnd.oa.memcache.impl.MemcachedBufferedDriver;

public class Check1 {
	private static transient Log log = LogFactory.getLog(Check1.class);

	public static void main(String[] args) throws Exception {
		Socket socket = null;
		
		try{
			//建立缓存服务器连接
			socket = new Socket();
			socket.setTcpNoDelay(true);
			socket.setKeepAlive(true);
			socket.connect(new InetSocketAddress(InetAddress.getByName("localhost"), 11211), 30 * 1000);
			socket.setSoTimeout(30 * 1000);
			//获取输入输出流
			/**
			 * 输出流：向memcached服务器中输出，可以借此执行相关命令
			 * 输入流：memcached服务器执行完命令的结果可以通过输入流获取，借此判断命令是否执行成功
			 */
			BufferedInputStream in = new BufferedInputStream(
					socket.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(
					socket.getOutputStream());

			Properties properties = PropertiesConfigure.getProperties("server.validate", true);
			String key = properties.getProperty("key");
			Object obj = properties.getProperty("value");
			
			//对key进行编码	  unicode->UTF-8
			key = URLEncoder.encode(key, MemcachedBufferedDriver.ENCODING_TYPE);
			int flag = 0;

			byte[] bs = object2Byte(obj);

			if (bs.length > MemcachedBufferedDriver.COMPRESS_THRESHOLD) {
				log.error("key为" + key + "的值对象数据超过"
						+ MemcachedBufferedDriver.COMPRESS_THRESHOLD + "字节,达到"
						+ bs.length + "字节");
			}

			if (bs.length > MemcachedBufferedDriver.COMPRESS_THRESHOLD) {
				ByteArrayOutputStream bos = new ByteArrayOutputStream(bs.length);
				GZIPOutputStream gos = new GZIPOutputStream(bos);
				gos.write(bs, 0, bs.length);
				gos.finish();

				bs = bos.toByteArray();
				flag |= 2;
			}

			if (bs.length >= MemcachedBufferedDriver.MAX_BYTE_SIZE) {
				throw new Exception("不能超过"
						+ MemcachedBufferedDriver.MAX_BYTE_SIZE + "字节");
			}
			
			//执行set命令
			out.write(MemcachedBufferedDriver.BYTE_SET);
			out.write(key.getBytes());
			out.write(MemcachedBufferedDriver.BYTE_SPACE);
			out.write(String.valueOf(flag).getBytes());
			out.write(MemcachedBufferedDriver.BYTE_SPACE);
			out.write("0".getBytes());
			out.write(MemcachedBufferedDriver.BYTE_SPACE);
			out.write(String.valueOf(bs.length).getBytes());
			out.write(MemcachedBufferedDriver.BYTE_CRLF);

			out.write(bs);
			out.write(MemcachedBufferedDriver.BYTE_CRLF);
			out.flush();
			
			//返回命令的执行结果
			String ret = readLine(in);
			if (!"STORED".equals(ret)) {
				throw new Exception("set出现错误:" + ret);
			}
			
			System.out.println("####################################");
			System.out.println("All Items have been Checked.");
			System.out.println("Memcached Server Running Status: OK");
			System.out.println("####################################");
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error(e.getMessage(), e);
			}
			throw e;
		} finally {
			if(socket != null){
				socket.close();
				socket = null;
			}
		}
	}

	private static String readLine(InputStream in) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		boolean eol = false;
		byte[] b = new byte[1];
		while (in.read(b, 0, 1) != -1) {
			if (b[0] == 13) {
				eol = true;
			} else {
				if ((eol) && (b[0] == 10)) {
					break;
				}
				eol = false;
			}
			bos.write(b, 0, 1);
		}
		if (bos.size() == 0)
			return null;
		return bos.toString().trim();
	}
	
	private static byte[] object2Byte(Object o) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		(new ObjectOutputStream(b)).writeObject(o);
		return b.toByteArray();
	}
}
