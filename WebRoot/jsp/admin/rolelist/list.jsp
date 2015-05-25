<%--
-文件名：listRole.jsp
-日期：2014.9.17
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询岗位列表
--%>
<%@ page language="java" pageEncoding="utf-8"%>
	<div class="mini-toolbar" style="border-bottom: 0; padding: 0px;">
		<table style="width: 100%;">
			<tr>
				<td style="width: 100%;">
					<a class="mini-button" iconCls="icon-add" style="display: none" onclick="addRole()" productsid="framemainroleadd"
						plain="true" tooltip="增加模版">增加模版</a>
					<a class="mini-button" iconCls="icon-save" style="display: none" onclick="save()" productsid="framemainrolesave"
						plain="true" tooltip="保存" id="saveBtn" enabled="false">保存</a>
					<a class="mini-button" iconCls="icon-search" style="display: none" onclick="getHistory()" productsid="framemainrolehistory"
						plain="true" tooltip="历史记录">历史记录查询</a>
				</td>
			</tr>
		</table>
	</div>
<div class="mini-fit">
<div id="treegrid" class="mini-treegrid"
	style="width: 100%; height: 100%;" url="ccrolelist/listRole.action"
	showTreeIcon="true" nodesField="position" expandOnLoad="true"
	treeColumn="name" idField="uuid" resultAsTree="true"
	contextMenu="#treeMenu">
	<div property="columns">
		<div name="name" field="name" width="200">
			名称
		</div>
		<div field="remark" width="100">
			备注
		</div>
		<div field="lev" width="100">
			级别
		</div>
	</div>
</div>
<ul id="treeMenu" class="mini-contextmenu" onBeforeOpen="onBeforeOpen">
	<li name="add" iconCls="icon-add" onclick="onAddNode">
		新增岗位
	</li>
	<li name="edit" iconCls="icon-edit" onclick="onEditNode">
		编辑岗位
	</li>
	<li name="remove" iconCls="icon-remove" onclick="onRemoveNode">
		删除岗位
	</li>
</ul>
</div>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemainroleadd","framemainrolesave", "framemainrolehistory"]; 
			initPage();
</script>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/rolelist/listRole.js" type="text/javascript">
</script>
<script src="js/admin/rolelist/deleteRole.js" type="text/javascript">
</script>
<script type="text/javascript" src="js/admin/frame/cookie.js">
</script>
