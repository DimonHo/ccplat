var tree=null;

mini.parse();

var dep = mini.get("dep");
var grids=mini.get("grids");
	
dep.load(basePath+"audit/getPersonForPer.action");


function changeMoudle(e){
    
   var node = dep.getSelectedNode();
   grids.load({frameRole:node.id});
}

function jumpPage(id){
    	
    mini.open({
		url: basePath+"jsp/admin/permission/queryaction.jsp",
		showMaxButton: false,
		title: "选择部门",
		width: 350,
		height: 350,
		onload:function(){
			var iframe = this.getIFrameEl();			 
			var data = {ccno:id};
			iframe.contentWindow.SetData(data);
		},
		ondestroy: function (action) {                    
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var data = iframe.contentWindow.GetData();
				data = mini.clone(data);
				if (data) {
					btnEdit.setValue(data.id);
					btnEdit.setText(data.name);
				}
			}
		}
	});
}
    
//////////////////////////////////
function CloseWindow(action) {
   if (window.CloseOwnerWindow) 
        return window.CloseOwnerWindow(action);
        else window.close();
}
    
function onSelectionChanged(e){
    
   var grid = e.sender;
   var record = grid.getSelected();
   if(record){
       ccno= record.ccno;
            	 
       mini.open({
			url: basePath+"jsp/admin/permission/selectact.jsp",
			showMaxButton: false,
			title: "选择部门",
			width: 350,
			height: 350,
			onload:function(){
				var iframe = this.getIFrameEl();			 
				var data = {ccno:ccno};
						
				iframe.contentWindow.SetData(data);
			},
		ondestroy: function (action) {                    
			if (action == "ok") {
				var iframe = this.getIFrameEl();
				var data = iframe.contentWindow.GetData();
				data = mini.clone(data);
				if (data) {
					btnEdit.setValue(data.id);
					btnEdit.setText(data.name);
				}
			}
		}
	});
            	 
  }
}
