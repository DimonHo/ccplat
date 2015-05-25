mini.parse();

//获取基础路径
var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath+"/";

//获取行业树对象
var industryTree = mini.get("industryTree");

//获取产品树对象
var productTree = mini.get("productTree");

//保存按钮
var saveBtn=mini.get("saveBtn");

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

//选中行业树节点时，更新右侧产品树
industryTree.on("nodeselect", function(e) {
	//置灰保存按钮
	saveBtn.disable();
	
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	
	//由于miniui的bug,不延迟加载的话，会导致鼠标变成选中文字的状态
	setTimeout(function(){
		productTree.load(basePath+"ccproductslist/listProduct.action?industryuuid="+currentUUID);
	},100)
	//productTree.load({industryuuid:currentUUID});
});

/**
 * 打开新增产品对话框
 * @param {Object} node 当前选中的产品节点
 */
function addProduct(node){
	if(currentUUID==''||currentUUID==null){
		mini.alert('请选择行业');
		return;
	}
	mini.open( {
		url : 'jsp/admin/productslist/addproduct.jsp',
		title : "新增产品",
		width : 550,
		height : 450,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(productTree,node);
		},
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.code]=[];
					historyData[content.data.code].push(content);
				}else{
					productTree.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.code]==null){
								historyData[e.code]=[];
							}
							historyData[e.code].push(content);
						}
					});
				}
				saveBtn.enable();
			}
		}
	});
}

/**
 * 增加产品树节点
 * @param {Object} e 触发事件对象
 */
function onAddNode(e) {
	var tree = mini.get("productTree");
	var node = tree.getSelectedNode();
	addProduct(node);
}

/**
 * 右击树节点触发事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("productTree");

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
 * 修改产品树节点
 * @param {Object} e 触发事件对象
 */
function onEditNode(e){
	var tree = mini.get("productTree");
	//选中节点
	var currentNode = tree.getSelectedNode();
	modifyProduct(currentNode);
}

/**
 * 修改产品
 * @param {Object} node 选中节点
 */
function modifyProduct(node){
	mini.open( {
		url : 'jsp/admin/productslist/modifyproduct.jsp',
		title : "编辑产品",
		width : 550,
		height : 450,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(productTree,node);
		},
		ondestroy : function(action) {
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				//保存历史记录
				var content=iframe.contentWindow.getData();
				if(node==null){
					historyData[content.data.code]=[];
					historyData[content.data.code].push(content);
				}else{
					productTree.bubbleParent(node,function(e){
						if(e._level==0){
							if(historyData[e.code]==null){
								historyData[e.code]=[];
							}
							historyData[e.code].push(content);
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
 */
function save(leave){
	if(leave==null){
		leave=false;
	}
	$.ajax( {
		cache : false,
		url : 'ccindustryinfo/whetherAuthority.action',
		type : 'post',
		datatype : 'json',
		async : !leave,
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
 * 保存产品树
 */
function saveData(leave,url){
	var treedata = productTree.getData();
	data = mini.clone(treedata);
	
	getUpdateData(data);
	
	var json=mini.encode(data);
	var deleteJson=mini.encode(deleteCode);
	var historyJson=mini.encode(historyData);
	//如果是离开页面时的保存，不要提示
	if (leave) {
		$.ajax( {
			cache : false,
			url : 'ccproductslist/saveProduct.action',
			type : 'post',
			datatype : 'json',
			async : !leave,
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
					url : 'ccproductslist/saveProduct.action',
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
		url : 'jsp/admin/productslist/listhistory.jsp',
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