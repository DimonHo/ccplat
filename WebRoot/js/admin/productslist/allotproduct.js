mini.parse();

//获取基础路径
var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath+"/";
//获取产品树
var tree = mini.get("tree");

//公司id
var companyno=null;
//行业id
var industryuuid=null;
/**
 * 确定按钮事件
 * @return {TypeName} 
 */
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
		value=nodes;
		
	if(value==""){
		mini.alert("没选中值！");
		return false;
	}
	
	win = mini.get("win1");
	win.show();
} 
/**
 * 关闭对话框
 */
function onCancel() {
	closeWindow("cancel");
}

function SaveData() {//保存自来水公司
			
	params={
		value:getTreeStruc(),
		companyno:companyno,
		stime:mini.get("stime").getValue(),
		etime:mini.get("etime").getValue()
	};
			
	mini.confirm("确定初始化产品？", "确定？",
      function (action) {
	        if (action == "ok") {
	            $.ajax({
	                url: basePath+'product/initproduct.action',
					type: 'post',
	                data: { param: mini.encode(params)},
	                cache: false,
	                success: function (text){
	                		if(text){
	                			closeWindow("save");
	                			win.hide();
	                		}else{
	                		}
	                },
	                error: function (jqXHR, textStatus, errorThrown) {
	                    CloseWindow();
	                }
	            });
	        }
       	}
   	);
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
			obj.ico=childnode.ico;
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

/**
 * 页面初始化
 */
function setData(industryid,company){
	industryuuid=industryid;
	companyno=company;
	tree.load(basePath+"product/productlisttree.action?industryno="+industryid);
}

/**
 * 关闭窗口
 * @param {Object} action 判断是否主动关闭
 * @return {TypeName} 
 */
function closeWindow(action) {            
    if (action == "close" && form.isChanged()) {
        if (confirm("数据被修改了，是否先保存？")) {
            return false;
        }
    }
    if (window.CloseOwnerWindow){
    	return window.CloseOwnerWindow(action);
    }else{
    	window.close();  
    }      
}