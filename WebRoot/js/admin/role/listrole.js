mini.parse();

//获取组织架构树对象
var datagrid = mini.get("datagrid");

/**
 * 根据企业id加载树节点
 * @param {Object} index 下标
 */
function setData(companyno,cell){
	datagrid.load({companyno:companyno,company:mini.encode(cell)});
}

/**
 * 点击确定按钮关闭
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	closeWindow("close");
}

/**
 * 关闭窗口
 * @param {Object} action 判断是否主动关闭
 * @return {TypeName} 
 */
function closeWindow(action) {            
    if (window.CloseOwnerWindow){
    	return window.CloseOwnerWindow(action);
    }else{
    	window.close();  
    }      
}