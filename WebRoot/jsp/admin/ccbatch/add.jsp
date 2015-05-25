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
<body id="okinfo" >
<form id="form1" method="post">
	<fieldset
		style="border: solid 0px #aaa; padding: 3px; width: 95%; margin: 0 auto; border-top:solid 1px #aaa; ">
		<legend>
			基本信息
		</legend>
		<div id= "" class="pad5">
			<table height="180" width="600" style="margin-left:30px;">
				<tr>
					<td style="width: 90px;">
						号码位数:
					</td>
					<td>
						<label for="s5" class="mini-tree-nodeshow fl">
							<input type="radio" value="2" name="type" checked="checked" id="s5" class="fl radios"/>
							4-5位数
						</label>
						
						<label for="s6" class="mini-tree-nodeshow fl">	
							<input type="radio" value="3" name="type" id="s6" class="fl radios"/>
							6-7位数
						</label>
						
						<label for="s7" class="mini-tree-nodeshow fl">	
							<input type="radio" value="4" name="type" id="s7" class="fl radios"/>
							8位数以上
						</label>
					</td>
				</tr>
				<tr>
					<td style="width: 60px;">
						起始号码:
					</td>
					<td>
						<input type="text" id="startNo" readonly="readonly" name="startNo" class="inputText" value=""
							style="width: 100px;"/>
						&nbsp;&nbsp;数量:&nbsp;<input type="text" id="count" name="count" class="inputText" value=""
							style="width: 100px;"/>
							<span style="color: red;" id="startNospan"></span>
							<span style="color:red;" id="countspan"></span>
					</td>
				</tr>
				<tr>
					<td style="width: 90px;">
						开放说明:
					</td>
					<td>
					<input name="content" id="remark" class="mini-textarea"
								required="true" style="width:400px; height: 100px;" />
					</td>
				</tr>
			</table>
		</div>
	</fieldset>
	<div class="ynnav mini-toolbar" style="padding-left:135px;">
		<a class="mini-button  mini-savebtn  mr20" id="OK" dir="ltr" onclick="onOk" >确定</a>
		<a class="mini-button  mini-dropbtn" onclick="onCancel" >取消</a>
	</div>
</form>
<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>js/admin/ccbatch/add.js" type="text/javascript"></script>
</body>
</html>