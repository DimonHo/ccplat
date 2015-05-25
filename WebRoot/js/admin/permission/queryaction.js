var grids=null;
var ccno="";

mini.parse();

//弹出框默认调用方法
function SetData(data) {
    data = mini.clone(data);
	ccno = data.ccno;
	grids=mini.get("grids");
	grids.load({ccno:ccno});
}
	
function jumpPage(id,name){
	mini.open({
		url: basePath+"jsp/admin/permission/editaction.jsp",
		showMaxButton: false,
		title: "选择部门",
		width: 350,
		height: 350,
		onload:function(){
			var iframe = this.getIFrameEl();			 
			var data = {actno:id,actname:name,ccno:ccno};
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
    
function CloseWindow(action) {
  if (window.CloseOwnerWindow) 
        return window.CloseOwnerWindow(action);
        else window.close();
    }