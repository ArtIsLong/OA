package com.gnd.oa.memcache.interfaces;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract interface IMemcachedDriver {
	public abstract boolean set(String paramString, Object paramObject)
			throws Exception;

	public abstract Object get(Socket paramSocket, String paramString)
			throws Exception;

	public abstract Map get(Socket paramSocket, String[] paramArrayOfString)
			throws Exception;

	public abstract boolean delete(String paramString) throws Exception;

	public abstract HashMap stats() throws Exception;

	public abstract HashMap stats(Socket paramSocket) throws Exception;
}