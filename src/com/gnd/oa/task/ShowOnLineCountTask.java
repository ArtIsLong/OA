package com.gnd.oa.task;

import java.util.TimerTask;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.gnd.oa.memcache.interfaces.IMemcachedService;

public class ShowOnLineCountTask extends TimerTask {
	
	private IMemcachedService memcachedService;
	
	public void setMemcachedService(IMemcachedService memcachedService) {
		this.memcachedService = memcachedService;
	}

	@Override
	public void run() {
		Long onLine = (Long)memcachedService.get("onLine");
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		request.setAttribute("onLine", onLine);
		try {
			request.getRequestDispatcher("memcache_onLineCount").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
