mini.parse();
//获取行业树对象
var industryTree = mini.get("industryTree");
//选中树节点时，将备注显示在右侧面板
industryTree.on("nodeselect", function(e) {
	mini.get("#textcontent").setValue(e.node.remark==null?"":e.node.remark);
	$("#textcontent").show();
});
//历史记录
var historyData=[];

var code="hangyexiugai";

//保存按钮
var saveBtn=mini.get("saveBtn");

/**
 * 右击树节点触发事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("industryTree");

	var node = tree.getSelectedNode();
	if (!node) {
		e.cancel = true;
		return;
	}

	var editItem = mini.getbyName("edit", menu);
	var removeItem = mini.getbyName("remove", menu);
	editItem.show();
	removeItem.enable();

}

/**
 * 创建新行业
 * @param {Object} key 子节点所处的位置
 * @param {Object} index 下标
 */
//function addIndustryinfo(key,index) {
//	mini.open( {
//		url : 'jsp/admin/industryinfo/addIndustryinfo.jsp',
//		title : "创建行业",
//		width : 500,
//		height : 300,
//		onload : function() {
//			var iframe = this.getIFrameEl();
//			iframe.contentWindow.setData(key,index);
//		},
//		ondestroy : function() {
//			industryTree.reload();
//		}
//	});
//}

/**
 * 创建新行业节点
 */
function addIndustryinfo(node) {
	mini.open( {
		url : 'jsp/admin/industryinfo/addIndustryinfo.jsp',
		title : "创建行业",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(industryTree,node);
		},
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var content=iframe.contentWindow.getData();
				historyData.push(content);
				saveBtn.enable();
			}
		}
	});
}

/**
 * 增加行业树节点
 * @param {Object} e 触发事件对象
 */
function onAddNode(e) {
//	var tree = mini.get("industryTree");
//	var node = tree.getSelectedNode();
//	//获取子节点所处的位置
//	var key = 'children';
//	//获取下标
//	var index = '';
//	//获取当前树节点在第几层
//	var level=node._level;
//	//根据层级获取所有下标位置
//	for(var i=level;i>=0;i--){
//		if(i==level){
//			index=tree.indexOfNode(node);
//		}else{
//			index=(tree.indexOfNode(node))+','+index;
//		}
//		key='children'+','+key;
//		node=tree.getParentNode(node);
//	}
//	addIndustryinfo(key,index);
	
	var tree = mini.get("industryTree");
	var node = tree.getSelectedNode();
	addIndustryinfo(node);
}

/**
 * 修改行业树节点
 * @param {Object} e 触发事件对象
 */
function onEditNode(e){
//	var tree = mini.get("industryTree");
//	//选中节点
//	var currentNode = tree.getSelectedNode();
//	//当前需要获取下标的节点
//	var node = tree.getSelectedNode();
//	//获取子节点所处的位置
//	var key = '';
//	//获取下标
//	var index = '';
//	//获取当前树节点在第几层
//	var level=node._level;
//	//根据层级获取所有下标位置
//	for(var i=level;i>=0;i--){
//		if(i==level){
//			index=tree.indexOfNode(node);
//		}else{
//			index=(tree.indexOfNode(node))+','+index;
//		}
//		key='children'+','+key;
//		node=tree.getParentNode(node);
//	}
//	modifyIndustryinfo(key,index,currentNode);
	
	var tree = mini.get("industryTree");
	var node = tree.getSelectedNode();
	modifyIndustryinfo(node);
}
/**
 * 修改行业
 * @param {Object} uuid 需要修改行业的uuid
 * @param {Object} key 子节点所处的位置
 * @param {Object} index 下标
 * @param {Object} node 选中节点
 */
//function modifyIndustryinfo(key,index,node){
//	mini.open( {
//		url : 'jsp/admin/industryinfo/modifyIndustryinfo.jsp',
//		title : "编辑行业架构",
//		width : 500,
//		height : 300,
//		onload : function() {
//			var iframe = this.getIFrameEl();
//			iframe.contentWindow.setData(key,index,node);
//		},
//		ondestroy : function() {
//			industryTree.reload();
//		}
//	});
//}

/**
 * 修改行业节点
 */
function modifyIndustryinfo(node,data){
	mini.open( {
		url : 'jsp/admin/industryinfo/modifyIndustryinfo.jsp',
		title : "编辑行业架构",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(industryTree,node);
		},
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var content=iframe.contentWindow.getData();
				historyData.push(content);
				saveBtn.enable();
			}
		}
	});
}

/**
 * 点击保存按钮,查看是否有权限
 */
