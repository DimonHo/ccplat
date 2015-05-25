
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
	</head>
	<body id="okinfo">
		<form id="form1" method="post">
			        <fieldset style="border:solid 1px #aaa;padding:3px;">

				<legend>
					基本信息
				</legend>
				<div  style="padding: 5px;height:300px;">
					<table style="table-layout:fixed;margin-left: 30px;">
				<!-- 	<table height="520px" width="562px" style="margin-left: 30px;"> -->
				 <tr>
						<td  style="width: 70px;">
							营业号码
						</td>
						<td style="width: 150px;">
							<input type="text" name="license" id="license" required="true"
								class="mini-textbox" />
						</td>
						<td style="width: 70px;">
							联系人
						</td>
						<td>
							<input type="text" name="contacts" id="contacts" required="true"
								class="mini-textbox" />
						</td>
						
						
						</tr>
						 <tr>
						<td style="width: 70px;">
						     公司名称
						</td>
						<td style="width: 150px;">
							<input type="text" name="name" id="name" class="mini-textbox" required="true"/>
						</td>
						<td style="width: 70px;">
							联系人电话
						</td>
						<td style="width: 150px;">
							<input type="text" vtype="int" name="phone" id="phone" class="mini-textbox" required="true" />
						</td>
						</tr>
						 <tr>
						<td style="width: 70px;">
							公司地址
						</td>
						<td style="width: 150px;">
							<input type="text" name="street" id="street" class="mini-textbox" required="true"/>
						</td>
						<td style="width: 70px;">
							邮箱
						</td>
						<td style="width: 150px;">
							<input type="text" idfield="email" name="email" id="email"
								vtype="email;rangeLength:5,20;" class="mini-textbox" />
						</td>
						</tr>
						 <tr>
						<td style="width: 70px;">
							端口
						</td>
						<td style="width: 150px;">
						<input type="text" name="port" id="port" class="mini-textbox" required="true"/>
						</td>
						<td style="width: 70px;">
							IP
						</td>
						<td style="width: 150px;">
							<input type="text" name="ipAddress" id="ipAddress" class="mini-textbox" required="true"/>
						</td>	
						</tr>	
						 <tr>				
						<td style="width: 70px;">
							所属地区
						</td>
						<td style="width: 150px;">
								<input id="treeselect" class="mini-treeselect"
								url="ccarea/list.action" name="areauuid"
								textField="name" idField="id" parentField="pid" value="control"
								valueFromSelect="false" showRadioButton="true" showFolderCheckBox="false"
								checkRecursive="false"
								expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
								value="5,6" Text="请选择地区"/>		
						</td>
						<td style="width: 70px;">
							详细地址
						</td>
						
						<td style="width: 150px;">
							<input type="text" name="address" id="address"
								class="mini-textbox" />
							
						</td>
						</tr>
						 <tr>
						<td>
							所属行业
						</td>
						<td>

							<input id="treeselect1" class="mini-treeselect"
								url="ccindustryinfo/getIndustryInfo.action" name="industryuuid"
								textField="name" idField="id" parentField="pid" value="control"
								valueFromSelect="false" showRadioButton="true"showFolderCheckBox="true"
								checkRecursive="false"  
								expandOnLoad="true" showClose="true" oncloseclick="onCloseClick"
								value="5,6" Text="请选择行业"/>
							<input type="text" visible="false" name="id" id="id"
								class="mini-textbox" />
							</td>
								
							</tr>
							 <tr>
						<td>
							备注
						</td>
						<td colspan="3">
							<input name="remark" class="mini-textarea" valueField="remark" textField="remark"  style="width:350px;"/>		
						</td>
								
						</tr>
						
					</table>
				</div>
			</fieldset>
        <div style="margin-left:100px;padding:20px;">               
				
				<a class="mini-button mini-savebtn mr20" id="OK" dir="ltr" onclick="onOk"  				>保存</a>
				<a class="mini-button mini-dropbtn" onclick="onCancel" >取消</a>
			</div>
		</form>
		
	</body>
	
		<script src="js/ui/scripts/boot.js" type="text/javascript">
</script>
	<script src="js/admin/companyapply/edit.js" type="text/javascript">
</script>

</html>

