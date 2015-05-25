mini.parse();

var node;

//标准方法接口定义
var companycc = mini.get("companycc");
function SetData(data) {
	var data = mini.decode(data);
	node = data;
	if (data) {
		companyno = data.companyno;
		companycc.load( {
			companyno : data.companyno,
			ccno : ""
		});
	}
}
function CloseWindow(action) {
	if (action == "close" && form.isChanged()) {
		window.close();
	}
	if (window.CloseOwnerWindow) {
		return window.CloseOwnerWindow(action);
	} else {
		window.close();
	}
}

function onCancel(e) {
    CloseWindow("close");
}

function onStartTime(e){
	var record = e.record;
	if(e.record == null || record.starttime == null ){
		return "";
	}
	var starttime = record.starttime.$date;
	return mini.formatDate (starttime,"yyyy-MM-dd");
}
function onEndTime(e){
	var record = e.record;
	if(e.record == null || record.endtime == null ){
		return "";
	}
	var endtime = record.endtime.$date;
	return mini.formatDate (endtime,"yyyy-MM-dd");
}


function onState(e){

	var record = e.record;
	if(e.record == null || record.state == null ){
		return "";
	}
	var state = record.state;
	var s="";
	if(parseInt(state) == 0){
		s = '未启用';
	}
	else if(parseInt(state) == 1){
		s = '启用';
	}
	else if(parseInt(state)==2){
		s = "企业停用";
	}
	else if(parseInt(state) == 3){
		s = "平台停用";
	}
	
	return s;
}

function addCC(a) {
	if (typeof (node) == "undefined") {
		mini.alert("请选择企业");
		return false;
	}
	mini.open( {
		url : "jsp/admin/companycc/add.jsp",
		title : "分配CC账号",	
		width : 750,
		height : 480,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(node);
		},
		ondestroy : function(action) {
			companycc.reload();
		}
	});
}

function modifyCC(){
	if (typeof (node) == "undefined") {
		mini.alert("请选择企业");
		return false;
	}
	var ccDate = companycc.getSelecteds();
	
	if (ccDate.length <= 0){
		mini.alert("请选择CC账号");
		return false;
	}
	mini.open( {
		url : "jsp/admin/companycc/modify.jsp",
		title : "重置CC账号期限",
		width : 560,
		height : 480,
		onload : function(){
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(node,ccDate);
		},
		ondestroy : function(action) {
			companycc.reload();
		}
	});
}

var powershow = mini.get("powershow");

function addpower(){
	
	if (typeof (node) == "undefined") {
		mini.alert("请选择企业");
		return false;
	}
	var ccDate = companycc.getSelected();
	if (typeof (ccDate) == "undefined"){
		mini.alert("请选择CC账号");
		return false;
	}
	ccDate = mini.clone(ccDate);
	mini.get("#ccno").setValue(ccDate.ccno);
	mini.get("#id").setValue(ccDate.id);
	mini.get("#uuid").setValue(ccDate.uuid);
    powershow.show();
}

function savepower(){

	var ccno = mini.get("#ccno").getValue();
	if (ccno == ""){
		mini.alert("请选择CC账号");
		return false;
	}
	if (typeof (node) == "undefined") {
		alert("请选择企业");
		return false;
	}
	var form = new mini.Form("form1");
	var data = form.getData();
	form.validate();
	if (form.isValid() == false)
		return;

	var id = mini.get("#id").getValue();
	var ccno = mini.get("#ccno").getValue();
	var remark = mini.get("#remark").getValue();
	var name = mini.get("#name").getValue();
	var uuid = mini.get("#uuid").getValue();
	
	if( confirm("确定要设置管理员吗？") ) {
		$.ajax({
			url: 'companycc/addpower.action',
			type: 'post',
			data: { "id":id,ccno:ccno,companyno:node.companyno,remark:remark,name:name,uuid:uuid},
			cache: false,
			success: function (text){
				if(text=="1"){
					mini.alert("设置成功!");
					powershow.hide();
					companycc.reload();
				}else if( text == "2"){
					mini.alert("设置失败!");
				}else if(text == "4"){
					mini.alert("webService服务返回数据异常!");
				}
				mini.get("#remark").setValue("");
			},
			error: function (jqXHR, textStatus, errorThrown) {
				CloseWindow();
			}
		});
	}
}

function searchCC(){
	var ccno = mini.get("#searchccno").getValue();
	if (node == undefined) {
		mini.alert("请选择公司");
		return ;
	}
	companycc.load( {
		companyno : node.companyno,
		ccno:ccno
	});
}