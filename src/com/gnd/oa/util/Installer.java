package com.gnd.oa.util;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.gnd.oa.domain.Privilege;
import com.gnd.oa.domain.User;

@Component
public class Installer {

	@Resource
	private SessionFactory sessionFactory;

	@Transactional
	public void install() {
		Session session = sessionFactory.openSession();

		// 保存超级管理员用户
		User user = new User();
		user.setName("超级管理员");
		user.setLoginName("admin");
		user.setPassword(DigestUtils.md5Hex("admin"));
		session.save(user);

		// 保存权限数据
		Privilege menu, menu1, menu2, menu3, menu4, menu5;
		menu = new Privilege("系统管理", null, null);
		menu1 = new Privilege("岗位管理", "/role_list", menu);
		menu2 = new Privilege("部门管理", "/department_list", menu);
		menu3 = new Privilege("用户管理", "/user_list", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);

		session.save(new Privilege("岗位列表", "/role_list", menu1));
		session.save(new Privilege("岗位删除", "/role_delete", menu1));
		session.save(new Privilege("岗位添加", "/role_add", menu1));
		session.save(new Privilege("岗位修改", "/role_edit", menu1));

		session.save(new Privilege("部门列表", "/department_list", menu2));
		session.save(new Privilege("部门删除", "/department_delete", menu2));
		session.save(new Privilege("部门添加", "/department_add", menu2));
		session.save(new Privilege("部门修改", "/department_edit", menu2));

		session.save(new Privilege("用户列表", "/user_list", menu3));
		session.save(new Privilege("用户删除", "/user_delete", menu3));
		session.save(new Privilege("用户添加", "/user_add", menu3));
		session.save(new Privilege("用户修改", "/user_edit", menu3));
		session.save(new Privilege("初始化密码", "/user_initPassword", menu3));

		menu = new Privilege("网上交流", null, null);
		menu1 = new Privilege("论坛管理", "/forumManage_list", menu);
		menu2 = new Privilege("论坛", "/forum_list", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);

		menu = new Privilege("审批流转", null, null);
		menu1 = new Privilege("审批流程管理", "/processDefinition_list", menu);
		menu2 = new Privilege("申请模板管理", "/applicationTemplate_list", menu);
		menu3 = new Privilege("起草申请", "/flowAction_applicationTemplateList", menu);
		menu4 = new Privilege("待我审批", "/flowAction_myTaskList", menu);
		menu5 = new Privilege("我的申请查询", "/flowAction_myApplicationList", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);

		menu = new Privilege("个人设置", null, null);
		menu1 = new Privilege("个人信息", "/user_editUserUI", menu);
		menu2 = new Privilege("密码修改", "/user_editPasswordUI", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);

		menu = new Privilege("实用工具", null, null);
		menu1 = new Privilege("学校网站", "http://www.gsau.edu.cn", menu);
		menu2 = new Privilege("火车时刻", "http://qq.ip138.com/train/", menu);
		menu3 = new Privilege("飞机航班", "http://www.airchina.com.cn/", menu);
		menu4 = new Privilege("邮编/区号", "http://www.ip138.com/post/", menu);
		menu5 = new Privilege("国际时间", "http://www.timedate.cn/", menu);
		session.save(menu);
		session.save(menu1);
		session.save(menu2);
		session.save(menu3);
		session.save(menu4);
		session.save(menu5);

		//menu = new Privilege("知识管理", "LanDisk_Folder/list.html", null);
		//session.save(menu);
	}

	public static void main(String[] args) {
		System.out.println("正在执行安装...");
		
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		Installer installer = (Installer) ac.getBean("installer");
		installer.install();
		
		System.out.println("== 安装完毕 ==");
	}
}
