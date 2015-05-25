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
	<body style="width: 100%;height: 100%;">


			<!--中间内容-->
			<div class="nav" id="myModal">
				<jsp:include page="../framework/menu.jsp"></jsp:include>
				<div class="grybg">
					<jsp:include page="list.jsp"></jsp:include>
				</div>
				<div class="introtxt">
					<div class="txt">
						<b class="f14">行业管理</b>
						<span class="line"></span>
						<p>
							管理行业结构树
						</p>
						<p class="mt20">
							行业管理：通过点击新增行业按钮添加新的行业，右键行业树对行业进行增删改查。
						</p>
						<p class="mt20">
							历史记录查看：点击查看历史记录按钮，可以看到所有对行业树的操作历史记录。
						</p>
					</div>
				</div>
			</div>

	</body>
</html>