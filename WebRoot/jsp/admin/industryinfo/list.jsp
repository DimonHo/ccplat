<%--
-文件名：listIndustryinfo.jsp
-日期：2014.9.15
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询行业信息列表
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<div class="mini-splitter" style="width:100%; height: 100%;">
	<div size="400" showCollapseButton="true" cls="bordernone border-right">
			<div class="mini-toolbar"
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<a class="mini-button"  iconCls="icon-add" productsid="framemaintradecreate" style="display:none"
					onclick="addIndustryinfo()" plain="true">创建行业</a>
				<a class="mini-button"  iconCls="icon-save" onclick="save()" productsid="framemaintradesave" style="display:none"
					plain="true" id="saveBtn" enabled="false">保存</a>
				<a class="mini-button"  iconCls="icon-search" onclick="getHistory()" productsid="framemaintradehistory" style="display:none"
					plain="true">历史记录查询</a>
			</div>
		<div class="mini-fit">
			<ul id="industryTree" class="mini-tree"
				url="ccindustryinfo/listIndustryinfo.action" style="width: 100%;height: 100%;"
				showTreeIcon="true" textField="name" expandOnLoad="true"
				idField="uuid" contextMenu="#treeMenu">
			</ul>
			<!--右健菜单-->

			<ul id="treeMenu" class="mini-contextmenu"
				onBeforeOpen="onBeforeOpen">
				<li name="add" iconCls="icon-add" onclick="onAddNode">
					新增行业
				</li>
				<li name="edit" iconCls="icon-edit" onclick="onEditNode">
					编辑行业
				</li>
				<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">
					删除行业
				</li>
			</ul>
		</div>
	</div>
	<div showCollapseButton="true" style="width: 100%; height: 100%"
		cls="bordernone border-left">
		<div class="mini-toolbar"
			style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
			<span style="padding-left: 5px;">行业说明： </span>
		</div>
		<div class="mini-fit">
			<input name="textcontent" id="textcontent" class="mini-textarea"
				cls="bordernone" readonly="readonly"
				style="width: 100%; height: 100%" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemaintradesave","framemaintradecreate", "framemaintradehistory"]; 
			initPage();
</script>
<script src="js/admin/frame/cookie.js" type="text/javascript"> </script>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/industryinfo/listIndustryinfo.js"
	type="text/javascript">
</script>
<script src="js/admin/industryinfo/deleteIndustryinfo.js"
	type="text/javascript">
</script>

