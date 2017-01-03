package com.gnd.oa.view.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gnd.oa.base.BaseAction;
import com.gnd.oa.domain.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope("prototype")
public class UpLoadFileAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private String title;
	// 可以得到上传文件的名称
	// 规则:输入域的名称+固定字符串FileName
	private String myFileFileName;

	// 取得文件数据
	// 规则:File输入域的名称
	private File myFile;

	// 取得内容类型
	// 规则:输入域的名称+固定字符串ContentType
	private String myFileContentType;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}

	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileContentType() {
		return myFileContentType;
	}

	public void setMyFileContentType(String myFileContentType) {
		this.myFileContentType = myFileContentType;
	}

	public String userHeadPortrait() throws Exception {
		User user = (User)ActionContext.getContext().getSession().get("user");
		InputStream is = null;
		OutputStream os = null;
		try{
			is = new BufferedInputStream(new FileInputStream(myFile));
			os = new BufferedOutputStream(new FileOutputStream("F:/study/apache-tomcat-6.0.14/webapps/OA/upload/images/" + myFileFileName));
			byte[] buffer = new byte[1024];
			int len = 0;
			while((len = is.read(buffer)) != -1){
				os.write(buffer, 0, len);
			}
			user.setFaceIcon(myFileFileName);
			userService.update(user);
			ActionContext.getContext().getSession().put("user",user);
		}finally{
			if(is != null){
				is.close();
			}
			if(os != null){
				os.close();
			}
		}
		return Action.SUCCESS;
	}

}
