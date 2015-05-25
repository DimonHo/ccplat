<%--

-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：Ron
修改人：
备注：账号管理中间菜单
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div class="box fl">
 <div class="boxmid" productsid="countmaincreate" style="display: none">
		<a style="cursor:pointer;" class="big-link more" data-reveal-id="myFrame" url="jsp/admin/ccbatch/listbatch.jsp">
		<div class="title">
			<span>账号生成</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont" >
			<div class="pic">
			<img alt="" src="images/module/m08.jpg"/>
			</div>
			<div class="text">
				<span>账号生成</span>
				<p>
					企业的管理的账号生成的操作
				</p>
				<a style="cursor:pointer;" class="big-link more" data-reveal-id="myFrame" url="jsp/admin/ccbatch/listbatch.jsp">查看详情</a>
			</div>
		</div>
	</div>
</div>

<div class="box fl" productsid="countmainmanager" style="display: none">
	<div class="boxmid">
		<a style="cursor:pointer;" class="big-link more" data-reveal-id="myFrame" url="jsp/admin/ccuser/listuser.jsp">
		<div class="title">
			<span>账号管理</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
			<img alt="" src="images/module/m09.jpg"/>
			</div>
			<div class="text">
				<span>账号管理</span>
				<p>
					企业的管理的账号的详细启动，停用的控制
				</p>
				<a style="cursor:pointer;" class="big-link more" data-reveal-id="myFrame" url="jsp/admin/ccuser/listuser.jsp">查看详情</a>
			</div>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/admin/frame/cookie.js"></script>
<script type="text/javascript">
		//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["countmaincreate","countmainmanager"]; 
			initPage();
</script>
