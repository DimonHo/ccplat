<%--
-文件名：auditdiv.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：审核公用弹出层
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>编辑</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
</head>
<body>
   <div style="width: 500px;height:300px"> 
  	 <div class="mini-fit" >
		<div>内容</div>
		<div id="content"></div>
		<div >处理意见：<input id="remark" name="remark" style="width:120px;" class="mini-textbox" floatErrorText="请输入审批处理原因" required="true"/></div>
		<div><input id="sub" class="mini-combobox mini-border-margin"  textField="name" valueField="id" 
				 parentField="pid"  valuechanged="selectDeal()"/><br/>
		</div>
        <div style="text-align:center;padding:10px;">               
             <a id="agree" class="mini-button" onclick="agree('1')" style="width:60px;margin-right:20px;">同意</a>
            <a id="onOk" class="mini-button" onclick="agree('0')" style="width:60px;margin-right:60px;">提交</a>          
            <a class="mini-button" onclick="agree('2')" style="width:60px;">拒绝</a>
        </div>        
     </div>
     </div>
	<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script> 
	<script src="<%=basePath %>js/admin/permission/auditdiv.js" type="text/javascript"></script> 
</body>
</html>