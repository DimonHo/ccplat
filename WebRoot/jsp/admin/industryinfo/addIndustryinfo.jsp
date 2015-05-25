<%--
-文件名：addIndustryinfo.jsp
-日期：2014.9.15
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：增加新行业页面
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
		<div class="padtople"> </div>
	     
	    <form id="form" method="post">
	       
       		<div class="mini-borderbottom-dotted"><b>行业信息</b></div>  
            <div class="pad5">
	        	<table class="mini-table-w85 h120"">
		            <tr>
		                <td >行业名称：</td>
		                <td >    
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" />
		                    <input name="key" id="key" class="mini-hidden" value="children" />
		                    <input name="index" id="index" class="mini-hidden" />
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
	        <div class="pad10 mle124">               
	            <a class="mini-button mini-savebtn mr20" onclick="onOk" >确定</a>       
	            <a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>       
	        </div>        
	    </form>
	   
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/industryinfo/addIndustryinfo.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/industryinfo/uuid.js" type="text/javascript"></script>
	</body>
</html>