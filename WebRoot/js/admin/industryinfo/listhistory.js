mini.parse();

//获取组织架构树对象
var datagrid = mini.get("datagrid");
//保存历史记录详细信息
var remark={};
var key=0;

datagrid.load();

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


function showDetail(key){
	mini.open( {
		url : 'jsp/admin/industryinfo/listdetail.jsp',
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
