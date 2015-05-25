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
			<fieldset 
				style="border: solid 1px #aaa; padding: 3px; width: 98%; margin: 0 auto;">
				<legend>
					基本信息
				</legend>
				<div style="padding: 5px;">
					<table height="200">
						<tr>
							<td align="right">
								CC号码类型:
							</td>
							<td>
								<input type="radio" value="1" name="special" id="s1" class="fl"/>
								<label for="s1" class="mini-tree-nodeshow fl">
								普通
								</label>
								<input type="radio" value="2" name="special" id="s2" class="fl"/>
								<label for="s2" class="mini-tree-nodeshow fl">
								特殊
								</label>
								<span id="specialspan" style="color: red;"></span>
							</td>
							<td align="right">
								状态:
							</td>
							<td>
								<label>
								<input type="radio" value="0" name="state" id="state1"/>
								未启用
								</label>
								<label >
								<input type="radio" value="1" name="state" id="state2" class=""/>
								正常
								</label>
								<label>
								<input type="radio" value="2" name="state" id="state3" class=""/>
								停用
								</label>
								<span id="statespan" style="color: red;"></span>
							</td>
						</tr>
						
						<tr>
							<td>选中的信息</td>
							<td colspan="3">
								<div id="listbox2" class="mini-listbox"
									style="width: 500px; height: 200px; background: #FFFFE6;"
									showCheckBox="true" multiSelect="true">
									<div property="columns">
										<div header="序号" type="indexcolumn"></div>
										<div field="ccno" name="ccno" width="100" allowSort="true">
											cc帐号
										</div>
										<div name="createtime" field="createtime" dateformat="yyyy-MM-dd" width="100" allowSort="true">
											开始时间
										</div>
										<div name="type" field="type" width="100" allowSort="true" >
											类型
										</div>
										<div name="state" field="state" width="100" allowSort="true">
											状态
										</div>
										<div name="special" field="special" width="100" allowSort="true">
											号码类型
										</div>
										<div name="bit" field="bit" width="100" allowSort="true">
											号码位数
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
			<div style="text-align: center; padding: 10px;">
				<a class="mini-button" onclick="onOk"
					style="width: 60px; margin-right: 20px;">确定</a>
				<a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
			</div>
		</form>
		
<script src="<%=basePath%>ui/scripts/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>ui/scripts/jquery-1.6.2.min.js" type="text/javascript"></script>

<script type="text/javascript">
mini.parse();
var d = {};
var reg = "";
var m = 0;
var form = new mini.Form("form1");

function SaveData() {
	
	var listbox2 = mini.get("listbox2");
	var json = listbox2.getSelecteds();
	if(json.length < 0 ){
		return false;
	}
	var state = $('input:radio[name="state"]:checked').val();
	var special = $('input:radio[name="special"]:checked').val();
	if(state == undefined){
		$("#statespan").html("请选择状态");
		return;
	}else{
		$("#statespan").html("");
	}
	if(special == undefined){
		$("#specialspan").html("请选择类型");
		return;
	}else{
		$("#specialspan").html("");
	}

	json = mini.encode(json);
	$.ajax( {
		url : "ccnumber/modify.action",
		type : 'post',
		data : {
			special : special,
			state:state,
			json:json
		},
		cache : false,
		success : function(text) {
			success("企业CC编辑成功! ");
			CloseWindow("close");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	});
}

//标准方法接口定义
function SetData(datas) {

	datas = mini.clone(datas);
	var listbox2 = mini.get("listbox2");
	listbox2.addItems(datas);
	listbox2.selectAll();
	var length = datas.length;
	if(length > 0 ){
		var data = datas[0];
		var special = data.specialnum;
		if(special == 1){
			$("#s1").attr("checked",true);
		}else if(special == 2){
			$("#s2").attr("checked",true);
		}
	}
}

function CloseWindow(action) {
	if (action == "close" && form.isChanged()) {
		if (confirm("数据被修改了，是否先保存？")) {
			return false;
		}
	}
	if (window.CloseOwnerWindow){
		return window.CloseOwnerWindow(action);
	}else{
		window.close();
	}
}
function onOk(e) {
	SaveData();
}
function onCancel(e) {
    CloseWindow("close");
}

</script>
</body>
</html>