function save(){
	$.ajax( {
			cache : false,
			url : 'ccindustryinfo/whetherAuthority.action',
			type : 'post',
			datatype : 'json',
			data : {
				code : code
			},
			success : function(okstr) {
				if (okstr != "1") {
					saveData();
				}else{
				//查看是否已提交过审批,提示已提交过
					$.ajax( {
						cache : false,
						url : 'ccindustryinfo/isRepeat.action',
						type : 'post',
						datatype : 'json',
						success : function(okstr) {
							if (okstr == "3") {
								mini.confirm("已提交过审批，是否继续提交？","提示",function(action){
									if(action=="ok"){
										listPeople();
									}
								});
							}else{
								listPeople();
							}
						},
						error : function(e) {
							mini.alert('请求异常');
						}
					});
				}
			},
			error : function(e) {
				mini.alert('请求异常');
			}
		});
}

/**
 * 展示上级列表
 */
function listPeople(){
	var treedata = industryTree.getData();
	data = mini.clone(treedata);
	
	getUpdateData(data);
	
	var json=mini.encode(data);
	
	var historyJson=mini.encode(historyData);
	
	mini.open( {
		url : 'jsp/admin/industryinfo/listpeople.jsp',
		title : "选择审批人",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(code,json,historyJson);
		},
		ondestroy : function(action) {
			if(action=="ok"){
				saveBtn.disable();
			}
		}
	});
}

/**
 * 保存行业树
 * leave 是否为离开页面时的保存
 */
function saveData(leave,url){
	if(leave==null){
		leave=false
	}
	var treedata = industryTree.getData();
	data = mini.clone(treedata);
	
	getUpdateData(data);
	
	var json=mini.encode(data);
	
	var historyJson=mini.encode(historyData);
	//当离开页面的保存时，不会再次提示
	if(leave){
		$.ajax( {
			cache : false,
			url : 'ccindustryinfo/saveIndustryinfo.action',
			type : 'post',
			datatype : 'json',
			async : !leave,
			data : {
				data : json,history : historyJson
			},
			success : function(okstr) {
				if (okstr == "1") {
					//如果是离开页面的保存，需要回调执行离开页面的操作
					mini.alert('保存成功!','提示',function(){
						//如果url为页面地址，则是跳转页面
						if("string"==typeof url){
							window.location.href=url;
						}else if("object"==typeof url){//如果不是字符串，则是关闭对话框
							url.trigger('reveal:close');
						}
					});
				}else{
					mini.alert('保存失败!');
				}
			},
			error : function(e) {
				mini.alert('请求异常');
			}
		});
	}else{//直接点击保存按钮时，提示是否保存
		mini.confirm("是否保存？","提示",function(action){
			if(action=="ok"){
				$.ajax( {
					cache : false,
					url : 'ccindustryinfo/saveIndustryinfo.action',
					type : 'post',
					datatype : 'json',
					data : {
						data : json,history : historyJson
					},
					success : function(okstr) {
						if (okstr == "1") {
							mini.alert('保存成功!');
							historyData=[];
							industryTree.reload();
							saveBtn.disable();
						}else{
							mini.alert('保存失败!');
						}
					},
					error : function(e) {
						mini.alert('请求异常');
					}
				});
			}
		});
	}
}

/**
 * 递归获取保存到数据库的字段
 * @param {Object} data
 */
function getUpdateData(data){
	for(var i=0;i<data.length;i++){
		delete data[i]["_id"];
		delete data[i]["pid"];
		delete data[i]["_level"];
		delete data[i]["expanded"];
		var children=data[i]["children"];
		if(children==null){
			data[i]['children']=[];
		}
		getUpdateData(data[i]['children']);
	}
}

/**
 * 获取历史记录
 */
function getHistory(){
	mini.open( {
		url : 'jsp/admin/industryinfo/listhistory.jsp',
		title : "历史记录",
		width : 800,
		height : 500,
		onload : function() {
		},
		ondestroy : function() {
		}
	});
}

/**
 * 离开页面时的保存方法
 */
function saveResult(url){
	var canleave=false;
	$.ajax( {
		cache : false,
		url : 'ccindustryinfo/whetherAuthority.action',
		type : 'post',
		datatype : 'json',
		async : false,
		data : {
			code : code
		},
		success : function(okstr) {
			//有权限就直接保存，保存完离开页面
			if (okstr != "1") {
				saveData(true,url);
			}else{//没有权限就留在页面，提交审批
				//查看是否已提交过审批,提示已提交过
				$.ajax( {
					cache : false,
					url : 'ccindustryinfo/isRepeat.action',
					type : 'post',
					datatype : 'json',
					success : function(okstr) {
						if (okstr == "3") {
							mini.confirm("已提交过审批，是否继续提交？","提示",function(action){
								if(action=="ok"){
									listPeople();
								}
							});
						}else{
							listPeople();
						}
					},
					error : function(e) {
						mini.alert('请求异常');
					}
				});
			}
		},
		error : function(e) {
			mini.alert('请求异常');
		}
	});
	return canleave;
}
