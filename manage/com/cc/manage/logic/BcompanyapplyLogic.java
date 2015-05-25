package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.AuditKey;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.CommonUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.impl.BcompanyapplyDaoImpl;
import com.cc.manage.dao.impl.CccompanyDaoImpl;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.cc.manage.service.AuditInterface;
import com.cc.manage.service.impl.AuditMongodbImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

/**
 * 企业注册Logic处理
 * 
 * @author yfm
 * @createTime 2014.09.17
 * 
 */
@Component
public class BcompanyapplyLogic {
    
    public static Log log = LogFactory.getLog(BcompanyapplyLogic.class);
    
    @Autowired
    private BcompanyapplyDaoImpl companyapplyDao;
    
    @Autowired
    private CccompanyDaoImpl companyDao;
    
    @Autowired
    private CcAreaLogic ccAreaLogic;
    
    @Autowired
    private CcAuditLogic auditLogic;
    
    @Autowired
    private CommonDaoImpl commonDaoImpl;
    
    @Autowired
    private CcIndustryinfoLogic ccIndustryinfoLogic;
    
    @Autowired
    private BusinessService businessService;
    
    /*-------------------------------------------------------------------------------------------------*/

    /**
     * 分页排序查询企业申请资料信息
     * 
     * @param request 企业申请注册信息查询
     * @return
     * @author Ron(modify 2014.10.17)
     */
    public JSONObject list(HttpServletRequest request) {
        
        DBObject conditionBean = new BasicDBObject();
        // 条件查询
        String inputname = RequestUtils.getString(request, "inputname");
        String state = RequestUtils.getString(request, "state");
        // 当输入关键字不为空时，进行模糊查询
        BasicDBList values = new BasicDBList();
        
        if (StringUtils.isNotBlank(inputname)) {
            Pattern pattern = Pattern.compile("^.*" + inputname + ".*$", Pattern.CASE_INSENSITIVE);// 字段进行模糊匹配
            values.add(new BasicDBObject("name", pattern));
            values.add(new BasicDBObject("contacts", pattern));
            conditionBean.put("$or", values);
        }
        
        if (StringUtils.isNotBlank(state)) {
            if (StringUtils.equals(state, "exists")) {
                // 查询 没有提交申请的企业信息
                conditionBean.put("state", new BasicDBObject("$exists", false));
            }
            else {
                conditionBean.put("state", state);
            }
        }
        
        // 查询条数
        long count = companyapplyDao.count(TableNameConstant.T_COMPANY_APPLY, conditionBean);
        if (count <= 0) {
            return null;
        }
        
        // 字段排序 判断排序是否为空
        String sortOrder = RequestUtils.getString(request, "sortOrder");
        DBObject orderBy = new BasicDBObject();
        if (StringUtils.isNotBlank(sortOrder)) {// String转Int
            orderBy.put(RequestUtils.getString(request, "sortField"), StringUtils.setOrder(sortOrder));
        }
        else {
            orderBy.put("_id", StringUtils.setOrder("1"));
        }
        
        DBObject field = new BasicDBObject();
        List<DBObject> dbList =
            companyapplyDao.list(TableNameConstant.T_COMPANY_APPLY,
                conditionBean,
                field,
                orderBy,
                RequestUtils.getInt(request, "pageIndex") + 1,
                RequestUtils.getInt(request, "pageSize"));
        // 获得所有行业名称
        Map<String, JSONObject> industryMap = this.ccIndustryinfoLogic.getIndustryInfoMap();
        // 获得所有地区
        Map<String, DBObject> areaMap = ccAreaLogic.getAreaMap();
        
        for (int i = 0; i < dbList.size(); i++) {
            DBObject dbobject = dbList.get(i);
            // 根据行业id得到行业名称
            if (CollectionUtils.isNotEmpty(industryMap)) {
                dbobject.put("industryName", JSONUtils.getString(industryMap.get(dbobject.get("industryuuid")), "name"));
            }
            // 获得所在地区
            if (CollectionUtils.isNotEmpty(areaMap)) {
                dbobject.put("areaName", DBObejctUtils.getString(areaMap.get(dbobject.get("areauuid")), "name"));
            }
        }
        JSONObject resultBean = new JSONObject();
        JSONArray json = JSONUtils.resertJSON(dbList);// 转_id
        resultBean.put("data", json.toString());
        resultBean.put("total", count);
        return resultBean;
    }
    
