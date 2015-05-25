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
		<title>树形联动</title>
		<link rel="stylesheet" type="text/css" href="css/admin/style.css" />
	</head>
	<body>

			<!--中间内容-->
			<div class="nav" id="myModal">
				<jsp:include page="../cc/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="list.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">帐号生成</b>
						<span class="line"></span>
						<p>
							可以批量生成企业帐号。
						</p>
						<p class="mt20">
							通过点击开放cc帐号按钮，输入生成的帐号数量和起始帐号进行批量生成。
						</p>
						<%--<p class="mt20">
							模块详细说明模块详细说明模明模块详细说明模块详细块详细说明。
						</p>
					--%></div>
				</div>
				
			</div>
			
</body>
</html>