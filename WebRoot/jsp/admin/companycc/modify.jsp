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
	height: 100%;
	overflow: auto;
	overflow-x: hidden;
}
#center {
	background: #fff;
	border: 0px;
}
#grid1.mini-panel-border {
	border: 0px;
	border-right: 1px solid #999 !important;
}
.mini-listbox-showColumns .mini-listbox-view td{border-right:0px !important;;border-bottom:0px !important;}
.mini-listbox-item-selected{color:#2d70c1 !important;background:#ffffef !important;}
.mini-listbox-item-hover{background: #fbf5de !important;}
</style>
</head>
<body>
	<form id="form1" method="post">
		<fieldset 
			style=" padding: 3px; width: 98%; margin: 0 auto;border:0px; border-top:solid 1px #aaa;">
			<legend>
				基本信息
			</legend>
			<div style="padding: 5px;">
				<table height="260px" width="100%">
					<tr>
						<td align="right" width="15%">
							开始日期:
						</td>
						<td >
					    <input id="starttime" name="starttime" width="180" style="width:180px" class="mini-datepicker" value="" required="true" />
						    &nbsp;&nbsp;至&nbsp;&nbsp;
						<input id="endtime" name="endtime" width="180"  style="width:180px" class="mini-datepicker" value="" required="true" />
						</td>
					</tr>
					
					<tr>
						<td align="right" width="15%">
							备注:
						</td>
						<td>
					    	<input name="remark" id="remark" autocomplete="off" placeholder="请输入备注" class="mini-textarea" 
					    		style="width:402px;height:60px;"/>
					    </td>
					</tr>
					
					<tr>
						<td align="right">选中的信息:</td>
						<td colspan="3">
							<div id="listbox2" class="mini-listbox" style="width: 90%; height: 210px; background: #FFF;" showCheckBox="false" multiSelect="true">
								<div property="columns">
									<div header="序号" type="indexcolumn"></div>
									<div header="CC账号" idField="ccno" field="ccno"></div>
									<div header="开始日期" renderer="onStartTime" idField="ksrq" field="starttime" dateformat="yyyy-MM-dd"></div>
									<div header="截止日期" renderer="onEndTime" idField="jsrq" field="endtime" dateformat="yyyy-MM-dd"></div>
									<div header="状态" renderer="onState" idField="state" field="state"></div>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<div class="ynnav mini-toolbar">
			<a class="mini-button" onclick="onOk"
				style="width: 60px; margin-right: 20px;">确定</a>
			<a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
		</div>
	</form>
<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="js/admin/companycc/modify.js" type="text/javascript">
</script>
</body>
</html>