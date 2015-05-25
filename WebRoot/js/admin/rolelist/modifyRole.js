mini.parse();

//新增岗位的表单对象
var form = new mini.Form("form");

//列表对象
var grid=null;
//当前选中节点
var currentNode=null;

//历史记录
var content={};

/**
 * 确认提交新增信息
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
 * 保存当前选中节点信息
 * @param {Object} node 当前选中节点
 */
function setData(roleGrid,node){
	mini.get("#name").setValue(node.name);
	mini.get("#remark").setValue(node.remark);
	
	form.setChanged(false);
	
	//修改节点不是模版时才显示级别表单
//	if(uuid!=node.uuid){
	if(node._level!=0){
		$("#levtr").css("display","table-row");
		mini.get("#lev").setValue(node.lev);
	}
	grid=roleGrid;
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
 * 保存修改岗位数据
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
//  	var url = "ccrolelist/modifyRole.action";
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
    grid.updateNode(currentNode,o);
    //保存历史记录
	var type="修改";
	content.type=type;
	var node={};
	node.uuid=currentNode.uuid;
	node.name=o.name;
	node.remark=o.remark;
	if(currentNode._level!=0){
		node.lev=o.lev;
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