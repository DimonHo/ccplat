mini.parse();

//加载数据
var grid = mini.get("datagrid");
grid.load();

/**
 * 组织架构分配状态单元格设置
 * @param {Object} e
 */
function frameState(e){
	if(e.value=="1"){
		var button = '<a href="javascript:listFrame()" style="color:green">已分配</a>';
	}else{
		var button = '<a href="javascript:initFrame()" style="color:red">未分配</a>';
	}
	return button;
}

/**
 * 岗位分配状态单元格设置
 * @param {Object} e
 */
function roleState(e){
	if(e.value=="1"){
		var button = '<a href="javascript:listRole()" style="color:green">已分配</a>';
	}else{
		var button = '<a href="javascript:initRole()" style="color:red">未分配</a>';
	}
	return button;
}

/**
 * 产品分配状态单元格设置
 * @param {Object} e
 */
function productState(e){
	if(e.value=="1"){
		var button = '<a href="javascript:listProduct()" style="color:green">已分配</a>';
	}else{
		var button = '<a href="javascript:initProduct()" style="color:red">未分配</a>';
	}
	return button;
}

/**
 * 帐号分配状态单元格设置
 * @param {Object} e
 */
function companyccState(e){
	if(e.value=="1"){
		var button = '<a href="javascript:listCompanycc()" style="color:green">已分配</a>';
	}else{
		var button = '<a href="javascript:initCompanycc()" style="color:red">未分配</a>';
	}
	return button;
}

/**
 * 查看数据
 * @param {Object} e
 */
function onShow(e){
	return '<a href="javascript:show()" style="color:green">'+e.value+'</a>';
}

/**
 * 初始化分配帐号
 */
function initCompanycc(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配组织架构   0为未分配  1为已分配
	var companycctate=cell.state.companycc;
	//当前行业uuid
	var industryuuid=cell.industryuuid;
	
	if(companycctate==1){
		mini.alert("该企业已分配帐号!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/companycc/add.jsp',
		title : "初始化帐号",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(cell);
		},
		ondestroy : function() {
			grid.reload();
		}
	});
}

/**
 * 初始化分配组织架构
 */
function initFrame(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配组织架构   0为未分配  1为已分配
	var framestate=cell.state.frame;
	//当前行业uuid
	var industryuuid=cell.industryuuid;
	
	if(framestate==1){
		mini.alert("该企业已分配组织架构!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/frame/addframe.jsp',
		title : "初始化组织架构",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(companyno,industryuuid,cell);
		},
		ondestroy : function() {
			grid.reload();
		}
	});
}

/**
 * 初始化分配岗位
 */
function initRole(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配岗位   0为未分配  1为已分配
	var rolestate=cell.state.role;
	
	if(rolestate==1){
		mini.alert("该企业已分配岗位!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/role/allotlist.jsp',
		title : "初始化岗位",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(companyno,cell);
		},
		ondestroy : function() {
			grid.reload();
		}
	});
}

/**
 * 初始化分配产品
 */
function initProduct(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配产品   0为未分配  1为已分配
	var productstate=cell.state.product;
	
	if(productstate==1){
		mini.alert("该企业已分配产品!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/productslist/allotproduct.jsp',
		title : "初始化产品",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(cell.industryuuid,companyno);
		},
		ondestroy : function() {
			grid.reload();
		}
	});
}

/**
 * 查看已分配组织架构
 */
function listFrame(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配组织架构   0为未分配  1为已分配
	var framestate=cell.state.frame;
	
	if(framestate==0){
		mini.alert("该企业未分配组织架构!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/frame/listframe.jsp',
		title : "当前组织架构",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(companyno,cell);
		},
		ondestroy : function() {
		}
	});
}
/**
 * 查看已分配岗位
 */
function listRole(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配岗位   0为未分配  1为已分配
	var rolestate=cell.state.role;
	
	if(rolestate==0){
		mini.alert("该企业未分配岗位!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/role/listrole.jsp',
		title : "当前岗位",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(companyno,cell);
		},
		ondestroy : function() {
		}
	});
}

/**
 * 查看已分配帐号
 */
function listCompanycc(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配产品   0为未分配  1为已分配
	var companyccstate=cell.state.companycc;
	
	if(companyccstate==0){
		mini.alert("该企业未分配帐号!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/companycc/showlist.jsp',
		title : "当前帐号",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.SetData(cell);
		},
		ondestroy : function() {
		}
	});
}

/**
 * 查看已分配产品
 */
function listProduct(){
	//获取选中行
	var cell=grid.getSelected();
	
	if(cell==''||cell==null){
		mini.alert('请选择企业');
		return;
	}
	//选中公司的id
	var companyno=cell.companyno;
	//是否已分配产品   0为未分配  1为已分配
	var productstate=cell.state.product;
	
	if(productstate==0){
		mini.alert("该企业未分配产品!");
		return;
	}
	
	mini.open( {
		url : 'jsp/admin/productslist/listalloted.jsp',
		title : "当前产品",
		width : 850,
		height : 550,
		onload : function() {
			var iframe = this.getIFrameEl();
			iframe.contentWindow.setData(companyno);
		},
		ondestroy : function() {
		}
	});
}

/**
 * 根据过滤条件查询公司
 */
function search(){
	//获取行业id
	var industryinfo = mini.get("industryinfo");
	//获取公司名称
	var company = mini.get("company");
	grid.load({industryinfo:industryinfo.value,company:company.value});
}

//清空行业过滤条件
function onCloseClick(e) {
    var obj = e.sender;
    obj.setText("");
    obj.setValue("");
}

function show() {

	var row = grid.getSelected();
	if (row) {
		mini.open( {
			url : "jsp/admin/company/show.jsp",
			title : "企业资料",
			width : 500,
			height : 470,
			onload : function() {
				var iframe = this.getIFrameEl();
				iframe.contentWindow.SetData(row);
			}
		});
	} else {
		mini.alert("请选中一条记录");
	}
}

function modify() {
	var row = grid.getSelected();
	if (row) {
		mini.open( {
			url : "jsp/admin/company/modify.jsp",
			title : "修改资料",
			width : 500,
			height : 470,
			onload : function() {
				var iframe = this.getIFrameEl();
				iframe.contentWindow.SetData(row);
			},
			ondestroy : function(action) {
				grid.reload();
			}
		});

	} else {
		alert("请选择修改资料");
	}
}
