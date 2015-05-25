var submittype;  //提交类型   0 正常提交 1. 强制提交  2 强制不提交
var actno;
var flowno;
var urlaction;
var type;
var key;
	    
mini.parse();
var sub = mini.get("sub");
var remark=mini.get("remark");
        
//标准方法接口定义
function setDataCommon(data) {
	
   $("#content").html(data.content);
        	
    actno=data.actno;
   flowno=data.flowno;
   urlaction=data.urlaction;
   key=data.key;
   
   //获取提交人列表
   $.post("companyapply/listPeople.action",{
			actno : actno
		},function(e){
			var result = mini.decode(e);
			var str0 = result[0].islock;
			
			if (str0 == "0") {
				$("#sub").hide();
				$("#onOk").hide();
			}else{
				var personList = [];
				for(var i=1;i<result.length;i++){
					personList.push(result[i]);
				}
				alert("6662---"+personList);
				sub.setData(personList);
				  

				if(personList.length==0){
	  					sub.hide();
	  				 	$("#onOk").hide();
   				}     	
			}
	});
  
			
	//加载父页面数据，include此页面的jsp必须要写这个方法
   getAuditPower();
   
  // loadData();
 }

function getAuditPower(){
	
	params={
		actno:actno,
		flowno:flowno,
		key:key
	};
	
	 $.ajax( {
		cache : false,
		url : 'companyapply/getAuditPower.action',
		type : 'post',
		datatype : 'json',
		data : {
			param: mini.encode(params)
		},
		success : function(okstr) {
			var ary=okstr.split(",");
			type=ary[0];
			submittype=ary[1];
			
			//type如果为0则没有权限同意
			if(type=="0"){
				$("#agree").hide();
			}
			
			//submittype如果为2,则没有提交按钮,如果type为-2,则不存在联动关系
			if(submittype=="2"||type=="-2"){
				$("#onOk").hide();
				$("#sub").hide();
			}
		},
		error : function(e) {
			success('请求失败');
		}
	});
}
        
//function onOk(e){
//    params={
//        ruletype : ruletype,
//	    rulestart:rulestart,
//	    ruleend:ruleend,
//		flowno:flowno,
//		ruleno:ruleno,
//		actno:actno,
//		useraction:sub.getValue(),
//		key:key,
//		type:type
//    };
//        	
//    $.ajax( {
//		cache : false,
//		url : 'reg/check.action',
//		type : 'post',
//		datatype : 'json',
//		data : {
//			param: mini.encode(params)
//		},
//		success : function(okstr) {
//			mini.alert("操作成功！");
//			CloseWindow();
//		},
//		error : function(e) {
//			success('请求失败');
//		}
//	});
//}

function agree(flag){
	params={
        submittype : submittype,
		submitno:sub.getValue(),
		remark:remark.getValue(),
		flowno:flowno,
		actno:actno,
		type:flag
     };
      $.ajax( {
		cache : false,
		url : basePath+urlaction,
		type : 'post',
		datatype : 'json',
		data : {
			param: mini.encode(params)
		},
		success : function(okstr) {
			mini.alert("操作成功！");
			CloseWindow();
		},
		error : function(e) {
			success('请求失败');
		}
	});
}
        
function onCancel(e) {
     CloseWindow("cancel");
}
        
function CloseWindow() {            
      if (window.CloseOwnerWindow) return window.CloseOwnerWindow(action);
       else window.close();            
 }