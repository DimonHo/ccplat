<%--
-文件名：listcompany.jsp
-日期：2014.9.18
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：展示需要初始化组织架构和岗位的企业列表
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
		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<title>树形联动</title>
		<link rel="stylesheet" type="text/css" href="css/admin/style.css" />
	</head>
	<body>
			<!--中间内容-->
			<div class="nav" id="myModal">
				<jsp:include page="../business/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="list.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">企业分配</b>
						<span class="line"></span>
						<p>
							管理企业分配状态。
						</p>
						<p class="mt20">
							企业管理：可以查看所有企业的组织架构、岗位、产品、帐号的分配状态，未分配的企业可以点击未分配按钮分配对应的模块。分配完成后，可以点击
							已分配按钮查看当前企业各模块的状态。
						</p>
						<%--<p class="mt20">
							模块详细说明模块详细说明模明模块详细说明模块详细块详细说明。
						</p>
					--%></div>
				</div>
			</div>
			
</body>
</html>