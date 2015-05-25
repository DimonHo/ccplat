<%--
-文件名：addRole.jsp
-日期：2014.9.17
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：新增岗位页面
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
		<title>新增岗位</title>
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
		<div class="padtople"> </div>
	    <form id="form" method="post">
       		<div class="mini-borderbottom-dotted"><b>岗位信息</b></div>  
            <div class="pad5">
	        	<table class="mini-table-w85 h110">
		            <tr>
		                <td >岗位名称：</td>
		                <td >    
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" />
		                    <%--<input name="uuid" id="uuid" class="mini-hidden" />
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
	        <div class="pad10 mle124">               
	            <a class="mini-button mini-savebtn mr20" onclick="onOk" >确定</a>       
	            <a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>       
	        </div>        
	    </form>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/rolelist/addRole.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/industryinfo/uuid.js" type="text/javascript"></script>
	</body>
</html>