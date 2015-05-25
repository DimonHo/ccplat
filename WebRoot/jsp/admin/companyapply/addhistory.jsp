
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
    String path = request.getContextPath();
    String basePath =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
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
					历史记录
				</legend>
				<div id="" class="pad5">
					<table width="450" class="mini-table-w85 h130">
						<tr>
						<td>
							<!-- visible="false"  -->
						</td>
						<td>
							<input type="text" visible="false"  name="id" id="id" class="mini-textbox" />
						</td>
						</tr>
						<tr>
						<td>
							操作时间
						</td>
						<td>
							<input type="createtime" enabled="false" name="createtime" id="createtime" class="mini-textbox" />
						</td>
						</tr>
						
						<tr>
						<td>
							备注
						</td>
						<td>
							<input name="remark" class="mini-textarea" valueField="remark" textField="remark" style="width:300px;"/>			
							</tr>
					</table>
				</div>
			</fieldset>
			<div class="pad10 mle100">              
				
				<a class="mini-savebtn mr20" id="OK" dir="ltr" onclick="onOk"	>保存</a>
				<a class="mini-dropbtn" onclick="onCancel" >取消</a>
			</div>
		</form>
	</body>
		<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
	<script src="js/admin/companyapply/addhistory.js" type="text/javascript">
</script>

</html>
</body>
</html>
