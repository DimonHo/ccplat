mini.parse();

//获取组织架构树对象
var datagrid = mini.get("datagrid");

//分页控件
var detailPage = mini.get("detailpage");
//历史记录数据
var detailData=null;

/**
 * 获取时间对象
 * @param {Object} e
 * @return {TypeName} 
 */
function setData(data){
	detailData=data;
	//每页条数
	var pageSize=detailPage.pageSize;
	//总条数
	var totalSize=detailData.length;
	detailPage.update(0,pageSize,totalSize);
	datagrid.setData(detailData.slice(0,pageSize));
	//datagrid.addRows(data);
}

//分页按钮事件
function onPageChanged(e){
	//page.update(e.pageIndex,e.pageSize,e.sender.totalCount);
	var start=e.pageIndex*e.pageSize;
	datagrid.setData(detailData.slice(start,start+e.pageSize));
}