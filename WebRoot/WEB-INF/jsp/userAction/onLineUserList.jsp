<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <title>在线用户列表</title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf" %>
</head>
<body>

<div id="Title_bar">
    <div id="Title_bar_Head"> 
        <div id="Title_Head"></div>
        <div id="Title"><!--页面标题-->
            <img border="0" width="13" height="13" src="${pageContext.request.contextPath}/style/images/title_arrow.gif"/> 在线用户列表
        </div>
        <div id="Title_End"></div>
    </div>
</div>

<div id="MainArea">
    <table cellspacing="0" cellpadding="0" class="TableStyle">
       
        <!-- 表头-->
        <thead>
            <tr valign=middle id=TableTitle style="text-align: center;">
                <td width="120">登录名</td>
                <td width="120">姓名</td>
                <td width="120">所属部门</td>
                <td width="200">岗位</td>
                <td>备注</td>
                <%--<td>相关操作</td>--%>
            </tr>
        </thead>
        
        <!--显示数据列表-->
        <tbody id="TableData" class="dataContainer" datakey="userList">
        
        <s:iterator value="#userList">
            <tr class="TableDetail1 template">
                <td style="text-align: center;">${loginName}&nbsp;</td>
                <td style="text-align: center;">${name}&nbsp;</td>
                <td style="text-align: center;">${department.name}&nbsp;</td>
                <td>
                	<s:iterator value="roles">
                		${name}
                	</s:iterator>
                </td>
                <td>${description}&nbsp;</td>
                <%--<td>
                	<s:a action="user_delete?id=%{id}" onclick="return delConfirm()">删除 &nbsp;</s:a>
                    <s:a action="user_editUI?id=%{id}">修改 &nbsp;</s:a>
					<s:a action="user_initPassword?id=%{id}" onclick="return window.confirm('您确定要初始化密码为1234吗？')">初始化密码 &nbsp;</s:a>
                </td>--%>
            </tr>
        </s:iterator> 
            
        </tbody>
    </table>
</div>

</body>
</html>
