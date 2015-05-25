<%--
-文件名：selectact.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：选择功能
--%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css">
    html,body{
        padding:0;
        margin:0;
        border:0;     
        width:100%;
        height:100%;
        overflow:hidden;   
    }
    </style>
</head>
<body>
    <div class="mini-fit" style="width: 100%; height: 90%;">
		<div>权限功能：</div>
		<div showCollapseButton="true" style="width: 100%; height: 90%;">
				 <div id="grids" class="mini-datagrid" url="<%=basePath %>audit/getProductActdeff.action" 
						style="width:100%;height:100%;" borderStyle="border:0;" multiSelect="true" onselectionchanged="onSelectionChanged">
					<div property="columns">
						<div type="checkcolumn" ></div>     
						<div field="actno" width="100" allowSort="true" headerAlign="center">
							功能编码
						</div>
						<div field="actname" width="120" headerAlign="center" allowSort="true">
							功能名称
						</div>
					</div>	
				</div>
		</div>
	</div>
	<div class="ynnav mini-toolbar">
		<a class="mini-button" style="width:60px;" onclick="SaveData()">确定</a>
       		<span style="display:inline-block;width:25px;"></span>
    	<a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    </div>
    <script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=basePath %>js/admin/permission/selectact.js" type="text/javascript"></script> 
</body>
</html>
