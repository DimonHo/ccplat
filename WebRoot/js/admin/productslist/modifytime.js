mini.parse();

//选中节点对象
var currentNode=null;
//树对象
var tree=null;

//历史记录
var content={};

var stime=mini.get("stime");
var etime=mini.get("etime");

/**
 * 保存当前选中节点数据
 * @param {Object} uuid 当前节点的uuid
 * @param {Object} key 子节点所处的位置
 * @param {Object} index 下标
 * @param {Object} node 当前节点对象
 */
function setData(treeData,node){
	if(node.open!=null){
		$("#urltr").css("display","table-row");
		$("#opentr").css("display","table-row");
		$("#codetr").css("display","table-row");
	}
	data = mini.clone(node);
	
	stime.setValue(node.starttime);
	etime.setValue(node.endtime);
	
	tree=treeData;
	currentNode=node;
}

/**
 * 获取保存的历史记录
 * @return {TypeName} 
 */
function getData(){
	return content;
}

/**
 * 确认提交修改信息
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	saveData();
}

/**
 * 取消修改
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}

/**
 * 保存修改产品数据
 * @return {TypeName} 
 */
function saveData() {
  
	
	var startTime=stime.getValue();
	var endTime=stime.getValue();
	
	if(startTime!=""||endTime!=""){
		mini.alert("时间设置不能为空！");
		return false;
	}
		
	var updateStr={starttime:startTime,endtime:endTime};
	
    tree.updateNode(currentNode,updateStr);
    //保存历史记录
	var type="修改";
	content.type=type;
	var node={};
	node.code=o.code;
	node.name=o.name;
	node.remark=o.remark;
    if(currentNode._level!=0){
    	node.url=o.url;
    	node.open=o.open;
    }
	content.data=node;
    
    closeWindow("ok");
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