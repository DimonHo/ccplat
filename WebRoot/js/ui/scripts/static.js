//全局的ajax访问，处理ajax清求时sesion超时  
// $.ajaxSetup({   
//           contentType:"application/x-www-form-urlencoded;charset=utf-8",  
//           complete:function(XMLHttpRequest,textStatus){ 
//             var sessionstatus=XMLHttpRequest.getResponseHeader("sessionstatus"); //通过XMLHttpRequest取得响应头，sessionstatus，  
//             	 if (sessionstatus == "timeout") {
//			        	alert("登陆过期，请重新登陆！");
//			            if (window.self == window.parent) {
//					    	 window.location.href=basePath+"jsp/login.jsp"; 
//						}else{
//							 window.parent.location.href=basePath+"jsp/login.jsp"; 
//					  }
//			        }else if(sessionstatus=="noPromiss"){
//			        	
//			        	mini.alert("您没有权限操作，如需操作，请向上级申请！");
//			 }
//	   }   
//});
 
//miniui 请求的处理方式
$(document).ajaxComplete(function (evt, request, settings) {
	
        var sessionstatus=request.getResponseHeader("sessionstatus");
        
        	
        //判断返回的数据内容，如果是超时，则跳转到登陆页面
        if (sessionstatus == "timeout") {
        	alert("登陆过期，请重新登陆！");
            if (window.self == window.parent) {
		    	 window.location.href=basePath+"jsp/login.jsp"; 
			}else{
				 window.parent.location.href=basePath+"jsp/login.jsp"; 
		  }
        }
    })