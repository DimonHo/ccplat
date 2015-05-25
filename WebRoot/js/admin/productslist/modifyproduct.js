mini.parse();

//修改行业的表单对象
var form = new mini.Form("form");

//选中节点对象
var currentNode=null;
//树对象
var tree=null;

//历史记录
var content={};

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
	form.setData(data);
	$("#defaultPicture").val(data.ico);
	form.setChanged(false);
	
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
    var o = form.getData();
    form.validate();
    if (form.isValid() == false){
    	return;
   	}
    
    var ico = $("#defaultPicture").val();
	if(ico != null){
		o.ico=$.trim(ico);
	}
    
    tree.updateNode(currentNode,o);
    //保存历史记录
	var type="修改";
	content.type=type;
	var node={};
	node.code=o.code;
	node.name=o.name;
	node.remark=o.remark;
	if(ico != null){
		node.ico=$.trim(ico);
	}
	
    if(currentNode._level!=0){
    	node.url=o.url;
    	node.open=o.open;
    }

	node.title=o.name;
	node.src=o.src;
	node.height=o.height;
	node.headHtml=o.headHtml;
	node.type=o.type;
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