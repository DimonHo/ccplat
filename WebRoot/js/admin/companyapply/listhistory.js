mini.parse();
var grid = mini.get("datagrid1");

//创建时间
function onCreatetime(e){
	var record = e.record;
	var createtime = record.createtime.$date;
	return mini.formatDate (createtime,"yyyy-MM-dd");

}
function SetData(data){
	var data=mini.clone(data);
	
	//获取某数据的历史记录
	grid.load({id:data.id});
}
//操作人
function onOpertor(e){
		var record = e.record;
		var operator=record.operator.name;
		return operator;
}