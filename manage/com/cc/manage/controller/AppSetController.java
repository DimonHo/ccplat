package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcuserDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Controller
@RequestMapping("appset")
public class AppSetController {
	
    public static Log log = LogFactory.getLog(AppSetController.class);
	
    @Autowired
    private CcuserDao ccuserDao;
    /***
	 * 手机端修改密码
	 * @param request
	 * @param response
	 * 输出返回字符串
	 * 
	 * ：1表示修改成功，2表示修改失败，3表示原始密码不匹配，4表示新密码不一致,5账号异常查不到数据
	 */
    @RequestMapping("setpsw")
    public void setpsw(HttpServletRequest request, HttpServletResponse response) {
        
    	String ccno = RequestUtils.getString(request, "ccno");
    	String oldpsw = RequestUtils.getString(request, "oldpsw");
    	String psw = RequestUtils.getString(request, "psw");
    	String confirmpsw = RequestUtils.getString(request, "confirmpsw");
    	
    	
    	if(!StringUtils.equals(psw, confirmpsw)){
    		Result.send("4" , response);
    		return ;
    	}
    	try{
	    	DBObject user = ccuserDao.queryCcuserByCcno(ccno);
	    	if(user != null){
	    		if(StringUtils.equals(oldpsw, DBObejctUtils.getString(user, "ccpwd"))){
	    			String id = user.get("_id").toString();
	    			DBObject userBean = new BasicDBObject();
	    			userBean.put("ccpwd", psw);
	    			if(this.ccuserDao.updateById(id, userBean)){
	    				Result.send("1" , response);
	            		return ;
	    			}else{
	    				Result.send("2" , response);
	            		return ;
	    			}
	    		}else{
	    			Result.send("3" , response);
	        		return ;
	    		}
	    	}else{
	    		Result.send("5" , response);
	    		return ;
	    	}
    	}catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
    }
}
