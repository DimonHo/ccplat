mini.parse();
var form = new mini.Form("form1");
var code = "enterprisemainregisterapply01";
var btn=mini.get("OK");
function SaveData() {
	var o = form.getData();
	form.validate();
	if (form.isValid() == false)
		return;
	var json = mini.encode(o);
	$.ajax( {
		url : "companyapply/add.action",
		type : 'post',
		data : {
			data : json
		},
		cache : false,
		success : function(text) {
			if(text=="1"){
				mini.alert("保存成功");
				btn.setEnabled(false);
			}else{
				mini.alert("保存失败");
			}
			CloseWindow("save");
			
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			// CloseWindow();
	}
	});
}
function CloseWindow(action) {

	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}

function onOk(e) {
	SaveData();
}
function onCancel(e) {
	CloseWindow("cancel");
}

function SetData(data) {
	if (data.action == "edit") {
		//跨页面传递的数据对象，克隆后才可以安全使用
		row = mini.clone(data);
		var flowid = "";
		if (data.flowno != undefined) {
			flowid = data.flowno
		}
		$.ajax( {
			url : "companyapply/query.action",
			dataType : "json",
			cache : false,
			data : {
				id : row.id,
				flowid : flowid,
				data : row
			},
			success : function(text) {
	
				var test = mini.encode(text);
				test = test.toString().substring(1, test.toString().length - 1);
				var o = mini.decode(test);
				form.setData(o);
				form.setChanged(false);
				mini.getbyName("id").setValue(row.id);
				if (o.history != undefined) {
					mini.getbyName("remark").setValue(
							o.history[0].remark);
				}
				if (o.address != undefined) {
					mini.getbyName("address").setValue("");
				}
	
			}
		});
	}
}

function onCloseClick(e) {
	var obj = e.sender;
	obj.setText("");
	obj.setValue("");
}
