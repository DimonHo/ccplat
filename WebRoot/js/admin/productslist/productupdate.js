mini.parse();
var tree = mini.get("tree");

tree.load(basePath+"product/productSelect.action");

var selectnodes=new Array();  //临时存放选中的节点
var removenodes=new Array();
var addnodes=new Array();

var tempParentNodes;
var flag=true;
var treeValue;

//修改前节点集合
var oldNodes=new Array();

//修改后节点集合
var newNodes=new Array();


//获取行业树对象
var industryTree = mini.get("industryTree");
var grids=mini.get("grids");
	
//选中行业树节点时，更新右侧产品树
industryTree.on("nodeselect", function(e) {
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	grids.load({industryid:currentUUID});
});

grids.on("rowclick",function(e){
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	var record=e.record;
	var messageid = mini.loading("Loading, Please wait ...", "Loading");
	$.ajax({
	   url: basePath+"product/productSelect.action?industryno="+currentUUID+"&companyno="+record.companyno,
	   type: 'post',
	   cache: false,
	   success: function (text){
	    		if(text=="2"){//调用失败
					mini.alert("公司服务器地址不正确!");
	    		}else{
	    			tree.loadList(eval(text),"id","pid");
	    			oldNodes=tree.getCheckedNodes(true);
	    		}
	    		mini.hideMessageBox(messageid);
	    },
	    error: function (jqXHR, textStatus, errorThrown) {
	    	mini.alert("数据加载失败!");
	    	mini.hideMessageBox(messageid);
	    }
	});
});
	 


var selectParentnodes=new Array();

var tempnode;
var tempnodes;

var tempflag;

function isCheckParent(node){
	
	tempnode=tree.getParentNode(node);
	
	if(tempnode==undefined||tempnode.name==undefined)
		return false;
	
	
	tempflag=true;
	if(tree.isCheckedNode(tempnode)){
		for(var i=0;i<selectParentnodes.length;i++){
			if(tempnode.id=selectParentnodes[i].id){
				tempflag=false;
			}
		}
	}
	
	if(tempflag){
		for(var j=0;j<removenodes.length;j++){
			if(tempnode.id=removenodes[j].id){  //如果添加的在移除列表，也就是本身存在的
				console.info("移除删除"+tempnode.name);
				removenodes.remove(tempnode);  //移除掉准备作为删除的node
				tempflag=false;
				break;
			}
		}
		
		if(tempflag)
			addnodes.add(tempnode);
		
		isCheckParent(tempnode); //在看看上级是否也需要移除
	
	}
}

function isNotCheckParent(node){
	
	tempnode=tree.getParentNode(node);
	
	if(tempnode==undefined||tempnode.name==undefined)
		return false;
	
	tempnodes=tree.getAllChildNodes(tempnode);
	
	tempflag=true;  //初始化标示
	
	for(var i=0;i<tempnodes.length;i++){
		if(tree.isCheckedNode(tempnodes[i])){
			tempflag=false;
		}
	}
	
	if(tempflag){
		console.info("移除删除"+tempnode.name);
		for(var j=0;j<addnodes.length;j++){
			if(tempnode.id=addnodes[j].id){  //如果添加的在移除列表，也就是本身存在的
				console.info("移除删除"+tempnode.name);
				
				addnodes.remove(tempnode);  //移除掉准备作为删除的node
				tempflag=false;
				break;
			}
		}
		
		if(tempflag)
			removenodes.add(tempnode);
		
		isNotCheckParent(tempnode); //在看看上级是否也需要移除
	}
}

//修改树形结构
function update(){
	
	var stime=mini.get("stime").getValue();
	var	etime=mini.get("etime").getValue();
	
	var updateStr={starttime:stime,endtime:etime};
	
	for(var i=0;i<addnodes.length;i++){
		tree.updateNode(addnodes[i],updateStr);
	}
}

/**
 * 右击树节点触发事件
 * @param {Object} e 触发事件对象
 * @return {TypeName} 
 */
function onBeforeOpen(e) {
	var menu = e.sender;
	var tree = mini.get("tree");

	var node = tree.getSelectedNode();
	if (!node) {
		e.cancel = true;
		return;
	}

	var editItem = mini.getbyName("edit", menu);
	
	editItem.show();
}

/**
 * 修改产品树节点
 * @param {Object} e 触发事件对象
 */
