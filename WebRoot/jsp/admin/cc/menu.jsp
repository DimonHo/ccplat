<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="Tutitle">
	<ul>
		<li>
			<a href="jsp/admin/ccbatch/listbatch.jsp" productsid="countmaincreate" style="display: none">
			<span class="hometu posi05"> </span> 账号生成
			</a>
		</li>
		
		<li>
			<a href="jsp/admin/ccuser/listuser.jsp" productsid="countmainmanager" style="display: none">
			<span class="hometu posi07"> </span> 账号管理
			</a>
		</li>
	</ul>
</div>

<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/admin/frame/cookie.js">
</script>
<script type="text/javascript">
	$(".Tutitle ul").width($(".Tutitle li").length*100); 
	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["countmainmanager","countmaincreate"]; 
			initPage();
</script>
