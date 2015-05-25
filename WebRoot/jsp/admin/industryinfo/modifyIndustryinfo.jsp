<%--
-文件名：modifyIndustryinfo.jsp
-日期：2014.9.15
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：修改行业信息列表
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
	    <title>创建行业</title>
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
	     
	    <form id="form" method="post">
	       
       		<div style="border-bottom:solid 1px #aaa;padding:3px; width:85%;margin:0 auto;"><b>行业信息</b></div>  
            <div style="padding:5px;">
	        	<table style=" width:85%;margin:0 auto;height:150px;">
		            <tr>
		                <td >行业名称：</td>
		                <td >    
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" />
		                    <%--
		                    <input name="key" id="key" class="mini-hidden" value="children" />
		                    <input name="index" id="index" class="mini-hidden" />
		                    --%>
		                    <input name="uuid" id="uuid" class="mini-hidden" />
		                </td>
		            </tr>
                    <tr>
                        <td >行业说明：</td>
                        <td >
                            <input name="remark" id="remark" class="mini-textbox"  height="25" width="235" required="true" />
                        </td>
                    </tr>
		        </table>        
            </div>
	        <div style="text-align:center;padding:10px;">               
	            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
	            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
	        </div>        
	    </form>
	   
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/industryinfo/modifyIndustryinfo.js" type="text/javascript"></script>
	</body>
</html>