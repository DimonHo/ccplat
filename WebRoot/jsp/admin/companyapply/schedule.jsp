<%--
-文件名：schedule.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：COCO
修改人：
备注：待办事项
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
    <div class="mini-splitter" style="width: 100%; height: 98%;">
			<div size="140" showCollapseButton="true" cls="bordernone border-right">
				<div class="mini-fit bgf" >
						 <div id="grids" class="mini-datagrid" url="<%=basePath %>companyapply/getSchedule.action"  showPageInfo="false"
						style="width:100%;height:100%;" borderStyle="border:0;" onrowclick="clickAct">
					<div property="columns">
						<div field="code" width="100" allowSort="true" visible="false" headerAlign="center">
							功能编码
						</div>
						<div field="name" width="80" headerAlign="center" allowSort="true">
							功能名称
						</div>
						<div field="count" width="60" headerAlign="center"   allowSort="true">
							待办事项
						</div>
						<div field="url" width="120" headerAlign="center" visible="false" allowSort="true">
							跳转
						</div>
					</div>	
				</div>
			</div>
			</div>
			<div showCollapseButton="true" >
				 <div id="schedule" class="mini-datagrid" url="<%=basePath %>companyapply/detail.action" 
						style="width:100%;height:100%;" borderStyle="border:0;" multiSelect="true" onrowclick="onSelectionChanged">
					<div property="columns">
						<div field="id" visible="false" width="120" headerAlign="center"
				allowSort="true">
				公司ID
			</div>
			<div field="state" width="100" headerAlign="center"
				renderer="onState" allowSort="true">
				审核状态
			</div>
			<div field="flowid" width="100" headerAlign="center"
				 allowSort="true">
				流程编码
			</div>			
			<div field="name" width="120" headerAlign="center" allowSort="true">
				公司名称
			</div>
			<div field="street" width="120" headerAlign="center" allowSort="true">
				公司地址
			</div>
			<div field="contacts" width="120" headerAlign="center"
				allowSort="true">
				联系人
			</div>
			<div field="phone" width="120" headerAlign="center" allowSort="true">
				联系电话
			</div>
			
			
			
							<div field="url" width="120" headerAlign="center" allowSort="true">
							url地址
						</div>
						<div field="urlaction" width="120" headerAlign="center" allowSort="true">
							urlaction地址
						</div>
						<div field="ccno" width="100" allowSort="true" headerAlign="center">
							cc编码
						</div>
						<div field="actno" width="100" allowSort="true" headerAlign="center">
						ruleno
						</div>
						<div field="ruleno" width="100" allowSort="true" headerAlign="center">
							规则编码
						</div>
						<div field="content" width="100" allowSort="true" headerAlign="center">
							内容
						</div>
						<div field="ruletype" width="100" allowSort="true" headerAlign="center">
							规则类型
						</div>
						<div field="rulestart" width="100" allowSort="true" headerAlign="center">
							最小值
						</div>
						<div field="ruleend" width="100" allowSort="true" headerAlign="center">
							最大值
						</div>
						<div field="key" width="100" allowSort="true" headerAlign="center">
							关键字
						</div>
						
						<div field="submit" width="100" allowSort="true" headerAlign="center">
							提交人
						</div>
						
			
			
			<div field="createtime" width="100" headerAlign="center"
				renderer="onCreatetime" dateFormat="yyyy-MM-dd" allowSort="true">
				创建日期
			</div>
				
					</div>	
				</div>
			</div>
		</div>
    	<script src="<%=basePath %>js/ui/scripts/boot.js" type="text/javascript"></script> 
    	<script src="<%=basePath %>js/admin/companyapply/schedule.js" type="text/javascript"></script> 
</body>
</html>