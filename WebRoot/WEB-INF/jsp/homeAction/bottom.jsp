<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title></title>
    <%@ include file="/WEB-INF/jsp/public/commons.jspf"%>
	<LINK href="${pageContext.request.contextPath}/style/blue/statusbar.css" type=text/css rel=stylesheet>
</head>

<body leftmargin=0 topmargin=0 marginwidth=0 marginheight=0 onload="init()">

<div id="StatusBar">
    <div id="Online">
    	在线人员：共 <span class="OnlineUser" id="onlineUserNum"></span>人
    	<span class="OnlineView">
    	<a  href="${pageContext.request.contextPath}/memcache_onLineUserList.action" target="right">[查看在线名单]</a>
    	</span></div>

    <div id="Info" style="display: none;">
    	<a href="http://www.gsau.edu.cn" title = "甘肃农业大学首页" target=_blank >甘肃农业大学首页</a> |
        <a href="http://www.gsau.edu.cn" title = "甘肃农业大学BBS" target=_blank >甘肃农业大学BBS</a>
    </div>
    <DIV id=DesktopText style="display: none;">
        <a href="javascript:void(0)"><img border="0" src="${pageContext.request.contextPath}/style/images/top/text.gif"/> 便笺</a>

        <span id=TryoutInfo>

        </span>
        <span id="Version">
            <a href="javascript:void(0)">
            	<img border="0" width="11" height="11" src="${pageContext.request.contextPath}/style/images/top/help.gif" />
            	<img border="0" width="40" height="11" src="${pageContext.request.contextPath}/style/blue/images/top/version.gif" />
            </a>
        </span>
    </DIV>
</div>

</body>
<script type="text/javascript">
	function init(){
		setInterval(showOnLineCount, 2000);
	}
	
	function showOnLineCount(){
		$.post("memcache_onLineCount.action",function(data){
			$("#onlineUserNum").html(data);
		});
		<%--
		$.ajax({
			url:"memcache_onLineCount.action",
			type:"post",
			dataType:"json",
			success:function(data){
				$(".onlineUserNum").html(data);
			}
		});--%>
	}
</script>
</html>
