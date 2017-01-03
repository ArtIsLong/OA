package com.gnd.oa.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.opensymphony.xwork2.ActionContext;

/**
 * 用户
 * 
 * @author 陈敏
 * 
 */
@SuppressWarnings("unchecked")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String USERNAME = "user";
	private Long id;
	private Department department;
	private Set<Role> roles = new HashSet<Role>();

	private String loginName; // 登录名
	private String password; // 密码
	private String name; // 真实姓名
	private String gender; // 性别
	private String phoneNumber; // 电话号码
	private String email; // 电子邮件
	private String description; // 说明
	private String faceIcon; // 图片

	/**
	 * 判断一个字符串中是否包含另一个字符
	 */
	public boolean contain(String str) {
		return str.contains("http");
	}
	
	public boolean isNull(String str){
		return str == null || str.length() == 0 || str.trim() == null;
	}

	/**
	 * 判断用户是否有指定名称的权限
	 */
	public boolean hasPrivilegeByName(String name) {
		// 超级管理员有所有的权限
		if (isAdmin()) {
			return true;
		}

		// 普通用户要判断是否含有这个权限
		for (Role role : roles) {
			for (Privilege priv : role.getPrivileges()) {
				if (priv.getName().equals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断用户是否有指定URL的权限
	 */
	public boolean hasPrivilegeByURL(String privUrl) {
		// 超级管理员有所有的权限
		if (isAdmin()) {
			return true;
		}

		int pos = privUrl.indexOf("?");
		if (pos > -1) {
			privUrl = privUrl.substring(0, pos);
		}

		if (privUrl.endsWith("UI")) {
			privUrl = privUrl.substring(0, privUrl.length() - 2);
		}

		// 普通用户要判断是否含有这个权限
		Collection<String> allprivilegeUrls = (Collection<String>) ActionContext
				.getContext().getApplication().get("allprivilegeUrls");
		if (!allprivilegeUrls.contains(privUrl)) {
			return true;
		}
		for (Role role : roles) {
			for (Privilege priv : role.getPrivileges()) {
				if (privUrl.equals(priv.getUrl())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断本用户是否是超级管理员
	 */
	public boolean isAdmin() {
		return "admin".equals(loginName);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFaceIcon() {
		return faceIcon;
	}

	public void setFaceIcon(String faceIcon) {
		this.faceIcon = faceIcon;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", department=" + department + ", roles="
				+ roles + ", loginName=" + loginName + ", password=" + password
				+ ", name=" + name + ", gender=" + gender + ", phoneNumber="
				+ phoneNumber + ", email=" + email + ", description="
				+ description + ", faceIcon=" + faceIcon + "]";
	}

}
