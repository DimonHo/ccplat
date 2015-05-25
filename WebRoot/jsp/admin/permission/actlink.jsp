<%--
-文件名：actlink.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：功能联动
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
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <title>树形联动</title>
    <style type="text/css">
    body{
      margin:8px;padding:0 ;border:0;width:100%;height:100%;overflow:hidden;
    }    
      a span.mini-button-text{padding-bottom:0px;}
    </style>  
</head>

<body>
    <div style="padding: 5px;height:150px;"> 
     <table style="table-layout:fixed;">
                <tr style="height:70px;">
                    <td style="width:70px;">处理功能：</td>
                    <td style="width:150px;">    
                        <input id="sub" style="width:300px" class="mini-treeselect" 
					 textField="name" valueField="id" parentField="pid"  onvaluechanged="changeDeal"/>
                    </td>
                    </tr><tr>
                    <td style="width:70px;">处理功能：</td>
                    <td style="width:150px;">    
                       <input id="deal" style="width:300px" class="mini-treeselect" beforenodecheck="isleaf"
					 textField="name" valueField="id" parentField="pid"/>
                    </td>
                </tr>
                </table>
               
	      <div style="margin-left:60px;padding:20px;">               
				
				<a class="mini-button mini-savebtn" id="OK" dir="ltr" onclick="onOk"  
					style="margin-right:20px;">保存</a>
				<a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>
			</div> 
 <script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
 <script src="<%=basePath %>js/admin/permission/actlink.js" type="text/javascript"></script> 
     <!--右健菜单-->
</body>
</html>