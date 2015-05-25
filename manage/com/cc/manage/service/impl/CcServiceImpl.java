package com.cc.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.StateConstant;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.EncrypUtil;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcuserDao;
import com.cc.manage.dao.CommonDao;
import com.cc.manage.dao.impl.BaseDao;
import com.cc.manage.dao.impl.CcuserDaoImpl;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.cc.manage.logic.CcuserLogic;
import com.cc.manage.service.ICcService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

/**
 * WebService接口实现类
 * 
 * @author zmx
 * @createtime 2014-09-27 this WebService is service for Android
 * 
 */
public class CcServiceImpl extends BaseDao implements ICcService {
    public static Log log = LogFactory.getLog(CcServiceImpl.class);
    
    private CcuserDao ccuserDao = new CcuserDaoImpl();
    
    private CommonDao commonDao = new CommonDaoImpl();
    
    private CcuserLogic ccuserLogic = new CcuserLogic();
    
    /**
     * 验证码List
     */
    private List<String> accessTokenList = new ArrayList<String>();
    
    /****************************************** for Android ******************************************/
    /**
     * 登陆
     * 
     * @param ccno 账号
     * @param ccpwd 密码
     * @param productName 产品名
     * @return state:-1表示账号密码不正确，正确的话会返回
     * @author Einstein
     */
    public String getWebLogin(String ccno, String ccpwd) {
        DBObject user = ccuserDao.queryCcuserByCcno(ccno);
        if (user == null) {
            return State.NO_RESULT;
        }
        if (!ccpwd.equalsIgnoreCase((String)user.get("ccpwd"))) {
            return State.NO_RESULT;
        }
        JSONObject jsonObj = new JSONObject();
        String state = user.get("state").toString();
        // 平台 状态1-平台启用，2平台停用，3.历史使用记录
        if ("1".equals(state)) {
            String access_token = UUID.randomUUID().toString();
            jsonObj.put("access_token", access_token);// return a access_token for validate
            accessTokenList.add(access_token);
            jsonObj.put("ccno", user.get("ccno"));
            jsonObj.put("companyno", user.get("companyno"));
            jsonObj.put("phone", user.get("phone"));
            jsonObj.put("realname", user.get("realname"));
            jsonObj.put("byname", user.get("byname"));
            jsonObj.put("sex", user.get("sex"));
            jsonObj.put("uuid", user.get("uuid"));
        }
        jsonObj.put("state", state);// 状态1-平台启用，2平台停用，3：平台历史记录;
        return jsonObj.toString();
    }
    
