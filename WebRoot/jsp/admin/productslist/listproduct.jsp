<%--
-文件名：listFrame.jsp
-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询组织架构列表
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
	<body style="width: 100%;overflow: auto;">
		<div class="nav" id="myModal">
			<jsp:include page="../framework/menu.jsp"></jsp:include>
			<div class="grybg">
				<jsp:include page="list.jsp"></jsp:include>
			</div>
			<div class="introtxt">
				<div class="txt">
					<b class="f14">产品管理</b>
					<span class="line"></span>
					<p>
						管理行业的所有产品模版。
					</p>
					<p class="mt20">
						产品管理：选中左侧行业，通过新增产品模版在该行业新增产品模版。右键右侧的产品树
						操作实现对产品的增删改查。
					</p>
					<p class="mt20">
						历史记录查看：选中左侧行业，点击查看历史记录按钮，可以看到该行业所有启用中的产品模版，可以点击按钮切换
						到已停用的产品模版。选中模版可以看到所有对该模版的操作历史记录。
					</p>
				</div>
			</div>
		</div>
	</body>
</html>