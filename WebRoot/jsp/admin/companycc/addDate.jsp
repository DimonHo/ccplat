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
	<base href="<%=basePath%>" />
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>树形联动</title>
	
	<style type="text/css">
	body {
		margin: 8px;
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
	
	#grid1 .mini-panel-border {
		border: 0px;
		border-right: 1px solid #999 !important;
	}
	</style>

	</head>
	<body>

		<form id="form1" method="post">
			<fieldset  style="text-align:center;border: solid 1px #aaa; padding: 2px; width: 340px; margin: 0 auto;">
				<legend>
					使用期限
				</legend>
				<div style="padding: 5px;">
					<table height="50">
						<tr>
							<td>
								开始日期:
							</td>
							<td >
								 <input id="starttime" name="starttime" class="mini-datepicker" value="new Date()" 
								 style="width:100px;horizontal-align:middle;" required="true" showTime="true" format="yyyy-MM-dd"/>
								 <input id="companyno" name="companyno" class="mini-hidden" value=""/>
								 <input id="ccjson" name="ccjson" class="mini-hidden" value=""/>
							至:
								<input id="endtime" name="endtime" class="mini-datepicker" value="" 
										style="width:100px;horizontal-align:middle;" format="yyyy-MM-dd" 
										required="true" showTime="true" />
							</td>
						</tr>
						
						<tr>
							<td>
								备注:
							</td>
							<td>
						    	<input name="remark" id="remark" autocomplete="off" placeholder="请输入备注" class="mini-textarea" 
						    		style="width:210px;height:60px;"/>
						    </td>
						</tr>
						
						<tr>
							<td colspan="2" id="errorInfo">
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			<div style="text-align: center; padding: 10px;">
				<a class="mini-button" onclick="onOk" id="ok"
					style="width: 60px; margin-right: 20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
			</div>
		</form>
<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>js/admin/companycc/addDate.js" type="text/javascript"></script>
</body>
</html>