<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="remark-type" content="text/html; charset=UTF-8" />
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

#grid1 .mini-panel-border {
	border: 0px;
	border-right: 1px solid #999 !important;
}
</style>
	</head>
	<body id="okinfo">
		<form id="form1" method="post">
			<fieldset
				style="border: solid 0px #aaa; padding: 3px; width: 95%; margin: 0 auto; border-top: solid 1px #aaa;">
				<legend>
					基本信息
				</legend>
				<div id="" style="padding: 5px;">
					<table height="300" width="480" style="margin-left: 30px;">
						<tr>
							<td style="width: 90px;">
								企业号码
							</td>
							<td>
								<input type="text" readonly="readonly" name="id"
									id="id" class="mini-hidden" />
								<input type="text" readonly="readonly" name="companyno"
									id="companyno" class="mini-textbox" />
							</td>
						</tr>
						<tr>
							<td>
								ip地址
							</td>
							<td>
								<input type="text" name="ipAddress" id="ipAddress" class="mini-textbox" />
							</td>

						</tr>
						<tr>
							<td>
								端口
							</td>
							<td>
								<input type="text" name="port" id="port" class="mini-textbox" />
							</td>

						</tr>
						<tr>
							<td>
								备注
							</td>
							<td>
								<input name="remark" class="mini-textarea" valueField="remark"
									textField="remark" />
							</td>

						</tr>
					</table>
				</div>
			</fieldset>
			<div class="ynnav mini-toolbar">
				<a class="mini-button" onclick="ok" style="width: 60px;">保存</a>
				<a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
			</div>
		</form>
		<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
		<script src="js/admin/company/modify.js" type="text/javascript">
</script>
	</body>



</html>

