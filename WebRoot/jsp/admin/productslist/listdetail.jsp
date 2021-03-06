<%--
-文件名：listdetail.jsp
-日期：2014.9.24
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：查询产品历史记录详细信息
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<base href="<%=basePath%>"/>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="pragma" content="no-cache" />
		<meta http-equiv="cache-control" content="no-cache" />
		<meta http-equiv="expires" content="0" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="This is my page" />
		<title>详细历史记录</title>
		<style type="text/css">
			body {
				margin: 8px;
				padding: 0;
				border: 0;
				width: 100%;
				height: 100%;
				overflow: hidden;
			}
		</style>
	</head>
	<body>
		<div id="datagrid" class="mini-datagrid" style="width:98%;height:89.5%;" showPager="false">
	    	<div property="columns">
		        <div name="type" field="type" width="50">类型</div>
		        <div name="code" field="data.code" width="100">产品编码</div>
		        <div name="name" field="data.name" width="100">产品名称</div>
		        <div name="remark" field="data.remark" width="100">产品备注</div>
		        <div name="url" field="data.url" width="200">产品url</div>
		        <div name="open" field="data.open" width="50" renderer="getOpen">打开方式</div>
	    	</div>
		</div>
		<div id="detailpage" class="mini-pager" style="width:97.7%;background:#f0f3f7;border:solid 1px #ccc;" 
		     onpagechanged="onPageChanged" sizeList="[5,16,20,100]" pageSize="16"
		    showPageSize="true" showPageIndex="true" showPageInfo="true">        
		</div>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="<%=basePath%>js/admin/industryinfo/listdetail.js" type="text/javascript"></script>
		<script type="text/javascript">
			function getOpen(e){
				var openData={};
				openData[0]='不打开';
				openData[1]='打开浏览器';
				openData[2]='内嵌';
				openData[3]='打开可执行文件';
				if(e.value!=null){
					return openData[e.value];
				}
			}
		</script>
	</body>
</html>