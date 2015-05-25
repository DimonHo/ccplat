<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<base href="<%=basePath%>" />
	<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
	<title>树形联动</title>
	
	<style type="text/css">
	body {
		margin: 0px;
		padding: 0;
		border: 0;
		width: 100%;
		height: 98%;
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
	.loading{background:url('js/ui/css/images/loading.gif') no-repeat center center;}
	</style>
	</head>
	<body>
		<form id="form1" method="post">
			<fieldset
				style="border: solid 1px #aaa; padding: 3px; width: 98%; margin: 0 auto;">
				<legend>
					基本信息<input id="companyno" name="companyno" class="mini-hidden" value=""/>
				</legend>
				<div style="padding:5px;">
					<table height="430">
						<tr>
							<td style="width:90px;">
								企业号码位数:
							</td>
							<td>
									<input type="radio" value="2" name="s" id="s4" class="fl" checked="checked"/>
										<label for="s4" class="mini-tree-nodeshow fl">
											4-5位数
										</label>
									<input type="radio" value="3" name="s" id="s6" class="fl"/>
										<label for="s6" class="mini-tree-nodeshow fl">
											6-7位数
										</label>
							</td>
							<td style="width: 90px;">
								分配数量:
							</td>
							<td>
								<input type="text" id="suliang" class="inputText" value="100"
									style="width: 60px;"/>
									&nbsp;&nbsp;号码起始位:&nbsp;&nbsp;
								<input type="text" id="st"
										value="1" class="inputText" style="width: 60px;"/>
							</td>
						</tr>
						<tr>
							<td>
								靓号类型:
							</td>
							<td colspan=3>
								<input type="hidden" id="ctype"/>
								<a id="cc_all" href="javascript:;">全部生成</a>&nbsp;|&nbsp;
								<a id="aabbcc" class="mini-button bordernone" enabled="false"
									onclick="aabbcc('aabbcc')">AABBCC</a>&nbsp;|&nbsp;
								<a class="mini-button bordernone" id="aaabbb"
									onclick="aabbcc('aaabbb')" enabled="false">AAABBB</a>&nbsp;|&nbsp;
								<a class="mini-button bordernone" onclick="aabbcc('ababab')"
									id="ababab" enabled="false">ABABAB</a>&nbsp;|&nbsp;
								<a id="abcabc" class="mini-button bordernone"
									onclick="aabbcc('abcabc')" enabled="false">ABCABC</a>&nbsp;|&nbsp;
								<a class="mini-button bordernone" onclick="aabbcc('3a')">3A</a>&nbsp;|&nbsp;
								<a class="mini-button bordernone" onclick="aabbcc('abc')">3顺</a>&nbsp;|&nbsp;
								<a class="mini-button bordernone" onclick="aabbcc('4a')">4A</a>&nbsp;|&nbsp;
								<a onclick="aabbcc('abcd')" class="mini-button bordernone">4顺</a>
							</td>
						</tr>
						<tr>
							<td style="width: 90px;">
								企业号码段:
							</td>
							<td colspan=3>
								<!-- CC号码 -->
								<table id="cc_auto">
									<tr>
										<td>
											<div style="color: gray">
												请选择CC账号
											</div>
											<div id="listbox1" class="mini-listbox" style="width: 200px; height: 220px;"
							                    textField="ccno" valueField="id" showCheckBox="true" multiSelect="true"
							                    url="ccnumber/notAllot.action" onbeforeload="onBeforeCcLoad">	
												<div property="columns">
													<div header="序号" type="indexcolumn"></div>
													<div header="CC账号" field="ccno"></div>
												</div>
											</div>
										</td>
										<td style="width: 120px; text-align: center;">
											<input type="button" id="button" value="添加 >>"
												onclick="add()" style="width: 70px;" />
											<br />
											<p></p>
											<br />
											<input type="button" value="删除 &lt;&lt;" onclick="removes()"
												style="width: 70px;" />
											<br/>
										</td>
										<td>
											<div>
												已选择CC账号
											</div>
											<div id="listbox2" class="mini-listbox"
												style="width: 200px; height: 220px; background: #FFFFE6;"
												showCheckBox="true" multiSelect="true">
												<div property="columns">
													<div header="序号" type="indexcolumn"></div>
													<div header="CC账号" idField="ccno" field="ccno"></div>
												</div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</form>
		<div class="ynnav mini-toolbar">
			<a class="mini-button" onclick="onOk" style="width: 60px; margin-right: 20px;">确定</a>
			<a class="mini-button" onclick="onCancel" style="width: 60px;">取消</a>
		</div>
		
<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="<%=basePath%>js/admin/companycc/add.js" type="text/javascript"></script>
</body>
</html>