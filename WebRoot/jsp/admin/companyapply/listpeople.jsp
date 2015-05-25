<%--
-文件名：listpeople.jsp
-日期：2014.9.20
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：展示审批人
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
		<title>选择审批人</title>
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
		<div id="type"> 关键字：<input id="key" name="key" style="width:120px;" class="mini-textbox" floatErrorText="请输入数字" required="true"/></div>
		<div style="margin-top:30px;">审批人：<input id="sub" class="mini-treeselect"  textField="name" valueField="name" 
				 parentField="pid"  valuechanged="selectDeal()"/><br/></div>
		<div style="text-align:center;padding:10px;">               				
				<a class="mini-button mini-savebtn mr20" id="OK" dir="ltr" onclick="onOk">保存</a>
				<a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>
			</div>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/companyapply/listpeople.js" type="text/javascript"></script>
	</body>
</html>