    /**
     * 提交企业注册申请信息
     * 
     * @param request 企业申请注册表单信息
     * @return
     */
    public String add(HttpServletRequest request) {
        DBObject conditionBean = new BasicDBObject();
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 查询营业号码是否存在（注册或者审批中），为空则表示新增
            String id = JSONUtils.getString(jsonBean, "id");
            String remark = JSONUtils.getString(jsonBean, "remark");
            
            // 新增
            if (StringUtils.isBlank(id)) {
                // 记录历史
                DBObject conditionBeannew = this.addBean(jsonBean);
                companyapplyDao.add(conditionBeannew);
                ObjectId objectId = (ObjectId)conditionBeannew.get("_id");
                if (objectId != null) {
                    // 记录历史
                    conditionBean.put("_id", objectId);
                    HistoryUtils.saveHistory(remark, request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
                    return ResultConstant.SUCCESS;
                    
                }
                else {
                    return ResultConstant.FAIL;
                }
                
            }
            // 修改
            else {
                DBObject conditionBeannew = this.addBean(jsonBean);
                conditionBean.put("_id", new ObjectId(id));
                companyapplyDao.update(conditionBean, conditionBeannew);
                // 记录历史操作
                HistoryUtils.saveHistory(remark, request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
                return ResultConstant.SUCCESS;
            }
        }
        return ResultConstant.FAIL;
    }
    
    /****
     * 组装前台传入的参数组装成DBObject对象
     * 
     * @param jsonBean
     * @return DBObject
     */
    public DBObject addBean(JSONObject jsonBean) {
        DBObject conditionBeannew = new BasicDBObject();
        conditionBeannew.put("license", JSONUtils.getString(jsonBean, "license"));
        conditionBeannew.put("name", JSONUtils.getString(jsonBean, "name"));
        conditionBeannew.put("street", JSONUtils.getString(jsonBean, "street"));
        conditionBeannew.put("contacts", JSONUtils.getString(jsonBean, "contacts"));
        conditionBeannew.put("phone", JSONUtils.getString(jsonBean, "phone"));
        conditionBeannew.put("email", JSONUtils.getString(jsonBean, "email"));
        conditionBeannew.put("industryuuid", JSONUtils.getString(jsonBean, "industryuuid"));
        conditionBeannew.put("areauuid", JSONUtils.getString(jsonBean, "areauuid"));
        conditionBeannew.put("ipAddress", JSONUtils.getString(jsonBean, "ipAddress"));
        conditionBeannew.put("port", JSONUtils.getString(jsonBean, "port"));
        conditionBeannew.put("createtime", new Date());
        DBObject addressBean = new BasicDBObject();
        addressBean.put("country", "");
        addressBean.put("province", "");
        addressBean.put("city", "");
        addressBean.put("county", "");
        conditionBeannew.put("address", addressBean);
        return conditionBeannew;
    }
    
    /**
     * 根据id查询企业申请信息
     * 
     * @param conditionBean 查询条件id
     * @return
     */
    public JSONArray query(HttpServletRequest request) {
        DBObject conditionBean = new BasicDBObject();
        String id = RequestUtils.getString(request, "id");
        String flowid = RequestUtils.getString(request, "flowid");
        if (StringUtils.isNotBlank(id)) {
            conditionBean.put("_id", new ObjectId(id));
            
        }
        if (StringUtils.isNotBlank(flowid)) {
            conditionBean.put("flowid", flowid);
            
        }
        // 根据id查询
        List<DBObject> companyapply = companyapplyDao.find(TableNameConstant.T_COMPANY_APPLY, conditionBean);
        JSONArray json = JSONUtils.resertJSON(companyapply);
        
        return json;
    }
    
    /**
     * 提交申请信息
     * 
     * @param request
     * @return
     */
    public String apply(HttpServletRequest request) {
        
        // 根据企业申请信息id修改状态为0，表示申请
        String id = RequestUtils.getString(request, "id");
        if (StringUtils.isNotBlank(id)) {
            String[] arr = id.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            DBObject conditionBean = new BasicDBObject();
            DBObject conditionBeannew = new BasicDBObject();
            for (int i = 0; i < arr.length; i++) {
                conditionBean.put("_id", new ObjectId(arr[i]));
                conditionBeannew.put("state", StateConstant.B_COMPANY_APPLY_STATE0);
                companyapplyDao.update(conditionBean, conditionBeannew);
                HistoryUtils.saveHistory("提交申请注册资料", request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
            }
            return ResultConstant.SUCCESS;
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * superuser审核申请的注册企业资料
     * 
     * @param request
     */
    public String supercheck(HttpServletRequest request) {
        // 根据企业申请信息id修改状态为1，表示审核通过
        String id = RequestUtils.getString(request, "id");
        if (StringUtils.isNotBlank(id)) {
            try {
                String arr[] = StringUtils.stringToArray(id, AplicationKeyConstant.STRING_SPLIT_CHAR);
                List<DBObject> dblist = new ArrayList<DBObject>();
                DBObject conditionBean = new BasicDBObject();
                DBObject conditionBeannew = new BasicDBObject();
                for (String _id : arr) {
                    // 根据id修改审核状态为通过1
                    conditionBean.put("_id", new ObjectId(_id));
                    conditionBeannew.put("state", StateConstant.B_COMPANY_APPLY_STATE1);
                    companyapplyDao.update(conditionBean, conditionBeannew);
                    HistoryUtils.saveHistory("审核注册资料", request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
                    // 查询审核后的数据
                    List<DBObject> dbobject = companyapplyDao.find(TableNameConstant.T_COMPANY_APPLY, conditionBean);
                    DBObject object = dbobject.get(0);
                    object.put("companyno", String.valueOf(System.nanoTime()));
                    DBObject stateBean = new BasicDBObject();
                    stateBean.put("frame", StateConstant.B_COMPANY_UNALLOCAT);
                    stateBean.put("role", StateConstant.B_COMPANY_UNALLOCAT);
                    stateBean.put("product", StateConstant.B_COMPANY_UNALLOCAT);
                    stateBean.put("companycc", StateConstant.B_COMPANY_UNALLOCAT);
                    object.put("state", stateBean);
                    object.removeField("_id");
                    object.removeField("history");
                    dblist.add(object);
                    
                }
                companyDao.addBatch(dblist);// 批量复制到企业表
                return ResultConstant.SUCCESS;
                
            }
            catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
            
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 编辑历史记录
     * 
     * @param request
     */
    public void editHistory(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 根据id添加历史记录
            String id = JSONUtils.getString(jsonBean, "id");
            String remark = JSONUtils.getString(jsonBean, "remark");
            
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            HistoryUtils.saveHistory(remark, request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
            
        }
        
    }
    
    /**
     * 查询历史记录
     * 
     * @param conditionBean 查询条件营业号码
     * @param byname 操作人
     * @return
     */
    public JSONObject history(HttpServletRequest request) {
        DBObject conditionBean = new BasicDBObject();
        
        String id = RequestUtils.getString(request, "id");
        if (StringUtils.isNotBlank(id)) {
            conditionBean.put("_id", new ObjectId(id));
            
        }
        List<DBObject> List = new ArrayList<DBObject>();
        List<DBObject> dblist = companyapplyDao.find(TableNameConstant.T_COMPANY_APPLY, conditionBean);
        for (int i = 0; i < dblist.size(); i++) {
            @SuppressWarnings("unchecked")
            List<DBObject> histroyList = (List<DBObject>)dblist.get(i).get("history");
            for (int j = 0; j < histroyList.size(); j++) {
                DBObject history = histroyList.get(j);
                List.add(history);
            }
        }
        JSONObject jo = new JSONObject();
        jo.put("data", List.toString());
        jo.put("total", List.size());
        return jo;
    }
    
    /**
     * 提交审批申请
     * 
     * @return
     */
    public String submit(HttpServletRequest request) {
        String id = RequestUtils.getString(request, "data");
        String submitno = RequestUtils.getString(request, "usercode");// 提交人编码，actno的组合
        String code = RequestUtils.getString(request, "code");// 提交人编码，actno的组合
        String key = RequestUtils.getString(request, "key");// 关键字

        if (StringUtils.isNotBlank(id)) {
            // 获取当前登录用户
            JSONObject userSession =
                JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
            String uuid = JSONUtils.getString(userSession, "uuid");
            
            JSONObject joBean = new JSONObject();
            joBean.put("uuid", uuid);
            joBean.put("submitno", submitno);
            joBean.put("content", "申请企业注册");
            joBean.put("sub", code);
            joBean.put("key", key);
            // 提交审批
            String[] result = AuditUtils.register(joBean);
            DBObject conditionBean = new BasicDBObject();
            if (ResultConstant.SUCCESS.equals(result[0])) {
                DBObject insertBean = new BasicDBObject();
                insertBean.put("flowid", result[1]);
                if (StringUtils.isNotBlank(id)) {
                    String[] arr = id.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
                    for (int i = 0; i < arr.length; i++) {
                        conditionBean.put("_id", new ObjectId(arr[i]));
                        insertBean.put("state", StateConstant.B_COMPANY_APPLY_STATE0);// 申请
                        companyapplyDao.update(conditionBean, insertBean);
                        HistoryUtils.saveHistory("提交申请注册资料", request, TableNameConstant.T_COMPANY_APPLY, conditionBean);
                    }
                    return ResultConstant.SUCCESS;
                    
                }
            }
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 查询审核流程列表
     * 
     * @param request
     * @return
     */
    public JSONObject getDetailInfo(HttpServletRequest request) {
        
        DBObject conditionBean = new BasicDBObject();
        String table = RequestUtils.getString(request, "table");
        List<DBObject> List = new ArrayList<DBObject>();
        String scheduleList = this.scheduleList(request);
        if (StringUtils.isNotBlank(scheduleList)) {
            // 得到代办审核数据
            JSONArray json = JSONArray.fromObject(scheduleList);
            for (int i = 0; i < json.size(); i++) {
                JSONObject obj = json.getJSONObject(i);
                // 根据代办审核流程编码查询提交审核的功能模块
                conditionBean.put("flowid", JSONUtils.getString(obj, "flowno"));
                Object checks = commonDaoImpl.searchCommon(table, conditionBean);// b_companyapply
                
                if (checks != null) {
                    JSONArray jsonarray = JSONArray.fromObject(checks);
                    for (int j = 0; j < jsonarray.size(); j++) {
                        JSONObject jo = jsonarray.getJSONObject(j);
                        String objString = jo.toString().substring(1, jo.toString().length() - 1);// 得到{}中的数据
                        // 将功能模块数据查询代办审核数据中
                        String newString =
                            obj.toString().substring(0, obj.toString().length() - 1)
                                + AplicationKeyConstant.STRING_SPLIT_CHAR + objString
                                + obj.toString().substring(obj.toString().length() - 1, obj.toString().length());
                        // String转DBobject
                        DBObject object = (DBObject)JSON.parse(newString);
                        List.add(object);
                        
                    }
                }
            }
            JSONObject resultBean = new JSONObject();
            JSONArray jsons = JSONUtils.resertJSON(List);// 转_id
            resultBean.put("data", jsons.toString());
            resultBean.put("total", jsons.size());
            return resultBean;
        }
        return null;
    }
    
    /**
     * 审核申请的注册企业资料
     * 
     * @param request
     */
    public String check(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "param");
        
        DBObject updateBean = new BasicDBObject();
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            String flowno = (String)jsonBean.get("flowno");
            String type = JSONUtils.getString(jsonBean, "type");
            
            if (AuditKey.AGREE_TYPE.equals(type)) {// 同意审核生效
                DBObject queryBean = new BasicDBObject();
                queryBean.put("flowid", flowno);
                updateBean.put("state", StateConstant.B_COMPANY_APPLY_STATE1);// 表审核同意
                companyapplyDao.update(queryBean, updateBean);
                HistoryUtils.saveHistory("审核注册资料", request, "b_companyapply", queryBean);
                // 查询审核后的数据
                List<DBObject> dbobject = companyapplyDao.find(TableNameConstant.T_COMPANY_APPLY, queryBean);
                DBObject object = dbobject.get(0);
                // 复制到企业表
                object.put("companyno", String.valueOf(System.nanoTime()));
                DBObject stateBean = new BasicDBObject();
                stateBean.put("frame", StateConstant.B_COMPANY_UNALLOCAT);
                stateBean.put("role", StateConstant.B_COMPANY_UNALLOCAT);
                stateBean.put("product", StateConstant.B_COMPANY_UNALLOCAT);
                stateBean.put("companycc", StateConstant.B_COMPANY_UNALLOCAT);
                object.put("state", stateBean);
                object.removeField("_id");
                object.removeField("history");
                companyDao.add(object);
                HistoryUtils.saveHistory(object.get("history"),
                    request,
                    TableNameConstant.T_COMPANY,
                    new BasicDBObject());
            }
            JSONObject userSession =
                JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
            if (userSession == null) {
                return ResultConstant.FAIL;
            }
            String uuid = JSONUtils.getString(userSession, "uuid"); // 获取用户uuid
            jsonBean.put("uuid", uuid);
            
            AuditUtils.AuditRegister(jsonBean.toString());
            // 历史记录
            return ResultConstant.SUCCESS;
        }
        return ResultConstant.FAIL;
    }
    
    public String personAction(HttpServletRequest request) {
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        if (userSession == null) {
            return ResultConstant.FAIL;
        }
        String uuid = JSONUtils.getString(userSession, "uuid"); // 获取用户uuid
        String actno = RequestUtils.getString(request, "actno");// 获取功能编码
        String companyno = JSONUtils.getString(userSession, "companyno"); // 获取公司编码
        // 获得ip和端口
        String address = CommonUtils.getIpAddress(companyno);
        // 调用企业内部接口
        String resultBean = businessService.personAction(actno, uuid, address);
        if (resultBean != null) {
            resultBean =
                AplicationKeyConstant.CHARACTER_BIG_LEFT + resultBean + AplicationKeyConstant.CHARACTER_BIG_right;
        }
        JSONArray json = JSONArray.fromObject(resultBean);
        return json.toString();
    }
    
    /**
     * 是否需要审核
     */
    public String islocked(HttpServletRequest request) {
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        if (userSession == null) {
            return ResultConstant.FAIL;
        }
        String islock = JSONUtils.getString(userSession, "islock"); // 获取用户uuid
        if (StringUtils.isNotBlank(islock)) {// 超级管理员直接返回提交
            System.out.println(islock);
            return ResultConstant.POWER;
        }
        else {
            return ResultConstant.FAIL;
        }
    }
    
    public String getSchedule(HttpServletRequest request) {
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String uuid = JSONUtils.getString(userSession, "uuid");
        String companyno = JSONUtils.getString(userSession, "companyno"); // 获取公司编码
        
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
        
        // 获得ip和端口
        String address = CommonUtils.getIpAddress(companyno);
        // 调用企业内部接口
        String resultBean = businessService.getSchedule(json.toString(), address);
        if (resultBean != null) {
            resultBean =
                AplicationKeyConstant.CHARACTER_BIG_LEFT + resultBean + AplicationKeyConstant.CHARACTER_BIG_right;
        }
        return resultBean;
    }
    
    public String scheduleList(HttpServletRequest request) {
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        if (userSession == null) {
            return ResultConstant.FAIL;
        }
        
        String companyno = JSONUtils.getString(userSession, "companyno"); // 获取公司编码
        // 获得ip和端口
        String address = CommonUtils.getIpAddress(companyno);
        
        String actno = RequestUtils.getString(request, "actno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("actno", actno);
        conditionBean.put("state", StateConstant.B_COMPANY_APPLY_STATE0);
        Object obj = commonDaoImpl.searchCommon(TableNameConstant.T_FLOW, conditionBean);
        String resultBean = null;
        if (!CollectionUtils.isEmpty(obj.toString())) {
            // 调用企业内部接口
            resultBean = businessService.scheduleList(obj.toString(), actno, address);
            if (StringUtils.isNotBlank(resultBean)) {
                resultBean =
                    AplicationKeyConstant.CHARACTER_BIG_LEFT + resultBean + AplicationKeyConstant.CHARACTER_BIG_right;
            }
            return resultBean;
            
        }
        return resultBean;
    }
    
    /**
     * 获取权限规则
     */
    public String getAuditPower(HttpServletRequest request) {
        
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        if (userSession == null) {
            return ResultConstant.IP_PORT_EXCEPTION;
        }
        String companyno = JSONUtils.getString(userSession, "companyno"); // 获取公司编码
        // 获得ip和端口
        String address = CommonUtils.getIpAddress(companyno);
        
        JSONObject company = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        // 获取用户uuid
        String uuid = JSONUtils.getString(company, "uuid");
        
        String data = RequestUtils.getString(request, "param");
        JSONObject joBean = JSONObject.fromObject(data);
        String actno = JSONUtils.getString(joBean, "actno");
        String flowno = JSONUtils.getString(joBean, "flowno");
        String key = JSONUtils.getString(joBean, "key");
        
        AuditInterface auditManager = new AuditMongodbImpl();
        Object result = auditManager.getAuditPower(uuid, actno, key, flowno, address);
        
        return result.toString();
    }
}