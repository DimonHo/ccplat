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
	data = mini.clone(node);
	form.setData(data);
	form.setChanged(false);
//	mini.get("#key").setValue(key);
//	mini.get("#index").setValue(index);
	
	tree=treeData;
	currentNode=node;
}

/**
 * 确认提交修改信息
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	saveData();
}

/**
 * 获取保存的历史记录
 * @return {TypeName} 
 */
function getData(){
	return content;
}

/**
 * 取消修改
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}

/**
 * 保存修改行业数据
 * @return {TypeName} 
 */
function saveData() {
    var o = form.getData();
    form.validate();
    if (form.isValid() == false){
    	return;
   	}
//    var json = mini.encode(o);
//   
//  	var url = "ccindustryinfo/modifyIndustryinfo.action";
//	
//    $.ajax({
//        url: url,
//		type: 'post',
//        data: { data: json},
//        cache: false,
//        success: function (text){
//        	if (text == "1") {
//        		alert("修改数据成功!");
//				closeWindow();
//        	}else{
//        		alert('修改失败');
//        	}
//        },
//        error: function (jqXHR, textStatus, errorThrown) {
//            alertClick(jqXHR.responseText);
//            closeWindow();
//        }
//    });
    
    tree.updateNode(currentNode,o);
    
    //保存历史记录
	var type="修改";
	content.type=type;
	var node={};
	node.uuid=currentNode.uuid;
	node.name=o.name;
	node.remark=o.remark;
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