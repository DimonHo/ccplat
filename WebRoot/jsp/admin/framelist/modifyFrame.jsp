<%--
-文件名：modifyFrame.jsp
-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：修改组织架构页面
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
	    <title>修改组织架构</title>
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
		<div class="padtople"> </div>
	     
	    <form id="form" method="post">
	       
       		<div style="border-bottom:solid 1px #aaa;padding:3px; width:85%;margin:0 auto;"><b>组织架构信息</b></div>  
            <div class="pad5">
	        	<table class="mini-table-w85 h150">
		            <tr>
		                <td >组织架构名称：</td>
		                <td >    
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" /><%--
		                    <input name="key" id="key" class="mini-hidden" />
		                    <input name="index" id="index" class="mini-hidden" />
		                    <input name="industryuuid" id="industryuuid" class="mini-hidden" />
		                    <input name="uuid" id="uuid" class="mini-hidden" />
		                    <input name="rootuuid" id="rootuuid" class="mini-hidden" />
		                --%></td>
		            </tr>
                    <tr>
                        <td >组织架构说明：</td>
                        <td >    
                            <input name="remark" id="remark" class="mini-textbox"  height="25" width="235" required="true" />
                        </td>
                    </tr>
		        </table>        
            </div>
	        <div style="text-align:center;padding:10px;">               
	            <a class="mini-button mr20" onclick="onOk" style="width:60px;">确定</a>       
	            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
	        </div>        
	    </form>
	   
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/framelist/modifyFrame.js" type="text/javascript"></script>
	</body>
</html>