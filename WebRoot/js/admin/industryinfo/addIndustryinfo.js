mini.parse();

//新增行业的表单对象
var form = new mini.Form("form");
//选中节点对象
var parentNode=null;
//树对象
var tree=null;
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
 * 取消新增
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}

/**
 * 保存当前选中节点数据
 * @param {Object} treeData 树对象
 * @param {Object} node 当前选择节点对象
 */
function setData(treeData,node){
//	mini.get("#key").setValue(key);
//	mini.get("#index").setValue(index);
	tree=treeData;
	parentNode=node;
}
/**
 * 获取保存的历史记录
 * @return {TypeName} 
 */
function getData(){
	return content;
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
//    var json = mini.encode(o);
   
//  	var url = "ccindustryinfo/addIndustryinfo.action";
//	
//    $.ajax({
//        url: url,
//		type: 'post',
//        data: { data: json},
//        cache: false,
//        success: function (text){
//        	if (text == "1") {
//        		alert("添加数据成功!");
//				closeWindow();
//        	}else{
//        		alert('添加失败');
//        	}
//			
//        },
//        error: function (jqXHR, textStatus, errorThrown) {
//            alertClick(jqXHR.responseText);
//            closeWindow();
//        }
//    });
    var node={};
    node.uuid=Math.uuid();
    node.name=o.name;
    node.remark=o.remark;
	tree.addNode(node,"add",parentNode);
	//保存历史记录
	var type="新增";
	content.type=type;
	var data={};
	data.uuid=node.uuid;
	data.name=node.name;
	data.remark=node.remark;
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