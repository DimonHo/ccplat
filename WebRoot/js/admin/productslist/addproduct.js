mini.parse();

//新增产品的表单对象
var form = new mini.Form("form");

//选中节点对象
var parentNode=null;
//树对象
var tree=null;

//历史记录
var content={};

/**
 * 保存当前选中节点数据
 * @param {Object} key 子节点所处的位置
 * @param {Object} index 下标
 */
function setData(treeData,node){
	if(node!=null){
		$("#urltr").css("display","table-row");
		$("#opentr").css("display","table-row");
		parentNode=node;
	}
	tree=treeData;
}

/**
 * 获取保存的历史记录
 * @return {TypeName} 
 */
function getData(){
	return content;
}

/**
 * 确认提交新增信息
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	saveData();
}

/**
 * 取消新增
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}

/**
 * 保存新增行业数据
 * @return {TypeName} 
 */
function saveData() {
    var o = form.getData();
    form.validate();
    if (form.isValid() == false){
    	return;
   	}
    var node={};
    node.code=o.code;
    node.name=o.name;
    node.remark=o.remark;
    if(parentNode!=null){
    	node.url=o.url;
		node.open=o.open;
    }
    var ico = $("#defaultPicture").val();
    if(ico != null){
    	o.ico=$.trim(ico);
		node.ico=$.trim(ico);
	}else{
		node.ico="";
	}
    
	tree.addNode(node,"add",parentNode);
    //保存历史记录
	var type="新增";
	content.type=type;
	var data={};
	data.code=node.code;
	data.name=node.name;
	data.remark=node.remark;
    if(parentNode!=null){
    	data.url=o.url;
    	data.open=o.open;
    	data.ico=o.ico;
    }
    console.log(data);
	content.data=data;
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