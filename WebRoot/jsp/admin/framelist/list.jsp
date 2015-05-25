<%--
-文件名：list.jsp
-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询组织架构列表
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<div class="mini-splitter" style="width:100%; height: 100%;">
	<div size="200" showCollapseButton="true" cls="bordernone border-right">
		
			<div class="mini-toolbar"
				style="padding: 2px; border-top: 0; border-left: 0; border-right: 0;">
				<span style="padding-left: 5px;">行业信息</span>
			</div>
		<div class="mini-fit">
			<ul id="industryTree" class="mini-tree" imgPath="images/admin/li04.png" 
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
				<a class="mini-button"  iconCls="icon-add" onclick="addFrame()" productsid="framemainorgadd" style="display:none"
					plain="true">新增组织架构模版</a>
				<a  class="mini-button" id="saveBtn"  iconCls="icon-save" onclick="save()" productsid="framemainorgsave" style="display:none"
					plain="true" enabled="false">保存</a>
				<a class="mini-button"  iconCls="icon-search" onclick="getHistory()" productsid="framemainorghistory" style="display:none"
					plain="true">历史记录查询</a>
			</div>
		<div class="mini-fit">
			<ul id="frameTree" class="mini-tree" style="width: 100%;height: 100%;"
				showTreeIcon="true" textField="name" expandOnLoad="true"
				allowDrop="true" allowLeafDropIn="true" idField="uuid"
				contextMenu="#treeMenu">
			</ul>
			<ul id="treeMenu" class="mini-contextmenu"
				onBeforeOpen="onBeforeOpen">
				<li name="add" iconCls="icon-add" onclick="onAddNode">
					新增组织架构
				</li>
				<li name="edit" iconCls="icon-edit" onclick="onEditNode">
					编辑组织架构
				</li>
				<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">
					删除组织架构
				</li>
			</ul>
		</div>
	</div>
</div>
<script type="text/javascript">
			//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemainorgadd","framemainorgsave","framemainorghistory"];
			initPage();
</script>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/framelist/listFrame.js" type="text/javascript">
</script>
<script src="js/admin/framelist/deleteFrame.js" type="text/javascript">
</script>
<script src="js/admin/frame/cookie.js" type="text/javascript">
</script>
