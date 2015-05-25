<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>树形联动</title>
	
	<style type="text/css">
	body {
		margin: 0px;
		padding: 0;
		border: 0;
		width: 100%;
		height: 98%;
		overflow: auto;
		overflow-x: hidden;
	}
	#center {
		background: #fff;
		border: 0px;
	}
	#grid1 .mini-panel-border {
		border: 0px;
		border-right: 1px solid #999 !important;
	}
	.loading{background:url('js/ui/css/images/loading.gif') no-repeat center center;}
	</style>
	</head>
	<body>
<div class="mini-fit">
	<table style="width: 100%;">
		<tr>
			<td>
				<a class="mini-button" style="width: 88px;" iconCls="icon-add"
					plain="true" onclick="addCC()">分配帐号</a>
				<a class="mini-button" style="width: 88px;" iconCls="icon-edit"
					plain="true" onclick="modifyCC()">修改期限</a>
	
				<a class="mini-button" style="width: 100px;" iconCls="icon-edit"
					plain="true" onclick="addpower()">设置管理员</a>
	
				<span>CC帐号：</span>
				<input id="searchccno" class="mini-textbox" emptyText="请输入CC号" 
					style="width: 150px;" onenter="onKeyEnter2" />
				<a class="mini-button" style="display: none" onclick="searchCC()" productsid="enterprisemainregistersearch">查询</a>
			</td>
		</tr>
	</table>
	<div id="companycc" class="mini-datagrid" sortField="ccno"
		sortOrder="asc" style="width: 100%; height: 94%;"
		borderStyle="border:0;" url="companycc/list.action"
		showFilterRow="false" allowCellSelect="true" allowResize="true"
		multiSelect="true" pageSize="10" allowCellEdit="true">
		<div property="columns">
			<div width="20" type="indexcolumn">
				序号
			</div>
			<div width="20" type="checkcolumn"></div>
			<div field="ccno" name="ccno" width="50" allowSort="true">
				cc帐号
			</div>
			<div name="starttime" renderer="onStartTime" field="starttime"
				width="100" allowSort="true">
				开始日期
			</div>
			<div name="endtime" renderer="onEndTime" field="endtime"
				width="100" allowSort="true">
				截止日期
			</div>
			<div name="state" field="state" width="100" allowSort="true"
				renderer="onState">
				状态
			</div>
		</div>
	</div>
</div>

<div id="powershow" class="mini-window" title="设置管理员"
	style="width: 480px; height: 260px; horizontal-align: middle;"
	showMaxButton="false" showToolbar="true" showFooter="true"
	showModal="true" allowResize="true" allowDrag="true">
	<form id="form1" method="post">
		<div style="margin-left: 60px;">
			<table width="85%" height="180">
				<tr>
					<td>
						CC账号：
					</td>
					<td>
						<input id='ccno' name="ccno" class="mini-textbox"
							readonly="readonly" required="true"
							style="width: 235px; height: 25px;" />
						<input id='id' name="id" class="mini-hidden" readonly="readonly"
							required="true" style="width: 235px; height: 25px;" />
						<input id='uuid' name="uuid" class="mini-hidden" readonly="readonly"
							required="true" style="width: 235px; height: 25px;" />
					</td>
				</tr>
				<tr>
					<td>
						管理员名称：
					</td>
					<td>
						<input id='name' name="name" class="mini-textbox" required="true"
							style="width: 235px; height: 25px;" value="系统管理员"/>
					</td>
				</tr>
				<tr>
					<td>
						备注:
					</td>
					<td>
						<input name="remark" id="remark"
							style="width: 235px; height: 25px;" class="mini-textbox "
							required="true" />
					</td>
				</tr>
			</table>
		</div>
	</form>
	<div property="footer"
		style="text-align: center; padding-top: 3px; padding-bottom: 3px;">
		<a class="mini-button" style="width: 60px;" onclick="savepower()">保存</a>
	</div>
</div>

<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/companycc/showlist.js" type="text/javascript">
</script>
<script src="js/admin/frame/cookie.js"type="text/javascript"></script>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["enterprisemainregistersearch"]; 
			initPage();
</script>
</body>

</html>
