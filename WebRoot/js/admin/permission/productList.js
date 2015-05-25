mini.parse();
var grids=mini.get("grids");
//获取行业id
var industryinfo = mini.get("industryinfo");
//获取公司名称
var company = mini.get("company");

//查询全部
grids.load({industryno:"",productName:""});

function search(){
	grids.load({industryno:industryinfo.value,productName:company.value});
}

/**
 * 选择行业关闭按钮
 * @return
 */
function onCloseClick(){
	
	industryinfo.setValue("");
}

//回车监听
company.on("enter",function(){
	search();
});

//将鼠标改为手型
grids.on("drawcell", function (e){
   	 e.cellStyle = "cursor: pointer;";
});

grids.on("rowclick",function(e){
	
	var record=e.record;

	mini.open({
		url: basePath+"jsp/admin/permission/actlink.jsp",
		showMaxButton: false,
		title: "功能联动设置",
		width: 450,
		height: 380,
		onload:function(){
			var iframe = this.getIFrameEl();	
			var data = {code:record.code};
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
	
});