<%--
-文件名：listRole.jsp
-日期：2014.9.17
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询岗位列表
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>后台管理</title>
		<link rel="stylesheet" type="text/css" href="css/admin/style.css" />
	</head>
	<body>

		<!--内容-->
			<!--中间内容-->
			<div class="nav" id="myModal">
				<jsp:include page="../framework/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="list.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">岗位管理</b>
						<span class="line"></span>
						<p>
							管理所有岗位模版
						</p>
						<p class="mt20">
							岗位管理:点击新增岗位模版按钮，可以添加新的岗位模版。右键岗位模版可以添加新的岗位，设置名称、说明和级别。
							级别数值越小，岗位的级别越高。
						</p>
						<p class="mt20">
							历史记录查看：点击查看历史记录按钮，可以看到所有启用中的岗位模版，可以点击按钮切换
							到已停用的岗位模版。选中模版可以看到所有对该模版的操作历史记录。
						</p>
					</div>
				</div>
			</div>
</body>
</html>