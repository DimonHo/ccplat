<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<base href="<%=basePath%>" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=7" />
		<title>CC管理系统</title>
		<link rel="stylesheet" type="text/css" href="css/admin/style.css"/>
		<script src="js/ui/scripts/boot.js" type="text/javascript"></script>
		<script src="js/admin/login.js" type="text/javascript"></script>
		<script type="text/javascript" src="js/admin/frame/cookie.js"></script>
		<script type="text/javascript">
			var acountArr = acountList();
			document.onkeydown=function(event){
			var e = event || window.event || arguments.callee.caller.arguments[0];
				if(e && e.keyCode==13){ // enter 键
					login();
				}
			};
		</script>
	</head>
	<body class="w1006">
		<div class="bigbg">
			<div id="entry">
				<div class="form">
					<!--账号-->
					<div class="item">
						<span class="fl">账&#12288;号:</span>
						<div class="item-ifo fl">
							<input id="userName" name="userName" property="editor" class="mini-combobox" popupHeight="80px"
								allowInput="true" style="width:180px;" data="acountArr" value=""/> 
						</div>
					</div>
					<!--密码-->
					<div class="item">
						<span class="fl">密&#12288;码:</span>
						<div class="item-ifo fl">
							<input type="password" value="" class="text" name="" name="password" id="password"/>
						</div>
					</div>
					<!--验证码-->
					<div class="item">
						<span class="fl">验证码:</span>
						<div class="item-ifo fl">
							<input type="text" value="${RANDOMVALIDATECODEKEY}" id="verifyCode" class="textw70 fl" name=""/>
								<span class="tu">
									<img src="login/getRanValidateCode.action" width="53" id="imgId" height="23" />
								</span> 
								<span class="change"> <a href="javascript:void(0)" onclick="reloadRecode(this)">换一张</a>
								</span>
						</div>
						<div class="clr"></div>
					</div>
					<!--按钮-->
					<div class="submit">
						<button class="S_Submit" type="button" onclick="login()"></button>

						 <div class="erro">				

			             </div>
					</div>
				</div>
			</div>
		</div>
	</body>
	
</html>
