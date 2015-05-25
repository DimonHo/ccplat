package com.cc.manage.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cc.core.constant.AuditKey;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.WebServiceUtils;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.cc.manage.service.AuditInterface;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * mongodb审核流程
 * 
 * @author Suan
 * @version 1.0
 * @createTime 2014-10-10
 * 
 */
public class AuditMongodbImpl implements AuditInterface {
    
    private CommonDaoImpl commonDaoImpl = new CommonDaoImpl();
    
    /*-------------------------------------------------------------------------------------------*/

    public Object getAuditPower(String uuid, String actno, String key, String flowno, String ipAddressAndPort) {
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        JSONArray json =
            JSONArray.fromObject(WebServiceUtils.getWebService("getAuditPower",
                new Object[] {uuid, actno, key, flowno},
                ipAddressAndPort));
        
        JSONObject resultObject = null;
        
        String ruletype = "";
        String submittype = "";
        
        Date starttime = null;
        Date endtime = null;
        int rulestart = 0;
        int ruleend = 0;
        
        try {
            
            for (int i = 0; i < json.size(); i++) {
                
                resultObject = json.getJSONObject(i);
                
                ruletype = JSONUtils.getString(resultObject, "ruletype");
                submittype = JSONUtils.getString(resultObject, "submittype");
                
                if (AuditKey.COMMON_DIGITAL_TYPE.equals(ruletype)) {
                    
                    starttime = sdf.parse(JSONUtils.getString(resultObject, "starttime"));
                    endtime = sdf.parse(JSONUtils.getString(resultObject, "endtime"));
                    
                    rulestart = JSONUtils.getInt(resultObject, "rulestart");
                    ruleend = JSONUtils.getInt(resultObject, "ruleend");
                    
                    if (AuditKey.HAVENOT_AUDIT_POWER.equals(getRuleState(starttime,
                        endtime,
                        rulestart,
                        ruleend,
                        uuid,
                        actno,
                        flowno))) {
                        
                        return AuditKey.HAVENOT_AUDIT_POWER + "," + submittype;
                    }
                    
                }
                else {
                    
                    return JSONUtils.getString(resultObject, "state") + "," + submittype;
                }
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        
        return AuditKey.HAVE_AUDIT_POWER + "," + submittype;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public String getRuleState(Date startTime, Date endTime, int rulestart, int ruleend, String uuid, String actno,
        String flowno) {
        
        // 查询当前功能点，在此段时间内使用额度
        BasicDBObject boj = new BasicDBObject();
        boj.put("uuid", uuid);
        boj.put("actno", actno);
        boj.put("ctime", new BasicDBObject("$gte", startTime).append("$lte", startTime));
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("flows", boj);
        
        BasicDBObject conditionBean1 = new BasicDBObject();
        conditionBean1.put("flowno", flowno);
        
        BasicDBList conditionList = new BasicDBList();
        conditionList.add(conditionBean);
        conditionList.add(conditionBean1);
        
        BasicDBObject condition = new BasicDBObject();
        condition.put("$or", conditionList);
        
        // 查询记录
        JSONArray list = JSONArray.fromObject(commonDaoImpl.searchCommon(TableNameConstant.T_FLOW, condition));
        
        int totalCount = 0; // 初始化总数
        
        JSONObject flowBean = null;
        for (int j = 0; j < list.size(); j++) {
            
            flowBean = list.getJSONObject(j);
            // 获取key值
            totalCount += Integer.parseInt(JSONUtils.getString(flowBean, "key"));
        }
        
        // 判断权限范围
        if (rulestart > totalCount || ruleend < totalCount) // 不满足条件
        
            return AuditKey.HAVENOT_AUDIT_POWER;
        
        return AuditKey.HAVE_AUDIT_POWER;
    }
    
}
