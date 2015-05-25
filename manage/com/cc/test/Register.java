package com.cc.test;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.common.BasicMethod;
import com.cc.core.constant.SessionKey;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class Register implements BasicMethod {
    
    @Autowired
    RegisterDao rd;
    
    public Object Collect(String met, Object param) {
        
        Object objret = null;
        
        if (met.equals(RegisterDao.M_ADD)) {
            
            return addRegisterInfo((HttpServletRequest)param);
        }
        else if (met.equals(RegisterDao.M_SEARCH)) {
            return getFlowPerson((HttpServletRequest)param);
        }
        
        else if (met.equals(RegisterDao.M_PERSON_ACTION)) {
            return personAction((HttpServletRequest)param);
        }
        
        return objret;
    }
    
    public Object Handle(String met, Object param) {
        
        Object objret = null;
        
        if (met.equals(RegisterDao.M_CHECK)) {
            
            return check((HttpServletRequest)param);
        }
        
        return objret;
    }
    
    public Object Control(String met, Object param) {
        
        Object objret = null;
        if (met.equals(RegisterDao.M_AUDIT)) {
            return AuditReg((HttpServletRequest)param);
        }
        
        return objret;
    }
    
    public Object Linkage(String met, Object param) {
        
        // TODO Auto-generated method stub
        return null;
    }
    
    public Object Analysis(String met, Object param) {
        
        // TODO Auto-generated method stub
        return null;
    }
    
    public Object getUntreated(Object param) {
        
        // TODO Auto-generated method stub
        
        return null;
    }
    
    /**
     * 注册申请
     * 
     * @param request
     * @return
     */
    private boolean addRegisterInfo(HttpServletRequest request) {
        
        /*
         * // 插入流程 bean = new BasicDBObject(); bean.put("flowname", JSONUtils.getString(company, "realname") + "0");
         * bean.put("flowno", StringUtils.randomString(6)); bean.put("content", JSONUtils.getString(company, "realname")
         * + "---------"); bean.put("key", "0"); bean.put("state", "0");
         * 
         * JSONArray json1 = new JSONArray(); JSONObject jo = new JSONObject();
         * 
         * jo.put("ccno", ccno); jo.put("actno", sub); jo.put("ruleno", ""); jo.put("state", "1");
         * 
         * json1.add(jo);
         * 
         * jo = new JSONObject();
         * 
         * // 查询特定cc编码对应的模块的特定规则 DBObject conditionBean = new BasicDBObject(); conditionBean.put("ccno", "6527");
         * conditionBean.put("actno", str);
         * 
         * JSONObject userAction = JSONObject.fromObject(rd.searchCommonOne("ccuseraction", conditionBean)); String
         * ruleno = JSONUtils.getString(userAction, "ruleno");
         * 
         * jo.put("ccno", "6527"); jo.put("actno", str); jo.put("ruleno", ruleno); jo.put("state", "0");
         * 
         * json1.add(jo); bean.put("flows", json1);
         * 
         * rd.insertCommon(RegisterDao.T_ccflow, bean);
         */

        String data = RequestUtils.getString(request, RegisterDao.P_param);
        JSONObject json = JSONObject.fromObject(data);
        
        // 插入申请
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(userBean, "ccno");
        String realname = JSONUtils.getString(userBean, "realname");
        
        String submitno = JSONUtils.getString(json, "submitno");
        String content = realname + RegisterDao.M_ADD_CONTENT;
        
        JSONObject joBean = new JSONObject();
        joBean.put("sub", RegisterDao.M_ADD);
        joBean.put("ccno", ccno);
        joBean.put("content", content);
        joBean.put("submitno", submitno);
        
        // 调用申请模块
        
        String[] ret = AuditUtils.register(joBean);
        
        if (ret[0].equals("1")) {
            
            String name = JSONUtils.getString(json, RegisterDao.name);
            String contact = JSONUtils.getString(json, RegisterDao.contact);
            String phone = JSONUtils.getString(json, RegisterDao.phone);
            String remark = JSONUtils.getString(json, RegisterDao.remark);
            // String flowno = JSONUtils.getString(json, RegisterDao.flowno);
            
            DBObject bean = new BasicDBObject();
            bean.put(RegisterDao.name, name);
            bean.put(RegisterDao.contact, contact);
            bean.put(RegisterDao.phone, phone);
            bean.put(RegisterDao.remark, remark);
            bean.put(RegisterDao.flowno, ret[1]);
            
            rd.insertCommon(RegisterDao.T_applyreg, bean);
        }
        
        return false;
    }
    
    /**
     * 审核注册申请
     * 
     * @param request
     * @return
     */
    private boolean AuditReg(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, RegisterDao.P_param);
        
        if (StringUtils.isNotBlank(data)) {
            
            JSONObject json = JSONObject.fromObject(data);
            
            String name = JSONUtils.getString(json, RegisterDao.name);
            String contact = JSONUtils.getString(json, RegisterDao.contact);
            String phone = JSONUtils.getString(json, RegisterDao.phone);
            String remark = JSONUtils.getString(json, RegisterDao.remark);
            
            DBObject bean = new BasicDBObject();
            bean.put(RegisterDao.name, name);
            bean.put(RegisterDao.contact, contact);
            bean.put(RegisterDao.phone, phone);
            bean.put(RegisterDao.remark, remark);
            
            rd.insertCommon(RegisterDao.T_company, bean);
            
            return true;
        }
        return false;
    }
    
    /**
     * param{ flowno:流程编码 ruleno:规则编码 actno:功能编码 useraction:人员功能编码 key:关键字，对于数据类型判断大小的数字 type:"0" 0表示没能力审核，1表示审核通过
     * 
     * }
     * 
     * @param request
     * @return
     */
    private boolean check(HttpServletRequest request) {
        
        JSONObject joBean = new JSONObject();
        joBean.put("actno", RegisterDao.M_CHECK);
        
        String data = RequestUtils.getString(request, RegisterDao.P_param);
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(userBean, "ccno");
        
        JSONObject json = JSONObject.fromObject(data);
        
        json.put("actno", RegisterDao.M_CHECK);
        json.put("ccno", ccno);
        
        int flag = AuditUtils.AuditRegister(json.toString());
        
        if (flag == 0) {// 流程处理成功，实际操作
        
            String name = JSONUtils.getString(json, RegisterDao.name);
            String contact = JSONUtils.getString(json, RegisterDao.contact);
            String phone = JSONUtils.getString(json, RegisterDao.phone);
            String remark = JSONUtils.getString(json, RegisterDao.remark);
            
            DBObject bean = new BasicDBObject();
            bean.put(RegisterDao.name, name);
            bean.put(RegisterDao.contact, contact);
            bean.put(RegisterDao.phone, phone);
            bean.put(RegisterDao.remark, remark);
            
            rd.insertCommon(RegisterDao.T_company, bean);
            
            return true;
        }
        return false;
        
    }
    
    /**
     * 获取个人流程
     * 
     * @param request
     */
    private Object getFlowPerson(HttpServletRequest request) {
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        JSONArray jsonAry = new JSONArray();
        
        String ccno = JSONUtils.getString(company, "ccno");
        
        DBObject bean = new BasicDBObject();
        bean.put("state", "0");
        bean.put("flows.ccno", ccno);
        bean.put("flows.state", "1");
        
        Object obj = rd.searchCommon(RegisterDao.T_ccflow, bean);
        
        JSONArray json = JSONArray.fromObject(obj);
        JSONObject jo = null;
        
        JSONObject flowObj = null;
        
        for (int i = 0; i < json.size(); i++) {
            
            jo = json.getJSONObject(i);
            
            JSONArray flowArray = JSONUtils.getJSONArray(jo, "flows");
            JSONObject flowObject = null;
            for (int j = 0; j < flowArray.size(); j++) {
                
                flowObject = flowArray.getJSONObject(j);
                
                if ("0".equals(JSONUtils.getString(flowObject, "state"))) {
                    flowObj = flowObject;
                }
            }
            
            if (flowObj != null) { // 存在值，查询对应的产品表，url
            
                DBObject conditionBean = new BasicDBObject();
                conditionBean.put("code", JSONUtils.getString(flowObj, "actno"));
                
                Object product = rd.searchCommonOne("p_products_item", conditionBean);
                
                JSONObject jsonObject = JSONObject.fromObject(product);
                flowObj.put("content", JSONUtils.getString(jo, "content"));
                flowObj.put("key", JSONUtils.getString(jo, "key"));
                flowObj.put("url", JSONUtils.getString(jsonObject, "url"));
                flowObj.put("flowno", JSONUtils.getString(jo, "flowno"));
                
                conditionBean = new BasicDBObject();
                conditionBean.put("ruleno", JSONUtils.getString(flowObject, "ruleno"));
                
                JSONObject accessRule = JSONObject.fromObject(rd.searchCommonOne("ccaccerules", conditionBean));
                
                flowObj.put("ruletype", JSONUtils.getString(accessRule, "ruletype"));
                flowObj.put("rulestart", JSONUtils.getString(accessRule, "rulestart"));
                flowObj.put("ruleend", JSONUtils.getString(accessRule, "ruleend"));
            }
            
            jsonAry.add(flowObj);
        }
        
        return jsonAry;
    }
    
    private Object personAction(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        
        // 获取ccno
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(company, "ccno");
        
        // 获取提交人submit
        conditionBean.put("ccno", ccno);
        
        JSONObject userAction = JSONObject.fromObject(rd.searchCommonOne("ccuseraction", conditionBean));
        
        String submit = JSONUtils.getString(userAction, "submit");
        
        String[] aryStr = submit.split(",");
        
        BasicDBList values = new BasicDBList();
        for (int i = 0; i < aryStr.length; i++) {
            values.add(aryStr[i]);
        }
        
        DBObject actLess = new BasicDBObject();
        actLess.put("_id", -1);
        actLess.put("deal", 1);
        
        conditionBean = new BasicDBObject();
        conditionBean.put("sub", actno);
        
        // 联动功能
        JSONObject actObject = JSONObject.fromObject(rd.searchLessCommonOne("ccsubaction", conditionBean, actLess));
        
        conditionBean = new BasicDBObject();
        conditionBean.put("actno", JSONUtils.getString(actObject, "deal"));
        conditionBean.put("ccno", new BasicDBObject("$in", values));
        
        JSONArray json = JSONArray.fromObject(rd.searchCommon("ccuseraction", conditionBean));
        
        JSONObject jo = null;
        JSONObject result = null;
        
        // 查询出联动
        JSONArray personList = new JSONArray();
        
        JSONObject person = null;
        
        for (int i = 0; i < json.size(); i++) {
            
            person = new JSONObject(); // new一个功能对象
            
            jo = json.getJSONObject(i);
            
            conditionBean = new BasicDBObject();
            conditionBean.put("ccno", JSONUtils.getString(jo, "ccno"));
            
            DBObject less = new BasicDBObject();
            less.put("_id", -1);
            less.put("realname", 1);
            
            result = JSONObject.fromObject(rd.searchLessCommonOne("ccuser", conditionBean, less));
            
            person.put("name", JSONUtils.getString(result, "realname"));
            person.put("id", JSONUtils.getString(jo, "ccno") + "," + JSONUtils.getString(jo, "actno"));
            
            personList.add(person); // 存入集合
        }
        
        return personList;
    }
}
