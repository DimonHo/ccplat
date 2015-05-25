<%--
-文件名：productupdate.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：产品修改
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title></title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
    <style type="text/css">
    html,body
    {
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
	<div class="mini-splitter" style="width: 100%; height: 90%;">
			<div size="200" showCollapseButton="true" cls="bordernone border-right"  style="height: 90%;">
				<div class="mini-fit">
					<div class="mini-toolbar"
						style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
						<a>行业信息</a>
					</div>
					<ul id="industryTree" class="mini-tree" 
						url="<%=basePath %>ccindustryinfo/listIndustryinfo.action" style="width: 100%;height: 80%;""
						showTreeIcon="true" textField="name" expandOnLoad="true" 
						allowDrop="true" allowLeafDropIn="true"
						idField="uuid">
					</ul>
				</div>
			</div>
			<div showCollapseButton="true" style="width: 100%;height: 90%" cls="bordernone border-left">
				 <div class="mini-fit">
					 	<div class="mini-splitter" style="width: 100%; height: 100%;">
					 		<div size="450" showCollapseButton="true" cls="bordernone border-right">
								<div class="mini-fit">
									<div class="mini-toolbar"
										style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
										<a>公司列表</a>
									</div>
									
									 <div id="grids" class="mini-datagrid" url="<%=basePath %>cccompany/list.action" 
											style="width:100%;height:93%;" borderStyle="border:0;" multiSelect="true">
										<div property="columns">
											<div field="companyno" width="100" allowSort="true" headerAlign="center">公司编码</div>
											<div field="name" width="120" headerAlign="center" allowSort="true">公司名称</div>
										</div>	
									</div>
						       </div>
		      	 			</div>
			    		<div showCollapseButton="true" style="width: 100%;height: 100%" cls="bordernone border-left">
						 <div class="mini-fit">
								<div class="mini-toolbar"
									style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
									<a>产品列表</a>
								</div>						 
						 	 <ul id="tree" class="mini-tree" style="width:100%;height:90%;" 
						            showTreeIcon="true" textField="name" idField="id" showCheckBox="true" parentField="pid" resultAsTree="false"  
						            expandOnLoad="false" onnodedblclick="onNodeDblClick" expandOnDblClick="false">
				        	</ul>
						 </div>
					 </div>
			        </div>
				</div>
			</div>
	</div>
	<div class="mini-toolbar" borderStyle="border-left:0;border-bottom:0;border-right:0;">
	        <a class="mini-button" style="width:60px;" onclick="save()">确定</a>
	        <span style="display:inline-block;width:25px;"></span>
	        <a class="mini-button" style="width:60px;" onclick="onCancel()">取消</a>
    	</div>
	 <div id="win1" class="mini-window" title="授权时间" style="width:400px;height:100px;horizontal-align:middle;" showMaxButton="false" showToolbar="true" showFooter="true" showModal="true" allowResize="true" allowDrag="true">
		  <div style="margin-left:60px;">  
		       授权时间：<input id="stime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />至
          <input id="etime" class="mini-datepicker" style="width:100px;" format="yyyy-MM-dd" showTime="true" />
          </div>
		  <div property="footer"  style="text-align:center;padding-top:2px;padding-bottom:2px;">
		        <a class="mini-button" style="width:60px;" onclick="SaveData()" style="horizontal-align:middle;">确定</a>
		  </div>
	</div>      
	<script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
    <script src="<%=basePath %>js/admin/productslist/productupdate.js" type="text/javascript"></script> 
</body>
</html>