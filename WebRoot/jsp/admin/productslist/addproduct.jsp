<%--
-文件名：addproduct.jsp
-日期：2014.9.22
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：增加新产品页面
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
	    <title>新增产品</title>
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
	    <script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
	</head>
	<body>
		<div class="padtople"> </div>
	     
	    <form id="form" method="post">
	       
       		<div class="mini-borderbottom-dotted"><b>产品信息</b></div>  
            <div class="pad5">
	        	<table class="mini-table-w85 h150">
	        		<tr>
		                <td >产品编码：</td>
		                <td >    
		                    <input name="code" id="code" class="mini-textbox" height="25" width="235" required="true" value="<%=System.nanoTime() %>"/>
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
	    						url="<%=basePath%>jsp/admin/productslist/open.txt" value="2" allowInput="false"/>
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
	        <div class="pad10 mle124">               
	            <a class="mini-button mini-savebtn mr20" onclick="onOk" >确定</a>       
	            <a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>       
	        </div>        
	    </form>
		
		<script src="<%=basePath%>js/admin/productslist/addproduct.js" type="text/javascript"></script>
	</body>
</html>