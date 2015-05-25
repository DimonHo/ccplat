mini.parse();

//获取行业树对象
var industryTree = mini.get("industryTree");

//获取组织架构树对象
var frameTree = mini.get("frameTree");

//当前选中的行业树节点的uuid
var currentUUID='';

//当前选中企业id
var companyid='';

//当前选中的节点数据
var checkedData=[];
//选中的模版数
var checkedroot=0
//选中根节点
var rootCheckedNode=null;

var companyData=null;

//选中行业树节点时，更新右侧组织架构树
industryTree.on("nodeselect", function(e) {
	var node = industryTree.getSelectedNode();
	currentUUID=node.uuid;
	frameTree.load({industryuuid:currentUUID});
});

/**
 * 保存选中的企业的数据
 * @param {Object} companyno 企业id
 * @param {Object} industryuuid 企业的行业id
 */
function setData(companyno,industryuuid,cell){
	currentUUID=industryuuid;
	companyid=companyno;
	companyData=cell;
	//获取对应行业节点
	var node=industryTree.getNode(industryuuid);
	//选中当前行业
	industryTree.selectNode(node);
}

/**
 * 确认提交初始化信息
 * @param {Object} e 触发事件对象
 */
function onOk(e){
	saveData();
}

/**
 * 保存初始化组织机构数据
 * @return {TypeName} 
 */
function saveData() {
	//当前选中的节点数据
	checkedData=[];
	//选中的模版数
	checkedroot=0
	//选中根节点
	rootCheckedNode=null;
	//获取所有选中节点集合
	var nodes=frameTree.getCheckedNodes(true);
	//封装树型选择节点数据
	for(var i=0;i<nodes.length;i++){
		getParents(nodes[i]);
	}
	
	if(checkedroot>1){
		mini.alert("不能跨模版选择组织架构!");
		return;
	}
	if(checkedroot==0){
		mini.alert("请选择组织架构!");
		return;
	}
	
    var json = mini.encode(rootCheckedNode.children);
   
  	var url = "bframe/addFrame.action";
	
  	mini.confirm("确定初始化组织架构？", "确定？",function(action){
  		if(action=="ok"){
  			 $.ajax({
		        url: url,
				type: 'post',
		        data: { data: '{children:'+json+'}',industryuuid:currentUUID,companyno:companyid,company:mini.encode(companyData)},
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
 * 递归获取所有上级节点
 */
function getParents(node){
	if(checkedData[node._uid]==null){
		//当前节点数据
		var nodeData={};
		nodeData.uuid=node.uuid;
		nodeData.name=node.name;
		nodeData.remark=node.remark;
		nodeData.children=[];
		//保存节点数据
		checkedData[node._uid]=nodeData;
		
		if(node._level!=0){
			if(checkedData[node._pid]==null){
				//父节点数据没有，继续往上找父节点,直到根节点
				var parentNode=frameTree.getParentNode(node);
				getParents(parentNode);
			}
			//父节点数据已经有了，直接插入当前节点数据
			checkedData[node._pid].children.push(checkedData[node._uid]);
		}else{
			//当前选中根节点数目
			checkedroot++;
			//当前选中根节点
			rootCheckedNode=nodeData;
		}
	}
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
