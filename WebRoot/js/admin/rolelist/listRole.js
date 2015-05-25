mini.parse();

//获取岗位列表对象
var roleGrid = mini.get("treegrid");

//所有删除的根节点
var deleteCode=[];

//当前功能编码
var code='test';

//历史记录
var historyData={};

//保存按钮
var saveBtn=mini.get("saveBtn");

/**
 * 增加岗位
 */
function addRole(node){
	mini.open( {
		url : 'jsp/admin/rolelist/addRole.jsp',
		title : "创建岗位",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(roleGrid,node);
		},
		ondestroy : function(action) {
			//roleGrid.reload();
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.uuid]=[];
					historyData[content.data.uuid].push(content);
				}else{
					roleGrid.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.uuid]==null){
								historyData[e.uuid]=[];
							}
							historyData[e.uuid].push(content);
						}
					});
				}
				saveBtn.enable();
			}
		}
	});
}

/**
 * 修改岗位
 */
function modifyRole(node){
	mini.open( {
		url : 'jsp/admin/rolelist/modifyRole.jsp',
		title : "修改岗位",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(roleGrid,node);
		},
		ondestroy : function(action) {
//			roleGrid.reload();
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.uuid]=[];
					historyData[content.data.uuid].push(content);
				}else{
					roleGrid.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.uuid]==null){
								historyData[e.uuid]=[];
							}
							historyData[e.uuid].push(content);
						}
					});
				}
				saveBtn.enable();
			}
		}
	});
}

/**
 * 右击树列表节点触发事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("treegrid");

	var node = tree.getSelectedNode();
	if (!node) {
		e.cancel = true;
		return;
	}

	var editItem = mini.getbyName("edit", menu);
	var removeItem = mini.getbyName("remove", menu);
	var addItem = mini.getbyName("add", menu);
	editItem.show();
	removeItem.enable();
	//当前节点为模版才显示新增按钮
	if(node.lev!=''&&node.lev!=null){
		addItem.hide();
	}else{
		addItem.show();
	}
}

/**
 * 增加岗位节点
 * @param {Object} e 触发事件对象
 */
function onAddNode(e) {
//	var tree = mini.get("treegrid");
	var node = roleGrid.getSelectedNode();
	//获取根节点uuid
//	var uuid=node.uuid;
	addRole(node);
}

/**
 * 修改岗位节点
 * @param {Object} e 触发事件对象
 */
function onEditNode(e){
	//当前选中节点所在模版uuid
//	var uuid='';
//	var tree = mini.get("treegrid");
	//当前选中的节点
	var node = roleGrid.getSelectedNode();
	//当选中节点为岗位时，获取所在模版的uuid
//	if(node._level==1){
//		var parentNode=tree.getParentNode(node);
//		uuid=parentNode.uuid;
//	}else if(node._level==0){//当前选中节点为模版时
//		uuid=node.uuid;
//	}
//	modifyRole(node,uuid);
	modifyRole(node);
}

/**
 * 是否有保存权限
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
			if (okstr == "1") {
				saveData();
			}else{
				//listPeople();
				saveData();
			}
		},
		error : function(e) {
			mini.alert('请求异常');
		}
	});
}

/**
 * 保存岗位树
 * leave 是否是离开页面时的保存
 * url 离开页面时的跳转url
 */
function saveData(leave,url){
	if(leave==null){
		leave=false;
	}
	var treedata = roleGrid.getData();
	data = mini.clone(treedata);
	
	getUpdateData(data);
	
	var json=mini.encode(data);
	var deleteJson=mini.encode(deleteCode);
	var historyJson=mini.encode(historyData);
	
	if(leave){
		$.ajax( {
			cache : false,
			url : 'ccrolelist/saveAll.action',
			type : 'post',
			datatype : 'json',
			data : {
				data : json,deleteCode:deleteJson,history : historyJson
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
	}else{
		mini.confirm("是否保存？","提示",function(action){
			if(action=="ok"){
				$.ajax( {
					cache : false,
					url : 'ccrolelist/saveAll.action',
					type : 'post',
					datatype : 'json',
					data : {
						data : json,deleteCode:deleteJson,history : historyJson
					},
					success : function(okstr) {
						if (okstr == "1") {
							mini.alert('保存成功!');
							roleGrid.reload();
							deleteCode=[];
							historyData={};
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
		var children=data[i]["position"];
		if(children==null){
			data[i]['position']=[];
		}
		getUpdateData(data[i]['position']);
	}
}

/**
 * 获取历史记录
 */
function getHistory(){
	mini.open( {
		url : 'jsp/admin/rolelist/listhistory.jsp',
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
	saveData(true,url);
	return false;
}