<%--

-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：Ron
修改人：
备注：账号管理1级界面
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<title>CC管理中心</title>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<link rel="stylesheet" type="text/css" href="css/admin/style.css" />
		
		<link rel="stylesheet" type="text/css"href="css/scroll/jquery.jscrollpane.codrops1.css"/>
	</head>

	<body class="indexclass">
		<jsp:include page="../../../jsp/admin/head.jsp" ></jsp:include>
		<!--内容-->
		<div id="Bigcont">
			<jsp:include page="../../../jsp/admin/left.jsp" ></jsp:include>
			<!--中间内容-->
			<div class="nav01">
				<div class="contbg">
					<jsp:include page="center.jsp"></jsp:include>					
				</div>
			</div>

			<!--右侧内容-->
			<jsp:include page="../../../jsp/admin/right.jsp"></jsp:include>
			<div class="clr"></div>
		</div>
		<!--弹出中间内容-->
		<div class="reveal-modal" id="myFrame" style="height: 585px;width: 1236px;">
			<a class="close-reveal-modal mini-button" iconCls="icon-close"></a>
			<iframe id="frame" style="width:100%;height:100%;border: 0px;"></iframe>
		</div>
		<script src="js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="js/admin/index.js" type="text/javascript"></script>
		
		<script type="text/javascript" src="js/scroll/jquery.mousewheel.js"></script>
		<script type="text/javascript" src="js/scroll/jquery.jscrollpane.min.js"></script>
		<script type="text/javascript" src="js/scroll/scroll-startstop.events.jquery.js"></script>
		<script type="text/javascript" src="js/scroll/scroll.js"></script>
		
		<script type="text/javascript">
			$("#systemmain").addClass("selected");
		</script>
	</body>
</html>

