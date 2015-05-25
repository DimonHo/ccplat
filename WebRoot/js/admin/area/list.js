$(".mini-combobox").click(function() {
	
});

mini.parse();

var tree = mini.get("tree1");

function onAddAfter1(e) {
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	console.info(node);
	var newNode = {
		grade : node.grade,
		orderby : node.orderby + 1,
		type : node.type,
		remark : node.remark
	};
	tree.addNode(newNode, "after", node);
}
function onAddNode1(e) {
	
	var node = tree.getSelectedNode();
	
	if(node != null){	
		var key = "请输入地区";
		var node = tree.getSelectedNode();
		var newNode = {
			grade : node.grade + 1,
			orderby : 1,
			type : node.type,
			pid : node.pid,
			pidaddress : node.pidaddress,
			name : key
		};
		if (node) {
			tree.addNode(newNode, "add", node);
		} else {
			tree.addNode(newNode, "add", tree.getRootNode()["children"][0]);
		}
		tree.beginEdit(newNode);
	}
}

function onEditNode1(e) {
	console.info(e);
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	
	if (node && node.id == 0) {
		mini.alert("不能编辑 ");
		return ;
	}
	tree.beginEdit(node);
}

tree.on("endedit", function(e) {
	var t = e.sender, n = e.node; //树对象//节点对象
	
		if( n.id == 0){
			mini.alert('不能修改地区节点');
			return;
		}else if (n.name == null || n.name == '') {
			mini.alert('节点名不能为空');
			return;
		} else if (n.name == "请输入地区") {
			tree.beginEdit(n);
			return;
		}

		if (n.id == null || n.id == "") {
			params = {
				name : n.name,
				pid : n.pid == null ? 0 : n.pid,
				pidaddress : n.pidaddress == null ? 0 : n.pidaddress
			};
			$.ajax( {
				cache : false,
				url : 'ccarea/add.action',
				type : 'post',
				datatype : 'json',
				data : {
					param : mini.encode(params)
				},
				success : function(okstr) {
					if (okstr != "false") {
						var ary = okstr.split(",");
						n.id = ary[0];
						n.pidaddress = ary[1];
						n.pid = ary[2];
						mini.alert('新增成功');
						//tree.reload();
					} else {
						
					}
				},
				error : function(e) {
					
				}
			});
		} else {
			params = {
				name : n.name,
				pid : n.pid,
				pidaddress : n.pidaddress,
				id : n.id
			};
			$.ajax( {
				cache : false,
				url : 'ccarea/modify.action',
				type : 'post',
				datatype : 'json',
				data : {
					param : mini.encode(params)
				},
				success : function(okstr) {
					if (okstr) {
						mini.alert('修改成功');
						//tree.reload();
					} else {
						
					}
				},
				error : function(e) {
				}
			});
		}
	});

function onRemoveNode1(e) {
	var tree = mini.get("tree1");
	var node = tree.getSelectedNode();
	if (node && node.id == 0) {
		mini.alert("不能删除 ");
		return ;
	}
	
	if (node) {
		if (confirm("确定删除选中节点?")) {
			tree.removeNode(node);
			if (node.id == null && node.id == '') {
				return;
			}
			$.ajax( {
				cache : false,
				url : 'ccarea/delete.action?id=' + node.id,
				type : 'post',
				datatype : 'json',
				success : function(okstr) {
					if (okstr) {
						mini.alert('删除成功');
						//tree.reload();
						$("#spancontent").text("");
						$("#textcontent").hide();
						$("#icon-add").removeClass("hidden");
						$("#icon-edit").addClass("hidden");
					} else {
						
					}
				},
				error : function(e) {
				}
			});
		}
	}
}

function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("tree1");

	var node = tree.getSelectedNode();
	if (!node) {
		e.cancel = true;
		return;
	}
	if (node && node.text == "Base") {
		e.cancel = true;
		//阻止浏览器默认右键菜单
		e.htmlEvent.preventDefault();
		return;
	}
	
	
	var editItem = mini.getbyName("edit", menu);
	var removeItem = mini.getbyName("remove", menu);
	editItem.show();
	removeItem.enable();

	if (node.pids == "forms") {
		editItem.hide();
	}
	if (node.pids == "lists") {
		removeItem.disable();
	}
}