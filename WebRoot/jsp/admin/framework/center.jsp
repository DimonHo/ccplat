<%--

-日期：2014.9.16
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：Ron
修改人：
备注：组织架构管理中间菜单
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<body>
<div class="box fl" productsid="framemainorg" style="display: none">
	<div class="boxle"></div>
	<div class="boxmid">
		<a class="big-link more" data-reveal-id="myFrame"  url="jsp/admin/framelist/listFrame.jsp" style="cursor:pointer;">
		<div class="title">
			<span>组织架构</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
				<img alt="" src="images/module/m01.jpg"/>
			</div>
			<div class="text">
				<span>组织架构</span>
				<p>
					企业的管理的组织结构的详细模板
				</p>
				<a style="cursor:pointer;" url="jsp/admin/framelist/listFrame.jsp" class="big-link more" data-reveal-id="myFrame">查看详情</a>
			</div>
		</div>
	</div>
	<div class="boxri"></div>
</div>

<div class="box fl" style="display: none" productsid="framemainrole">
	
	<div class="boxmid">
		<a style="cursor:pointer;" class="big-link more" url="jsp/admin/rolelist/listRole.jsp"  data-reveal-id="myFrame">
		<div class="title">
			<span>岗位架构</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
			<img alt="" src="images/module/m02.jpg"/>
			</div>
			<div class="text">
				<span>岗位结构</span>
				<p>
					企业的管理的岗位结构的详细模板
				</p>
				<a style="cursor:pointer;" class="big-link more" url="jsp/admin/rolelist/listRole.jsp" data-reveal-id="myFrame">查看详情</a>
			</div>
		</div>
	</div>
	
</div>

<div class="box fl" style="display: none" productsid="framemainproduct">
	<div class="boxmid">
		<a style="cursor:pointer;" class="big-link more" url="jsp/admin/productslist/listproduct.jsp"  data-reveal-id="myFrame">
		<div class="title">
			<span>产品模板</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
			<img alt="" src="images/module/m05.jpg"/>
			</div>
			<div class="text">
				<span>产品模板</span>
				<p>
					企业的管理的产品模板的详细模板
				</p>
				<a style="cursor:pointer;" class="big-link more" url="jsp/admin/productslist/listproduct.jsp" data-reveal-id="myFrame">查看详情</a>
			</div>
		</div>
	</div>
</div>


<div class="box fl" style="display: none" productsid="framemaintrade">
	<div class="boxmid">
		<a style="cursor:pointer;" class="big-link more" url="jsp/admin/industryinfo/listIndustryinfo.jsp"  data-reveal-id="myFrame">
		<div class="title">
			<span>行业</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
			<img alt="" src="images/module/m03.jpg"/>
			</div>
			<div class="text">
				<span>行业</span>
				<p>
					企业的管理的所属行业的详细模板
				</p>
				<a style="cursor:pointer;" class="big-link more" url="jsp/admin/industryinfo/listIndustryinfo.jsp" data-reveal-id="myFrame">查看详情</a>
			</div>
		</div>
	</div>
</div>

<div class="box fl" style="display: none"  productsid="areamain">
	<div class="boxmid">
		<a style="cursor:pointer;" class="big-link more" url="jsp/admin/area/listarea.jsp"  data-reveal-id="myFrame">
		<div class="title">
			<span>地区</span>
			<strong></strong>
		</div>
		</a>
		<div class="boxcont">
			<div class="pic">
			<img alt="" src="images/module/m04.jpg"/>
			</div>
			<div class="text">
				<span>地区</span>
				<p>
					企业的管理的地区的详细模板
				</p>
				<a style="cursor:pointer;" class="big-link more" url="jsp/admin/area/listarea.jsp" data-reveal-id="myFrame">查看详情</a>
			</div>
		</div>
	</div>
</div>
</body>
<script type="text/javascript" src="js/admin/frame/cookie.js">
</script>
<script type="text/javascript">
	//初始化页面的button，控制显示与隐藏需要用到
			var pagefunction = ["framemainrole","framemainproduct", "framemaintrade","areamain","framemainorg"]; 
			initPage();
</script>



