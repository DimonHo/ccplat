package com.cc.manage.logic;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CccompanyDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 分配岗位逻辑处理类。
 * @author zzf
 * @since BRoleLogic1.0
 */
@Component
public class BRoleLogic {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(BRoleLogic.class);
	
	@Autowired
    private CccompanyDao cccompanyDao;
	
	@Autowired
    private BusinessService businessService;
	
	public String listRole(HttpServletRequest request){
		//获取选中的企业id
		String companyno = RequestUtils.getString(request, "companyno");
        //公司数据
		String companyData = RequestUtils.getString(request, "company");
		JSONObject companyJson = JSONObject.fromObject(companyData);
		
        //公司服务器地址
        String address=JSONUtils.getString(companyJson, "ipAddress")+":"+JSONUtils.getString(companyJson, "port");
        
        String result=businessService.listRole(companyno,RequestUtils.getInt(request, "pageIndex"),RequestUtils.getInt(request, "pageSize"), address);
        return result;
        
	}
	
	/**
	 * 分配组织架构
	 * @param request 请求对象
	 * @return 返回操作是否成功
	 * @author admin
	 */
	public String addallot(HttpServletRequest request){
		String data = RequestUtils.getString(request, "data");
		if (StringUtils.isNotBlank(data)) {
			//公司id
			String companyno = RequestUtils.getString(request, "companyno");
			
			//公司数据
			String companyData = RequestUtils.getString(request, "company");
			JSONObject companyJson = JSONObject.fromObject(companyData);
			
			//获取当前登录用户
	        DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
	        JSONObject user=new JSONObject();
	        user.put("realname", DBObejctUtils.getString(userSession, "realname"));
	        user.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
			
	        //公司服务器地址
	        String address=JSONUtils.getString(companyJson, "ipAddress")+":"+JSONUtils.getString(companyJson, "port");
			
	        String result = businessService.initRole(data, companyno, user.toString(), address);
			if(ResultConstant.SUCCESS.equals(result)){
				
				//更新企业数据的行业和分配状态
				DBObject updateBean=new BasicDBObject();
				updateBean.put("state.role", StateConstant.B_COMPANY_ALLOCATED);
				cccompanyDao.updateByCompanyno(updateBean,companyno);
				
            	return ResultConstant.SUCCESS;
			}
		}
		return ResultConstant.FAIL;
	}
}
