mini.parse();


function onBeforeExpand(e) {
	var tree = e.sender;
	var nowNode = e.node;
	var level = tree.getLevel(nowNode);

	var root = tree.getRootNode();
	tree.cascadeChild(root, function(node) {
		if (tree.isExpandedNode(node)) {
			var level2 = tree.getLevel(node);
			if (node != nowNode && !tree.isAncestor(node, nowNode)
					&& level == level2) {
				tree.collapseNode(node, true);
			}
		}
	});

}

var tree = mini.get("tree1");

var grid = mini.get("grid1");
grid.load();

function seach() {
	var state = document.getElementById("state").value;
	var inputname = mini.get("inputname").getValue();
	var ccnostart = mini.get("ccnostart").getValue();
	var ccnoend = mini.get("ccnoend").getValue();
	grid.load({
			inputname : inputname,
			state : state,
			ccnostart : ccnostart,
			ccnoend : ccnoend
		});

}
//操作状态
var State = [ {
	id : 1,
	text : '已启用'
}, {
	id : 2,
	text : '已停用'
} ];
function onStateRenderer(e) {
	for ( var i = 0, l = State.length; i < l; i++) {
		var s = State[i];
		if (s.id == e.value)
			return s.text;
	}
	return "";

}
//分配状态
var useState = [ {
	id : 0,
	text : '未分配'
}, {
	id : 1,
	text : '已分配'
}, {
	id : 2,
	text : '已收回'
}];
function onUseStateRenderer(e) {
	for ( var i = 0, l = useState.length; i < l; i++) {
		var s = useState[i];
		if (s.id == e.value)
			return s.text;
	}
	return "";

}
function start() {
	var row = grid.getSelected();
	var record = row.state;
	var state = "1";
		if(record==state){
			mini.alert("已启用");
		}else{
			operate("1", "启用");		
		}
	
}
function stop() {
	var row = grid.getSelected();
	var record = row.state;
	var state = "2";
	if(record==state){
			mini.alert("已停用");
		}else{
			operate("2", "停用");
		}
}

function operate(operatestate, operateName) {
	//带上查询参数
	var state = document.getElementById("state").value;
	var inputname = mini.get("inputname").getValue();
	var ccnostart = mini.get("ccnostart").getValue();
	var ccnoend = mini.get("ccnoend").getValue();
	var rows = grid.getSelecteds();
	if (rows.length > 0) {
		if (confirm("确定" + operateName + "选中记录？")) {
			var ids = [];
			for ( var i = 0, l = rows.length; i < l; i++) {
				var r = rows[i];
				ids.push(r.id);
			}
			var id = ids.join(',');
			grid.loading("操作中，请稍后......");
			$.ajax( {
				type : 'post',
				url : "user/update.action",
				data : {
					data : id,
					operatestate : operatestate,
					inputname : inputname,
					state : state,
					ccnostart : ccnostart,
					ccnoend : ccnoend
				},
				success : function(text) {
					if(text=="1"){
						mini.alert("操作成功");
					}else{
						mini.alert("操作失败");
					}
					grid.load();

			},
			error : function() {
				mini.alert("操作异常");
			}
			});
		}
	} else {
		mini.alert("请选中一条记录");
	}
}