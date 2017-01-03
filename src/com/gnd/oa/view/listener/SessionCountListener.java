package com.gnd.oa.view.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionCountListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent se) {
		/*SockIOPool memCachedPool = null;
		try {
			memCachedPool = (SockIOPool)SpringContextHolder.getBean("memCachedPool");
			if(!ValidateFactory.validate(memCachedPool)){
				HttpServletRequest request = ServletActionContext.getRequest();
				
				HttpSession session = se.getSession();
				Object obj = session.getAttribute("onLine");
				Object userObj = session.getAttribute(User.USERNAME);
				if(obj != null){
					session.setAttribute("onLine", Integer.parseInt(String.valueOf(obj)) + 1);
				}else{
					session.setAttribute("onLine",1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		/*SockIOPool memCachedPool = null;
		IMemcachedService memcachedService = null;
		try {
			memCachedPool = (SockIOPool)SpringContextHolder.getBean("memCachedPool");
			memcachedService = (IMemcachedService)SpringContextHolder.getBean("memcachedService");
			
			if(!ValidateFactory.validate(memCachedPool)){
				HttpSession session = se.getSession();
				Object obj = session.getAttribute("onLine");
				if(obj != null){
					session.setAttribute("onLine", Integer.parseInt(String.valueOf(obj)) - 1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

}
