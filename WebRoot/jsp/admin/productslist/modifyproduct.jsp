<%--
-文件名：modifyproduct.jsp
-日期：2014.9.22
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：修改产品页面
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
	    <title>修改产品</title>
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
	   <!--  <script type="text/javascript" src="js/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="js/jquery/ajaxfileupload.js"></script> -->
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		
	</head>
	<body>
		<div style="padding-left:11px;padding:5px 0;"> </div>
	     
	    <form id="form" method="post" >
	       
       		<div style="border-bottom:solid 1px #aaa;padding:3px; width:85%;margin:0 auto;"><b>产品信息</b></div>  
            <div style="padding:5px;">
	        	<table style=" width:85%;margin:0 auto;height:150px;">
	        		<tr style="display:none" id="codetr">
		                <td >产品编码：</td>
		                <td >    
		                    <input name="code" id="code" class="mini-textbox" height="25" width="235" required="true" />
		                </td>
		            </tr>
		            <tr>
		                <td >产品名称：</td>
		                <td >    
		                    <input name="name" id="name" class="mini-textbox" height="25" width="235" required="true" />
		                </td>
		            </tr>
		            <tr id="icotr">
                        <td >图标ico：</td>
                        <td >    
                        	<jsp:include page="../../../upload.jsp">
								<jsp:param value="defaultPictureSpanId" name="spanId"/>
								<jsp:param value="defaultPicture" name="textId"/>
								<jsp:param value="one" name="showType"/>
								<jsp:param value="" name="imgPath"/>
								<jsp:param value="2" name="uploadId"/>
								<jsp:param value="2" name="uploadType"/>
								<jsp:param value="" name="srcPath"/>
							</jsp:include>
							<input name="ico" id="ico" class="mini-textbox"  height="25" width="235"/>
							<input name="defaultPicture" id="defaultPicture" type="hidden"  height="25" width="235" />
                        </td>
                    </tr>
                    
                    <tr>
                        <td >产品说明：</td>
                        <td >    
                            <input name="remark" id="remark" class="mini-textbox"  height="25" width="235" required="true" />
                        </td>
                    </tr>
                    <tr style="display:none" id="urltr">
                        <td >url：</td>
                        <td >    
                            <input name="url" id="url" class="mini-textbox"  height="25" width="235" />
                        </td>
                    </tr>
                    <tr style="display:none" id="opentr">
                        <td >打开方式：</td>
                        <td >
	                        <input name="open" id="open" class="mini-combobox" style="width:235px;" textField="text" valueField="open" 
	    						url="<%=basePath%>jsp/admin/productslist/open.txt" allowInput="false"/>
                        </td>
                    </tr>
                      <tr  >
                        <td >height：</td>
                        <td >    
                            <input name="height" id="height" class="mini-textbox"  height="25" width="235" />
                        </td>
                    </tr>
                      <tr  >
                        <td >headHtml：</td>
                        <td >    
                            <input name="headHtml" id="headHtml" class="mini-textbox"  height="25" width="235" />
                        </td>
                    </tr>
                     <tr  >
                        <td >type：</td>
                        <td >    
                            <input name="type" id="type" class="mini-textbox"  height="25" width="235" />
                        </td>
                    </tr>
                     <tr  >
                        <td >src：</td>
                        <td >    
                            <input name="src" id="src" class="mini-textbox"  height="25" width="235" />
                        </td>
                    </tr>
		        </table>        
            </div>
	        <div style="text-align:center;padding:10px;">               
	            <a class="mini-button" onclick="onOk" style="width:60px;margin-right:20px;">确定</a>       
	            <a class="mini-button" onclick="onCancel" style="width:60px;">取消</a>       
	        </div>        
	    </form>
		<script src="<%=basePath%>js/admin/productslist/modifyproduct.js" type="text/javascript"></script>
	</body>
</html>