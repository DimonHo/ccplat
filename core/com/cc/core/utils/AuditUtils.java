package com.cc.core.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.AuditKey;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.TableNameConstant;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.cc.test.RegisterDao;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
@SuppressWarnings("unused")
public class AuditUtils {
    
    @Autowired
    private static CommonDaoImpl commonDaoImpl = new CommonDaoImpl();
    
    public static void setCommonDaoImpl() {
        
        commonDaoImpl = new CommonDaoImpl();
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 申请审核
     * 
     * @param joBean (ccno 当前提交用户，content 申请内容，sub 提交功能编码，submitno 提交人 )
     * @param sub 提交模块编码
     * 
     * @return 0 不需要进入审核流程 || 1表示成功 || 2表示操作失败
     */
    public static String[] register(JSONObject joBean) {
        
        String[] ret = new String[2];
        ret[0] = "2";
        try {
            
            String uuid = JSONUtils.getString(joBean, "uuid");
            String sub = JSONUtils.getString(joBean, "sub");
            
            // 插入流程
            DBObject bean = new BasicDBObject();
            
            // 生成唯一的流程编码
            String flowno = StringUtils.getUniqueString() + uuid;
            
            String submitno = JSONUtils.getString(joBean, "submitno"); // 提交人编码，actno的组合
            String[] submitact = submitno.split(",");
            
            bean.put("flowno", flowno);
            bean.put("content", JSONUtils.getString(joBean, "content"));
            bean.put("actno", submitact[1]);
            if(StringUtils.isBlank(JSONUtils.getString(joBean, "key"))){
           	 bean.put("key","0");//字符类型
            }
            bean.put("key", JSONUtils.getString(joBean, "key"));//获取关键字
            bean.put("state", "0");
            
            JSONArray json1 = new JSONArray();
            DBObject jo = new BasicDBObject();
            
            jo.put("uuid", JSONUtils.getString(joBean, "uuid"));
            jo.put("actno", sub);
            jo.put("state", "1");
            jo.put("ctime", new Date());
            
            json1.add(jo);
            
            jo = new BasicDBObject();
            
            // 查询特定cc编码对应的模块的特定规则
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("uuid", submitact[0]);
            conditionBean.put("actno", submitact[1]);
            
            jo.put("uuid", submitact[0]);
            jo.put("actno", submitact[1]);
            jo.put("ctime", "");
            jo.put("state", "0");
            
            json1.add(jo);
            bean.put("flows", json1);
            
            commonDaoImpl.insertCommon(TableNameConstant.T_FLOW, bean);
            
            ret[0] = "1";
            ret[1] = flowno;
            
            return ret;
            
        }
        catch (Exception e) {
            
        }
        
        return ret;
        
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 审核注册 param{ flowno:流程编码 actno:功能编码 ccno:人员 对于数据类型判断大小的数字 type:"0" 0表示没能力审核，1表示审核通过,table 申请表,ruleType 规则类型
     * 
     * @param data 页面传过来的数据
     * @return 返回数据：状态码， 1为同意，2为提交，3为拒绝 4为操作失败
     */
    public static int AuditRegister(String data) {
        
        DBObject conditionBean = null;
        DBObject changeBean = null;
        
        int flagCount = 4;
        
        try {
            if (StringUtils.isNotBlank(data)) {
                JSONObject jo = JSONObject.fromObject(data);
                
                String type = JSONUtils.getString(jo, "type");
                String remark=JSONUtils.getString(jo, "remark");

                if (AuditKey.SUBMIT_TYPE.equals(type)) {
                    type = AuditKey.SUBMIT_DEAL_TYPE;
                }
                
                String actno = JSONUtils.getString(jo, "actno");
                String flowno = JSONUtils.getString(jo, "flowno");
                String uuid = JSONUtils.getString(jo, "uuid");
                
                String submittype = JSONUtils.getString(jo, "submittype"); // 提交类型
                
                if (AuditKey.COMMON_FORCE_SUBMIT.equals(submittype)
                    || (AuditKey.COMMON_SUBMIT_TYPE.equals(submittype) && AuditKey.SUBMIT_DEAL_TYPE.equals(type))) { // 如果为强制提交
                
                    // 获取提交人 格式为ccno,actno 这个值一定不能为空
                    String submitno = JSONUtils.getString(jo, "submitno");
                    
                    if ("".equals(submitno)) {
                        
                        return 4; // 返回失败
                    }
                    
                    String[] submitAry = submitno.split(",");
                    
                    // 修改 添加流程
                    updateAddFlow(uuid, actno, flowno, submitAry[0], submitAry[1], type,remark);
                    
                    flagCount = 2;
                    
                }
                else {
                    
                    conditionBean = new BasicDBObject();
                    conditionBean.put("flowno", flowno);
                    conditionBean.put("flows.actno", actno);
                    
                    changeBean = new BasicDBObject();
                    changeBean.put("flows.$.state", type);
                    changeBean.put("flows.$.ctime", new Date());
                    changeBean.put("state", type);
                    changeBean.put("flows.$.remark", remark);//获取处理备注

                    
                    // 修改
                    commonDaoImpl.updateCommon(TableNameConstant.T_FLOW, conditionBean, changeBean);
                    
                    flagCount = 1;
                }
            }
        }
        catch (Exception e) {
            flagCount = 4;
        }
        
        return flagCount;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取个人流程
     * 
     * @param userSession 获取session对象
     */
    public static Object getFlowPerson(JSONObject userSession) {
        
        // JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        JSONArray jsonAry = new JSONArray();
        
        String ccno = JSONUtils.getString(userSession, "ccno");
        
        DBObject bean = new BasicDBObject();
        bean.put("state", "0");
        bean.put("flows.ccno", ccno);
        bean.put("flows.state", "1");
        
        Object obj = commonDaoImpl.searchCommon(TableNameConstant.T_FLOW, bean);
        
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
                
                Object product = commonDaoImpl.searchCommonOne("b_products_item", conditionBean);
                
                JSONObject jsonObject = JSONObject.fromObject(product);
                flowObj.put("content", JSONUtils.getString(jo, "content"));
                flowObj.put("key", JSONUtils.getString(jo, "key"));
                flowObj.put("url", JSONUtils.getString(jsonObject, "url"));
                flowObj.put("flowno", JSONUtils.getString(jo, "flowno"));
                
                conditionBean = new BasicDBObject();
                conditionBean.put("ruleno", JSONUtils.getString(flowObject, "ruleno"));
                
                JSONObject accessRule =
                    JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_ACCESS_RULE, conditionBean));
                
                flowObj.put("ruletype", JSONUtils.getString(accessRule, "ruletype"));
                flowObj.put("rulestart", JSONUtils.getString(accessRule, "rulestart"));
                flowObj.put("ruleend", JSONUtils.getString(accessRule, "ruleend"));
            }
            
            jsonAry.add(flowObj);
        }
        
        return jsonAry;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取用户功能
     * 
     * @param userSession 获取session对象
     * @param actno 功能编码
     * @return id为ccno+“，”+actno name为提交人员名称
     */
    public static JSONArray personAction(HttpServletRequest request) {
        
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String uuid = JSONUtils.getString(userSession, "uuid"); // 获取用户uuid
        String actno = RequestUtils.getString(request, "actno");// 获取功能编码
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        // 获取提交人submit
        conditionBean.put("uuid", uuid);
        
        DBObject less = new BasicDBObject();
        less.put("submit", 1);
        
        JSONObject userAction =
            JSONObject.fromObject(commonDaoImpl.searchLessCommonOne(TableNameConstant.T_ACCESS_RULE,
                conditionBean,
                less));
        
        String submit = JSONUtils.getString(userAction, "submit");
        
        String[] aryStr = submit.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
        
        // 人员uuid集合
        BasicDBList values = new BasicDBList();
        for (int i = 0; i < aryStr.length; i++) {
            values.add(aryStr[i]);
        }
        
        // 查询deal字段
        DBObject actLess = new BasicDBObject();
        actLess.put("_id", -1);
        actLess.put("deal", 1);
        
        conditionBean = new BasicDBObject();
        conditionBean.put("sub", actno);
        
        // 联动功能
        JSONObject actObject =
            JSONObject.fromObject(commonDaoImpl.searchLessCommonOne(TableNameConstant.T_SUBACTION,
                conditionBean,
                actLess));
        
        conditionBean = new BasicDBObject();
        conditionBean.put("actno", JSONUtils.getString(actObject, "deal"));
        conditionBean.put("uuid", new BasicDBObject("$in", values));
        
        JSONArray json =
            JSONArray.fromObject(commonDaoImpl.searchCommon(TableNameConstant.T_ACCESSS_RULES, conditionBean));
        
        JSONObject jo = null;
        JSONObject result = null;
        
        // 查询出联动
        JSONArray personList = new JSONArray();
        
        JSONObject person = null;
        
        for (int i = 0; i < json.size(); i++) {
            
            person = new JSONObject(); // new一个功能对象
            
            jo = json.getJSONObject(i);
            
            conditionBean = new BasicDBObject();
            conditionBean.put("uuid", JSONUtils.getString(jo, "uuid"));
            
            less = new BasicDBObject();
            less.put("_id", -1);
            less.put("realname", 1);
            
            result =
                JSONObject.fromObject(commonDaoImpl.searchLessCommonOne(TableNameConstant.T_USER, conditionBean, less));
            
            person.put("name", JSONUtils.getString(result, "realname"));
            person.put("id", JSONUtils.getString(jo, "uuid") + AplicationKeyConstant.STRING_SPLIT_CHAR
                + JSONUtils.getString(jo, "actno"));
            
            personList.add(person); // 存入集合
        }
        
        return personList;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 判断是否存在权限，
     * 
     * return 1拥有权限，2需要审核
     */
    public static String whetherAuthority1(HttpServletRequest request) {
        
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(company, "ccno");
        
        String data = RequestUtils.getString(request, "param");
        JSONObject joBean = JSONObject.fromObject(data);
        String actno = JSONUtils.getString(joBean, "actno");
        int key = JSONUtils.getInt(joBean, "key");
        
        String flowno = JSONUtils.getString(joBean, "flowno"); // 流程编码
        
        // 第一步：先看是否需要审核，查询联动表，是否存在联动
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("deal", actno);
        
        // 联动编码
        Object obj = commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION, conditionBean);
        
        if (obj == null || "[]".equals(obj.toString())) { // 没有查询到数据
        
            return "-2," + "0"; // 不需要进入审核
        }
        
        conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        conditionBean.put("ccno", ccno);
        
        // 用户功能是否为空
        // if (useraction != null) { // 查看是否拥有提交人员
        // JSONObject userActionObj = JSONObject.fromObject(useraction);
        //
        // String submit = JSONUtils.getString(userActionObj, "submit");
        //
        // if ("".equals(submit))
        // submitState = "-3";
        // }
        
        Object ruleObjectt = commonDaoImpl.searchCommonOne(TableNameConstant.T_ACCESSS_RULES, conditionBean);
        
        JSONArray ruleList = new JSONArray();
        if (ruleObjectt != null) {
            
            JSONObject ruleJo = JSONObject.fromObject(ruleObjectt);
            
            ruleList = JSONUtils.getJSONArray(ruleJo, "rules");
        }
        
        // -----------------------------------------------------------------------------------
        
        // 获取数据为一个json对象
        
        // 时间类型：timetype,0为不采用时间类型，采用单笔计算，1以年为单位，2以月为单位，3以日为单位，4以时间为单位
        // 时间参数:当timetype不等于0时有效
        // 规则类型为1的处理： 1为数字类型 ，数字类型中采用时间段计算，或者采用单笔计算
        
        int auditDigital = 100;
        
        int rulestart = 0;
        int ruleend = 1;
        
        JSONObject ruleBean = null;
        
        String ruletype = "";
        String timetype = "";
        Date startDate = null;
        Date endDate = null;
        JSONObject flowBean = null;
        
        int totalCount = 0;
        
        for (int i = 0; i < ruleList.size(); i++) { // 找出类型为1的
        
            ruleBean = ruleList.getJSONObject(i);
            
            ruletype = JSONUtils.getString(ruleBean, "ruletype");
            
            rulestart = JSONUtils.getInt(ruleBean, "rulestart");
            ruleend = JSONUtils.getInt(ruleBean, "ruleend");
            
            if (AuditKey.COMMON_DIGITAL_TYPE.equals(ruletype) || AuditKey.DIGITAL_TYPE_SUBMIT.equals(ruletype)
                || AuditKey.DIGITAL_REFUSE_SUBMIT.equals(ruletype)) { // 如果类型为1
            
                // 类型为1 判断大小
                // 查看时间类型是否为0，为0不需要考虑时间，只需要考虑单笔，否则，需要查询记录
                timetype = JSONUtils.getString(ruleBean, "timetype");
                
                if (AuditKey.SINGLE_PEN.equals(timetype)) { // 为单笔计算
                
                    if (rulestart > auditDigital || auditDigital > ruleend) // 不满足条件
                        return AuditKey.HAVENOT_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR + ruletype;
                    
                }
                else if (AuditKey.TIME_YEAR.equals(timetype)) {
                    
                    startDate = TimeUtils.getCurrentYearStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentYearEndTime(); // 当年的结束时间
                    
                }
                else if (AuditKey.TIME_QUARTER.equals(timetype)) { // 季度
                    startDate = TimeUtils.getCurrentQuarterStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentQuarterEndTime(); // 当年的结束时间
                    
                }
                else if (AuditKey.TIME_MONTH.equals(timetype)) {
                    
                    startDate = TimeUtils.getCurrentMonthStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentMonthEndTime(); // 当年的结束时间
                }
                else if (AuditKey.TIME_WEEK.equals(timetype)) {
                    
                    startDate = TimeUtils.getCurrentWeekDayStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentWeekDayEndTime(); // 当年的结束时间
                }
                else if (AuditKey.TIME_DAY.equals(timetype)) {
                    
                    startDate = TimeUtils.getCurrentDayStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentDayEndTime(); // 当年的结束时间
                }
                else if (AuditKey.TIME_HOUR.equals(timetype)) {
                    
                    startDate = TimeUtils.getCurrentHourStartTime(); // 获取当前的开始时间
                    endDate = TimeUtils.getCurrentHourEndTime(); // 当年的结束时间
                }
                
                if (AuditKey.HAVENOT_AUDIT_POWER.equals(getRuleState(startDate,
                    endDate,
                    rulestart,
                    ruleend,
                    ccno,
                    actno,
                    flowno))) {
                    
                    return AuditKey.HAVENOT_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR + ruletype;
                }
                
            }
            else if (AuditKey.COMMON_CHARACTER_TYPE.equals(ruletype)) {
                
                return AuditKey.HAVE_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR
                    + AuditKey.COMMON_CHARACTER_TYPE;
            }
            else if (AuditKey.CHARACTER_TYPE_SUBMIT.equals(ruletype)) {
                
                return AuditKey.HAVE_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR
                    + AuditKey.CHARACTER_TYPE_SUBMIT;
            }
            else if (AuditKey.CHARACTER_REFUSE_SUBMIT.equals(ruletype)) {
                
                return AuditKey.HAVE_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR
                    + AuditKey.CHARACTER_REFUSE_SUBMIT;
            }
        }
        
        return AuditKey.HAVE_AUDIT_POWER + AplicationKeyConstant.STRING_SPLIT_CHAR + ruletype;
    }
    
    public static String getRuleState(Date startTime, Date endTime, int rulestart, int ruleend, String ccno,
        String actno, String flowno) {
        
        // 查询当前功能点，在此段时间内使用额度
        BasicDBObject boj = new BasicDBObject();
        boj.put("ccno", ccno);
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
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 判断是否存在权限，
     * 
     * return 1拥有权限，2需要审核
     */
    public static int whetherAuthority(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, RegisterDao.P_param);
        
        JSONObject joBean = JSONObject.fromObject(data);
        
        String ccno = JSONUtils.getString(joBean, "ccno");
        String sub = JSONUtils.getString(joBean, "sub");
        int key = JSONUtils.getInt(joBean, "key");
        
        // 第一步：先看是否需要审核，查询联动表，是否存在联动
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("sub", sub);
        
        // 联动编码
        Object obj = commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION, conditionBean);
        
        conditionBean = new BasicDBObject();
        conditionBean.put("actno", sub);
        conditionBean.put("ccno", ccno);
        
        Object useraction = commonDaoImpl.searchCommonOne(TableNameConstant.T_ACCESSS_RULES, conditionBean);
        
        // 用户功能是否为空
        if (useraction != null) {
            JSONObject userActionObj = JSONObject.fromObject(useraction);
            
            String ruletype = JSONUtils.getString(userActionObj, "ruletype");
            int rulestart = 0;
            int ruleend = 0;
            
            if ("1".equals(ruletype)) { // 为1为数字类型，可以直接判断是否有权限
            
                rulestart = JSONUtils.getInt(userActionObj, "rulestart");
                ruleend = JSONUtils.getInt(userActionObj, "ruleend");
                
                if (rulestart < key && key < ruleend) { // 在权限范围内
                    return 1;
                }
            }
        }
        
        return 2;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public static JSONObject updateAddFlow(String uuid, String actno, String flowno, String subCcno, String subActno,
        String type,String remark) {
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("flowno", flowno);
        
        // 先查询模块数据
        JSONObject obj = JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_FLOW, conditionBean));
        JSONArray jsonArray = JSONUtils.getJSONArray(obj, "flows");
        
