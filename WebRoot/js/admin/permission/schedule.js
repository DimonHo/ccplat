var tree=null;
mini.parse();

var grids=mini.get("grids");
var schedule=mini.get("schedule");
grids.load();

function clickAct(e){
	var record =e.record;
	schedule.load({actno:record.code});
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
			mini.alert("请选中一条记录");
		}
   }