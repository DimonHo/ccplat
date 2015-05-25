<%--
-文件名：modifyRole.jsp
-日期：2014.9.17
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：修改岗位页面
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
		<title>修改岗位</title>
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
		<div style="padding-left:11px;padding:5px 0;"> </div>
	    <form id="form" method="post">
       		<div style="border-bottom:solid 1px #aaa;padding:3px; width:85%;margin:0 auto;"><b>岗位信息</b></div>  
            <div style="padding:5px;">
	        	<table style=" width:85%;margin:0 auto;height:150px;">
		            <tr>
		                <td >岗位名称：</td>
		                <td >
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" />
		                    <!-- 当前模版节点uuid -->
		                    <%--<input name="uuid" id="uuid" class="mini-hidden" />
		                    --%><!-- 当前修改节点uuid -->
		                    <%--<input name="currentuuid" id="currentuuid" class="mini-hidden" />
		                --%></td>
		            </tr>
                    <tr>
                        <td >岗位说明：</td>
                        <td >    
                            <input name="remark" id="remark" class="mini-textbox"  height="25" width="235" required="true" />
                        </td>
                    </tr>
                    <tr style="display:none" id="levtr">
                        <td >岗位级别：</td>
                        <td >    
                            <input name="lev" id="lev" class="mini-spinner"  height="25" width="235" required="true" minValue="1"/>
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
		<script src="<%=basePath%>js/admin/rolelist/modifyRole.js" type="text/javascript"></script>
	</body>
</html>