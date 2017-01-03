package com.gnd.oa.view.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.danga.MemCached.SockIOPool;
import com.gnd.oa.base.BaseAction;
import com.gnd.oa.domain.User;
import com.gnd.oa.memcache.ValidateFactory;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class MemcacheAction extends BaseAction {

	private static transient Log log = LogFactory.getLog(MemcacheAction.class);

	@Resource
	private SockIOPool sockIOPool;

	private static final long serialVersionUID = 1L;

	public String onLineCount() throws Exception {
		if (ValidateFactory.validate(sockIOPool)) {
			String onLine = (String) memcachedService.get("onLine");
			log.info("当前在线人数：" + onLine);
			PrintWriter printWriter = response.getWriter();
			printWriter.write(onLine);
			printWriter.flush();
			printWriter.close();
		}
		return "bottom";
	}

	public String onLineUserList() throws Exception {
		List<User> users = new ArrayList<User>();
		if (ValidateFactory.validate(sockIOPool)) {
			Map<String, Object> keys = memcachedService.getAllKeys();
			if (keys != null && keys.size() != 0) {
				Set<String> keySet = keys.keySet();
				for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
					Object obj = memcachedService.get(it.next());
					if (obj instanceof User) {
						users.add((User) obj);
					}
				}
			}
		}
		ActionContext.getContext().put("userList", users);
		return "onLineUserList";
	}
}
