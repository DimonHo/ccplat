mini.parse();
var d = {};
var reg = "";
var m = 0;
var form = new mini.Form("form1");
var cDate = new Date();
var iYear = cDate.getFullYear();
var iMonth = cDate.getMonth() + 1;
var iDate = cDate.getDate();
var sDate = iYear + "-" + iMonth + "-" + iDate;
var eDate = iYear+1 + "-" + iMonth + "-" + iDate;
mini.get("starttime").setValue(sDate);
mini.get("endtime").setValue(eDate);
function SaveData() {
	var data = form.getData();
	form.validate();
	if (form.isValid() == false)
		return;
	var url = "companycc/modify.action";
	data = mini.encode(data);
	var listbox2 = mini.get("listbox2");
	listbox2.selectAll();
	var json = listbox2.getSelecteds();
	if(json.length < 0 ){
		return false;
	}
	json = mini.encode(json);
	$.ajax( {
		url : url,
		type : 'post',
		data : {
			data : data,
			json:json
		},
		cache : false,
		success : function(text) {
			mini.alert("企业CC编辑成功! ");
			CloseWindow("close");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			CloseWindow();
		}
	});
}
//标准方法接口定义
function SetData(node,datas) {
	node = mini.clone(node);
	form.setData(node);
	datas = mini.clone(datas);
	var listbox2 = mini.get("listbox2");
	listbox2.addItems(datas);
	listbox2.selectAll();
	
	
	var cDate = new Date();
	var iYear = cDate.getFullYear();
	var iMonth = cDate.getMonth() + 1;
	var iDate = cDate.getDate();
	mini.get("starttime").setValue(iYear + "-" + iMonth + "-" + iDate);
	mini.get("endtime").setValue(iYear+1 + "-" + iMonth + "-" + iDate);
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
function onStartTime(e){
	var record = e.record;
	if(e.record == null || record.starttime == null ){
		return "";
	}
	var starttime = record.starttime.$date;
	return mini.formatDate (starttime,"yyyy-MM-dd" )
}
function onEndTime(e){
	var record = e.record;
	if(e.record == null || record.endtime == null ){
		return "";
	}
	var endtime = record.endtime.$date;
	return mini.formatDate (endtime,"yyyy-MM-dd" )
}
function onState(e){
	var record = e.record;
	var state = record.state;
	var s="";
	if(parseInt(state) == 1){
		s = '启用';
	}
	else if(parseInt(state)==2){
		s = "停用";
	}
	return s;
}