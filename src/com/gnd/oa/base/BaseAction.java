package com.gnd.oa.base;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.danga.MemCached.SockIOPool;
import com.gnd.oa.domain.User;
import com.gnd.oa.memcache.interfaces.IMemcachedService;
import com.gnd.oa.service.ApplicationService;
import com.gnd.oa.service.ApplicationTemplateService;
import com.gnd.oa.service.DepartmentService;
import com.gnd.oa.service.ForumService;
import com.gnd.oa.service.PrivilegeService;
import com.gnd.oa.service.ProcessDefinitionService;
import com.gnd.oa.service.ReplyService;
import com.gnd.oa.service.RoleService;
import com.gnd.oa.service.TopicService;
import com.gnd.oa.service.UserService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 所有Action的基类
 */
@SuppressWarnings({ "serial" })
public abstract class BaseAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware, ServletContextAware {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected ServletContext application;

	@Resource
	protected DepartmentService departmentService;

	@Resource
	protected RoleService roleService;

	@Resource
	protected UserService userService;

	@Resource
	protected PrivilegeService privilegeService;

	@Resource
	protected ForumService forumService;

	@Resource
	protected TopicService topicService;

	@Resource
	protected ReplyService replyService;

	@Resource
	protected IMemcachedService memcachedService;

	@Resource
	protected ProcessDefinitionService processDefinitionService;

	@Resource
	protected ApplicationTemplateService applicationTemplateService;

	@Resource
	protected ApplicationService applicationService;

	@Resource
	protected SockIOPool sockIOPool;

	protected User getCurrentUser() {
		return (User) ActionContext.getContext().getSession().get("user");
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
		this.session = this.request.getSession();
	}

	public void setServletContext(ServletContext application) {
		this.application = application;
	}

	// ================== 分页用的参数 ==================
	protected int pageNum = 1; // 当前页
	protected int pageSize = 10; // 每页显示多少条记录

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @Function saveUpLoadFile
	 * @Description 保存上传的文件，并返回文件在服务器端的真是存储路径
	 * 
	 * @param @param upLoad
	 * @param @return
	 * @return String
	 * @throws
	 * 
	 * @version: v1.0.0
	 * @author: chenmin
	 * @date :2016-5-2下午3:49:48
	 * 
	 * <strong>Modification History:</strong><br>
	 * Date Author Version Description<br>
	 * ---------------------------------------------------------<br>
	 * 2016-5-2 chenmin v1.0.0 修改原因<br>
	 */
	protected String saveUpLoadFile(File upLoad) {
		SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");
		// >> 获取路径
		String basePath = ServletActionContext.getServletContext().getRealPath(
				"/WEB-INF/upLoad_files");
		String subPath = sdf.format(new Date());

		// >> 如果文件夹不存在，就创建
		File dir = new File(basePath + subPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		// >> 拼接路径
		String path = basePath + subPath + UUID.randomUUID().toString();

		// >> 移动文件
		upLoad.renameTo(new File(path));
		return path;
	}
}
