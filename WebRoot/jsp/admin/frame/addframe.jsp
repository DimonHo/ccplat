<%--
-文件名：addframe.jsp
-日期：2014.9.18
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：初始化企业组织架构页面
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
		<title>初始化组织架构</title>
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
		<div class="mini-splitter" style="width: 97.5%; height: 87%;">
			<div size="200" showCollapseButton="true" cls="bordernone border-right">
				<div class="mini-fit">
					<div class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<a>行业信息</a>
					</div>
						
					<ul id="industryTree" class="mini-tree" 
						url="ccindustryinfo/listIndustryinfo.action" style="width: 100%;"
						showTreeIcon="true" textField="name" expandOnLoad="true"
						allowDrop="true" allowLeafDropIn="true"
						idField="uuid">
					</ul>
				</div>
			</div>
			<div showCollapseButton="true" style="width: 100%;height: 100%" cls="bordernone border-left">
				 <div class="mini-fit">
				 	<div class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<a>分配组织架构</a>
					</div>
       				<ul id="frameTree" class="mini-tree" 
						url="ccframelist/listFrame.action" style="width: 100%;"
						showTreeIcon="true" textField="name" expandOnLoad="true"
						allowDrop="true" allowLeafDropIn="true"
						idField="uuid" showCheckBox="true" checkRecursive="true">
					</ul>
				</div>
			</div>
		</div>
		<div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">保存</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>  
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/frame/addframe.js" type="text/javascript"></script>
	</body>
</html>