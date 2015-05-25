package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.DateUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.BcompanyccDao;
import com.cc.manage.dao.CccompanyDao;
import com.cc.manage.dao.CcnumberDao;
import com.cc.manage.dao.CcuserDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 企业cc账号的logic层
 * 
 * @author Ron
 * @createTime 2014.09.18
 */
@Component
public class BcompanyccLogic {
    
    public static Log log = LogFactory.getLog(BcompanyccLogic.class);
    
    @Autowired
    private CcuserLogic ccuserLogic;
    
    @Autowired
    private BcompanyccDao cccompanyccDao;
    
    @Autowired
    private CcnumberDao ccnumberDao;
    
    @Autowired
    private CccompanyDao cccompanyDao;
    
    @Autowired
    private CcuserDao ccuserDao;
    
    @Autowired
    private BusinessService businessService;
    
    /***
     * 平台分配cc账号到企业；<br>
     * 1.分配的账号传入企业端的webservice中企业端形成B_companycc数据，成功返回字符串1，失败返回字符串2，异常返回字符串4
     * 1.返回1，则根据ccno修改cc_number的的状态state为1,根据ccno插入cc_user表状态state为1，usestate为0的数据，修改b_company的state.companycc状态为 1
     * 2.返回2，提示返回错误， 3.返回3，提示操作异常。
     * 
     * 注：没有审核流程
     * 
     * @param request
     * @return 成功：1;失败：2 ;异常4
     * @author Ron
     */
    public String add(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        
        try {
            if (StringUtils.isNotBlank(data)) {
                
                JSONObject jsonBean = JSONObject.fromObject(data);
                String remark = JSONUtils.getString(jsonBean, "remark");
                String companyno = JSONUtils.getString(jsonBean, "companyno");// 企业id
                String ccjson = JSONUtils.getString(jsonBean, "ccjson");
                
                // 公司数据
                String companyData = RequestUtils.getString(request, "company");
                JSONObject companyJson = JSONUtils.getJsonObject(companyData);
                String ipAddress = JSONUtils.getString(companyJson, "ipAddress");
                String port = JSONUtils.getString(companyJson, "port");
                if (StringUtils.isBlank(ipAddress) || StringUtils.isBlank(port)) {
                    return ResultConstant.IP_PORT_EXCEPTION;
                }
                // 调用webService请求
                String result =
                    businessService.initCcAccount(ccjson,
                        companyno,
                        remark,
                        JSONUtils.getString(jsonBean, "starttime"),
                        JSONUtils.getString(jsonBean, "endtime"),
                        ipAddress,
                        port);
                if (StringUtils.equals(result, "1")) {// 成功
                    JSONArray json = JSONArray.fromObject(ccjson);
                    if (!CollectionUtils.isEmptys(json)) {
                        DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
                        DBObject operator = new BasicDBObject();
                        operator.put("name", DBObejctUtils.getString(userSession, "realname"));
                        operator.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
                        
                        // 账号历史记录
                        DBObject historyBean = new BasicDBObject();
                        historyBean.put("createTime", new Date());
                        historyBean.put("operator", operator);
                        historyBean.put("remark", remark + ";分配账号到企业" + companyno);
                        List<DBObject> historyList = new ArrayList<DBObject>();
                        historyList.add(historyBean);
                        for (int i = 0; i < json.size(); i++) {
                            
                            // 修改账号池状态
                            JSONObject numberBean = json.getJSONObject(i);
                            String id = JSONUtils.getString(numberBean, "id");
                            ccnumberDao.modifyStateById(StateConstant.CC_NUMBER_STATE1, id);
                            // 记录账号池历史
                            ccnumberDao.addHistroyById(id, historyBean);
                            DBObject userBean =
                                ccuserLogic.buildCcUserBean(null,
                                    historyList,
                                    companyno,
                                    JSONUtils.getString(numberBean, "ccno"),
                                    DateUtils.stringToDate(JSONUtils.getString(jsonBean, "starttime"),
                                        DateUtils.DATE_FORMAT_STS),
                                    DateUtils.stringToDate(JSONUtils.getString(jsonBean, "endtime"),
                                        DateUtils.DATE_FORMAT_STS));
                            ccuserDao.insert(userBean);
                        }
                    }
                    // 更新企业数据的行业和分配状态
                    DBObject updateBean = new BasicDBObject();
                    updateBean.put("state.companycc", StateConstant.B_COMPANY_ALLOCATED);
                    cccompanyDao.updateByCompanyno(updateBean, companyno);
                    return ResultConstant.SUCCESS;
                }
                else if (StringUtils.equals(result, "2")) {// 失败
                    return ResultConstant.FAIL;
                }
                else if (StringUtils.equals(result, "4")) {// 操作异常
                    return ResultConstant.EXCEPTION;
                }
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /***
     * 企业内的CC号管理列表通过企业ID查询结果
     * 
     * @param request
     * @return
     */
    public JSONObject list(HttpServletRequest request) {
        return cccompanyccDao.list(request);
    }
    
    /***
     * 调整账号期限,直接根据_id修改cc_user的使用期限，以及添加操作记录。
     * 
     * @param request
     * @return 成功返回true,否则false
     * @param Ron
     */
    public boolean modify(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        
        if (StringUtils.isNotBlank(data)) {
            
            JSONObject jsonobjec = JSONObject.fromObject(data);
            JSONArray jsonarr = JSONUtils.getJSONArray(RequestUtils.getString(request, "json"));// 传入的cc账号
            String remark = JSONUtils.getString(jsonobjec, "remark");
            Date starttime =
                DateUtils.stringToDate(JSONUtils.getString(jsonobjec, "starttime"), DateUtils.DATE_FORMAT_STS);
            Date endtime = DateUtils.stringToDate(JSONUtils.getString(jsonobjec, "endtime"), DateUtils.DATE_FORMAT_STS);
            remark =
                remark + "{开始时间:" + DateUtils.dateToString(starttime, DateUtils.DATE_FORMAT_DD) + "-结束时间:"
                    + DateUtils.dateToString(endtime, DateUtils.DATE_FORMAT_DD) + "}";
            Date creteaTime = new Date();
            for (int i = 0; i < jsonarr.size(); i++) {
                JSONObject tempObj = jsonarr.getJSONObject(i);
                DBObject bean = new BasicDBObject();
                bean.put("starttime", starttime);
                bean.put("endtime", endtime);
                ccuserDao.updateById(JSONUtils.getString(tempObj, "id"), bean);
                ccuserLogic.addHistory(request, JSONUtils.getString(tempObj, "id"), remark, creteaTime);
            }
            return true;
        }
        
        return false;
    }
    
    /***
     * 平台停用企业中的CC账号信息
     * 
     * @param request
     * @return
     */
    public boolean updateState(HttpServletRequest request) {
        String id = RequestUtils.getString(request, "id");
        
        if (StringUtils.isNotBlank(id)) {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            DBObject bean = new BasicDBObject();
            bean.put("state", RequestUtils.getString(request, "state"));
            cccompanyccDao.update(conditionBean, bean);
            return true;
        }
        return false;
    }
    
    /**
     * 企业账号分配管理员
     * 
     * @param request
     * @return
     * @author Ron
     */
    public String addpower(HttpServletRequest request) {
        
        return cccompanyccDao.addpower(request);
    }
    
    public String queryuser(HttpServletRequest request) {
        
        String ccno = RequestUtils.getString(request, "ccno");
        if (StringUtils.isNotBlank(ccno)) {
            return ccuserDao.queryByCcno(ccno);
        }
        return "";
    }
}
