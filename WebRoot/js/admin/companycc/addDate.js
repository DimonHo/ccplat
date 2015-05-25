mini.parse();
var ccjson,datafrom;
var form = new mini.Form("form1");

var company=null;
function SaveData() {
	
	var data = form.getData();
	form.validate();
	if (form.isValid() == false)
		return;
	data = mini.encode(data);
	$("#errorInfo").html("正在分配数据请稍候！");
	$.ajax( {
		url : "companycc/add.action",
		type : 'post',
		data : {
			data : data,company:company
		},
		cache : false,
		success : function(text) {
			if(text == "1"){
				mini.alert("企业CC添加成功! ");
			}else if(text == "2"){
				mini.alert("企业CC添加失败! ");
			}else if(text == "4"){
				mini.alert("企业CC添加操作异常! ");
			}else if(text == "4"){
				mini.alert("访问企业端的webService的IP,端口为空! ");
			}else if(text == "5"){
				mini.alert("访问企业端的webService请求失败! ");
			}else{
				mini.alert("请求失败!");
			}
			CloseWindow("success");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	});
}

////////////////////
//标准方法接口定义
function SetData(data,ccjson,companyData) {
	company=companyData;
	datafrom = mini.decode(data);
	mini.get("#companyno").setValue(datafrom.companyno);
	ccjson = mini.decode(ccjson);
	mini.get("#ccjson").setValue(ccjson);
	
	var cDate = new Date();
	var iYear = cDate.getFullYear();
	var iMonth = cDate.getMonth() + 1;
	var iDate = cDate.getDate();
	var eDate = iYear+1 + "-" + iMonth + "-" + iDate;
	mini.get("endtime").setValue(eDate);
	
	return ;
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
	mini.get("ok").disable();
	SaveData();
}
function onCancel(e) {
    CloseWindow("close");
}