// var map = new Map();
// map.put("a", "aaa");
// map.put("b","bbb");
// map.put("cc","cccc");
// map.put("c","ccc");
// map.remove("cc");

var tree=null;
var cccompanyno = "";
var flg="";
var win =null;
var value =null;
var companyno="";
	
mini.parse();
var grids=mini.get("grids");
var tree = mini.get("tree");
	   
//获取行业树对象
var industryTree = mini.get("industryTree");
	
//选中行业树节点时，更新右侧产品树
industryTree.on("nodeselect", function(e) {
	
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	grids.load({industryid:currentUUID});
	
	tree.load(basePath+"product/productlisttree.action?industryno="+currentUUID);
});
	   
function openwin(){
	
	var cDate = new Date();
	var iYear = cDate.getFullYear();
	var iMonth = cDate.getMonth() + 1;
	var iDate = cDate.getDate();
	
	var sDate = iYear + "-" + iMonth + "-" + iDate;
	var eDate = iYear+1 + "-" + iMonth + "-" + iDate;
	    
	mini.get("stime").setValue(sDate);
	mini.get("etime").setValue(eDate);
	    
	var nodes =tree.getCheckedNodes(true);
		
	if(nodes==""){
		mini.alert("没选中值！");
		return false;
	}
	
	win = mini.get("win1");
	win.show();
} 

function onOk() {
	
   var value = tree.getValue(true);
   
   if(value==""){
	   mini.alert("没选中值！");
		return false;
	}

     CloseWindow("ok");        
}
    
function SaveData() {//保存自来水公司
			
	var record=grids.getSelected();
	if(record.companyno==null||record.companyno==""){
		mini.alert("请选择一家公司！");
		return false;
	}
	
	params={
		value:getTreeStruc(),
		companyno:record.companyno,
		stime:mini.get("stime").getValue(),
		etime:mini.get("etime").getValue()
	};
			
	if(!flg){
		mini.confirm("确定初始化产品框架？", "确定？",
	      function (action) {
	        if (action == "ok") {	
	            $.ajax({
	                url: basePath+'product/initproduct.action',
					type: 'post',
	                data: { param: mini.encode(params)},
	                cache: false,
	                success: function (text){
	                		if(text){
	                			CloseWindow("save");
	                			win.hide();
								alter("添加数据成功!");
	                		}else{
	                			alter("添加数据失败!");
	                		}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    CloseWindow();
	                }
	            });
            }else
				return false;
           	}
       	);
	}
}


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
    
function onCancel() {
	CloseWindow("cancel");
}
    