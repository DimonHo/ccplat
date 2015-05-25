<%--
-文件名：listhistory.jsp
-日期：2014.9.23
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询产品管理历史记录
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
		<title>历史记录</title>
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
		<div class="mini-splitter" style="width: 97.5%; height: 97%;">
			<div size="200" showCollapseButton="true" cls="bordernone border-right">
					<div class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<a class="mini-button" iconCls="icon-search" onclick="getExist()" productsid="countmainmanageropen"
							plain="true">启用中</a>
						<a class="mini-button" iconCls="icon-search" onclick="getDelete()" productsid="countmainmanageroff"
							plain="true">已停用</a>
					</div>
					<div class="mini-fit">
					<ul id="productTree" class="mini-tree" 
						url="ccproductslist/listProduct.action" style="width: 100%;height: 100%;"
						showTreeIcon="true" textField="name" expandOnLoad="true"
						resultAsTree="false"
						idField="code" contextMenu="#treeMenu">
					</ul>
				</div>
			</div>
			<div showCollapseButton="true" style="width: 100%;height: 100%" cls="bordernone border-left">
				<div class="mini-fit">
					<div class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<a>历史记录</a>
					</div>
					<div id="datagrid" class="mini-datagrid" style="width:100%;height:86.6%;" showPager="false">
				    	<div property="columns">
					        <div name="name" field="operator.name" width="100">操作人</div>
					        <div name="ccno" field="operator.ccno" width="100">cc号</div>
					        <div name="createtime" field="createtime" width="100" renderer="getDate">时间</div>
					        <div name="detail" width="50" headerAlign="center" align="center" renderer="detail" cellStyle="padding:0;">详情</div>
				    	</div>
					</div>
					<div id="page" class="mini-pager" style="width:99.6%;background:#f0f3f7;border:solid 1px #ccc;" 
					     onpagechanged="onPageChanged" sizeList="[5,16,20,100]" pageSize="16"
					    showPageSize="true" showPageIndex="true" showPageInfo="true">        
					</div>
				</div>
			</div>
		</div>
		<script type="text/javascript">
		//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["countmainmanageroff", "countmainmanageropen"]; 
			initPage(); 
		</script>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/productslist/listhistory.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/admin/frame/cookie.js"></script>
	</body>
	
</html>

