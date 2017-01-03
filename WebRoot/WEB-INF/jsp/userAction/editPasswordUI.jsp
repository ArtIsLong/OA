<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>修改密码</title>
	<%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
	<script type="text/javascript">
	$(document).ready(function(){
	   $(":input[name='oldPassword']").change(function() {
			//提交的参数，name和inch是和struts action中对应的接收变量
			var params = {
				oldPassword : $(":input[name='oldPassword']").val()
			};
			$.ajax({
				type: "POST",
				url: "userCheck/userPasswordCheck.action",
				data: params,
				dataType:"json", //ajax 返回值设置为text（json格式也可用它返回，可打印出结果，也可设置成json）
				success: function(data){
					//var obj = $.parseJSON(json);  //使用这个方法解析json
					//var state_value = obj.msg;  //result是和action中定义的result变量的get方法对应的
					$("#pass").text(data);
					return false;
				},
				error: function(json){
					alert("json=" + json);
					return false;
				}
			});
		});
	});
</script>
</head>
<body>
 
<!-- 标题显示 -->
<div id="Title_bar">
    <div id="Title_bar_Head">
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 修改密码
        </div>
        <div id="Title_End"></div>
    </div>
</div>
 
<!--显示表单内容-->
<div id=MainArea>
    <s:form action="user_editPassword" method="post">
        <div class="ItemBlock_Title1"><!-- 信息说明<DIV CLASS="ItemBlock_Title1">
        	<IMG BORDER="0" WIDTH="4" HEIGHT="7" SRC="${pageContext.request.contextPath}/style/blue/images/item_point.gif" /> 修改密码 </DIV>  -->
        </div>
        
        <!-- 表单内容显示 -->
        <div class="ItemBlockBorder">
            <div class="ItemBlock">
                <table cellpadding="0" cellspacing="0" class="mainForm">
					<tr height="50">
						<td width="150">
							<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
							请输入原密码
						</td>
						<td><input type="password" name="oldPassword" class="InputStyle"/><font color="red"> *　</font><font color="red"><span id="pass"><s:fielderror/></span></font></td>
					</tr>
					<tr height="25">
						<td width="150">
							<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
							请填写新密码
						</td>
						<td><input type="password" name="password" class="InputStyle" /><font color="red"> *　</font><font color="red"><s:fielderror/></font></td>
						<td></td>
					</tr>
					<tr height="25">
						<td width="150">
							<img border="0" width="4" height="7" src="${pageContext.request.contextPath}/style/blue/images/item_point.gif" />
							再次输入新密码
						</td>
						<td><input type="password" name="password2" class="InputStyle" /><font color="red"> *　</font><font color="red"><s:fielderror/></font></td>
						<td></td>
					</tr>
                </table>
            </div>
        </div>
       
        <!-- 表单操作 -->
        <div id="InputDetailBar">
            <input type="image" src="${pageContext.request.contextPath}/style/images/save.png"/>
            <a href="javascript:history.go(-1);"><img src="${pageContext.request.contextPath}/style/images/goBack.png"/></a>
        </div>
    </s:form>
</div>
 
<div class="Description">
	验证规则：<br />
	1，旧密码不能为空。<br />
	2，新密码不能为空。<br />
	3，再次输入的密码要和新密码一致。<br />
</div>
</body>
</html>

