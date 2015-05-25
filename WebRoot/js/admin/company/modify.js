mini.parse();

var form = new mini.Form("form1");

function CloseWindow(action) {

	if (window.CloseOwnerWindow)
		return window.CloseOwnerWindow(action);
	else
		window.close();
}

function ok(e) {
    if (form.isValid() == false) {
        return;
    }
    var o = form.getData();
    form.validate();
    var json = mini.encode(o);
    $.ajax({
        url: "cccompany/modify.action",
		type: 'post',
        data: { data: json },
        cache: false,
        success: function (text) {
        	if(text=="1"){
	            CloseWindow("save");
	            mini.alert("保存成功");
	        }else{
	        	 mini.alert("保存失败");
	        }
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	mini.alert(jqXHR.responseText);
        }
    });
}

function onCancel(e) {
	CloseWindow("cancel");
}

function SetData(data) {
	data = mini.clone(data);
	form.setData(data);
}
