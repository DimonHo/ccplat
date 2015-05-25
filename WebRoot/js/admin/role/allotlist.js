mini.parse();

//获取行业树对象
var roleTree = mini.get("roleTree");

//当前选中企业id
var companyid='';

//当前选中的节点数据
var checkedData=[];
//选中的模版数
var checkedroot=0
//公司数据
var company=null;

/**
 * 保存选中的企业的数据
 * @param {Object} companyno 企业id
 */
function setData(companyno,cell){
	companyid=companyno;
	company=cell;
}

/**
 * 确认提交初始化信息
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	saveData();
}

/**
 * 保存初始化岗位数据
 * @return {TypeName} 
 */
function saveData() {
	//当前选中的节点数据
	checkedData=[];
	//选中的模版数
	checkedroot=0;
	//获取所有选中节点集合
	var nodes=roleTree.getCheckedNodes(true);
	//封装树型选择节点数据
	for(var i=0;i<nodes.length;i++){
		var node=nodes[i];
		if(node._level==0){
			checkedroot++;
		}else{
			//当前节点数据
			var nodeData={};
			nodeData.uuid=node.uuid;
			nodeData.name=node.name;
			nodeData.remark=node.remark;
			nodeData.lev=node.lev;
			//保存节点数据
			checkedData.push(nodeData);
		}
	}
	if(checkedroot>1){
		mini.alert("不能跨模版选择岗位!");
		return;
	}
	if(checkedroot==0){
		mini.alert("请选择分配的岗位!");
		return;
	}
	
    var json = mini.encode(checkedData);
   
  	var url = "brole/addallot.action";
	mini.confirm("确定初始化岗位？", "确定？",function(action){
		if(action=="ok"){
			$.ajax({
		        url: url,
				type: 'post',
		        data: { data: '{position:'+json+'}',companyno:companyid,company:mini.encode(company)},
		        cache: false,
		        success: function (text){
		        	if (text == "1") {
		        		mini.alert("初始化成功!");
						closeWindow();
		        	}else{
		        		mini.alert('初始化失败');
		        	}
					
		        },
		        error: function (jqXHR, textStatus, errorThrown) {
		            mini.alert(jqXHR.responseText);
		            closeWindow();
		        }
		    });
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
 * 取消初始化
 * @param {Object} e 触发事件对象
 */
function onCancel(e){
	closeWindow("close");
}