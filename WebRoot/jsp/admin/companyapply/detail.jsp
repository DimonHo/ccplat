
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
    String path = request.getContextPath();
    String basePath =
        request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="remark-type" content="text/html; charset=UTF-8" />
		<title>树形联动</title>

		<style type="text/css">
body {
	margin: 0px;
	padding: 0;
	border: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	overflow-x: hidden;
}

#center {
	background: #fff;
	border: 0px;
}

#grid1 .mini-panel-border {
	border: 0px;
	border-right: 1px solid #999 !important;
}
</style>
		<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
	</head>
	<body id="okinfo">
		<form id="detail" setEnabled="false">
			<fieldset
				style="border: solid 0px #aaa; padding: 3px; width: 95%; margin: 0 auto; border-top: solid 1px #aaa;">
				<legend>
					基本信息
				</legend>
				<div id="" class="pad5">
					<table height="300" width="480" style="margin-left: 30px;">

						<td  style="width: 90px;">
							营业号码
						</td>
						<td>
							<input type="text" name="license" id="license" enabled="false"
								class="mini-textbox" />
						</td>
						</tr>
						<td>
							公司名称
						</td>
						<td>
							<input type="text" name="name" id="name" class="mini-textbox" enabled="false"/>
						</td>
						</tr>
						<td>
							公司地址
						</td>
						<td>
							<input type="text" name="street" id="street" class="mini-textbox" enabled="false"/>
						</td>
						</tr>
						<td>
							联系人
						</td>
						<td>
							<input type="text" name="contacts" id="contacts" enabled="false"
								class="mini-textbox" />
						</td>
						</tr>
						<td>
							联系人电话
						</td>
						<td>
							<input type="text" name="phone" id="phone" class="mini-textbox" enabled="false"/>
						</td>
						</tr>
						<td>
							邮箱
						</td>
						<td>
							<input type="text" idfield="email" name="email" id="email" enabled="false"
								vtype="email;rangeLength:5,20;" class="mini-textbox" />
						</td>
						</tr>
						<td>
							详细地址
						</td>
						<td>
							<input type="text" name="address" id="address" enabled="false"
								class="mini-textbox" />
							
						</td>
						</tr>
						
						<td>
							所属地区
						</td>
						<td>
								<input id="treeselect" class="mini-treeselect" enabled="false"
								url="ccarea/list.action" name="areauuid"
								textField="name" idField="id" parentField="pid" value="control"
								valueFromSelect="false" showFolderCheckBox="false"
								checkRecursive="false"  multiSelect="true"
								expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
								value="5,6" Text="请选择地区"/>		
						</td>
						</tr>
						<td>
							所属行业
						</td>
						<td>

							<input id="treeselect1" class="mini-treeselect" enabled="false"
								url="ccindustryinfo/getIndustryInfo.action" name="industryuuid"
								textField="name" idField="id" parentField="pid" value="control"
								valueFromSelect="false" showFolderCheckBox="false"
								checkRecursive="false"  multiSelect="true"
								expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
								value="5,6" Text="请选择行业"/>
							<input type="text" visible="false" name="id" id="id" enabled="false"
								class="mini-textbox" />
							</td>
								
							</tr>
						
					</table>
				</div>
			</fieldset>
						<jsp:include page="../../../jsp/register/auditdiv.jsp"></jsp:include>		
			
		</form>
	</body>

	<script src="js/admin/companyapply/detail.js" type="text/javascript">
</script>

</html>
