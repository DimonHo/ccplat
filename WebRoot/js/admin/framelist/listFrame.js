mini.parse();
//获取基础路径
var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath+"/";

//获取行业树对象
var industryTree = mini.get("industryTree");

//获取组织架构树对象
var frameTree = mini.get("frameTree");
//保存按钮
var saveBtn=mini.get("saveBtn");

/*window.onbeforeunload = function(){
	if(saveBtn.enabled){
		if (confirm("数据已修改,是否保存？")) {
			save(true);
		}
	}
}*/

//当前选中的行业树节点的uuid
var currentUUID='';

//当前功能编码
var code='test';

//所有删除的根节点
var deleteCode=[];

//历史记录
var historyData={};

//检查是否要保存数据
industryTree.on("beforenodeselect", function(e) {
	if(saveBtn.enabled&&saveBtn.style!='display: none;'){
		mini.showMessageBox({
		 	title: "提示",    
		    message: "数据已修改，是否保存",
		    buttons: ["ok", "no", "cancel"],    
		    iconCls: "mini-messagebox-question",
		    callback: function(action){
				if(action=="no"){
					//置灰保存按钮
					saveBtn.disable();
					industryTree.selectNode(e.node);
				}else if(action=="ok"){
					save(true);
					//置灰保存按钮
					saveBtn.disable();
					industryTree.selectNode(e.node);
				}
		    }
		});
		//阻止点击事件
		e.cancel=true;
	}
});

//选中行业树节点时，更新右侧组织架构树
industryTree.on("nodeselect", function(e) {
	//置灰保存按钮
	saveBtn.disable();
	
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	//由于miniui的bug,不延迟加载的话，会导致鼠标变成选中文字的状态
	setTimeout(function(){
		frameTree.load(basePath+"ccframelist/listFrame.action?industryuuid="+currentUUID);
	},100)
	//frameTree.load({industryuuid:currentUUID});
});

/**
 * 打开新增组织机构对话框
 */
function addFrame(node){
	if(currentUUID==''||currentUUID==null){
		mini.alert('请选择行业');
		return;
	}
	mini.open( {
		url : 'jsp/admin/framelist/addFrame.jsp',
		title : "新增组织架构",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(frameTree,node);
		},
		ondestroy : function(action) {
			//frameTree.reload();
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.uuid]=[];
					historyData[content.data.uuid].push(content);
				}else{
					frameTree.bubbleParent(node,function(e){
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
 * 增加组织架构树节点
 * @param {Object} e 触发事件对象
 */
function onAddNode(e) {
	var tree = mini.get("frameTree");
	var node = tree.getSelectedNode();
	addFrame(node);
//	var tree = mini.get("frameTree");
//	var node = tree.getSelectedNode();
//	//获取子节点所处的位置
//	var key = 'children';
//	//获取下标
//	var index = '';
//	//获取当前树节点在第几层
//	var level=node._level;
//	//根据层级获取所有下标位置
//	for(var i=level;i>0;i--){
//		if(i==level){
//			index=tree.indexOfNode(node);
//		}else{
//			index=(tree.indexOfNode(node))+','+index;
//		}
//		key='children'+','+key;
//		node=tree.getParentNode(node);
//	}
//	//获取根节点uuid
//	var uuid=node.uuid;
//	addFrame(key,index,uuid);
}

/**
 * 右击树节点触发事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("frameTree");

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
 * 修改组织架构树节点
 * @param {Object} e 触发事件对象
 */
function onEditNode(e){
	var tree = mini.get("frameTree");
	//选中节点
	var currentNode = tree.getSelectedNode();
	modifyFrame(currentNode);
//	var tree = mini.get("frameTree");
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
//	for(var i=level;i>0;i--){
//		if(i==level){
//			index=tree.indexOfNode(node);
//		}else{
//			index=(tree.indexOfNode(node))+','+index;
//		}
//		key='children'+','+key;
//		node=tree.getParentNode(node);
//	}
//	//获取根节点uuid
//	var uuid=node.uuid;
//	modifyFrame(uuid,key,index,currentNode);
}

/**
 * 修改组织架构
 * @param {Object} node 选中节点
 */
function modifyFrame(node){
	mini.open( {
		url : 'jsp/admin/framelist/modifyFrame.jsp',
		title : "编辑组织架构",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			//iframe.contentWindow.setData(key,index,uuid,node);
			iframe.contentWindow.setData(frameTree,node);
		},
		ondestroy : function(action) {
			//frameTree.reload();
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.uuid]=[];
					historyData[content.data.uuid].push(content);
				}else{
					frameTree.bubbleParent(node,function(e){
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
 * 是否有保存权限
 * leave 如果是离开的时候保存，不需要提示是否需要保存，需要同步请求
 */
function save(leave){
	if(leave==null){
		leave=false;
	}
	$.ajax( {
		cache : false,
		url : 'ccindustryinfo/whetherAuthority.action',
		type : 'post',
		async : !leave,
		datatype : 'json',
		data : {
			code : code
		},
		success : function(okstr) {
			if (okstr == "1") {
				saveData(leave);
			}else{
				//listPeople();
				saveData(leave);
			}
		},
		error : function(e) {
			mini.alert('请求异常');
		}
	});
}

/**
 * 保存组织架构树
 */
function saveData(leave,url){
	var treedata = frameTree.getData();
	data = mini.clone(treedata);
	
	getUpdateData(data);
	
	var json=mini.encode(data);
	var deleteJson=mini.encode(deleteCode);
	var historyJson=mini.encode(historyData);
	//如果是离开页面时的保存，不需要再次提示
	if (leave) {
		$.ajax( {
			cache : false,
			url : 'ccframelist/saveAll.action',
			type : 'post',
			async : !leave,
			datatype : 'json',
			data : {
				data : json,industryuuid:currentUUID,deleteCode:deleteJson,history : historyJson
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
		//正常的保存提示
		mini.confirm("是否保存？","提示",function(action){
			if(action=="ok"){
				$.ajax( {
					cache : false,
					url : 'ccframelist/saveAll.action',
					type : 'post',
					datatype : 'json',
					data : {
						data : json,industryuuid:currentUUID,deleteCode:deleteJson,history : historyJson
					},
					success : function(okstr) {
						if (okstr == "1") {
							mini.alert('保存成功!');
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
	if(currentUUID==''||currentUUID==null){
		mini.alert('请选择行业');
		return;
	}
	mini.open( {
		url : 'jsp/admin/framelist/listhistory.jsp',
		title : "历史记录",
		width : 800,
		height : 500,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(currentUUID);
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
