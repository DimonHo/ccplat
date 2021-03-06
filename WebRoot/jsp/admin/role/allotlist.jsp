<%--
-文件名：allotlist.jsp
-日期：2014.9.18
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：初始化企业岗位页面
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
		<title>岗位分配</title>
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
		<div showCollapseButton="true" style="width: 97.5%;height: 85%" cls="bordernone border-left">
			<div class="mini-fit">
   				<ul id="roleTree" class="mini-tree" 
					url="ccrolelist/listRole.action" style="width: 100%;"
					showTreeIcon="true" textField="name" expandOnLoad="true"
					allowDrop="true" allowLeafDropIn="true" nodesField="position"
					idField="uuid" showCheckBox="true" checkRecursive="true">
				</ul>
			</div>
		</div>
		<div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">保存</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>  
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/role/allotlist.js" type="text/javascript"></script>
	</body>
</html>