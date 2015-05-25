<%--
-文件名：selectperson.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：选择人员
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
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" /><link href="../demo.css" rel="stylesheet" type="text/css" />
   <script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
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
    <div class="mini-splitter" style="width: 100%; height: 90%;">
			<div size="200" showCollapseButton="true" cls="bordernone border-right">
				<div class="mini-fit bgf" >
				<div class="mini-toolbar"
					style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
					行业信息
				</div>
   					    <ul id="dep" class="mini-tree" style="width:100%;height:100%;" 
				            showTreeIcon="true" textField="name" idField="id" parentField="pid" resultAsTree="false"  
				            expandOnLoad="0" onnodedblclick="changeMoudle" expandOnDblClick="false">
				        </ul>	 
					<!--右健菜单-->
				</div>
			</div>
			<div showCollapseButton="true" >
				 <div id="grids" class="mini-datagrid" url="<%=basePath %>audit/getDepArrayForDepno.action" 
						style="width:100%;height:100%;" borderStyle="border:0;" multiSelect="true" onselectionchanged="onSelectionChanged">
					<div property="columns">
						<div type="checkcolumn" ></div>     
						<div field="ccno" width="100" allowSort="true" headerAlign="center">
							cc编码
						</div>
						<div field="realname" width="120" headerAlign="center" allowSort="true">
							姓名
						</div>
						<div field="href" width="120" headerAlign="center" allowSort="true">
							跳转
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
    	<script src="<%=basePath %>js/admin/permission/selectperson.js" type="text/javascript"></script> 
</body>
</html>
