var cccompanyno="";
var dealno="";
   		 
mini.parse();
        
var sub = mini.get("sub");
var deal = mini.get("deal");
var codeValue="";         
function onOk(e) {
     SaveData();
}

function SetData(data) {
    
  	data = mini.clone(data);
  	codeValue = data.code;
  	
  	sub.load(basePath+"audit/getProduct.action?code="+codeValue);
  	deal.load(basePath+"audit/getProduct.action?code="+codeValue);
  
}
		
//对数据进行保存
function SaveData() {
	   
	var subValue=sub.getValue();
	var dealValue=deal.getValue();
	     
	var str="确认提交数据？";
	
	if(dealValue==""){
		mini.alert("请选择联动功能！！");
	 	return false;
	}else if(dealValue==dealno){
		mini.alert("已经存在这个功能的联动，请重新选择！");
	     return false;
	}
	     
	 if(dealno!=""&&dealValue!=""){
	    str="已经存在联动，是否需要进行修改！";
	}
	     
	 mini.confirm(str, "确认框", function (e) {
        if(e=="ok"){
              $.ajax( {
					url : basePath+"audit/saveLink.action",
					type : 'post',
					data : {
						sub : subValue,
						deal: dealValue
					},
					cache : false,
					success : function(text) {
						if(text=="true"){
							mini.alert("数据添加成功！");
						}else{
							mini.alert("数据添加失败！");
						}
					},
					error : function(jqXHR, textStatus, errorThrown) {
						mini.alert(jqXHR.responseText);
						CloseWindow();
					}
				});
           }
      });
}

sub.on("nodeclick",function(e){
	var node = e.node;
	var sender = e.sender;
	var children = node.children;
	if(children){
		sub.setValue("");
	}else{
		sub.setValue(node.name);
	}
});

deal.on("nodeclick",function(e){
	var node = e.node;
	var sender = e.sender;
	var children = node.children;
	if(children){
		deal.setValue("");
	}else{
		deal.setValue(node.name);
	}
});

//改变处理模块
function changeDeal(e){

	 $.ajax({
		url : basePath+"audit/getLink.action",
		type : 'post',
		data : {
			actno : sub.getValue()
		},
		cache : false,
		success : function(text) {
			deal.setValue(text);
			//存在的联动功能
			dealno=deal.getValue();
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
		}
	});
}