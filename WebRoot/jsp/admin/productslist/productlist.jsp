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
		<title>CC管理</title>
		<link rel="stylesheet" type="text/css" href="css/admin/style.css" />
	</head>
	<body>

			<!--中间内容-->
			<div class="nav" id="myModal">
				<jsp:include page="../business/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="productupdate.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">产品维护</b>
						<span class="line"></span>
						<p>
							产品维护。
						</p>
						<p class="mt20">
							对企业已经分配的产品进行维护
						</p>
					</div>
				</div>
				
			</div>
			
</body>
</html>