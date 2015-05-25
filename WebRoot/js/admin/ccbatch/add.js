mini.parse();

$.ajax( {
	url : "ccnumber/maxCcNo.action",
	type : 'post',
	data : {
		type : 2
	},
	cache : false,
	success : function(text) {
		var type = $(":radio:checked").val();
		if(parseInt(type) == 2 && parseInt(text) == 100000 ){
			$("#startNospan").html("该类型账号已全部开放请选择其它类型");
		}else if(parseInt(type) == 3 && parseInt(text) == 10000000){
			$("#startNospan").html("该类型账号已全部开放请选择其它类型");
		}else{
			$("#startNo").val(text);
		}
	},
	error : function(jqXHR, textStatus, errorThrown) {
		mini.alert(jqXHR.responseText);
		CloseWindow();
	}
});
	
function IsNum(num){
	var reNum=/^\d*$/;
	return(reNum.test(num));
} 

function SaveData() {
	var form = new mini.Form("form1");
	var type = $(":radio:checked").val();
	
	var startNo = $("#startNo").val();
	if(startNo == "" || !IsNum(startNo)){
		$("#startNospan").html("请填写起始号码，必须为数字");
		return ;
	}else{
		$("#startNospan").html("");
	}
	var count = $("#count").val();
	if(count == "" || !IsNum(count)){
		$("#countspan").html("请填写开放数量，必须为数字");
		return ;
	}else{
		$("#countspan").html("");
	}
	
	if(parseInt(count)+parseInt(startNo) > parseInt("100000") && parseInt(type) == parseInt("2")){
		$("#countspan").html("4-5位类型中开放的数量超过该类型的数量，该类型剩余"+(parseInt("100000")-parseInt(startNo))+"个");
		return ;
	}else{
		$("#countspan").html("");
	}
	
	if(parseInt(count)+parseInt(startNo) > parseInt("10000000") && parseInt(type) == parseInt("3")){
		$("#countspan").html("6-7位类型中开放的数量超过该类型的数量，该类型剩余"+(parseInt("100000")-parseInt(startNo))+"个");
		return ;
	}else{
		$("#countspan").html("");
	}
	
	
	var data = form.getData();
	form.validate();
	if (form.isValid() == false)
		return;
	mini.get("OK").disable();
	data = mini.encode(data);
	var remark = mini.get("remark").getValue();
	$.ajax( {
		url : "ccbatch/add.action",
		type : 'post',
		data : {
			remark: remark,
			count: count,
			startNo: startNo,
			type : type
		},
		cache : false,
		success : function(text) {
			if(text == "1"){
				mini.alert("CC账号创建成功!");
			}else if(text == "2"){
				mini.alert("CC账号创建失败!");
			}else{
				mini.alert("CC账号创建失败，没有空余账号!");
			}
			CloseWindow(text);
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		},complete:function(jqXHR){
		},beforeSend:function(jqXHR){
			
			var m = $("<div id='m'></div>").css( {
				position : "absolute",
				top : $("body").offset().top + 18,
				left : $("body").offset().left,
				zIndex : 99,
				color : 'red'
			});
			var s = "正在开放号码，请稍候！";
			m.html(s).appendTo("body");
			$("body").focus();
		}
	});
}

//标准方法接口定义
function SetData(data) {
	 mini.decode(data);
	 mini.get("#companyno").setValue(data.companyno);
}

function CloseWindow(action) {

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

function ccsile(u){
	$("#cc_auto").hide();
	$(u).html("自动分配");
}

$(".radios").click(function(){
	var type = $(":radio:checked").val();
	$.ajax( {
		url : "ccnumber/maxCcNo.action",
		type : 'post',
		data : {
			type : type
		},
		cache : false,
		success : function(text) {
			if(parseInt(type) == 2 && parseInt(text) == 100000 ){
				$("#startNospan").html("该类型账号已全部开放请选择其它类型");
				$("#startNo").val("");
			}else if(parseInt(type) == 3 && parseInt(text) == 10000000){
				$("#startNospan").html("该类型账号已全部开放请选择其它类型");
				$("#startNo").val("");
			}else{
				$("#startNo").val(text);
				$("#startNospan").html("");
			}
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	});
});
