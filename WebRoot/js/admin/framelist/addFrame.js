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
 * 保存当前选中节点数据
 * @param {Object} treeData 组织架构树对象
 * @param {Object} node 选中节点
 */
function setData(treeData,node){
//	mini.get("#key").setValue(key);
//	mini.get("#index").setValue(index);
//	mini.get("#industryuuid").setValue(industryuuid);//当前行业树选择节点uuid
//	mini.get("#uuid").setValue(uuid);//当前选中组织结构树根节点uuid
	parentNode=node;
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
//    var json = mini.encode(o);
//   
//  	var url = "ccframelist/addFrame.action";
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