package com.gnd.oa.memcache.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.danga.MemCached.MemCachedClient;
import com.gnd.oa.memcache.interfaces.IValidate;

@SuppressWarnings("unused")
public class NormalValidateImpl implements IValidate {
	private static transient Log log = LogFactory.getLog(NormalValidateImpl.class);

	public boolean validate(MemCachedClient client, Properties properties)
			throws Exception {
		boolean rtn = false;
		try {
			String key = properties.getProperty("key");
			String value1 = properties.getProperty("value");
			String value2 = (String) client.get(key);
			if (value2 == null) {
				throw new Exception("在memcached中没有设置对应的key的value");
			}
			if (value2.equals(value1)) {
				rtn = true;
			}
		} catch (Exception ex) {
			if (log.isErrorEnabled()) {
				log.error("验证出现错误", ex);
			}
			throw ex;
		}
		return rtn;
	}

	
	private Object getObjectFromStream(InputStream in, OutputStream out) throws Exception {
		String cmd = readLine(in);

		if (cmd == null) {
			throw new Exception("读取命令出现返回空指针");
		}

		if (cmd.startsWith("VALUE")) {
			String[] part = StringUtils.split(cmd, " ");
			int flag = Integer.parseInt(part[2]);
			int length = Integer.parseInt(part[3]);

			byte[] bs = new byte[length];

			int count = 0;
			while (count < bs.length)
				count += in.read(bs, count, bs.length - count);

			if (count != bs.length) {
				throw new IOException("读取数据长度错误");
			}

			readLine(in);

			String endstr = readLine(in);
			if ("END".equals(endstr)) {
				if ((flag & 0x2) != 0) {
					GZIPInputStream gzi = new GZIPInputStream(
							new ByteArrayInputStream(bs));
					ByteArrayOutputStream bos = new ByteArrayOutputStream(
							bs.length);

					count = 0;
					byte[] tmp = new byte[2048];
					while ((count = gzi.read(tmp)) != -1) {
						bos.write(tmp, 0, count);
					}

					bs = bos.toByteArray();
					gzi.close();
				}

				return bytes2object(bs);
			}

			throw new IOException("结束标记错误");
		}

		return null;
	}

	private String readLine(InputStream in) throws IOException {
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

	public Object bytes2object(byte[] bytes) throws IOException,
			ClassNotFoundException {
		return new ObjectInputStream(new ByteArrayInputStream(bytes))
				.readObject();
	}
	private byte[] object2Byte(Object o) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		(new ObjectOutputStream(b)).writeObject(o);
		return b.toByteArray();
	}

}