function onEditNode(e){
	var tree = mini.get("tree");
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
		url : basePath+'jsp/admin/productslist/modifytime.jsp',
		title : "修改产品时间",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(tree,node);
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

function openwin(){
	
	treeValue=tree.getCheckedNodes(true);  //获取树的选中节点
	
	if(treeValue==""){
		mini.alert("没选中值！");
		return false;
	}
	
	var addLength=addnodes.length;
	
	if(addLength.length>0){ //如果没有新增产品
		
		var cDate = new Date();
		var iYear = cDate.getFullYear();
		var iMonth = cDate.getMonth() + 1;
		var iDate = cDate.getDate();
		
		var sDate = iYear + "-" + iMonth + "-" + iDate;
		var eDate = iYear+1 + "-" + iMonth + "-" + iDate;
		    
		mini.get("stime").setValue(sDate);
		mini.get("etime").setValue(eDate);
		    
		var nodes =tree.getCheckedNodes(true);
			value=nodes;
			
		if(value==""){
			mini.alert("没选中值！");
			return false;
		}
		
		win = mini.get("win1");
		win.show();
	
	}else{//没有就默认提交
		
		save();
	}
}

var childnode;
var nodeChecked;
var childCheckd;

//获取选中的树
function getTreeStruc(){
	
	var root=tree.getRootNode();
	var treeArray=root.children;
		
	var obj;
	
	var totalArray=new Array();
	var tempArray;
	for(var i=0;i<treeArray.length;i++){
		obj={};
		childnode=	treeArray[i];
		
		nodeChecked=tree.isCheckedNode(childnode);
		childCheckd=havaCheckedChild(childnode);
		
		if(nodeChecked||childCheckd){  //为父节点并且下级有选中节点
		
			obj.code=childnode.id;
			obj.name=childnode.name;
			obj.open=childnode.open;
			obj.remark=childnode.remark;
			obj.url=childnode.url;
			obj.level=childnode._level;
			obj.starttime=childnode.starttime;
			obj.endtime=childnode.endtime;
			obj.ico=childnode.ico;
			tempArray=childnode.children;
			
			if(nodeChecked)
				obj.currentcheck=true;
			else
				obj.currentcheck=false;
                
            if (tempArray!=undefined&&tempArray.length > 0) { // 是否存在子节点
                getChildren(obj, tempArray);
            }
			
            totalArray.add(obj);
		}
	}
	
	for(var i=0;i<totalArray.length;i++){
		console.log(totalArray[i]);
	}
	
	return totalArray;
}

function getChildren(obBean,childrenArray){
	
	var obj;
	var array=new Array();
	var tempArray=new Array();
	for(var i=0;i<childrenArray.length;i++){
		
		childnode=childrenArray[i];
		
		nodeChecked=tree.isCheckedNode(childnode);
		childCheckd=havaCheckedChild(childnode);
		
		if(nodeChecked||childCheckd){
			
			obj={};
			
			obj.code=childnode.id;
			obj.name=childnode.name;
			obj.open=childnode.open;
			obj.remark=childnode.remark;
			obj.url=childnode.url;
			obj.level=childnode._level;
			obj.starttime=childnode.starttime;
			obj.endtime=childnode.endtime;
			obj.ico=childnode.ico;
			if(nodeChecked)
				obj.currentcheck=true;
			else
				obj.currentcheck=false;
			
			tempArray=childnode.children;
			
			if (tempArray!=undefined&&tempArray.length > 0) { // 是否存在子节点
	            getChildren(obj, tempArray);
	      	 }
			
			array.add(obj);
		}
	}
	
	obBean.children=array;
}

function assembleTree(){

	var nodesb=tree.getRootNode();
	
	var temkk=nodesb.children;
	
	console.log(temkk.length);
}
	
function havaCheckedChild(tempnode){
	
	tempnodes=tree.getAllChildNodes(tempnode);
	
	for(var i=0;i<tempnodes.length;i++){
		if(tree.isCheckedNode(tempnodes[i])){
			return true;
		}
	}
	
	return false;
}

function addCheckFlag(updateObejct){
	
	for(var i=0;i<updateObejct.length;i++){
		if(tree.isCheckedNode(updateObejct[i])){
			updateObejct[i].currentcheck=true;
		}else{
			updateObejct[i].currentcheck=false;
		}
	}
	
	return updateObejct;
}

/**
 * 设置有变化的节点集合
 * admin
 */
function setChangeNode(){
	//清空变化数据
	removenodes=new Array();
	addnodes=new Array();
	//当前选中节点
	newNodes=tree.getCheckedNodes(true);
	//获取移除节点
	for(var i=0;i<oldNodes.length;i++){
		if(newNodes.indexOf(oldNodes[i])==-1){
			removenodes.add(oldNodes[i]);
		}
	}
	//获取新增节点
	for(var i=0;i<newNodes.length;i++){
		if(oldNodes.indexOf(newNodes[i])==-1){
			addnodes.add(newNodes[i]);
		}
	}
}

function save(){
	
	//设置有变化的节点集合
	setChangeNode();
	
	treeValue=tree.getCheckedNodes(true);  //获取树的选中节点
	
	if(treeValue==""){
		
		mini.alert("没选中值！");
		
		return false;
	}
	
//	var companyno = treeValue.companyno;
	var row = grids.getSelected();
	params={
		value:getTreeStruc(),
		companyno:row.companyno,
		removenodes:removenodes,
		addnodes:addCheckFlag(addnodes)
	};
	
	 $.ajax({
	   url: basePath+'product/updateProduct.action',
	   type: 'post',
	   data: { param: mini.encode(params)},
	   cache: false,
	   success: function (text){
    		if(text){
    			//CloseWindow("save");
    			//win.hide();
    			oldNodes = newNodes;
				mini.alert("添加数据成功!");
    		}else{
    			mini.alert("添加数据失败!");
    		}
	    },
	    error: function (jqXHR, textStatus, errorThrown) {
	        CloseWindow();
	    }
	});
}