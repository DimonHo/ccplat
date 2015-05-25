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
			
			if(nodeChecked)
				obj.currentcheck=true;
			else
				obj.currentcheck=false;
			
			tempArray=childnode.children;
                
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

var tempnodes;
function havaCheckedChild(tempnode){
	
	tempnodes=tree.getAllChildNodes(tempnode);
	
	for(var i=0;i<tempnodes.length;i++){
		if(tree.isCheckedNode(tempnodes[i])){
			return true;
		}
	}
	
	return false;
}


function nodecheck(e){
	
	var node=e.node;
	var nodes=tree.getAllChildNodes(node);
	nodes.add(node);
	
	if(tree.isCheckedNode(node)){
		
		for(var i=0;i<nodes.length;i++){ //影响的node
			flag=true;
			
			console.info("正在处理的节点名称"+nodes[i].name);
			
			for(var k=0;k<selectnodes.length;k++){
				if(nodes[i].id=selectnodes[k].id){
						console.info("之前已经选中的节点"+nodes[i].name);
					flag=false;
					break;
				}
			}

			if(flag){
				for(var j=0;j<removenodes.length;j++){  //存在移除列表当中的nodes
					if(nodes[i].id=removenodes[j].id){  //如果添加的在移除列表，也就是本身存在的
						removenodes.remove(nodes[i]);  //移除掉准备作为删除的node
									console.info("移除掉准备作为删除的node"+nodes[i].name);
						flag=false;
						break;
					}
				}
				
				if(flag){  //如果在移除列表当中不存在
						console.info("如果在移除列表当中不存在node"+nodes[i].name);
					addnodes.add(nodes[i]);
				}
				
			}
		}
		
			isCheckParent(node);
			
	}else{
		for(var i=0;i<nodes.length;i++){
			flag=true;
			for(var j=0;j<addnodes.length;j++){  //存在添加数组当中的node
					if(nodes[i].id=addnodes[j].id){  //如果添加的在移除列表，也就是本身存在的
						addnodes.remove(nodes[i]);  //移除掉准备作为删除的node
						
						flag=false;
						break;
					}
				}
				if(flag){  //如果在添加列表当中不存在
					removenodes.add(nodes[i]);
				}
			}
		
			isNotCheckParent(node);
		}
}


var selectParentnodes=new Array();

tree.on("beforenodecheck", function(e) {
	
	var node=e.node;
	var nodes=tree.getAllChildNodes(node);
	
	selectnodes=new Array();  //清空数组
	
	for(var i=0;i<nodes.length;i++){
		
		if(tree.isCheckedNode(nodes[i])){//如果这个节点已经选中
			selectnodes.add(nodes[i]);
		}	
	}
	
	nodes=tree.getAncestors(node);
	
	for(var i=0;i<nodes.length;i++){
		
		if(tree.isCheckedNode(nodes[i])||havaCheckedChild(nodes[i])){//如果这个节点已经选中
			selectParentnodes.add(nodes[i]);
		}	
	}
});