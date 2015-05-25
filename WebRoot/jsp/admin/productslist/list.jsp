<%--
-文件名：listproduct.jsp
-日期：2014.9.22
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询行业产品
--%>
<%@ page language="java" pageEncoding="utf-8"%>

<div class="mini-splitter" style="width:100%; height: 100%;">
	<div size="200" showCollapseButton="true" cls="bordernone border-right">
		
		<div class="mini-toolbar"
			style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
			<span style="padding-left: 5px;">行业信息</span>
		</div>
		<div class="mini-fit">
			<ul id="industryTree" class="mini-tree"
				url="ccindustryinfo/listIndustryinfo.action" style="width: 100%;height: 100%;"
				showTreeIcon="true" textField="name" expandOnLoad="true"
				allowDrop="true" allowLeafDropIn="true" idField="uuid">
			</ul>
		</div>
	</div>
	<div showCollapseButton="true" style="width: 100%; height: 100%"
		cls="bordernone border-left">
			<div class="mini-toolbar"
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<a class="mini-button" style="display: none" iconCls="icon-add" onclick="addProduct()" productsid="framemainproductadd"
					plain="true">新增产品模版</a>
				<a class="mini-button" style="display: none" iconCls="icon-save" onclick="save()" productsid="framemainproductsave"
					plain="true" id="saveBtn" enabled="false">保存</a>
				<a class="mini-button" style="display: none" iconCls="icon-search" onclick="getHistory()" productsid="framemainproducthistory"
					plain="true">查询历史记录</a>
			</div>
			<div class="mini-fit">
			<ul id="productTree" class="mini-tree" style="width: 100%;height: 100%;"
				showTreeIcon="true" textField="name" expandOnLoad="true"
				allowDrop="true" allowLeafDropIn="true" idField="uuid"
				contextMenu="#treeMenu">
			</ul>
			<ul id="treeMenu" class="mini-contextmenu"
				onBeforeOpen="onBeforeOpen">
				<li name="add" iconCls="icon-add" onclick="onAddNode">
					新增产品
				</li>
				<li name="edit" iconCls="icon-edit" onclick="onEditNode">
					编辑产品
				</li>
				<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">
					删除产品
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">

       	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemainproductadd","framemainproductsave", "framemainproducthistory"]; 
			initPage(); 

</script>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/productslist/listProduct.js"
	type="text/javascript">
</script>
<script src="js/admin/productslist/deleteProduct.js"
	type="text/javascript">
</script>
<script type="text/javascript" src="js/admin/frame/cookie.js"></script>

