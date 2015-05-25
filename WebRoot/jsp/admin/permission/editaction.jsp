<%--
-文件名：editaction.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：权限范围编辑
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
    <form id="form1" method="post">
        <fieldset style="border:solid 0px #aaa;padding:3px;border-top:1px solid #aaa;">
            <legend >基本信息</legend>
            <div style="padding:5px;">
        <table height="210" width="100%" style="margin-left:-15px;">
          <tr>
         	 <td align="right" style="width:120px;"> 功能名称：</td>
             <td>    
                <input id="actname" name="actname" style="width:220px;" class="mini-textbox" required="true"/>
             </td>
         </tr>
         <tr>
            <td style="width:120px;" align="right"> 规则：</td>
            <td style="width:220px;">    
                 <input class="mini-combobox" id="rule" style="width:150px;" textField="name" valueField="id" 
  				 showNullItem="false" allowInput="true" onvaluechanged="changeRuleName"/>
            </td>
         </tr>
         <tr>
            <td style="width:120px;" align="right">规则类型：</td>
            <td style="width:220px;">    
               <input class="mini-combobox" id="ruleType" style="width:150px;" textField="name" valueField="id" 
  					 url="<%=basePath %>audit/ruleType.action" showNullItem="false" allowInput="true"/>
            </td>
         </tr>
         <tr>
            <td style="width:120px;" align="right">规则开始：</td>
            <td style="width:220px;">    
                <input id="rulestart" name="rulestart" style="width:220px;" class="mini-textbox" required="true"/>
            </td>
         </tr>
         <tr>
            <td style="width:120px;" align="right">规则结束：</td>
            <td style="width:220px;">    
                <input  id="ruleend" name="ruleend" style="width:220px;" class="mini-textbox" required="true"/>
            </td>
         </tr>
         <tr>
            <td style="width:120px;" align="right"> 提交人员：</td>
            <td style="width:240px;">  
           		 <input class="mini-combobox" id="submit" style="width:150px;" textField="name" valueField="id" 
  					 multiSelect="true" showNullItem="false" allowInput="true"/>
            </td>
         </tr>
        </table>            
       </div>
        </fieldset>
        <div class="ynnav mini-toolbar">               
            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
        </div>        
    </form>
    
<script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
<script src="<%=basePath %>js/admin/permission/editaction.js" type="text/javascript"></script> 
</body>
</html>