        DBObject changeBean = null;
        JSONObject flowObject = null;
        
        // 遍历，查询出数据且修改
        for (int i = 0; i < jsonArray.size(); i++) {
            flowObject = jsonArray.getJSONObject(i);
            
            if (JSONUtils.getString(flowObject, "uuid").equals(uuid)
                && JSONUtils.getString(flowObject, "actno").equals(actno)
                && JSONUtils.getString(flowObject, "state").equals("0")) { // 是否在这几个条件当中
            
                changeBean = new BasicDBObject();
                changeBean.put("flows." + i + ".state", type);
                changeBean.put("flows." + i + ".ctime", new Date());
                changeBean.put("flows." + i + ".remark", remark);
                changeBean.put("actno", actno);
                
                commonDaoImpl.updateCommon(TableNameConstant.T_FLOW, conditionBean, changeBean);
                
                break;
            }
        }
        
        changeBean = new BasicDBObject();
        changeBean.put("uuid", subCcno);
        changeBean.put("actno", subActno);
        changeBean.put("ctime", "");
        changeBean.put("state", "0");
        
        DBObject changeBean1 = new BasicDBObject();
        changeBean1.put("flows", changeBean);
        
        conditionBean = new BasicDBObject();
        conditionBean.put("flowno", flowno);
        
        // 新增一条数据
        commonDaoImpl.updateCommonPush(TableNameConstant.T_FLOW, conditionBean, changeBean1);
        
        return null;
    }
}