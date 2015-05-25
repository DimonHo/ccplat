var tree=null;
mini.parse();
var table="b_companyapply"
var grids=mini.get("grids");
var schedule=mini.get("schedule");
grids.load();

function clickAct(e){
	var record =e.record;
	schedule.load({actno:record.code,table:table});
}

function onSelectionChanged(e){
  var row = schedule.getSelected();
  //判断是否选取了一条数据
  if (row) {
	var rows = mini.encode(row);
	
	mini.open({
		url : basePath+row.url,
		title : "审核",
		width : 600,
		height : 360,
		onload : function() {
		var iframe = this.getIFrameEl();
		var data = {
			action : "edit",
			url: row.url,
			urlaction: row.urlaction,
			ccno : row.ccno,
			content : row.content,
			ruletype : row.ruletype,
			rulestart : row.rulestart,
			ruleend : row.ruleend,
			flowno :row.flowno,
			actno : row.actno,
			ruleno : row.ruleno,
			key:row.key
			};
		 iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {
				gridid.reload();
				}
			});
		} else {
			alert("请选中一条记录");
		}
   }
//创建时间
	function onCreatetime(e){
	var record = e.record;
	var createtime = record.createtime.$date;
	return mini.formatDate (createtime,"yyyy-MM-dd");

}
//审核状态
     function onState(e){
	var record = e.record;
	var state = record.state;
	if(state==0){
		return "待审核";
	}
	else{
		return null;
	}
}