<%--

-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：Ron
修改人：
备注：组织架构管理1级界面菜单
--%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="Tutitle">
	<ul>
		<li>
			<a url="jsp/admin/framelist/listFrame.jsp"  productsid="framemainorg" class="menuBtn" style="cursor:pointer;" style="display: none">
			<span class="hometu posi01"> </span> 组织架构
			</a>
		</li>
		<li>
			<a url="jsp/admin/rolelist/listRole.jsp" productsid="framemainrole" class="menuBtn" style="cursor:pointer;" style="display: none">
			<span class="hometu posi02"> </span> 岗位结构
			</a>
		</li>
		
		<li>
			<a url="jsp/admin/productslist/listproduct.jsp" productsid="framemainproduct" class="menuBtn" style="cursor:pointer;" style="display: none">
			<span class="hometu posi10"> </span> 产品模板
			</a>
		</li>
		
		<li>
			<a url="jsp/admin/industryinfo/listIndustryinfo.jsp" productsid="framemaintrade" class="menuBtn" style="cursor:pointer;" style="display: none">
			<span class="hometu posi09"> </span> 行业
			</a>
		</li>
		
		<li>
			<a url="jsp/admin/area/listarea.jsp" productsid="areamain" class="menuBtn" style="cursor:pointer;" style="display: none">
			<span class="hometu posi08"> </span> 地区
			</a>
		</li>
		
	</ul>
</div>
<script type="text/javascript" src="js/jquery/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="js/admin/frame/cookie.js">
</script>
<script type="text/javascript">
//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemainorg","framemainrole", "framemainproduct","framemaintrade","areamain"]; 
			initPage();

	$(".Tutitle ul").width($(".Tutitle li").length*100); 
//是否有数据修改，进行
$(".menuBtn").click(function(e){
	//获取基础路径
	var local = window.location;  
	var contextPath = local.pathname.split("/")[1];  
	var basePath = local.protocol+"//"+local.host+"/"+contextPath+"/";
	
	var url=e.currentTarget.getAttribute("url");
	if("undefined" == typeof saveBtn){
		window.location.href=basePath+url; 
		return;
	}
	if(saveBtn.enabled&&saveBtn.style!='display: none;'){
		mini.showMessageBox({
		 	title: "提示",    
		    message: "数据已修改，是否保存",
		    buttons: ["ok", "no", "cancel"],    
		    iconCls: "mini-messagebox-question",
		    callback: function(action){
				if(action=="no"){
					window.location.href=basePath+url;
				}else if(action=="ok"){
					//执行保存方法，返回是否可以离开当前页面
					var canleave=saveResult(basePath+url);
					if(canleave){
						window.location.href=basePath+url;
					}
				}
		    }
		});
	}else{
		window.location.href=basePath+url;
	}
});

</script>