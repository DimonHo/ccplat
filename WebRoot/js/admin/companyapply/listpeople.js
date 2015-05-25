mini.parse();

//获取审批人列表
var sub = mini.get("sub");
//关键字
var key=mini.get("key");

//临时数据
var treedata={};
//历史记录
var code;

function setData(data,codeData,id){
	treedata=id;
	sub.setData(data);
	code=codeData;
	if(data[0].ruletype!="1"){
		$("#type").hide();
	}
}
 
function onOk(){
	var selectnode = sub.getSelectedNode();
	if(selectnode==null){
		mini.alert("请选择审核人");
		return;
	}
					$.ajax( {
							cache : false,
							url : 'companyapply/submit.action',
							datatype : 'json',
							data : {
								usercode:selectnode.id,
								data:treedata,
								code:code,
								key:key.getValue()
								
							},
							success : function(okstr) {
								if (okstr == "1") {
									mini.alert('成功');
								}else{
									mini.alert('失败');
								}
								closeWindow("ok");
							},
							error : function(e) {
								mini.alert('请求异常');
							}
						});
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

/**
 * 取消提交
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}