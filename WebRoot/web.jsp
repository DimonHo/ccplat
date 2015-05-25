<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>My JSP 'index.jsp' starting page</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>

	<body>
		
		<input class="mini-treeselect" url="tree.txt" 
			valueField="id"
		 	textField="text"
		 	/>
		 	
		 	
		<ul id="tree1" class="mini-tree" url="tree.txt" style="width:300px;padding:5px;" 
		    showTreeIcon="true" textField="text" idField="id" >        
		</ul>
		<script src="<%=basePath%>js/ui/scripts/boot.js" type="text/javascript"></script> 
 		<script type="text/javascript">
 		
 			mini.parse();
 			
 		</script>	
	</body>
</html>
