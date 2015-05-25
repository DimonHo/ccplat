package com.cc.manage.logic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cc.business.service.BusinessService;
import com.cc.core.common.CommonMongo;
import com.cc.core.constant.ConstantKey;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.CommonUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.EncrypUtil;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Service
public class CcloginLogic {
    
    public static Log log = LogFactory.getLog(CcloginLogic.class);
    
    @Autowired
    private CommonMongo commonMongo;
    
    @Autowired
    private BusinessService businessService;
    
    @Autowired
    private CcProductslistLogic ccProductslistLogic;
    
    /**
     * 
     * @param param 用户名、密码、校验码
     * @param response
     * @param request
     * @return 登录状态、登录人的功能编码
     * @author DK(最后修改)
     */
    public String checkLogin(String param, HttpServletResponse response, HttpServletRequest request) {
        
        JSONObject json = JSONObject.fromObject(param);
        BasicDBObject bean = new BasicDBObject();
        bean.put("ccno", JSONUtils.getString(json, "name"));
        String verifyCode = JSONUtils.getString(json, "verifyCode");
        try {
        	if (!StringUtils.equalsIgnoreCase(verifyCode, SessionUtils.getSessionString("RANDOMVALIDATECODEKEY"))) {
                return null;
            }
            bean.put("ccpwd", EncrypUtil.getMD5(JSONUtils.getString(json, "password")));
            String resultString = "";
            if (StringUtils.equals(JSONUtils.getString(json, "name"), "0001")
                && StringUtils.equals(JSONUtils.getString(json, "password"), "0001")) {
                // 平台的超级管理员处理
                JSONArray result = ccProductslistLogic.getProductslistCode("ccmanager01");
                if (!CollectionUtils.isEmptys(result)) {
                    resultString = result.toString();
                    resultString = resultString.replace("[", "");
                    resultString = resultString.replace("]", "");
                }
                DBObject userbean = new BasicDBObject();
                userbean.put("ccno", "0001");
                userbean.put("realname", "超级管理员");
                userbean.put("islock", "0");
                request.getSession().setAttribute(SessionKey.SESSION_KEY_USER, userbean);
                return ResultConstant.SUCCESS + "/" + resultString;// 验证通过
            }
            DBObject userbean = commonMongo.queryCommonOne("cc_user", bean);
            if (userbean != null) {
                // 获取当前登录用户在这个系统当中有的功能点
                // 普通账号登录
                String uuid = DBObejctUtils.getString(userbean, "uuid");
                String companyno = DBObejctUtils.getString(userbean, "companyno");
                // 获得ip和端口
                String address = CommonUtils.getIpAddress(companyno);
                
                // 调用webservice接口获得产品功能编码
                String resultBean = businessService.getUserPower(uuid, companyno, address);
                
                if (StringUtils.isNotBlank(resultBean) && !resultBean.equals("null")) {
                    JSONObject powerBean = null;
                    JSONObject userPowerBean = JSONObject.fromObject(resultBean);
                    JSONArray powerArray = JSONArray.fromObject(JSONUtils.getString(userPowerBean, "productsid"));
                    for (int i = 0; i < powerArray.size(); i++) {
                        powerBean = powerArray.getJSONObject(i);
                        if (ConstantKey.PROJECT_NAME.equals(JSONUtils.getString(powerBean, "name"))) {
                            resultString = JSONUtils.getString(powerBean, "functions");
                            resultString = resultString.replace("[", "");
                            resultString = resultString.replace("]", "");
                            break;
                        }
                    }
                }
                request.getSession().setAttribute(SessionKey.SESSION_KEY_USER, userbean);
                return ResultConstant.SUCCESS + "/" + resultString;// 验证通过
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
}
