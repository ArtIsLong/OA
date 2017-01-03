package com.gnd.oa.view.interceptor;

import com.gnd.oa.domain.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor{

	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//获取信息
		//获取当前登录用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		String namespace = invocation.getProxy().getNamespace();
		String actionName = invocation.getProxy().getActionName();
		
		String privUrl = namespace + actionName;
		
		//如果未登录就转到登录页面
		if(user == null){
			if(privUrl.startsWith("/user_login")){
				return invocation.invoke();
			}else{
				return "loginUI";
			}
		}
		
		//如果已登录,就判断权限
		if(user.hasPrivilegeByURL(privUrl)){
			return invocation.invoke();
		}else{
			return "noPrivilegeError";
		}
	}
}
