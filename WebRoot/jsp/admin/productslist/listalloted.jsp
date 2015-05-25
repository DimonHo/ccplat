<%--
-文件名：listalloted.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查看企业产品页面
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<title>查看产品</title>
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
	<body style="overflow:auto;width:95%;height:95%;">
		<div showCollapseButton="true" style="width: 100%;height: 97.5%" cls="bordernone border-left">
			<ul id="productTree" class="mini-tree" style="width: 100%;"
				showTreeIcon="true" textField="name" expandOnLoad="true"
				allowDrop="true" allowLeafDropIn="true"
				idField="uuid">
			</ul>
		</div>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/productslist/listalloted.js" type="text/javascript"></script>
	</body>
</html>