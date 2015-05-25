<%--
-文件名：modifytime.jsp
-日期：2014.9.27
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：coco
修改人：
备注：修改产品时间
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
	    <title>修改产品使用期限</title>
	    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	    
	    <style type="text/css">
	    html, body
	    {
	        font-size:12px;
	        padding:0;
	        margin:0;
	        border:0;
	        height:100%;
	        overflow:hidden;
	    }
	    </style>
	</head>
	<body>
		<div style="padding-left:11px;padding:5px 0;"> </div>
       		<div style="border-bottom:solid 1px #aaa;padding:3px; width:85%;margin:0 auto;"><b>产品信息</b></div>
			<div id="win1"  title="授权时间" style="width:400px;height:60px;horizontal-align:middle;" showMaxButton="false" showToolbar="true" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		  		<div style="margin-left:60px;">  
		   		    授权时间：<input id="stime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />至
         	 	<input id="etime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />
         		 </div>
			</div> 
	        <div style="text-align:center;">               
	            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
	            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
	        </div>        
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/productslist/modifytime.js" type="text/javascript"></script>
	</body>
</html>