package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.TabExpander;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.CommonUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.NumberUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.impl.BcompanyapplyDaoImpl;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CcAuditLogic {
    
    @Autowired
    private CommonDaoImpl commonDaoImpl;
    
    @Autowired
    private BcompanyapplyDaoImpl companyapplyDao;
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object functionAceess(HttpServletRequest request) {
        
        String code = RequestUtils.getString(request, "code");
        
        String actno = RequestUtils.getString(request, "actno");
        
        // 第一步：先看是否需要审核，查询联动表，是否存在联动
        DBObject bean = new BasicDBObject();
        bean.put("sub", actno);
        
        // 联动编码
        Object obj = commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION, bean);
        
        String deal = "";
        boolean isCheck = false;
        if (obj != null) {
            
            isCheck = true;
            deal = JSONUtils.getString(JSONObject.fromObject(obj), "deal");
        }
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("code", code);
        
        JSONObject result =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_PRODUCT_LIST, conditionBean));
        
        String children = JSONUtils.getString(result, "children");
        
        JSONArray json = JSONArray.fromObject(children);
        
        JSONArray childrenArray = new JSONArray();
        JSONObject childObject = null;
        for (int i = 0; i < json.size(); i++) {
            
            childObject = json.getJSONObject(i);
            if (code.equals(JSONUtils.getString(childObject, "code"))) {
                
                childrenArray.add(childObject);
            }
        }
        
        result.remove("children");
        result.put("children", childrenArray);
        
        JSONArray jsonBean = new JSONArray();
        jsonBean.add(result);
        
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        
        List<String> stringList = new ArrayList<String>();
        stringList.add(deal);
        
        JSONArray checkJSON = new JSONArray();
        JSONObject tempBean = new JSONObject();
        tempBean.put("code", deal);
        checkJSON.add(tempBean);
        
        return CommonUtils.childrenList(jsonBean, list, isCheck, checkJSON, "");
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getLink(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno");
        
        // 第一步：先看是否需要审核，查询联动表，是否存在联动
        DBObject bean = new BasicDBObject();
        bean.put("sub", actno);
        
        // 联动编码
        Object obj = commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION, bean);
        
        return JSONUtils.getString(JSONObject.fromObject(obj), "deal");
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object ruleType(HttpServletRequest request) {
        
        Object result = commonDaoImpl.searchCommonAll(TableNameConstant.T_RULE_TYPE);
        
        JSONArray ruleTypeList = new JSONArray();
        
        if (result != null) { // 结果是否为空
            JSONArray resultArray = JSONArray.fromObject(result);
            
            JSONObject ruleType = null;
            
            JSONObject jo = null;
            for (int i = 0; i < resultArray.size(); i++) { // 循环查询结果
                jo = resultArray.getJSONObject(i);
                
                ruleType = new JSONObject(); // new一个功能对象
                
                ruleType.put("id", JSONUtils.getString(jo, "typeno"));
                ruleType.put("name", JSONUtils.getString(jo, "name"));
                
                ruleTypeList.add(ruleType); // 存入集合
            }
        }
        
        return ruleTypeList;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object personList(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno"); // 功能编码
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        
        JSONObject ruleAccess =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_ACCESSS_RULES, conditionBean));
        
        String islock = JSONUtils.getString(ruleAccess, "islock"); // 0为不开始，1为开启
        
        // 查询出联动
        JSONArray personList = new JSONArray();
        JSONObject person = new JSONObject();
        
        if ("0".equals(islock)) { // 关闭锁
        
            person.put("islock", "0");
            personList.add(person);
            
            return personList.toString(); // 不需要进入审核流程
        }
        else {
            person.put("islock", "1");
            personList.add(person);
        }
        
        JSONObject result = null;
        
        DBObject less = new BasicDBObject();
        
        conditionBean = new BasicDBObject();
        conditionBean.put("sub", actno);
        
        DBObject actLess = new BasicDBObject();
        actLess.put("_id", -1);
        actLess.put("deal", 1);
        
        // 获取处理功能
        Object actList = commonDaoImpl.searchLessCommonOne(TableNameConstant.T_SUBACTION, conditionBean, actLess);
        
        // 获取处理功能
        if (actList != null) {
            String deal = JSONUtils.getString(JSONObject.fromObject(actList), "deal");
            
            conditionBean = new BasicDBObject();
            conditionBean.put("actno", deal);
            
            less = new BasicDBObject();
            less.put("uuid", 1);
            
            JSONArray userAction =
                JSONArray.fromObject(commonDaoImpl.searchLessCommon(TableNameConstant.T_ACCESSS_RULES,
                    conditionBean,
                    less));
            
            for (int i = 0; i < userAction.size(); i++) {
                
                person = new JSONObject(); // new一个功能对象
                
                conditionBean = new BasicDBObject();
                
                conditionBean.put("uuid", JSONUtils.getString(userAction.getJSONObject(i), "uuid"));
                
                less = new BasicDBObject();
                less.put("_id", -1);
                less.put("realname", 1);
                
                // 查询用户公司表
                result =
                    JSONObject.fromObject(commonDaoImpl.searchLessCommonOne(TableNameConstant.T_USER,
                        conditionBean,
                        less));
                
                person.put("name", JSONUtils.getString(result, "realname"));
                person.put("id",
                    JSONUtils.getString(userAction.getJSONObject(i), "uuid") + ","
                        + JSONUtils.getString(userAction.getJSONObject(i), "actno"));
                
                personList.add(person); // 存入集合
            }
            
        }
        
        // 不存在联动
        return personList.toString();
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object saveFuncRule(HttpServletRequest request, HttpServletResponse response) {
        
        DBObject insertBean = new BasicDBObject();
        
        String actno = RequestUtils.getString(request, "actno");
        String actname = RequestUtils.getString(request, "actname");
        
        // 生成唯一的ruleno
        String ruleno = StringUtils.getUniqueString();
        
        insertBean.put("actno", actno);
        insertBean.put("ruleno", ruleno);
        insertBean.put("ruletype", RequestUtils.getString(request, "ruletype"));
        insertBean.put("rulestart", RequestUtils.getString(request, "rulestart"));
        insertBean.put("ruleend", RequestUtils.getString(request, "ruleend"));
        
        // 插入权限规则
        commonDaoImpl.insertCommon("p_accerules", insertBean);
        
        DBObject insertBean2 = null;
        BasicDBList dblist = new BasicDBList();
        
        String[] ccnos = RequestUtils.getString(request, "ccnos").split(AplicationKeyConstant.STRING_SPLIT_CHAR);
        String submit = RequestUtils.getString(request, "submit");
        
        for (int i = 0; i < ccnos.length; i++) { // 循环用户编码数组
        
            insertBean2 = new BasicDBObject();
            insertBean2.put("ccno", ccnos[i]);
            insertBean2.put("actno", actno);
            // insertBean2.put("code", StringUtils.randomString(6));
            insertBean2.put("actname", actname);
            insertBean2.put("ruleno", ruleno);
            insertBean2.put("submit", submit);
            
            dblist.add(insertBean2);
        }
        
        // 批量插入
        return commonDaoImpl.insertBatchCommon("p_useraction", dblist);
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object saveLink(HttpServletRequest request, HttpServletResponse response) {
        
        String version = "";
        // 修改版本号
        JSONObject versionArr =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION_VERSION,
                new BasicDBObject()));
        if (versionArr != null) {
            version = JSONUtils.getString(versionArr, "code");
            
            version = StringUtils.stringToInt(version) + 1 + "";
        }
        else {
            version = "0";
        }
        
        commonDaoImpl.updateCommon("p_subaction_version", new BasicDBObject(), new BasicDBObject("code", version));
        
        // 添加联动
        String sub = RequestUtils.getString(request, "sub");
        String deal = RequestUtils.getString(request, "deal");
        
        DBObject insertBean = new BasicDBObject();
        
        insertBean.put("sub", sub);
        insertBean.put("deal", deal);
        
        // 添加这个联动
        return commonDaoImpl.insertCommon(TableNameConstant.T_SUBACTION, insertBean);
    }
    
    public Object updateLink(HttpServletRequest request, HttpServletResponse response) {
        
        DBObject insertBean = new BasicDBObject();
        
        insertBean.put("sub", RequestUtils.getString(request, "sub"));
        insertBean.put("deal", RequestUtils.getString(request, "deal"));
        
        return commonDaoImpl.insertCommon("p_subaction", insertBean);
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getProducts(HttpServletRequest request) {
        
        // JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        String companyno = RequestUtils.getString(request, "companyno");
        String productCode = RequestUtils.getString(request, "code"); // 产品编码
        
        Object obj = null;
        
        DBObject condition = new BasicDBObject();
        
        if (StringUtils.isNotBlank(companyno)) { // 从前台传来的参数
        
            condition.put("companyno", companyno);
            
            obj = commonDaoImpl.searchCommon(TableNameConstant.T_PRODUCTS, condition);
        }
        else if (StringUtils.isNotBlank(productCode)) {
            
            condition.put("code", productCode);
            obj = commonDaoImpl.searchCommon(TableNameConstant.T_PRODUCT_LIST, condition);
            
        }
        else { // 查询自己公司的产品
        
            obj = commonDaoImpl.searchCommonAll(TableNameConstant.T_PRODUCT_LIST);
        }
        
        if (obj != null) {
            
            // 转换成为json
            JSONArray jsonArray = JSONArray.fromObject(obj);
            
            // 需要显示的字段
            List<String> list = new ArrayList<String>();
            list.add("code");
            list.add("name");
            
            return CommonUtils.childrenList(jsonArray, list, false, null, "");
        }
        
        return new JSONArray();
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getProductAct(HttpServletRequest request) {
        
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(company, "ccno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccno);
        
        DBObject less = new BasicDBObject();
        less.put("_id", -1);
        less.put("actno", 1);
        less.put("actname", 1);
        
        return null;// JSONArray.fromObject(commonDaoImpl.searchLessCommon(TableNameConstant.T_USERACTION,
        // conditionBean, less));
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getProductActSome(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccnoSelf = JSONUtils.getString(userBean, "ccno");
        String ccno = RequestUtils.getString(request, "ccno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccno);
        
        DBObject less = new BasicDBObject();
        less.put("_id", -1);
        less.put("actno", 1);
        less.put("actname", 1);
        
        // 点击人所拥有的权限
        JSONArray userAction =
            JSONArray.fromObject(commonDaoImpl.searchLessCommon("p_useraction", conditionBean, less));
        
        conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccnoSelf);
        
        // 自己所拥有的权限
        JSONArray userActionSelf =
            JSONArray.fromObject(commonDaoImpl.searchLessCommon("p_useraction", conditionBean, less));
        
        JSONObject actionSelf = null;
        JSONObject action = null;
        JSONArray resultArray = new JSONArray();
        for (int j = 0; j < userActionSelf.size(); j++) {
            actionSelf = userActionSelf.getJSONObject(j);
            for (int i = 0; i < userAction.size(); i++) {
                action = userAction.getJSONObject(i);
                
                // 是否相等，相等将jsonObject存入返回对象中
                if (JSONUtils.getString(actionSelf, "actno").equals(JSONUtils.getString(action, "actno"))) {
                    
                    action.put("href",
                        "<a href=\"javascript:void(0)\" onclick=\"jumpPage('" + JSONUtils.getString(action, "actno")
                            + "','" + JSONUtils.getString(action, "actname") + "')\">编辑</a>");
                    resultArray.add(action);
                    break;
                }
            }
        }
        
        return resultArray;
        
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getProductActdeff(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccnoSelf = JSONUtils.getString(userBean, "ccno");
        String ccno = RequestUtils.getString(request, "ccno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccno);
        
        DBObject less = new BasicDBObject();
        less.put("_id", -1);
        less.put("actno", 1);
        less.put("actname", 1);
        
        // 点击人所拥有的权限
        JSONArray userAction =
            JSONArray.fromObject(commonDaoImpl.searchLessCommon("p_useraction", conditionBean, less));
        
        conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccnoSelf);
        
        // 自己所拥有的权限
        JSONArray userActionSelf =
            JSONArray.fromObject(commonDaoImpl.searchLessCommon("p_useraction", conditionBean, less));
        
        JSONObject actionSelf = null;
        JSONObject action = null;
        JSONArray resultArray = new JSONArray();
        
        boolean flag = true;
        for (int j = 0; j < userActionSelf.size(); j++) {
            actionSelf = userActionSelf.getJSONObject(j);
            flag = true;
            for (int i = 0; i < userAction.size(); i++) {
                action = userAction.getJSONObject(i);
                
                // 是否相等，相等
                if (JSONUtils.getString(actionSelf, "actno").equals(JSONUtils.getString(action, "actno"))) {
                    flag = false;
                    
                    // action.put("href", "<a href=\"javascript:void(0)\" onclick=\"jumpPage('"
                    // + JSONUtils.getString(action, "actno") + "','" + JSONUtils.getString(action, "actname")
                    // + "')\">编辑</a>");
                    
                    break;
                }
                
            }
            
            if (flag) { // 如果为true,说明不同
                resultArray.add(actionSelf);
            }
        }
        
        return resultArray;
        
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getTypeForAct(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        
        // 设计中，一个功能只存在一种规则类型
        Object obj = commonDaoImpl.searchCommonOne("p_accerules", conditionBean);
        
        String ruletype = "";
        
        if (obj != null) {
            JSONObject joBean = JSONObject.fromObject(obj);
            ruletype = JSONUtils.getString(joBean, "ruletype");
        }
        
        return ruletype;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public String setSelectPerson(JSONObject joBean) {
        
        String actno = JSONUtils.getString(joBean, "actno");
        String type = JSONUtils.getString(joBean, "type");
        
        // 获取类型，1为自身actno,2为需要查询出处理actno
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        
        // 设计中，一个功能只存在一种规则类型
        Object obj = commonDaoImpl.searchCommon("p_useraction", conditionBean);
        JSONArray json = JSONArray.fromObject(obj);
        
        String ccnos = "";
        if ("2".equals(type)) {
            
            // 对于提交，只取第一条
            for (int i = 0; i < json.size(); i++) {
                ccnos += JSONUtils.getString(json.getJSONObject(i), "submit") + AplicationKeyConstant.STRING_SPLIT_CHAR;
            }
            
            Set<String> set = new HashSet<String>();
            String[] ary = ccnos.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            
            for (int i = 0; i < ary.length; i++) {
                set.add(ary[i]);
            }
            
            ccnos = "";
            Iterator<String> it = set.iterator();
            
            while (it.hasNext()) {
                ccnos += it.next() + AplicationKeyConstant.STRING_SPLIT_CHAR;
            }
        }
        else {
            
            for (int i = 0; i < json.size(); i++) {
                
                ccnos += JSONUtils.getString(json.getJSONObject(i), "ccno") + AplicationKeyConstant.STRING_SPLIT_CHAR;
            }
        }
        
        if (!"".equals(ccnos)) {
            ccnos = ccnos.substring(0, ccnos.length() - 1);
        }
        
        return ccnos;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getRulePerson(HttpServletRequest request) {
        
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(company, "ccno");
        
        String subno = RequestUtils.getString(request, "sub"); // 提交功能
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccno);
        conditionBean.put("actno", subno);
        
        DBObject less = new BasicDBObject();
        less.put("_id", -1);
        less.put("ruleno", 1);
        
        Object obj = commonDaoImpl.searchLessCommonOne("p_useraction", conditionBean, less);
        
        Object acceObj = null;
        
        if (obj != null) {
            JSONObject jo = JSONObject.fromObject(obj);
            
            String ruleno = JSONUtils.getString(jo, "ruleno");
            
            conditionBean = new BasicDBObject();
            conditionBean.put("ruleno", ruleno);
            conditionBean.put("actno", subno);
            
            acceObj = commonDaoImpl.searchCommonOne("p_accerules", conditionBean);
        }
        
        return acceObj;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getPersonForPer(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        String ccno = JSONUtils.getString(userBean, "ccno");
        String companyno = JSONUtils.getString(userBean, "companyno");
        
        // 先查询b_companycc表，查询出所在部门，等级
        JSONObject companycc =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne("b_companycc", new BasicDBObject("ccno", ccno)));
        
        JSONArray jobArray = JSONArray.fromObject(JSONUtils.getString(companycc, "job"));
        JSONObject jobObject = null;
        
        JSONObject joBean =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne("b_frame", new BasicDBObject("companyno", companyno)));
        
        String frameuuid = "";
        String roleuuid = "";
        JSONArray jsonArray = null;
        JSONObject resultBean = null;
        JSONArray childrenArray = new JSONArray();
        JSONObject childrenObject = null;
        JSONArray sb = null;
        
        List<String> list = new ArrayList<String>();
        list.add("uuid");
        list.add("name");
        
        JSONArray json = null;
        
        JSONArray total = new JSONArray();
        
        for (int i = 0; i < jobArray.size(); i++) {
            
            jobObject = jobArray.getJSONObject(i);
            frameuuid = JSONUtils.getString(jobObject, "frameuuid");
            roleuuid = JSONUtils.getString(jobObject, "roleuuid");
            
            jsonArray = new JSONArray();
            jsonArray.add(joBean);
            
            resultBean = CommonUtils.getResult(jsonArray, "uuid", frameuuid);
            
            childrenArray = new JSONArray();
            childrenObject = new JSONObject();
            sb = new JSONArray();
            sb.add(resultBean);
            childrenObject.put("children", sb);
            childrenArray.add(childrenObject);
            
            if ("".equals(roleuuid)) {
                roleuuid = "-1";
            }
            
            json =
                CommonUtils.childrenList(childrenArray, list, false, null, AplicationKeyConstant.STRING_SPLIT_CHAR
                    + roleuuid);
            
            total.addAll(json);
        }
        
        return total;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object SaveFunForCcno(HttpServletRequest request) {
        
        String actnos = RequestUtils.getString(request, "actnos"); // frameuuid+AplicationKeyConstant.STRING_SPLIT_CHAR+roleuuid
        String ccno = RequestUtils.getString(request, "ccno");
        
        String[] strAry = actnos.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
        
        BasicDBList values = new BasicDBList();
        DBObject dbObject = null;
        for (int i = 0; i < strAry.length; i++) {
            
            dbObject = new BasicDBObject();
            dbObject.put("ccno", ccno);
            
            // 从页面传过来的格式actno+"/"+actname
            String[] actArray = strAry[i].split("/");
            dbObject.put("actno", actArray[0]);
            dbObject.put("actname", actArray[1]);
            
            values.add(dbObject);
        }
        
        return commonDaoImpl.insertBatchCommon("p_useraction", values);
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    /**
     * 查询某个部门下面所有的人
     * 
     * @return
     */
    public Object getDepArrayForDepno(HttpServletRequest request) {
        
        String frameRole = RequestUtils.getString(request, "frameRole"); // frameuuid+AplicationKeyConstant.STRING_SPLIT_CHAR+roleuuid
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String companyno = JSONUtils.getString(userBean, "companyno");
        
        String[] strAry = frameRole.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
        
        if (strAry.length == 2) { // 只在等于2的时候数据才是正常的
            String frameuuid = strAry[0];
            String roleuuid = strAry[1];
            
            // 查询出当前frameuuid下面的所有部门
            JSONObject frameList =
                JSONObject.fromObject(commonDaoImpl.searchCommonOne("b_frame",
                    new BasicDBObject("companyno", companyno)));
            
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(frameList);
            List<String> list = CommonUtils.getUUIDArray(jsonArray, "uuid", false, frameuuid, new ArrayList<String>());
            
            BasicDBList values = new BasicDBList();
            
            for (String str : list) { // 获取当前下的部门
                values.add(str);
            }
            
            // 查询出下面所有的人
            DBObject condition = new BasicDBObject();
            condition.put("job.frameuuid", new BasicDBObject("$in", values));
            // condition.put("job.roleuuid", new BasicDBObject("$lte", roleuuid));
            
            DBObject less = new BasicDBObject();
            less.put("ccno", 1);
            
            JSONArray companyccArray =
                JSONArray.fromObject(commonDaoImpl.searchLessCommon("b_companycc", condition, less));
            
            JSONObject companycc = null;
            values = new BasicDBList();
            for (int i = 0; i < companyccArray.size(); i++) {
                companycc = companyccArray.getJSONObject(i);
                
                // 查出所有ccno
                values.add(JSONUtils.getString(companycc, "ccno"));
            }
            
            condition = new BasicDBObject();
            condition.put("ccno", new BasicDBObject("$in", values));
            
            less = new BasicDBObject();
            less.put("ccno", 1);
            less.put("realname", 1);
            
            JSONArray resultArray = JSONArray.fromObject(commonDaoImpl.searchLessCommon("cc_user", condition, less));
            JSONObject resultObject = null;
            JSONArray result = new JSONArray();
            for (int i = 0; i < resultArray.size(); i++) {
                resultObject = resultArray.getJSONObject(i);
                resultObject.put("href",
                    "<a href=\"javascript:void(0)\" onclick=\"jumpPage(" + JSONUtils.getString(resultObject, "ccno")
                        + ")\">编辑</a>");
                
                result.add(resultObject);
            }
            
            // 查询职工
            return result;
            
        }
        
        return new JSONArray();
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getPersonRuleInfo(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno"); // 提交功能
        String ccno = RequestUtils.getString(request, "ccno"); // 提交功能
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("ccno", ccno);
        conditionBean.put("actno", actno);
        
        DBObject less = new BasicDBObject();
        less.put("_id", -1);
        less.put("ruleno", 1);
        
        Object obj = commonDaoImpl.searchLessCommonOne("p_useraction", conditionBean, less);
        
        JSONObject resultObject = null;
        if (obj != null) {
            JSONObject jo = JSONObject.fromObject(obj);
            resultObject = new JSONObject();
            
            resultObject.put("ruletype", JSONUtils.getString(jo, "ruletype"));
            resultObject.put("rulestart", JSONUtils.getString(jo, "rulestart"));
            resultObject.put("ruleend", JSONUtils.getString(jo, "ruleend"));
            resultObject.put("submit", JSONUtils.getString(jo, "submit"));
        }
        
        return resultObject;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object getRule(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno"); // 提交功能
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        
        JSONArray json =
            JSONArray.fromObject(commonDaoImpl.searchCommon(TableNameConstant.T_ACCESS_RULE, conditionBean));
        
        JSONObject joBean = null;
        String ruleno = "";
        String ruletype = "";
        String rulestart = "";
        String ruleend = "";
        
        JSONArray resultArray = new JSONArray();
        JSONObject resultObject = null;
        for (int i = 0; i < json.size(); i++) {
            joBean = json.getJSONObject(i);
            
            ruleno = JSONUtils.getString(joBean, "ruleno");
            ruletype = JSONUtils.getString(joBean, "ruletype");
            rulestart = JSONUtils.getString(joBean, "rulestart");
            ruleend = JSONUtils.getString(joBean, "ruleend");
            
            resultObject = new JSONObject();
            resultObject.put("id", ruleno + AplicationKeyConstant.STRING_SPLIT_CHAR + ruletype
                + AplicationKeyConstant.STRING_SPLIT_CHAR + rulestart + AplicationKeyConstant.STRING_SPLIT_CHAR
                + ruleend);
            resultObject.put("name", ruleno);
            
            resultArray.add(resultObject);
        }
        
        return resultArray;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object saveRuleToUserAction(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno"); // 提交功能
        String ccno = RequestUtils.getString(request, "ccno"); // 用户编码
        String ruletype = RequestUtils.getString(request, "ruletype"); // 规则类型
        String rulestart = RequestUtils.getString(request, "rulestart"); // 规则开始
        String ruleend = RequestUtils.getString(request, "ruleend"); // 规则结束
        // String submit = RequestUtils.getString(request, "submit"); // 提交人员
        
        String timetype = RequestUtils.getString(request, "timetype"); // 规则结束
        String timearg = RequestUtils.getString(request, "timearg"); // 规则结束
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        conditionBean.put("ccno", ccno);
        
        Object obj = commonDaoImpl.searchCommon(TableNameConstant.T_ACCESSS_RULES, conditionBean);
        
        if (obj == null || "[]".equals(obj.toString())) { // 如果不存在数据，就新增
        
            JSONArray json = new JSONArray();
            JSONObject jo = new JSONObject();
            jo.put("timetype", timetype);
            jo.put("timeparam", timearg);
            jo.put("ruletype", ruletype);
            jo.put("rulestart", rulestart);
            jo.put("ruleend", ruleend);
            
            json.add(jo);
            
            conditionBean.put("ruleno", StringUtils.getUniqueString());
            conditionBean.put("rules", json);
            
            commonDaoImpl.insertCommon(TableNameConstant.T_ACCESSS_RULES, conditionBean);
            
        }
        else { // 否则，修改
        
            DBObject updateBean = new BasicDBObject();
            
            JSONObject jo = new JSONObject();
            jo.put("timetype", timetype);
            jo.put("timeparam", timearg);
            jo.put("ruletype", ruletype);
            jo.put("rulestart", rulestart);
            jo.put("ruleend", ruleend);
            
            updateBean.put("rules", jo);
            
            commonDaoImpl.updateCommonPush(TableNameConstant.T_ACCESSS_RULES, conditionBean, updateBean);
        }
        
        return new JSONArray();
    }
    
    public Object saveRuleSubmit(HttpServletRequest request) {
        
        String actno = RequestUtils.getString(request, "actno"); // 提交功能
        String ccno = RequestUtils.getString(request, "ccno"); // 用户编码
        String submit = RequestUtils.getString(request, "submit"); // 用户编码
        
        DBObject insertBean = new BasicDBObject();
        insertBean.put("actno", actno);
        insertBean.put("ccno", ccno);
        insertBean.put("submit", submit);
        
        return null;// commonDaoImpl.insertCommon(TableNameConstant.T_USERACTION, insertBean);
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public JSONArray getSchedule(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String uuid = JSONUtils.getString(userBean, "uuid");
        
        String map = "function(){emit(this.actno,{count:1})}";
        
        String reduce =
            "function(key,emits){ total=0; for(var i in emits){ total+=1;  } return {count:total,size:emits.length,key:key}; }";
        
        DBObject conditionBean = new BasicDBObject();
        
        JSONObject jo = new JSONObject();
        jo.put("uuid", uuid);
        jo.put("state", "0");
        
        JSONObject elemMatch = new JSONObject();
        elemMatch.put("$elemMatch", jo);
        conditionBean.put("flows", elemMatch);
        
        JSONArray json = commonDaoImpl.mapReduceCommon("p_flow", map, reduce, conditionBean);
        
        JSONObject joBean = null;
        JSONObject products = null;
        JSONArray productList = new JSONArray();
        Object obj = null;
        JSONObject values = null;
        
        // 限定查询字段
        DBObject less = new BasicDBObject();
        less.put("_id", 0);
        less.put("name", 1);
        less.put("code", 1);
        less.put("url", 1);
        
        for (int i = 0; i < json.size(); i++) {
            joBean = json.getJSONObject(i);
            
            conditionBean = new BasicDBObject();
            conditionBean.put("code", JSONUtils.getString(joBean, "_id"));
            
            obj = commonDaoImpl.searchLessCommonOne(TableNameConstant.T_PRODUCT_ITEM, conditionBean, less);
            
            if (obj != null) {
                products = JSONObject.fromObject(obj);
                values = JSONObject.fromObject(JSONUtils.getString(joBean, "value"));
                products.put("count", JSONUtils.getString(values, "count"));
                productList.add(products);
            }
            
        }
        
        return productList;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public JSONArray scheduleList(HttpServletRequest request) {
        
        // 分页
        int pageIndex = RequestUtils.getInt(request, "pageIndex") + 1;
        int pageSize = RequestUtils.getInt(request, "pageSize");
        
        // 字段排序
        String sortField = RequestUtils.getString(request, "sortField");
        String sortOrder = RequestUtils.getString(request, "sortOrder");
        
        String actno = RequestUtils.getString(request, "actno");
        
        JSONArray scheduleList = new JSONArray();
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        conditionBean.put("state", StateConstant.B_COMPANY_APPLY_STATE0);
        Object obj = commonDaoImpl.searchCommon(TableNameConstant.T_FLOW, conditionBean);
        
        JSONArray jsonAry = new JSONArray();
        
        String level = ""; // 数据所处路径
        JSONArray flowArray = null; // 流程数组
        JSONObject flowObject = null; // 一个流程
        JSONObject useraction = null;
        
        if (obj != null) {
            
            scheduleList = JSONArray.fromObject(obj);
            
            JSONObject jo = null;
            JSONObject flowObj = null;
            for (int i = 0; i < scheduleList.size(); i++) {
                
                jo = scheduleList.getJSONObject(i);
                
                flowArray = JSONUtils.getJSONArray(jo, "flows");
                for (int j = 0; j < flowArray.size(); j++) { // 循环获取状态为0的对象
                
                    flowObject = flowArray.getJSONObject(j);
                    
                    if ("0".equals(JSONUtils.getString(flowObject, "state"))) { // 为0：当前未审批的
                        flowObj = flowObject;
                    }
                }
                
                if (flowObj != null) { // 存在值，查询对应的产品表，url
                
                    conditionBean = new BasicDBObject();
                    conditionBean.put("code", JSONUtils.getString(flowObj, "actno"));
                    
                    // 获取单个产品
                    Object product = commonDaoImpl.searchCommonOne(TableNameConstant.T_PRODUCT_ITEM, conditionBean);
                    
                    JSONObject jsonObject = JSONObject.fromObject(product);
                    flowObj.put("content", JSONUtils.getString(jo, "content"));
                    flowObj.put("key", JSONUtils.getString(jo, "key"));
                    flowObj.put("urlaction", JSONUtils.getString(jsonObject, "url"));
                    
                    // url需要查询上级,先获取到等级
                    level = JSONUtils.getString(jsonObject, "levadd");
                    
                    // // 查询条件 children.children.code
                    // conditionBean = new BasicDBObject();
                    // conditionBean.put(level + ".code", JSONUtils.getString(jsonObject, "code"));
                    //
                    // if (level.length() > 8) { // 至少为children.children
                    // level = level.substring(0, level.length() - 9);
                    // }
                    //
                    // DBObject productLess = new BasicDBObject();
                    // productLess.put(level, 1);
                    //
                    // // 查询产品表
                    // Object products =
                    // commonDaoImpl.searchLessCommonOne(TableNameConstant.T_PRODUCTS, conditionBean, productLess);
                    
                    // WebService ws = new WebService();
                    // ws.setAddress("localhost:8080");
                    // ws.setMethodName("getProductForLevCode");
                    // ws.setProperty(new Object[] {level, JSONUtils.getString(jsonObject, "code")});
                    // String products = ws.getString();
                    //
                    // if (products != null && !"".equals(products)) {
                    //
                    // JSONObject productObject = JSONObject.fromObject(products);
                    // JSONArray productArray = new JSONArray();
                    // productArray.add(productObject);
                    //
                    // // 获取到上一级的url
                    // String url =
                    // CommonUtils.getResultString(productArray, "code", JSONUtils.getString(jsonObject, "code"));
                    //
                    // // 写死了
                    //
                    // flowObj.put("url", "jsp/admin/companyapply/detail.jsp");
                    // }
                    
                    flowObj.put("url", "jsp/admin/companyapply/detail.jsp");
                    
                    flowObj.put("flowno", JSONUtils.getString(jo, "flowno"));
                    
                    conditionBean = new BasicDBObject();
                    conditionBean.put("ccno", JSONUtils.getString(flowObj, "ccno"));
                    conditionBean.put("actno", actno);
                    
                    flowObj.put("ruletype", JSONUtils.getString(useraction, "ruletype"));
                    flowObj.put("rulestart", JSONUtils.getString(useraction, "rulestart"));
                    flowObj.put("ruleend", JSONUtils.getString(useraction, "ruleend"));
                    
                    jsonAry.add(flowObj);
                }
            }
            
        }
        return jsonAry;
    }
    
    public JSONObject getProductListName(HttpServletRequest request) {
        
        String industryno = RequestUtils.getString(request, "industryno");
        String productName = RequestUtils.getString(request, "productName");
        
        DBObject conditionBean = new BasicDBObject();
        
        if (StringUtils.isNotBlank(industryno)) // 行业uuid
            conditionBean.put("industryuuid", industryno);
        
        if (StringUtils.isNotBlank(productName) && !"null".equals(productName)) { // 产品名称
            Pattern pattern = Pattern.compile(productName, Pattern.CASE_INSENSITIVE);
            
            conditionBean.put("children.name", pattern);
        }
        // 查询条数
        long count = companyapplyDao.count(TableNameConstant.T_PRODUCT_LIST, conditionBean);
        if (count <= 0) {
            return null;
        }
        // 字段排序 判断排序是否为空
        String sortOrder = RequestUtils.getString(request, "sortOrder");
        DBObject orderBy = new BasicDBObject();
        if (StringUtils.isNotBlank(sortOrder)) {// String转Int
            orderBy.put(RequestUtils.getString(request, "sortField"), StringUtils.setOrder(sortOrder));
        }
        DBObject field = new BasicDBObject();
        List<DBObject> dbList =
            companyapplyDao.list(TableNameConstant.T_PRODUCT_LIST,
                conditionBean,
                field,
                orderBy,
                RequestUtils.getInt(request, "pageIndex") + 1,
                RequestUtils.getInt(request, "pageSize"));
        JSONObject resultBean = new JSONObject();
        JSONArray json = JSONUtils.resertJSON(dbList);// 转_id
        resultBean.put("data", json.toString());
        resultBean.put("total", count);
        return resultBean;
    }
    
    /*-------------------------------------------------------------------------------------------*/
    
    public Object timeType(HttpServletRequest request) {
        
        Object result = commonDaoImpl.searchCommonAll(TableNameConstant.T_TIME_TYPE);
        
        JSONArray timeTypeList = new JSONArray();
        
        if (result != null) { // 结果是否为空
            JSONArray resultArray = JSONArray.fromObject(result);
            
            JSONObject timeType = null;
            
            JSONObject jo = null;
            for (int i = 0; i < resultArray.size(); i++) { // 循环查询结果
                jo = resultArray.getJSONObject(i);
                
                timeType = new JSONObject(); // new一个功能对象
                timeType.put("id", JSONUtils.getString(jo, "typeno"));
                timeType.put("name", JSONUtils.getString(jo, "name"));
                
                timeTypeList.add(timeType); // 存入集合
            }
        }
        
        return timeTypeList;
    }
    
    public String getAuditPower(HttpServletRequest request) {
        
        return AuditUtils.whetherAuthority1(request);
    }
    
    public JSONObject getRuleRecord(HttpServletRequest request) {
        
        JSONObject json = new JSONObject();
        String uuid = RequestUtils.getString(request, "uuid");
        String actno = RequestUtils.getString(request, "actno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("uuid", uuid);
        conditionBean.put("actno", actno);
        
        DBObject dbObject = commonDaoImpl.searchCommonOne(TableNameConstant.T_ACCESSS_RULES, conditionBean);
        if (dbObject != null) {
            int pageIndex = RequestUtils.getInt(request, "pageIndex");
            String pageSize = RequestUtils.getString(request, "pageSize");
            // 查询出rules的数据 返回的界面
            List<DBObject> list = DBObejctUtils.getList(dbObject, "rules");
            if (CollectionUtils.isNotEmpty(list)) {
                Integer endSize =
                    (pageIndex + 1) * NumberUtils.stringToint(pageSize) > list.size() ? list.size() : (pageIndex + 1)
                        * NumberUtils.stringToint(pageSize);
                json.put("total", list.size());
                json.put("data", list.subList(pageIndex * NumberUtils.stringToint(pageSize), endSize));
                return json;
            }
        }
        return json;
    }
}
