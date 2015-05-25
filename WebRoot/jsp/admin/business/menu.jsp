<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="Tutitle">
	<ul>
		<li>
			<a class="cphand" href="jsp/admin/companyapply/listcompany.jsp" style="display: none" productsid="enterprisemainregister" >
			<span class="hometu posi04"> </span> 企业注册
			</a>
		</li>
		<li>
			<a class="cphand" href="jsp/admin/company/listcompany.jsp" style="display: none" productsid="enterprisemaininit" >
			<span class="hometu posi08"> </span> 企业初始化
			</a>
		</li>
		<!--<li>
			<a class="cphand" href="jsp/admin/companycc/listcompanycc.jsp">
			<span class="hometu posi03"> </span> 账号分配
			</a>
		</li>
		--><li>
			<a class="cphand" href="jsp/admin/productslist/productlist.jsp" style="display: none" productsid="enterprisemainproup" >
			<span class="hometu posi03"> </span> 产品维护
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
			var pagefunction = ["enterprisemainproup","enterprisemaininit", "enterprisemainregister"]; 
			initPage();
</script>
