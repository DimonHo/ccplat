<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!--栏目左侧内容-->
<div class="bglist">
	<ul>
		<li class="first h75" productsid="framemain" id="framemain" style="display: none">
			<span class="ic01"> </span>
			<span class="title">
				<a href="jsp/admin/framework/index.jsp">架构管理</a>
			</span>
		</li>
		<li class="h75 " productsid="enterprisemain" id="enterprisemain" style="display: none">
			<span class="ic02"> </span>
			<span class="title">
				<a href="jsp/admin/business/index.jsp">企业管理</a>
			</span>
		</li>
		<li class="h75" productsid="countmain" id="countmain" style="display: none">
			<span class="ic03"> </span>
			<span class="title">
				<a href="jsp/admin/cc/index.jsp">账号管理</a>
			</span>
		</li>
		<li class="h75" productsid="systemmain" id="systemmain" style="display: none">
			<span class="ic04"> </span>
			<span class="title">
				<a href="jsp/admin/permission/power.jsp">系统维护</a>
			</span>
		</li>
		<li class="h75" productsid="audit" id="audit" style="display: none">
			<span class="ic04"> </span>
			<span class="title">
				<a href="jsp/admin/audit/index.jsp">待办事项</a>
			</span>
		</li>
	</ul>
</div>
<script type="text/javascript" src="js/admin/frame/cookie.js"></script>
<script type="text/javascript">
		//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemain","enterprisemain", "countmain","systemmain"]; 
			initPage();
</script>
