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
				<jsp:include page="../business/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="list.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">企业注册</b>
						<span class="line"></span>
						<p>
							企业的注册修改管理
						</p>
						<p class="mt20">
							点击填写审批按钮，填写企业信息，实现新企业的注册。
						</p>
						<p class="mt20">
							点击修改按钮，实现对企业信息的修改。
						</p>
					</div>
				</div>
				
			</div>

</body>
</html>