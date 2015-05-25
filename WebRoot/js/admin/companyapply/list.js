mini.parse();
var grid = mini.get("datagrid1");
grid.load();
//功能编码
var code = "companyapplysubmit";

//添加
function add() {
	mini.open( {
		url : "jsp/admin/companyapply/edit.jsp",
		title : "填写申请资料",
		width : 562,
		height : 460,
		onload : function() {
			var iframe = this.getIFrameEl();
			var data = {
				action : "new"
			};
			iframe.contentWindow.SetData(data);
		},
		ondestroy : function(action) {

			grid.reload();
		}
	});
}
//编辑  
function edit() {

	var row = grid.getSelected();
	if (row) {
		if (row.state == "0" || row.state == "1") {
			mini.alert("请检查是否申请或者审核");
		} else {
			mini.open( {
				url : "jsp/admin/companyapply/edit.jsp",
				title : "修改申请资料",
				width : 562,
				height : 460,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "edit",
						id : row.id
					};
					iframe.contentWindow.SetData(data);

				},
				ondestroy : function(action) {
					grid.reload();

				}
			});
		}
	} else {
		mini.alert("请选中一条记录");
	}

}
//审核状态
function onState(e) {
	var record = e.record;
	var state = record.state;
	if (state == 0) {
		return "已申请";
	} else if (state == 1) {
		return "已审核";
	} else {
		return "未提交";
	}
}
//创建时间
function onCreatetime(e){
	var record = e.record;
	var createtime = record.createtime.$date;
	return mini.formatDate (createtime,"yyyy-MM-dd h:mm:ss");

}
	//所在地区
	function onAddress(e){
	var record = e.record; 
	if(record.address!=undefined){
		var address = record.address.country+record.address.province+record.address.city+record.address.county;
	if(record.street != undefined)
		address=address+record.street;
		return address;

	}else{
			return null;
	}	
}
//提交申请	
function superapply() {
	var rows = grid.getSelecteds();
		if (rows.length > 0) {
			if (confirm("确定提交选中记录？")) {
				var ids = [];
				var flat=true;
				for ( var i = 0, l = rows.length; i < l; i++) {
					var r = rows[i];
					ids.push(r.id);
					var state=r.state;
					if(state==0||state==1){
						flat=false;
					}
				}
				if(flat==true){
					var id = ids.join(',');				
					$.ajax({
		                url: "companyapply/apply.action",
						type: 'post',
		                data: { id: id },
		                cache: false,
		                success: function (text) {
		                	if(text=="1"){
		                		mini.alert("操作成功");
		                	}else{
		                		mini.alert("操作失败");
		                	}
		                	grid.load();
		                	
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	mini.alert(jqXHR.responseText);
		                   
		                }
		            })	
		            }else{
						alert("请检查是否已提交申请或者已经审核");
					}
			}
			}else{
				mini.alert("请选中一条记录");
			}
        }
//审核
function supercheck(){  
	var rows = grid.getSelecteds();
		if (rows.length > 0) {
			if (confirm("确定审核选中记录？")) {
				var ids = [];
				var flat=true;
				for ( var i = 0, l = rows.length; i < l; i++) {
					var r = rows[i];
					ids.push(r.id);	
					var state=r.state;
					if(state!=0){	
						flat=false;
					}
				}
				if(flat==true){
					var id = ids.join(',');
					$.ajax({
		                url: "companyapply/supercheck.action",
						type: 'post',
		                data: { id: id },
		                cache: false,
		                success: function (text) {
		                	if(text=="1"){
		                		mini.alert("操作成功");
		                	}else{
		                		mini.alert("操作失败");
		                	}
		                	grid.load();
		                },
		                error: function (jqXHR, textStatus, errorThrown) {
		                	mini.alert(jqXHR.responseText);
		                   
		                }
		            });	
					}else{
						alert("请检查是否已提交申请或者已经审核");
					}
			}
			}else{
				mini.alert("请选中一条记录");
			}
	
}
//查询
function seach() {
	var state = document.getElementById("state").value;
	var inputname = mini.get("inputname").getValue();
	grid.load({
			inputname : inputname,
			state : state
		});

}
//查询历史记录
function history() {
var  row=grid.getSelected();
	if(row){
			mini.open( {
				url : "jsp/admin/companyapply/listhistory.jsp",
				title : "历史记录",
				width : 500,
				height : 360,
				onload : function() {
					var iframe = this.getIFrameEl();
					
						var data = {id : row.id};
						iframe.contentWindow.SetData(data);
											
				},
				ondestroy : function(action) {					
					grid.reload();
				}
			});
		}else{
			mini.alert("请选中一条记录");
		}
	}
		
