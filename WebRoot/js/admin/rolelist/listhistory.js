mini.parse();

//获取岗位树对象
var tree = mini.get("roleTree");

//获取岗位树对象
var datagrid = mini.get("datagrid");

//分页控件
var page = mini.get("page");
//历史记录数据
var hisData=null;

//保存历史记录详细信息
var remark={};
var key=0;

//选中模版节点时，更新右侧历史记录列表
tree.on("nodeselect", function(e) {
	var node = tree.getSelectedNode();
	//历史记录数据
	hisData=node.history;
	//每页条数
	var pageSize=page.pageSize;
	//总条数
	var totalSize=hisData.length;
	
	page.update(0,pageSize,totalSize);
	datagrid.setData(hisData.slice(0,pageSize));
	//datagrid.clearRows();
	//datagrid.addRows(node.history);
});

//分页按钮事件
function onPageChanged(e){
	//page.update(e.pageIndex,e.pageSize,e.sender.totalCount);
	var start=e.pageIndex*e.pageSize;
	datagrid.setData(hisData.slice(start,start+e.pageSize));
}

/**
 * 获取时间对象
 * @param {Object} e
 * @return {TypeName} 
 */
function getDate(e){
	return new Date(e.value.time).toLocaleString();
}

/**
 * 显示详细按钮
 * @param {Object} e
 */
function detail(e){
	remark[key] = e.record.remark;
	var s = '<a class="Detail_Button" href="javascript:showDetail(\'' + key + '\')" >详细</a>';
	key++;
	return s;
}

/**
 * 获取未删的模版列表
 */
function getExist(){
	tree.load();
}

/**
 * 获取已删删的模版列表
 */
function getDelete(){
	tree.load({isDelete:"true"});
}

function showDetail(key){
	mini.open( {
		url : 'jsp/admin/rolelist/listdetail.jsp',
		title : "历史记录",
		width : 800,
		height : 500,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(remark[key]);
		},
		ondestroy : function() {
		}
	});
}