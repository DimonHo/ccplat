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
	<div style="width:100%;height:10%;">
    	<div class="mini-toolbar" style="border-bottom:0;padding:0px;height:100%;">
	        <table style="width:100%;height:100%;">
	            <tr>
	                <td style="width:100%;height:100%;padding-left:15px;">
	                	当前行业：<input id="industryinfo" class="mini-treeselect" url="<%=basePath %>ccindustryinfo/listIndustryinfo.action" 
	                		multiSelect="false"  valueFromSelect="false" resultAsTree="true" showTreeIcon="false"
		       				 textField="name" valueField="uuid" allowInput="true" showClose="true" oncloseclick="onCloseClick"
		        			showRadioButton="true" showFolderCheckBox="true" />&nbsp&nbsp&nbsp&nbsp
		          		公司名称：<input id="company" class="mini-textbox" emptyText="请输入公司名称"/>
	         			<a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
	                </td>
	            </tr>
	        </table>           
   	    </div>
	</div>
	<div class="mini-fit">
	 <div id="grids" class="mini-datagrid" url="<%=basePath %>audit/getProductListName.action" 
			style="width:100%;height:100%;" borderStyle="border:0;" >
		<div property="columns">
			<div field="code" width="100" allowSort="true" headerAlign="center">产品编码</div>
			<div field="name" width="120" headerAlign="center" allowSort="true">产品名称</div>
			<div field="remark" width="120" headerAlign="center" allowSort="true">说明</div>
		</div>	
	</div>
	</div>
	<div class="ynnav mini-toolbar">
		<a class="mini-button mini-savebtn" style="width:61px;" onclick="SaveData()">确定</a>
      				<span style="display:inline-block;width:25px;"></span>
   		<a class="mini-button mini-dropbtn" style="width:61px;" onclick="onCancel()">取消</a>
   	</div>
   	<script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
   	<script src="<%=basePath %>js/admin/permission/productList.js" type="text/javascript"></script> 
</body>
</html>
