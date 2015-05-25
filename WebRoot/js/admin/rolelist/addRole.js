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
 * 取消新增
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}

/**
 * 保存当前选中模版节点
 * @param {Object} node 当前选中节点
 */
function setData(roleGrid,node){
	grid=roleGrid;
	currentNode=node;
	//mini.get("#uuid").setValue(uuid);
	//新增岗位时才显示级别表单
	if(node!=null){
		$("#levtr").css("display","table-row");
	}
}

/**
 * 获取保存的历史记录
 * @return {TypeName} 
 */
function getData(){
	return content;
}

/**
 * 保存新增岗位数据
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
    if(currentNode!=null){
    	node.lev=o.lev;
    }
    grid.addNode(node,"add",currentNode);
    //保存历史记录
	var type="新增";
	content.type=type;
	var data={};
	data.uuid=node.uuid;
	data.name=node.name;
	data.remark=node.remark;
	if(currentNode!=null){
    	data.lev=node.lev;
    }
	content.data=data;
    closeWindow("ok");
    
//    var json = mini.encode(o);
//   
//  	var url = "ccrolelist/addRole.action";
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