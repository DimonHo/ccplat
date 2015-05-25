function reloadRecode() {
document.getElementById("imgId").src = "login/getRanValidateCode.action?a="
			+ Math.random();
}

var acountArr = acountList();//获取所有历史账号



function login() {
	var name = mini.get("#userName").getValue();
	var password = $("#password").val();
	var verifyCode = $("#verifyCode").val();
	if (name == "" || password == "") {
		mini.alert("请输入用户名和密码！");
		return;
	} else if (verifyCode == "") {
		mini.alert("请输入验证码！");
		return;
	}
	
	params = {
		name : name,
		password : password,
		verifyCode : verifyCode
	};
	$.post("login/loginCheck.json",{
		param : mini.encode(params)
	},function(e){
		var ary=e.split("/");
		if (ary[0] == "1") {
			setCookie("functions_ccplat",ary[1]);
			//保存登陆账号
			saveAcount(name);
			window.location = "jsp/admin/index.jsp";
			return ;
		} else if (ary[0] == "2") {
			$(".erro").html("用户名或者密码有误！！");
		} else {
			$(".erro").html("验证码输入有误！");
		}
		reloadRecode();
	});
}


