mini.parse();

//获取审批人列表
var sub = mini.get("sub");

//临时数据
var treedata={};
//历史记录
var historyData=[];

function setData(code,json,hisdata){
	sub.load("ccindustryinfo/listPeople.action?actno="+code);
	treedata=json;
	historyData=hisdata
}

function onOk(){
	var usercode = sub.getValue().split(",")[0];
	$.ajax( {
			cache : false,
			url : 'ccindustryinfo/submit.action',
			type : 'post',
			datatype : 'json',
			data : {
				usercode : usercode,data:treedata,history:historyData
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