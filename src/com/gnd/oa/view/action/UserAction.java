package com.gnd.oa.view.action;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.ModelDrivenBaseAction;
import com.gnd.oa.domain.Department;
import com.gnd.oa.domain.Role;
import com.gnd.oa.domain.User;
import com.gnd.oa.memcache.ValidateFactory;
import com.gnd.oa.util.DepartmentUtils;
import com.gnd.oa.util.QueryHelper;
import com.opensymphony.xwork2.ActionContext;

@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public class UserAction extends ModelDrivenBaseAction<User> {

	private Long departmentId;
	private Long[] roleIds;

	private String faceIcon;
	private String password;
	private String oldPassword;
	private String msg;

	/** 列表 */
	public String list() throws Exception {
		// List<User> userList = userService.findAll();
		// ActionContext.getContext().put("userList", userList);
		new QueryHelper(User.class, "t")//
				.preparePageBean(userService, pageNum, pageSize);
		return "list";
	}

	/** 删除 */
	public String delete() throws Exception {
		userService.delete(model.getId());
		return "toList";
	}

	/** 添加页面 */
	public String addUI() throws Exception {
		// 准备数据 departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备数据roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);
		return "saveUI";
	}

	/** 添加 */
	public String add() throws Exception {
		// 封装到对象中(当model是实体类型时,也可以使用model,但是要设置未封装的属性)
		// 设置所属部门
		model.setDepartment(departmentService.getById(departmentId));
		List<Role> roleList = roleService.getByIds(roleIds);
		// 设置岗位
		model.setRoles(new HashSet<Role>(roleList));
		// 设置默认密码为1234(要使用MD5摘要)
		String md5Digest = DigestUtils.md5Hex("1234");
		model.setPassword(md5Digest);

		userService.save(model);
		return "toList";
	}

	/** 修改页面 */
	public String editUI() throws Exception {
		// 准备数据 departmentList
		List<Department> topList = departmentService.findTopList();
		List<Department> departmentList = DepartmentUtils
				.getAllDepartments(topList);
		ActionContext.getContext().put("departmentList", departmentList);
		// 准备数据roleList
		List<Role> roleList = roleService.findAll();
		ActionContext.getContext().put("roleList", roleList);

		User user = userService.getById(model.getId());
		ActionContext.getContext().getValueStack().push(user);
		if (user.getDepartment() != null) {
			departmentId = user.getDepartment().getId();
		}
		if (user.getRoles() != null) {
			int index = 0;
			roleIds = new Long[user.getRoles().size()];
			for (Role role : user.getRoles()) {
				roleIds[index++] = role.getId();
			}
		}

		return "saveUI";
	}

	/** 修改个人信息页面 */
	public String editUserUI() throws Exception {
		User user = (User) ActionContext.getContext().getSession()
				.get(User.USERNAME);
		Set<Role> roles = user.getRoles();
		List<Role> roleList = null;
		if (roles != null && roles.size() != 0) {
			Long[] ids = new Long[roles.size()];
			int i = 0;
			for (Iterator<Role> iterator = roles.iterator(); iterator.hasNext();) {
				Role role = iterator.next();
				ids[i++] = role.getId();
			}
			roleList = roleService.getByIds(ids);
			ActionContext.getContext().put("roleList", roleList);
		}
		Department depart = user.getDepartment();
		Department department = null;
		if (depart != null) {
			department = departmentService.getById(depart.getId());
			ActionContext.getContext().put("department", department);
		}

		return "editUserUI";
	}

	/** 修改个人信息 */
	@Deprecated
	public String editUser() throws Exception {
		User user = userService.getById(model.getId());
		user.setFaceIcon(faceIcon);
		userService.update(user);
		memcachedService.set(String.valueOf(user.getId()), user);
		return "editUserUI";
	}

	/** 修改密码页面 */
	public String editPasswordUI() throws Exception {
		return "editPasswordUI";
	}

	/** 修改密码 */
	public String editPassword() throws Exception {
		User user = getCurrentUser();
		oldPassword = DigestUtils.md5Hex(oldPassword);
		if(user.getPassword().equals(oldPassword)){
			user.setPassword(DigestUtils.md5Hex(model.getPassword()));
			userService.update(user);
		}else{
			addFieldError("editPassword","原密码错误！");
		}
		return "editPasswordUI";
	}

	/** 修改 */
	public String edit() throws Exception {
		// 1，从数据库中取出原对象
		User user = userService.getById(model.getId());

		// 2，设置要修改的属性
		// >> 普通属性
		user.setLoginName(model.getLoginName());
		user.setName(model.getName());
		user.setGender(model.getGender());
		user.setPhoneNumber(model.getPhoneNumber());
		user.setEmail(model.getEmail());
		user.setDescription(model.getDescription());
		// >> 所属部门
		Department department = departmentService.getById(departmentId);
		user.setDepartment(department);
		// >> 关联的岗位
		List<Role> roleList = roleService.getByIds(roleIds);
		user.setRoles(new HashSet<Role>(roleList));

		// 3，更新到数据库
		userService.update(user);

		return "toList";
	}

	/** 初始化密码 */
	public String initPassword() throws Exception {
		User user = userService.getById(model.getId());
		// 设置默认密码为1234(要使用MD5摘要)
		String md5Digest = DigestUtils.md5Hex("1234");
		user.setPassword(md5Digest);

		// 3，更新到数据库
		userService.update(user);
		return "toList";
	}

	/** 登录页面 */
	public String loginUI() throws Exception {
		return "loginUI";
	}

	/** 登录 */
	public String login() throws Exception {
		User user = userService.findByLoginNameAndPassword(
				model.getLoginName(), model.getPassword());
		if (user == null) {
			addFieldError("login", "用户名或密码不正确！");
			return "loginUI";
		} else {
			// 登录用户
			ActionContext.getContext().getSession().put(User.USERNAME, user);
			if (ValidateFactory.validate(sockIOPool)) {
				Object obj = memcachedService.get("onLine");
				Integer onLine = null;
				if (obj != null) {
					onLine = Integer.parseInt(String.valueOf(obj));
				}
				if (onLine != null) {
					memcachedService.set("onLine", ++onLine);
				} else {
					memcachedService.set("onLine", 1);
				}
				memcachedService.set(String.valueOf(user.getId()), user);
			}
			return "toIndex";
		}
	}

	/** 注销 */
	public String logout() throws Exception {
		User user = (User) ActionContext.getContext().getSession()
				.get(User.USERNAME);
		if (ValidateFactory.validate(sockIOPool)) {
			memcachedService.delete(String.valueOf(user.getId()));
			Integer onLine = Integer.parseInt(String.valueOf(memcachedService
					.get("onLine")));
			memcachedService.set("onLine", --onLine);
		}
		ActionContext.getContext().getSession().remove(User.USERNAME);
		return "logout";
	}

	public String checkPassword() throws Exception {
		User user = userService.getById(getCurrentUser().getId());
		oldPassword = DigestUtils.md5Hex(oldPassword);
		if (!user.getPassword().equals(oldPassword)) {
			msg = "密码不正确，请重新输入！";
		}else{
			msg = "";
		}
		return "editPasswordUI";
	}

	// ---

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Long[] roleIds) {
		this.roleIds = roleIds;
	}

	public String getFaceIcon() {
		return faceIcon;
	}

	public void setFaceIcon(String faceIcon) {
		this.faceIcon = faceIcon;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
}
