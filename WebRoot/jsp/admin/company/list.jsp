<%--
-文件名：list.jsp
-日期：2014.9.18
-版权声明：Copyright (c) 2014 www.thiscc.com All rights reserved.
创建人：zzf
修改人：
备注：展示需要初始化组织架构和岗位的企业列表
--%>
<%@ page language="java" pageEncoding="utf-8"%>
<div style="width:100%;height:7%;">
    <div class="mini-toolbar" style="border-bottom:0;padding:0px;height:100%;">
        <table style="width:100%;height:100%;">
            <tr>
                <td style="width:100%;height:100%;padding-left:15px;">
                	当前行业：<input id="industryinfo" class="mini-treeselect" url="ccindustryinfo/listIndustryinfo.action" 
                		multiSelect="false"  valueFromSelect="false" resultAsTree="true"
	       				 textField="name" valueField="uuid" allowInput="true" showClose="true" oncloseclick="onCloseClick"
	        			showRadioButton="true" showFolderCheckBox="true" />&nbsp&nbsp&nbsp&nbsp
	          		公司名称：<input id="company" class="mini-textbox" emptyText="请输入公司名称"/>
         			<a class="mini-button" iconCls="icon-search" onclick="search()">查询</a>
         			<a class="mini-button" iconCls="icon-edit" onclick="modify()">修改</a>
                </td>
            </tr>
        </table>           
    </div>
</div>
<div id="datagrid" class="mini-datagrid" style="width:100%;height:93%;" allowResize="true" pageSize="20"
    url="cccompany/list.action" multiSelect="true" idField="companyno" sortField="companyno" sortOrder="asc">
    <div property="columns">
    	<div type="indexcolumn"></div>
    	<div type="checkcolumn" ></div>     
        <div field="companyno" width="120" headerAlign="center" allowSort="true" renderer="onShow">公司编码</div>
        <div field="name" width="120" headerAlign="center" allowSort="true">公司名称</div>
        <div field="contacts" width="50" headerAlign="center" allowSort="true">联系人</div>
        <div field="areaname" width="60" headerAlign="center" allowSort="true">地区</div>
        <div field="state.frame" width="50" headerAlign="center" allowSort="true" renderer="frameState">组织架构</div>
        <div field="state.role" width="50" headerAlign="center" allowSort="true" renderer="roleState">岗位</div>
        <div field="state.product" width="50" headerAlign="center" allowSort="true" renderer="productState">产品</div>
        <div field="state.companycc" width="50" headerAlign="center" allowSort="true" renderer="companyccState">帐号</div>
        <div field="industryuuid" visible="false" width="120" headerAlign="center" allowSort="true">当前行业</div>
    </div>
</div>
<script src="js/ui/scripts/boot.js" type="text/javascript"></script>
<script src="js/admin/company/listcompany.js" type="text/javascript"></script>
	