    /**
     * 企业列表
     * 
     * @param access_token 验证码
     * @param pageIndex 起始页
     * @param pageSize 记录数
     * @return
     * @author Einstein
     */
    public String getAllCompanys(String access_token, int pageIndex, int pageSize) {
        
        // 认证
        if (!accessTokenList.contains(access_token)) {
            return State.TOKEN_NOT_MACTH;
        }
        List<DBObject> listCompany = mongo.find(B_COMPANY, new BasicDBObject(), pageIndex, pageSize);
        if (listCompany == null || listCompany.size() == 0) {
            return State.NO_RESULT;
        }
        JSONArray companyArray = new JSONArray();
        for (DBObject object : listCompany) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("companyno", object.get("companyno"));
            jsonObj.put("name", object.get("name"));
            jsonObj.put("industryuuid", object.get("industryuuid"));
            jsonObj.put("ipAddress", object.get("ipAddress"));
            jsonObj.put("port", object.get("port"));
            jsonObj.put("state", object.get("state"));
            companyArray.add(jsonObj);
        }
        return companyArray.toString();
    }
    
    /**
     * 企业账号，更改用户状态
     * 
     * @param access_token
     * @param companyno
     * @param ccno
     * @param state
     * @return
     * @author Einstein
     */
    public String updateCcuserState(String access_token, String companyno, String ccno, String state) {
        // 认证
        if (!accessTokenList.contains(access_token)) {
            return State.TOKEN_NOT_MACTH;
        }
        DBObject condition = new BasicDBObject();
        condition.put("companyno", companyno);
        condition.put("ccno", ccno);
        // 平台状态更改，只有二种状态,状态1-平台启用，2平台停用;
        if ("1".equals(state) || "2".equals(state)) {
            boolean flag = mongo.update(CC_USER, condition, new BasicDBObject("state", state));
            if (flag) {
                return "{\"state\":\"1\"}";
            }
        }
        return "{\"state\":\"0\"}";
    }
    
    /****************************************** for Businessplat ******************************************/
    
    /**
     * 更新企业
     * 
     * @param companyno
     * @param updateStr
     * @return
     * @author Einstein
     */
    public String updateCompany(String companyno, String updateStr, String historyStr) {
        DBObject conditionBean = null;
        DBObject setBean = null;
        DBObject insertBean = null;
        DBObject historyBean = null;
        try {
            conditionBean = new BasicDBObject("companyno", companyno);
            setBean = (DBObject)JSON.parse(updateStr);
            insertBean = (DBObject)JSON.parse(historyStr);
            insertBean.put("createtime", new Date());
            historyBean = new BasicDBObject("history", insertBean);
            boolean flag = mongo.update(B_COMPANY, conditionBean, setBean);
            if (flag) {
                mongo.updatePush(B_COMPANY, conditionBean, historyBean);
                return "1";
            }
            return "0";
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return "-1";
        }
    }
    
    /**
     * 重置Cc_user密码
     * 
     * @param companyno
     * @param ccno
     * @param historyJson
     * @return 1:更改成功；2：更改失败；9：不能操作
     * @author Einstein
     */
    public String resetPsw(String companyno, String ccno, String historyJson) {
        DBObject historyBean = new BasicDBObject();
        try {
            historyBean = (DBObject)com.mongodb.util.JSON.parse(historyJson);
        }
        catch (Exception e) {
            return ResultConstant.FAIL;
        }
        String isDisable = isDisable(ccno, companyno);
        if (StateConstant.CC_USER_STATE1.equals(isDisable)) {
            DBObject condition = new BasicDBObject();
            condition.put("companyno", companyno);
            condition.put("ccno", ccno);
            condition.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
            String ccpwd = EncrypUtil.getMD5(ccno);
            boolean flag = mongo.update(CC_USER, condition, new BasicDBObject("ccpwd", ccpwd));
            if (flag) {
                mongo.updatePush(CC_USER, condition, new BasicDBObject("history", historyBean));
                return ResultConstant.SUCCESS;
            }
            return ResultConstant.FAIL;
        }
        else {
            return ResultConstant.DISABLE;
        }
    }
    
    /**
     * 更改用户
     * 
     * @param companyno
     * @param ccno
     * @param updateJson
     * @param historyJson
     * @return 1:成功;2:失败;9:不能操作
     * @author Einstein
     */
    public String updateUser(String companyno, String ccno, String updateJson, String historyJson) {
        DBObject setBean = new BasicDBObject();
        DBObject historyBean = new BasicDBObject();
        try {
            setBean = (DBObject)com.mongodb.util.JSON.parse(updateJson);
            historyBean = (DBObject)com.mongodb.util.JSON.parse(historyJson);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResultConstant.FAIL;
        }
        String isDisable = isDisable(ccno, companyno);
        if (StateConstant.CC_USER_STATE1.equals(isDisable)) {
            
            DBObject condition = new BasicDBObject();
            condition.put("companyno", companyno);
            condition.put("ccno", ccno);
            condition.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
            boolean falg = mongo.update(CC_USER, condition, setBean);
            if (falg) {
                mongo.updatePush(CC_USER, condition, new BasicDBObject("history", historyBean));
                return ResultConstant.SUCCESS;
            }
            return ResultConstant.FAIL;
        }
        else {
            return ResultConstant.DISABLE;
        }
    }
    
    /**
     * 回收账号
     * 
     * @param operatorJson 操作人
     * @param companyno 企业号
     * @param ccno
     * @return "1":回收成功;"2":回收失败; "9":不能操作
     * @author Einstein
     */
    public String recycleCcuser(String operatorJson, String companyno, String ccno) {
        DBObject operator = null;
        try {
            operator = (DBObject)com.mongodb.util.JSON.parse(operatorJson);
        }
        catch (Exception e1) {
            return ResultConstant.FAIL;
        }
        String isDisable = isDisable(ccno, companyno);
        if (StateConstant.CC_USER_STATE1.equals(isDisable)) {
            DBObject condition = new BasicDBObject();
            condition.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
            condition.put("ccno", ccno);
            DBObject oldUser = mongo.findOne(CC_USER, condition);
            if (oldUser == null) {
                return null;
            }
            boolean flag =
                mongo.update(CC_USER, condition, new BasicDBObject("usestate", StateConstant.CC_USER_USESTATE2));
            if (flag) {
                
                List<DBObject> historyList = new ArrayList<DBObject>();
                DBObject history = new BasicDBObject();
                history.put("createtime", new Date());
                history.put("operator", operator);
                history.put("remark", operator.get("name") + "收回" + oldUser.get("realname") + "的CC号");
                historyList.add(history);
                DBObject user =
                    this.ccuserLogic.buildCcUserBean(null, historyList, companyno, ccno, DBObejctUtils.getDate(oldUser,
                        "starttime"), DBObejctUtils.getDate(oldUser, "endtime"));
                
                WriteResult result = mongo.insert(CC_USER, user);
                if (null != result) {
                    if (null == result.getError() || "null".equals(result.getError())) {
                        return ResultConstant.SUCCESS;
                    }
                }
            }
            return ResultConstant.FAIL;
        }
        else {
            return ResultConstant.DISABLE;
        }
    }
    
    /**
     * 获取cc_user帐号状态是否停用
     * 
     * @param ccno 帐号编码
     * @param companyno 公司编码
     * @return String 1,表示启用，2表示停用
     * @author admin
     */
    public String isDisable(String ccno, String companyno) {
        DBObject user = ccuserDao.queryCcuserByCompanyNoAndCcno(companyno, ccno);
        if (user != null) {
            return StateConstant.CC_USER_STATE1;
        }
        else {
            return StateConstant.CC_USER_STATE2;
        }
    }
    
    /**
     * 更换帐号
     * 
     * @param oldccno 旧帐号编码
     * @param newccno 新帐号编码
     * @param companyno 公司编码
     * @return
     * @author admin
     */
    public String changeUser(String oldccno, String newccno, String companyno) {
        
        DBObject olduser = ccuserDao.queryCcuserByCompanyNoAndCcno(companyno, oldccno);
        DBObject newuser = ccuserDao.queryCcuserByCompanyNoAndCcno(companyno, newccno);
        // 两个帐号是否停用
        if (StateConstant.CC_USER_STATE1.equals(DBObejctUtils.getString(olduser, "state"))
            || StateConstant.CC_USER_STATE1.equals(DBObejctUtils.getString(newuser, "state"))) {
            
            String olduserStr = olduser.toString();
            DBObject copyBean = (DBObject)JSON.parse(olduserStr);
            copyBean.put("ccno", newuser.get("ccno"));
            copyBean.put("companyno", newuser.get("companyno"));
            copyBean.put("state", StateConstant.CC_USER_STATE1);
            copyBean.put("usestate", StateConstant.CC_USER_USESTATE1);
            copyBean.removeField("_id");
            
            // 复制旧账号的数据到新帐号
            ccuserDao.updateState(newuser, copyBean);
            
            // 修改旧账号状态
            DBObject updateBean = (DBObject)JSON.parse(olduserStr);
            updateBean.put("usestate", StateConstant.CC_USER_STATE2);
            ccuserDao.updateState(olduser, updateBean);
            
            // 由于旧账号回收，新建一条空数据
            DBObject insertBean =
                ccuserLogic.buildCcUserBean(null,
                    new ArrayList<DBObject>(),
                    companyno,
                    DBObejctUtils.getString(olduser, "ccno"),
                    DBObejctUtils.getDate(olduser, "starttime"),
                    DBObejctUtils.getDate(olduser, "endtime"));
            insertBean.put("state", StateConstant.CC_USER_STATE1);
            insertBean.put("usestate", StateConstant.CC_USER_USESTATE0);
            ccuserDao.insert(insertBean);
            
            return ResultConstant.SUCCESS;
        }
        else {
            return ResultConstant.FAIL;
        }
    }
    
    /**
     * 根据uuid获取帐号的ccno
     * 
     * @param uuid 帐号对应的uuid
     * @return
     * @author admin
     */
    public String getCcnoByUuid(String uuid) {
        DBObject user = ccuserDao.queryByUuid(uuid);
        if (user == null) {
            return null;
        }
        String ccno = (String)user.get("ccno");
        return "ccno:" + ccno;
    }
    
    /**
     * 得到可分配的账号List
     * 
     * @param companyno
     * @return
     * @author Einstein
     */
    public String getCcuserListCanAdd(String companyno, int pageindex, int pagesize) {
        DBObject condition = new BasicDBObject();
        condition.put("companyno", companyno);
        condition.put("state", StateConstant.CC_USER_STATE1);
        condition.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
        DBObject fields = new BasicDBObject();
        fields.put("ccno", 1);
        fields.put("_id", 0);
        List<DBObject> list = mongo.findLess(CC_USER, condition, fields, pageindex, pagesize);
        if (list == null)
            return null;
        return list.toString();
    }
    
    /**
     * 获得当前联动功能的版本信息
     * 
     * @return String
     * @author DK
     */
    public String getSubActionVersion() {
        
        JSONObject versionBean =
            JSONObject.fromObject(commonDao.searchCommonOne(TableNameConstant.T_SUBACTION_VERSION, new BasicDBObject()));
        
        if (versionBean.isEmpty()) {
            
            return "";
        }
        // 这里由于只有一个元素，所以直接获取，不使用循环
        String code = JSONUtils.getString(versionBean, "code");
        return code;
    }
    
    /**
     * 获得当前平台的联动数据
     * 
     * @author DK
     */
    public String getSubAction() {
        
        DBObject lessBean = new BasicDBObject();
        lessBean.put("_id", 0);
        lessBean.put("sub", 1);
        lessBean.put("deal", 1);
        JSONArray subAction = JSONArray.fromObject(commonDao.searchLessCommonAll("p_subaction", lessBean));
        return subAction.toString();
    }
    
    /***
     * 查询可以被分配CC账号
     * 
     * @param num分配数量
     * @param start号码起始位
     * @param type企业号码位数
     * @return
     * @author Suan
     */
    public String notAllot(int num, String start, String type) {
        MongoDBManager db = MongoDBManager.getInstance();
        DBObject temp = new BasicDBObject();
        if (StringUtils.isNotBlank(start)) {
            temp.put("ccno", new BasicDBObject("$gte", start));
        }
        temp.put("type", type);
        temp.put("state", StateConstant.CC_NUMBER_STATE2);// 未使用的
        DBObject orderBy = new BasicDBObject();
        orderBy.put("order", StringUtils.setOrder(""));
        List<DBObject> list = db.findLess("cc_number", temp, new BasicDBObject("ccno", 1), orderBy, 1, num);
        
        return JSONUtils.resertJSON(list).toString();
    }
    
    /**
     * 修改密码
     * @param pwd
     * @param uuid
     * @param newPwd
     * @return 1 失败 2成功
     * @author DK
     */
	public String contrastPwd(String pwd,String uuid,String newPwd) {
		
		String re = "1";
		try {
			//获得原来密码
			DBObject condition = new BasicDBObject();
			condition.put("uuid", uuid);
			
			JSONObject userBean =  JSONObject.fromObject(commonDao.searchCommonOne(TableNameConstant.T_USER, condition));
			String oldPwd = JSONUtils.getString(userBean, "pwd");
			if(pwd.equals(oldPwd)){
				
				DBObject pwdBean = new BasicDBObject();
				pwdBean.put("pwd", newPwd);
				
				Object result = commonDao.updateCommon(TableNameConstant.T_USER, condition, pwdBean);
				String resultStr = result.toString();
				
				if(resultStr.equals("true")){
					re = "2";
				}
			}	
		} catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
			
		return re;
	}

}
