<%--
-文件名：allotproduct.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：初始化企业产品页面
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
		<title>产品分配</title>
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
		<div showCollapseButton="true" style="width: 97.5%;height: 85%" cls="bordernone border-left">
			<div class="mini-fit">
   				<ul id="tree" class="mini-tree" style="width:100%;height:90%;" 
			            showTreeIcon="true" textField="name" idField="id" showCheckBox="true" parentField="pid" resultAsTree="false"  
			             onnodedblclick="onNodeDblClick" expandOnDblClick="false" expandOnLoad="true">
	        	</ul>
			</div>
		</div>
		<div style="text-align:center;padding:10px;">               
            <a class="mini-button" onclick="openwin" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>  
        <div id="win1" class="mini-window" title="授权时间" style="width:400px;height:100px;horizontal-align:middle;" showMaxButton="false" showToolbar="true" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		  <div style="margin-left:60px;">  
		       授权时间：<input id="stime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />至
          <input id="etime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />
          </div>
		  <div property="footer"  style="text-align:center;padding-top:2px;padding-bottom:2px;">
		        <a class="mini-button" style="width:60px;" onclick="SaveData()" style="horizontal-align:middle;">确定</a>
		  </div>
	</div>  
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/productslist/allotproduct.js" type="text/javascript"></script>
	</body>
</html>