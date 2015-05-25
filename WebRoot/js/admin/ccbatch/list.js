
mini.parse();
var r = mini.get('r');
var e = mini.get('e');
var s = $("#ss");

var ccbatch = mini.get("ccbatch");
ccbatch.reload();
var ccnumber = mini.get("ccnumber");
//点击批次查询
function onSelectionChanged(e) {
	var grid = e.sender;
	var node = grid.getSelected();
	if (node) {
		ccnumber.load( {
			ccbatchid : node.id
		});
	}
}

function addCC(a) {
	mini.open( {
		url : "jsp/admin/ccbatch/add.jsp",
		title : "开放CC账号",
		width : 580,
		height : 300,
		onload : function() {
			
		},
		ondestroy : function(action) {
			if(action == ""){
				ccbatch.reload();
				ccnumber.reload();
				mini.alert("该类型账号没有剩余");
			}else{
				ccbatch.reload();
				ccnumber.load( {
					ccbatchid : action
				});
			}
		}
	});
}

function modifyCC() {
	var ccDate = ccnumber.getSelecteds();
	if (ccDate.length <= 0){
		mini.alert("请选择CC账号");
		return false;
	}
	
	mini.open( {
		url : "jsp/admin/ccnumber/modify.jsp",
		title : "编辑CC账号",
		width : 620,
		height : 360,
		onload : function(){
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(ccDate);
		},
		ondestroy : function(action) {
			ccnumber.reload();
		}
	});
}

function onState(e){
	var record = e.record;
	var state = record.state;
	var s="";
	if(parseInt(state)==1)
		s = "启用";
	else if(parseInt(state) == 2)
		s = "未启用";
	return s;
}

function onType(e){
	var record = e.record;
	var type = record.type;
	var s ="";
	if(type == 1)
		s="预留";
	else if(type == 2)
		s="内部企业号码";
	else if(type == 3)
		s="外部企业号码";
	else if(type == 4)
		s="外部使用";
	return s;
}

function onSpecial(e){
	var record = e.record;
	var level = record.level;
	var s ="";
	if(level == "1")
		s="普通";
	else if(level == "2")
		s="特殊";
	return s;
}

function onCreatetime(e){
	if(e == null || e.record == null || e.record.createtime == null ){
		return "";
	}
	var record = e.record;
	var createtime = record.createtime.$date;
	return mini.formatDate (createtime,"yyyy-MM-dd");
}


function onOperator(e){
	if(e == null || e.record == null || e.record.operator == null ){
		return "";
	}
	var record = e.record;
	return record.operator.name+"("+record.operator.ccno+")";
}
