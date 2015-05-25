mini.parse();

var local = window.location;  
var contextPath = local.pathname.split("/")[1];  
var basePath = local.protocol+"//"+local.host+"/"+contextPath+"/";  

//获取产品树对象
var productTree = mini.get("productTree");

/**
 * 根据企业id加载树节点
 * @param {Object} index 下标
 */
function setData(companyno){
	productTree.load(basePath+"ccproductslist/listalloted.action?companyno="+companyno);
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