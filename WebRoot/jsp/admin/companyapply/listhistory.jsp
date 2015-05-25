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
	<div id="datagrid1" class="mini-datagrid" sortField="ccno"
		sortOrder="asc" style="width: 100%; height: 100%;"
		borderStyle="border:0;" url="companyapply/show.action"
		showFilterRow="false" allowCellSelect="true" allowResize="true"
		multiSelect="true" pageSize="10" allowCellEdit="true">
		
		
		<div property="columns">
			<div type="checkcolumn"></div>
			<div field="id" visible="false" width="120" headerAlign="center"
				allowSort="true">
				公司ID
			</div>
			<div field="operator" width="120" renderer="onOpertor" headerAlign="center"
				allowSort="true">
				操作人
			</div>
			<div field="remark" width="120" headerAlign="center"
				allowSort="true">
				备注
			</div>
			<div field="createtime" width="100" headerAlign="center"
				renderer="onCreatetime" dateFormat="yyyy-MM-dd" allowSort="true">
				创建日期
			</div>
		</div>
	</div>
</div>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/companyapply/listhistory.js" type="text/javascript">
</script>

