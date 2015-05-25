<%--
-文件名：listtemp.jsp
-日期：2014.9.20
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：审批行业树修改列表
--%>
<%@ page language="java" pageEncoding="utf-8"%>

<%
	String path1 = request.getContextPath();
	String basePath1 = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path1 + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath1%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<title>审批行业</title>
		<style type="text/css">
			body {
				margin: 8px;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div size="200" showCollapseButton="true" cls="bordernone border-right">
				<ul id="industryTree" class="mini-tree" 
					url="ccindustryinfo/listTemp.action" style="width: 97%;"
					showTreeIcon="true" textField="name" expandOnLoad="true"
					allowDrop="true" allowDrag="true" allowLeafDropIn="true"
					idField="uuid" contextMenu="#treeMenu">
				</ul>
		</div>
		<%@include file="../../../jsp/register/auditdiv.jsp"%>
		<script src="<%=basePath%>js/admin/industryinfo/listtemp.js" type="text/javascript"></script>
	</body>
</html>
