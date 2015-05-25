<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!--表格-->
<div class="mini-splitter" vertical="true" style="width: 100%; height: 97%;">
	<div style="width: 100%; height: 100%;" showCollapseButton="true">
		<div class="mini-toolbar"
			style="padding: 0px; border-top: 0; border-left: 0; border-right: 0;">
			<table style="width: 100%;">
				<tr>
					<td style="width: 100%;">
						<a class="mini-button" iconCls="icon-add" plain="true"
							onclick="addCC()">开放CC号码</a>
					</td>
				</tr>
			</table>
		</div>
		<div class="mini-fit">
			<div id="ccbatch" class="mini-datagrid"
				style="width: 100%; height: 100%;" borderStyle="border:0;"
				url="ccbatch/list.action" showFilterRow="false"
				allowCellSelect="true" allowResize="true" multiSelect="true"
				pageSize="10" allowCellEdit="true"
				onselectionchanged="onSelectionChanged">
				<div property="columns">
					<div type="indexcolumn">序号</div>
					<div name="start" field="start" width="100"
						allowSort="true">
						开始号码
					</div>
					<div name="end" field="end" width="100"
						allowSort="true">
						结束号码
					</div>
					<div field="remark" width="100" allowSort="true">
						开放说明
					</div>
					<div name="operator" renderer="onOperator" field="operator" width="100" allowSort="true">
						操作人
					</div>
					<div name="createtime" field="createtime" renderer="onCreatetime" width="100" allowSort="true" >
						创建时间
					</div>
				</div>
			</div>
		</div>
	</div>
	<div style="width: 100%; height: 100%;" showCollapseButton="true">
		<div class="mini-fit">
			<div id="ccnumber" class="mini-datagrid"
				style="width: 100%; height: 100%;" borderStyle="border:0;"
				url="ccnumber/list.action" showFilterRow="false"
				allowCellSelect="true" allowResize="true" multiSelect="true"
				pageSize="10" allowCellEdit="true">
				<div property="columns">
					<div width="20" type="indexcolumn">序号
					</div>
					
					<div field="ccno" name="ccno" width="100" allowSort="true">
						cc帐号
					</div>
					
					<div name="type" field="type" width="100" renderer="onType" allowSort="true" >
						类型
					</div>
					<div name="state" field="state" width="100" renderer="onState" allowSort="true">
						状态
					</div>
					<div name="level" field="level" width="100" renderer="onSpecial" allowSort="true">
						号码类型
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>js/admin/ccbatch/list.js" type="text/javascript"></script>

