<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<div class="mini-toolbar" style="padding: 2px; border-bottom: 0;">
	<table style="width: 100%;">
		<tr>
			<td style="width: 100%;">
				<a class="mini-button" iconCls="icon-add" plain="true" style="display: none" productsid="enterprisemainregisterapply"
					onclick="add();">填写申请</a>
				<span class="separator"></span>
					
				<a class="mini-button" iconCls="icon-edit" plain="true" style="display: none" productsid="enterprisemainregisterupdate"
					onclick="edit()">修改</a>
				<span class="separator"></span>

				<a class="mini-button" iconCls="icon-edit" plain="true" style="display: none" productsid="enterprisemainregisteredithistory"
					onclick="addhistory()">编辑历史记录</a>
				
				<span class="separator"></span>
				<a class="mini-button" iconCls="icon-save" plain="true" style="display: none" productsid="companyapplysubmit"
					onclick="apply();">提交申请</a>
					
				<!--<span class="separator"></span>
				<a class="mini-button" iconCls="icon-save" plain="true" productsid="enterprisemainregistersubmit"
					onclick="applyadmin();">提交申请admin</a>
					
				--><!--<span class="separator"></span>
				<a class="mini-button" iconCls="icon-save" plain="true" productsid="enterprisemainregistercheck"
					onclick="checkadmin();">审核admin</a>
				--><span class="separator"></span>
				<a class="mini-button" iconCls="icon-save" plain="true" style="display: none" productsid="companyapplycheck"
					onclick="check();">审核</a>

				<select id="state" style="margin-right: 25px;">
							<option value="">全部</option>
							<option value="exists">未提交</option>
							<option value="0">已申请</option>
							<option value="1">已审核</option>
				</select>
						<input id="inputname" class="mini-textbox" emptyText="请输入公司名称或者联系人" style="width:160px;"/>
						<a class="mini-button" iconCls="icon-search" plain="true" onclick="seach()" productsid="enterprisemainregistersearch" style="display: none">查询</a>
				<a class="mini-button" iconCls="icon-search" plain="true"  style="display: none" productsid="enterprisemainregisterhistory"
				onclick="history();">历史记录</a>	
			</td>

		</tr>
	</table>
</div>
<!--撑满页面-->
<div class="mini-fit">
	<div id="datagrid1" class="mini-datagrid"
		style="width: 100%; height: 100%;" url="companyapply/list.action" onrowdblclick="edit()" 
		multiSelect="true" idField="id" sizeList="[5,10,20,50]" pageSize="10">
		<div property="columns">
			<div type="checkcolumn"></div>
			<div field="id" visible="false" width="120" headerAlign="center"
				allowSort="true">
				公司ID
			</div>
			
			<div field="name" width="120" headerAlign="center" allowSort="true">
				公司名称
			</div>
			<div field="street" width="120" headerAlign="center" allowSort="true">
				公司地址
			</div>
			<div field="contacts" width="120" headerAlign="center"
				allowSort="true">
				联系人
			</div>
			<div field="phone" width="120" headerAlign="center" allowSort="true">
				联系电话
			</div>
			<div field="address" width="120" headerAlign="center"
				renderer="onAddress" allowSort="true">
				详细地址
			</div>
			<div field="areaName" width="120" headerAlign="center"
				allowSort="true">
				所属地区
			</div>
			<div field="industryName" width="120" headerAlign="center"
				allowSort="true">
				所属行业
			</div>
			<div field="state" width="100" headerAlign="center"
				renderer="onState" allowSort="true">
				审核状态
			</div>
			<div field="createtime" width="100" headerAlign="center"
				renderer="onCreatetime" dateFormat="yyyy-MM-dd" allowSort="true">
				创建日期
			</div>
			
		</div>
	</div>
</div>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
	var pagefunction = ["enterprisemainregisterupdate","enterprisemainregisterapply", "enterprisemainregisteredithistory","companyapplysubmit","companyapplycheck","enterprisemainregistersearch","enterprisemainregisterhistory"]; 
	initPage();
</script>
<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
<script src="js/admin/companyapply/list.js" type="text/javascript">
</script>
<script src="js/admin/frame/cookie.js" type="text/javascript">
</script>
