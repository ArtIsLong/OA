package com.gnd.oa.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpSession;

//session操作类
public class SessionMap {

	private static Map<String, HttpSession> sesMap = new ConcurrentHashMap<String, HttpSession>();

	private SessionMap() {
	}

	private static class MapUtilsN {
		private static SessionMap sessionMap = new SessionMap();
	}

	public static SessionMap getInstance() {
		return MapUtilsN.sessionMap;
	}

	public void addMap(String sessionId, HttpSession session) {
		if (!sesMap.containsKey(sessionId)) {
			sesMap.put(sessionId, session);
		}
	}

	public void removeMap(String sessionId) {
		sesMap.remove(sessionId);
	}

	public int getSize() {
		return sesMap.size();
	}

	public HttpSession getSession(String key) {
		return sesMap.get(key);
	}
}