<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Audit.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script> 

  </head>
  
  <body>
  	<div style="width: 1000px;height:300px"> 
  	 <div class="mini-fit" >
  		<div id="grids" class="mini-datagrid" url="reg/getFlowPerson.action" 
			style="width:100%;height:100%;" borderStyle="border:0;" >
					<div property="columns">
						<div field="url" width="120" headerAlign="center" allowSort="true">
							url地址
						</div>
						<div field="ccno" width="100" allowSort="true" headerAlign="center">
							cc编码
						</div>
						<div field="actno" width="100" allowSort="true" headerAlign="center">
						ruleno
						</div>
						<div field="ruleno" width="100" allowSort="true" headerAlign="center">
							规则编码
						</div>
						<div field="content" width="100" allowSort="true" headerAlign="center">
							内容
						</div>
						<div field="ruletype" width="100" allowSort="true" headerAlign="center">
							规则类型
						</div>
						<div field="rulestart" width="100" allowSort="true" headerAlign="center">
							最小值
						</div>
						<div field="ruleend" width="100" allowSort="true" headerAlign="center">
							最大值
						</div>
						<div field="key" width="100" allowSort="true" headerAlign="center">
							关键字
						</div>
						<div field="flowno" width="100" allowSort="true" headerAlign="center">
							流程编码
						</div>
						<div field="submit" width="100" allowSort="true" headerAlign="center">
							提交人
						</div>
					</div>	
					<div class="mini-pager"
						style="width:700px;background:#f0f3f7;border:solid 1px #ccc;"
						totalCount="123" pageSize="[5,10,20,100]" showPageSize="true"
						showPageIndex="true" showPageInfo="true" buttons="#buttons">
					</div>
				</div>
  </div>
  </div>
    审批. <br>
    
    <input type="button" onClick="auditInfo()" text="提交" style="margin-top:5px;" value="&nbsp;">
    
    
  </body>
  <script type="text/javascript">
   		 
    //$(".mini-combobox").click(function(){});
    	
    mini.parse();
    var grid = mini.get("grids"); 
    grid.load();
    
    function auditInfo(){
   			var row = grid.getSelected();
				//判断是否选取了一条数据
				if (row) {
					var rows = mini.encode(row);

					mini.open({
						url : row.url,
						title : "审核",
						width : 600,
						height : 360,
						onload : function() {
							var iframe = this.getIFrameEl();
							var data = {
								action : "edit",
								url: row.url,
								ccno : row.ccno,
								content : row.content,
								ruletype : row.ruletype,
								rulestart : row.rulestart,
								ruleend : row.ruleend,
								flowno :row.flowno,
								actno : row.actno,
								ruleno : row.ruleno,
								key:row.key
							};
							iframe.contentWindow.SetData(data);
						},
						ondestroy : function(action) {
							gridid.reload();

						}
					});

				} else {
					mini.alert("请选中一条记录");
				}
   }
        
  </script>
</html>
