var grids=null;
var ccno="";

mini.parse();

function SetData(data) {
    data = mini.clone(data);
	ccno = data.ccno;
	
	grids=mini.get("grids");
	grids.load({ccno:ccno});
}
	
function SaveData(){
	var records=grids.getSelecteds();
	var actnos="";
	for(var i=0;i<records.length;i++){ //拼装数据到后台
		actnos+=records[i].actno+"/"+records[i].actname+",";
	}
		
	if(actnos!=""){
			actnos=actnos.substring(0,actnos.length-1);
	}
		
	$.ajax( {
		url : basePath+"audit/SaveFunForCcno.action",
		type : 'post',
		data : {
			actnos : actnos,
			ccno:ccno
		},
		cache : false,
		success : function(text) {
			if(text=="false"){
				mini.alert("操作失败！");
			}else{
				mini.alert("操作成功！");
			}
			CloseWindow("close");
		},
		error : function(jqXHR, textStatus, errorThrown) {
			mini.alert(jqXHR.responseText);
			CloseWindow();
		}
	});
}
    
function CloseWindow(action) {
   if (window.CloseOwnerWindow) 
      return window.CloseOwnerWindow(action);
     else window.close();
 }