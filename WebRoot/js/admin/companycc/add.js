mini.parse();
var listbox1 = mini.get("listbox1");
var listbox2 = mini.get("listbox2");
//公司数据
var company = null;

function aabbcc(s) {
	listbox1.removeAll();
	setTimeout(function() {
		listbox1.load('ccnumber/notAllot.action');
	}, 1000);
}

function onBeforeCcLoad(e) {

	var params = e.params;
	var sender = e.sender;
	var l = false
	if ($("#cl").is(':checked')) {
		l = true;
	}
	params.createlianghao = l;

	//生成几位数的CC
	var h = $(":radio:checked").val();
	params.type = h;

	//生成多少个
	var s = $("#suliang").val();
	params.num = s;

	//号码段起始位 
	var start = $("#st").val();
	params.start = start;

	//生成类型 
	var ctype = $("#ctype").val();
	params.ctype = ctype;
}

var v1 = mini.get("abcabc");
var v2 = mini.get("aaabbb");
var v3 = mini.get("ababab");
var v4 = mini.get("aabbcc");

$("#s4").click(function() {
	v1.disable();
	v2.disable();
	v3.disable();
	v4.disable();

	cc_all();
});

$("#s5").click(function() {
	v1.disable();
	v2.disable();
	v3.disable();
	v4.disable();
});

$("#s6").click(function() {
	v1.enable();
	v2.enable();
	v3.enable();
	v4.enable();
	cc_all();
});

//取出系统的全部CC号码 
$("#cc_all").click(function() {
	listbox2.removeAll();
	listbox1.removeAll();
	cc_all()
});

function cc_all() {

	listbox1.load("ccnumber/notAllot.action");
}

function add() {
	var itemsright = listbox1.getSelecteds();
	listbox1.removeItems(itemsright);
	listbox2.addItems(itemsright);
	listbox2.selectAll();
}

function removes() {
	var items = listbox2.getSelecteds();
	listbox2.removeItems(items);
	listbox1.addItems(items);
}

function IsNum(num) {
	var reNum = /^\d*$/;
	return (reNum.test(num));
}

//起始号码段
$("#st").blur(function() {
	var o = this;
	var v = $(o).val();

	var h = $(":radio:checked").val();
	$("#m").remove();

	if (!IsNum(v)) {
		var m = $("<div id='m'></div>").css( {
			position : "absolute",
			top : $(o).offset().top + 18,
			left : $(o).offset().left,
			zIndex : 99,
			color : 'red'
		});
		var s = "请输入数字";
		m.html(s).appendTo("body");
		$(o).focus();
		return false;
	}

});

//分配数量
$("#suliang").blur(function() {
	var o = this;
	var v = $(o).val();

	var h = $(":radio:checked").val();
	$("#m").remove();

	if (!IsNum(v)) {
		var m = $("<div id='m'></div>").css( {
			position : "absolute",
			top : $(o).offset().top + 18,
			left : $(o).offset().left,
			zIndex : 99,
			color : 'red'
		});
		var s = "请输入数字";
		m.html(s).appendTo("body");
		$(o).focus();
		return false;
	}

});

var form = new mini.Form("form1");
var companyno = mini.get("companyno");

function SaveData() {
	var data = form.getData();
	form.validate();

	if (form.isValid() == false)
		return;
	var url = "companycc/add.action";

	data = mini.encode(data);
	var cc = listbox2.getData();

	if (cc.length <= 0) {
		mini.alert("请选择分配的CC号");
		return;
	}
	var ccjson = mini.encode(cc);
	mini.open( {
		url : "jsp/admin/companycc/addDate.jsp",
		title : "设置使用期限",
		width : 440,
		height : 280,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(data, ccjson, company);
		},
		ondestroy : function(action) {
			if (action == "success")
				CloseWindow("close");
		}
	});
}

//标准方法接口定义
function SetData(data) {
	mini.decode(data);
	mini.get("#companyno").setValue(data.companyno);
	company=mini.encode(data);
}
function CloseWindow(action) {
	if (action == "close" && form.isChanged()) {
		if (confirm("数据被修改了，是否先保存？")) {
			return false;
		}
	}
	if (window.CloseOwnerWindow) {
		return window.CloseOwnerWindow(action);
	} else {
		window.close();
	}
}function onOk(e) {
	SaveData();
}
function onCancel(e) {
	CloseWindow("close");
}
