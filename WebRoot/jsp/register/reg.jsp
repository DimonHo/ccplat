<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'reg.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script> 
	<script src="<%=basePath%>js/register/reg.js" type="text/javascript"></script> 

  </head>
  
  <body>
    This is my JSP page. <br>
    <input type="button" onClick="regInfo()" text="提交" style="margin-top:5px;" value="&nbsp;">
    <input type="button" onClick="regInfo2()" text="提交1" style="margin-top:5px;" value="&nbsp;">	
    </input>
  </body>
</html>
