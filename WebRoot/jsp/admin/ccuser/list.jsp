<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<div class="mini-toolbar"
	style="padding: 2px;">

	帐号：从
	<input id="ccnostart" class="mini-textbox" emptyText="请输入开始号码" />
	到
	<input id="ccnoend" class="mini-textbox" emptyText="请输入结束号码" />
	<span class="separator"></span>状态：
	<select id="state" style="margin-right: 25px;">
		<option value="">全部</option>
		<option value="1">启用</option>
		<option value="2">停用</option>
	</select>
	<span class="separator"></span>
	<input id="inputname" class="mini-textbox" emptyText="请输入姓名或者cc号" />
	<a class="mini-button" iconCls="icon-search" plain="true" onclick="seach()" productsid="countmainmanagersearch" style="display: none">查询</a>
	<a class="mini-button" iconCls="icon-add" plain="true" onclick="start()" productsid="countmainmanageropen" style="display: none">启用</a>
	<a class="mini-button" iconCls="icon-remove" plain="true" onclick="stop()" productsid="countmainmanageroff" style="display: none">停用</a>
</div>
<div class="mini-fit">
	<div id="grid1" class="mini-datagrid"
		style="width: 100%; height: 100%;" borderStyle="border:0;"
		url="user/list.action" allowResize="true"  sizeList="[5,10,20,50]"
		multiSelect="true">
		<div property="columns">
			<div type="checkcolumn"></div>
			<div field="id" visible="false" width="120" headerAlign="center"
				allowSort="true">
				
			</div>
			<div field="realname" width="120" headerAlign="center"
				allowSort="true">
				姓名
			</div>
			<div field="ccno" width="100" headerAlign="center"
				allowSort="true">
				cc号
			</div>
			<div field="state" width="100" allowSort="true" align="center"
				headerAlign="center" renderer="onStateRenderer">
				操作状态
			</div>
			<div field="usestate" width="100" allowSort="true" align="center"
				headerAlign="center" renderer="onUseStateRenderer">
				使用状态
			</div>
		</div>
	</div>
</div>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
var pagefunction = ["countmainmanagersearch","countmainmanageropen", "countmainmanageroff"]; 
initPage();
	
</script>
<script src="js/ui/scripts/boot.js" type="text/javascript"></script>
<script type="text/javascript" src="js/admin/ccuser/list.js"></script>
<script type="text/javascript" src="js/admin/frame/cookie.js"></script>