//编辑历史记录
function addhistory() {
	var row = grid.getSelected();
		if (row) {
			var createtime=row.createtime.$date;
			createtime=mini.formatDate (createtime,"yyyy-MM-dd");
			mini.open( {
				url : "jsp/admin/companyapply/addhistory.jsp",
				title : "编辑历史记录",
				width : 500,
				height : 320,
				onload : function() {
					var iframe = this.getIFrameEl();
					var data = {
						action : "edit",
						id : row.id,
						createtime:createtime
					};
					iframe.contentWindow.SetData(data);
	
				},
				ondestroy : function(action) {
					grid.reload();
	
				}
			});
	
		} else {
			mini.alert("请选中一条记录");
		}
}

function check(){
$.ajax({
		url: "companyapply/islocked.action",
		type: 'post',
		data: { id: id },
		     cache: false,
		     success: function (text) {
		    	 if(text=="0"){
		    		 supercheck();
		    	 }else{
		               mini.open( {
								url : "jsp/admin/companyapply/schedule.jsp",
								title : "审核列表页",
								width : 720,
								height : 460,
								onload : function() {
								},
								ondestroy : function(action) {
						
									grid.reload();
								}
							});
		                	 //end
					 }
		    	 }
	})
	
}
function apply(){
	$.ajax({
		url: "companyapply/islocked.action",
		type: 'post',
		data: { id: id },
		     cache: false,
		     success: function (text) {
		    	 if(text=="0"){
		    		 superapply();
		    	 }else{
		    		 $.ajax({
						url: "companyapply/listPeople.action?actno="+code,
						type: 'post',
						data: { id: id },
		    			cache: false,
		    			success: function (text) {
		    	        	var rows = grid.getSelecteds();
							if (rows.length > 0) {
								if (confirm("确定提交选中记录？")) {
									var ids = [];
									var flat=true;
									for ( var i = 0, l = rows.length; i < l; i++) {
										var r = rows[i];
										ids.push(r.id);
										var state=r.state;
										if(state==1||state== 0){
											flat=false;
										}
									}
									if(flat==true){
										var id = ids.join(',');		
										var result = mini.decode(text);
										if(result[0].islock==0){
											superapply();
										}else{
											listPeople(id,result);		
										}
							             
									}else{
										alert("请检查是否已提交申请");
									}
								}
								}else{
									alert("请选中一条记录");
								}
							    	        }
		    		 //
		    	 })
		    	 }}
		});
}
/////////////////////////////////////////////////////////////////////////////////
/**
 * 展示上级列表
 */
function listPeople(id,result){
//	$.ajax({
//		                url: "companyapply/listPeople.action?actno="+code,
//						type: 'post',
//		                data: { id: id },
//		                cache: false,
//		                success: function (text) {alert(text);
//		                islock=0;
//		                       // text=text.toString().substring(1,text.toString().length-1);
//                        		var o = mini.decode(text); //alert(o[0].islock); alert(o[1].name); 
//	                       		var arrayObj = new Array();　
//	                       		arrayObj. push(o[2]);                   		
//								if(arrayObj.length==1){
//									//默认提交第一个
//									$.ajax( {
//										cache : false,
//										url : 'companyapply/submit.action',
//										type : 'post',
//										datatype : 'json',
//										data : {
//											usercode : o.id,data:id,history:historyData
//										},
//										success : function(okstr) {
//											if (okstr == "1") {
//												alert('成功');
//											}else{
//												alert('失败');
//											}
//											//closeWindow("ok");
//										},
//										error : function(e) {
//											alert('请求异常');
//										}
//									});
//									//end
//								}else if(arrayObj.length>1){
//										mini.open( {
//											url : 'jsp/admin/companyapply/listpeople.jsp',
//											title : "选择审批人",
//											width : 500,
//											height : 300,
//											onload : function() {
//												var iframe = this.getIFrameEl();
//												iframe.contentWindow.setData(code,id);
//											},
//											ondestroy : function(action) {
//												if(action=="ok"){
//													saveBtn.disable();
//												}
//											}
//										});
//								}
//		                	
//		                },
//		                error: function (jqXHR, textStatus, errorThrown) {
//		                    alert(jqXHR.responseText);
//		                   
//		                }
//		            });
	var personList = [];
			for(var i=1;i<result.length;i++){
				personList.push(result[i]);
			}
	mini.open( {
		url : 'jsp/admin/companyapply/listpeople.jsp',
		title : "选择审批人",
		width : 500,
		height : 300,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(personList,code,id);
		},
		ondestroy : function(action) {
			if(action=="ok"){
				saveBtn.disable();
			}
		}
	});
}
