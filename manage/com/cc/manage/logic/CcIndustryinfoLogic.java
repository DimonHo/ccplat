package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcFramelistDao;
import com.cc.manage.dao.CcIndustryinfoDao;
import com.cc.manage.dao.impl.CccompanyDaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 行业管理逻辑处理类。 包含行业管理的增删改查等逻辑操作。
 * 
 * @author zzf
 * @since CcIndustryinfoLogic1.0
 */
@Component
public class CcIndustryinfoLogic {
    
    /**
     * 行业管理数据库接口
     */
    @Autowired
    private CcIndustryinfoDao ccIndustryinfoDao;
    
    @Autowired
    private CcFramelistDao ccFramelistDao;
    
    @Autowired
    private CccompanyDaoImpl cccompanyDaoImpl;
    
    /**
     * 主要用于下拉框显示结构<br>
     * 构建单条以id,pid为树型结构的JSONArray数组
     * 
     * @param request
     * @return JSONArray
     * @author Ron
     */
    public JSONArray getIndustryInfo(HttpServletRequest request) {
        
        List<DBObject> list = ccIndustryinfoDao.listIndustryinfo();
        if(CollectionUtils.isNotEmpty(list)){
            JSONArray result = new JSONArray();
            this.build(list, result, null);
            return result;
        }
        return null;
    }
    
    /****
     * 把List<DBObject>包含children的嵌套集合的树型结构，构建成单条记录以id,pid为树型结构的JSONArray 
     * @param list 嵌套结构的集合
     * @param result 返回的单条id,pid的树型集合
     * @param pid 当为null是表示第一级
     */
    @SuppressWarnings("unchecked")
    public void build(List<DBObject> list,JSONArray result,String pid){
        if(StringUtils.isBlank(pid)){//为null表示最高级
            pid ="0";
        }
        if(CollectionUtils.isNotEmpty(list)){
            for(DBObject temp:list){
                JSONObject tempBean = new JSONObject();
                String uuid = DBObejctUtils.getString(temp, "uuid");
                tempBean.put("id", uuid);
                tempBean.put("name", temp.get("name"));
                tempBean.put("pid", pid);
                tempBean.put("uuid", uuid);
                result.add(tempBean);
                List<DBObject> tempList = (List<DBObject>)temp.get("children");
                if(!CollectionUtils.isEmpty(tempList)){
                    this.build(tempList, result, uuid);
                }
            }
        }
    }
    
    /**
     * 主要用于通过行业uuid获取单条对象<br>
     * 构建单条以id,pid为树型结构的JSONArray数组
     * 
     * @param request
     * @return JSONArray
     * @author Ron
     */
    public Map<String,JSONObject> getIndustryInfoMap() {
        
        List<DBObject> list = ccIndustryinfoDao.listIndustryinfo();
        if(CollectionUtils.isNotEmpty(list)){
            Map<String,JSONObject> result = new HashMap<String,JSONObject>();
            this.build(list, result, null);
            return result;
        }
        return null;
    }
    
    /****
     * 把List<DBObject>包含children的嵌套集合的树型结构，构建成单条记录以id,pid为树型结构的Map<String, JSONObject> 其中key值为uuid,value为结果单条对象 
     * @param list 嵌套结构的集合
     * @param result 返回的单条id,pid的Map集合key值为uuid,value为结果单条对象
     * @param pid 当为null是表示第一级
     */
    @SuppressWarnings("unchecked")
    public void build(List<DBObject> list,Map<String, JSONObject> result,String pid){
        if(StringUtils.isBlank(pid)){//为null表示最高级
            pid ="0";
        }
        if(CollectionUtils.isNotEmpty(list)){
            for(DBObject temp:list){
                JSONObject tempBean = new JSONObject();
                String uuid = DBObejctUtils.getString(temp, "uuid");
                tempBean.put("id", uuid);
                tempBean.put("name", temp.get("name"));
                tempBean.put("pid", pid);
                tempBean.put("uuid", uuid);
                result.put(uuid, tempBean);
                List<DBObject> tempList = (List<DBObject>)temp.get("children");
                if(!CollectionUtils.isEmpty(tempList)){
                    this.build(tempList, result, uuid);
                }
            }
        }
    }
    
    
    /**
     * 查询所有行业数据集合
     * 
     * @param request 请求对象
     * @return 返回查询出的所有行业数据集合
     */
    public List<DBObject> listIndustryinfo(HttpServletRequest request) {
        
        return ccIndustryinfoDao.listIndustryinfo();
    }
    
    /**
     * 查询临时表所有行业数据集合
     * 
     * @param request 请求对象
     * @return 返回查询出临时表的所有行业数据集合
     */
    public List<DBObject> listTemp(HttpServletRequest request) {
        // 流程id
        String flowid = RequestUtils.getString(request, "flowid");
        if (StringUtils.isNotBlank(flowid)) {
            DBObject queryBean = new BasicDBObject();
            queryBean.put("flowid", flowid);
            // 根据流程id查临时数据
            return ccIndustryinfoDao.listTemp(queryBean);
        }
        return null;
    }
    
    /**
     * 是否已提交审批
     * 
     * @param request
     * @return
     */
    public String isRepeat(HttpServletRequest request) {
        // 获取当前登录用户
        JSONObject userSession = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        String ccno = JSONUtils.getString(userSession, "ccno");
        // 查询是否重复提交
        DBObject queryBean = new BasicDBObject();
        queryBean.put("ccon", ccno);
        DBObject temp = ccIndustryinfoDao.getTempData(queryBean);
        if (temp != null) {
            return ResultConstant.RPEAT;
        }
        return ResultConstant.SUCCESS;
    }
    
    /**
     * 提交审批
     * 
     * @return
     */
    public String submit(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        String history = RequestUtils.getString(request, "history");
        String usercode = RequestUtils.getString(request, "usercode");
        
        if (StringUtils.isNotBlank(data)) {
            // 获取当前登录用户
            JSONObject userSession =
                JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
            String ccno = JSONUtils.getString(userSession, "ccno");
            
            JSONObject joBean = new JSONObject();
            joBean.put("ccno", ccno);
            joBean.put("submitno", usercode);
            joBean.put("content", "修改行业树");
            joBean.put("sub", "hangyexiugai");
            
            // 提交审批
            String[] result = AuditUtils.register(joBean);
            
            if (ResultConstant.SUCCESS.equals(result[0])) {
                
                DBObject insertBean = new BasicDBObject();
                insertBean.put("flowid", result[1]);
                
                JSONArray jsonBean = JSONArray.fromObject(data);
                insertBean.put("children", jsonBean);
                insertBean.put("ccon", ccno);
                
                JSONArray historyBean = JSONArray.fromObject(history);
                
                insertBean.put("history", historyBean);
                ccIndustryinfoDao.saveTemp(insertBean);
                
                return ResultConstant.SUCCESS;
            }
        }
        
        return ResultConstant.FAIL;
    }
    
    /**
     * 通过审批
     * 
     * @return
     */
    public String agree(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "param");
        
        if (StringUtils.isNotBlank(data)) {
            
            JSONObject jsonBean = JSONObject.fromObject(data);
            String flowno = JSONUtils.getString(jsonBean, "flowno");
            DBObject queryBean = new BasicDBObject();
            queryBean.put("flowid", flowno);
            DBObject tempData = ccIndustryinfoDao.getTempData(queryBean);
            
            DBObject conditionBean = new BasicDBObject();
            
            DBObject updateBean = new BasicDBObject();
            updateBean.put("children", tempData.get("children"));
            String result = ccIndustryinfoDao.saveIndustryinfo(conditionBean, updateBean);
            if (ResultConstant.SUCCESS.equals(result)) {
                AuditUtils.AuditRegister(data);
                // 删除临时数据
                ccIndustryinfoDao.deleteTemp(queryBean);
                // 历史记录
                HistoryUtils.saveHistory(tempData.get("history"), request, "cc_industryinfo", new BasicDBObject());
                // addHistory(tempData.get("history"),request);
                return ResultConstant.SUCCESS;
            }
        }
        
        return ResultConstant.FAIL;
    }
    
    /**
     * 保存行业树
     * 
     * @param request
     * @return
     */
    public String saveIndustryinfo(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        String history = RequestUtils.getString(request, "history");
        if (StringUtils.isNotBlank(data)) {
            JSONArray jsonBean = JSONArray.fromObject(data);
            // 修改数据对象
            DBObject updateBean = new BasicDBObject();
            updateBean.put("children", jsonBean);
            // 查询数据对象
            DBObject queryBean = new BasicDBObject();
            String result = ccIndustryinfoDao.saveIndustryinfo(queryBean, updateBean);
            if (ResultConstant.SUCCESS.equals(result)) {
                JSONArray hisJson = JSONArray.fromObject(history);
                HistoryUtils.saveHistory(hisJson, request, "cc_industryinfo", new BasicDBObject());
                // addHistory(hisJson,request);
                return ResultConstant.SUCCESS;
            }
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 增加行业
     * 
     * @param request 请求对象
     * @return
     */
    public String addIndustryinfo(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            // 新增数据对象
            DBObject updateBean = new BasicDBObject();
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 名称
            String name = JSONUtils.getString(jsonBean, "name");
            // 备注
            String remark = JSONUtils.getString(jsonBean, "remark");
            
            updateBean.put("uuid", UUID.randomUUID().toString());
            updateBean.put("name", name);
            updateBean.put("remark", remark);
            updateBean.put("children", new ArrayList<DBObject>());
            
            // 当前节点所处的位置
            String key = JSONUtils.getString(jsonBean, "key");
            String[] keys = {};
            String[] indexs = {};
            if (StringUtils.isNotBlank(key)) {
                keys = key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            // 当前节点所处的树型结构中的下标
            String index = JSONUtils.getString(jsonBean, "index");
            if (StringUtils.isNotBlank(index)) {
                indexs = index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            // 根据下标拼出要添加数据的数组条件
            StringBuilder queryStr = new StringBuilder();
            for (int i = 0; i < keys.length; i++) {
                if (i != 0) {
                    queryStr.append(".");
                    queryStr.append(indexs[i - 1]);
                    queryStr.append(".");
                }
                queryStr.append(keys[i]);
            }
            DBObject pushBean = new BasicDBObject();
            pushBean.put(queryStr.toString(), updateBean);
            
            // 查询数据对象
            DBObject queryBean = new BasicDBObject();
            
            String result = ccIndustryinfoDao.addIndustryinfo(queryBean, pushBean);
            // 历史记录
            StringBuilder content = new StringBuilder();
            content.append("新增,成功;");
            content.append("名称:" + name);
            content.append(";备注:" + remark);
            if (ResultConstant.SUCCESS.equals(result)) {
                // addHistory(content.toString(),request);
                return ResultConstant.SUCCESS;
            }
        }
        // addHistory("新增,失败",request);
        return ResultConstant.FAIL;
    }
    
    /**
     * 修改行业
     * 
     * @param request 请求对象
     * @return
     */
    public String modifyIndustryinfo(HttpServletRequest request) {
        
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 当前节点所处的位置
            String key = JSONUtils.getString(jsonBean, "key");
            String[] keys = {};
            String[] indexs = {};
            if (StringUtils.isNotBlank(key)) {
                keys = key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            // 当前节点所处的树型结构中的下标
            String index = JSONUtils.getString(jsonBean, "index");
            if (StringUtils.isNotBlank(index)) {
                indexs = index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            // 根据下标拼出要添加数据的数组条件
            StringBuilder queryStr = new StringBuilder();
            for (int i = 0; i < keys.length; i++) {
                queryStr.append(keys[i]);
                queryStr.append(".");
                queryStr.append(indexs[i]);
                queryStr.append(".");
            }
            
            // 封装修改后的数据
            DBObject updateBean = new BasicDBObject();
            
            // 名称
            String name = JSONUtils.getString(jsonBean, "name");
            // 备注
            String remark = JSONUtils.getString(jsonBean, "remark");
            
            updateBean.put(queryStr.toString() + "name", name);
            updateBean.put(queryStr.toString() + "remark", remark);
            
            // 查询数据对象
            DBObject queryBean = new BasicDBObject();
            queryBean.put(queryStr.toString() + "uuid", JSONUtils.getString(jsonBean, "uuid"));
            
            String result = ccIndustryinfoDao.modifyIndustryinfo(queryBean, updateBean);
            
            // 历史记录
            StringBuilder content = new StringBuilder();
            content.append("修改,成功;");
            content.append("名称:" + name);
            content.append(";备注:" + remark);
            if (ResultConstant.SUCCESS.equals(result)) {
                // addHistory(content.toString(),request);
                return ResultConstant.SUCCESS;
            }
        }
        // addHistory("修改,失败",request);
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除行业
     * 
     * @param request 请求对象
     * @return
     */
    public String deleteIndustryinfo(HttpServletRequest request) {
        // 获取选中树节点uuid
        String uuid = RequestUtils.getString(request, "uuid");
        if (StringUtils.isNotBlank(uuid)) {
            // 获取下标
            String index = RequestUtils.getString(request, "index");
            // 获取节点位置
            String key = RequestUtils.getString(request, "key");
            String[] keys = {};
            String[] indexs = {};
            if (StringUtils.isNotBlank(key)) {
                keys = key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            if (StringUtils.isNotBlank(index)) {
                indexs = index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            // 根据下标拼出要添加数据的数组条件
            StringBuilder queryStr = new StringBuilder();
            for (int i = 0; i < keys.length; i++) {
                if (i != 0) {
                    queryStr.append(".");
                    queryStr.append(indexs[i - 1]);
                    queryStr.append(".");
                }
                queryStr.append(keys[i]);
            }
            // 删除数据对象
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("uuid", uuid);
            DBObject deleteBean = new BasicDBObject();
            deleteBean.put(queryStr.toString(), conditionBean);
            
            // 查询数据对象
            DBObject queryBean = new BasicDBObject();
            
            String result = ccIndustryinfoDao.deleteIndustryinfo(queryBean, deleteBean);
            
            // 历史记录
            StringBuilder content = new StringBuilder();
            content.append("删除,成功;");
            content.append("uuid:" + uuid);
            if (ResultConstant.SUCCESS.equals(result)) {
                // addHistory(content.toString(),request);
                return ResultConstant.SUCCESS;
            }
        }
        // addHistory("删除,失败",request);
        return ResultConstant.FAIL;
    }
    
    /**
     * 是否包含关联数据
     * 
     * @param request 请求对象
     * @return
     */
    public String hasAssociated(HttpServletRequest request) {
        // 获取选中树节点uuid
        String uuid = RequestUtils.getString(request, "uuid");
        if (StringUtils.isNotBlank(uuid)) {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("industryuuid", uuid);
            List<DBObject> frame = ccFramelistDao.listFrame(conditionBean, new BasicDBObject());
            List<DBObject> company = cccompanyDaoImpl.getCompany(conditionBean);
            if (CollectionUtils.isNotEmpty(frame) || CollectionUtils.isNotEmpty(company)) {
                return "true";
            }
        }
        return "false";
    }
    
    /**
     * 记录历史操作
     * 
     * @param type 操作类型
     * @param content 操作内容
     * @return
     */
    public String addHistory(Object content, HttpServletRequest request) {
        // 查询数据对象
        DBObject queryBean = new BasicDBObject();
        // 新增数据对象
        DBObject insertBean = new BasicDBObject();
        insertBean.put("createtime", new Date());
        DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
        DBObject operator = new BasicDBObject();
        operator.put("name", DBObejctUtils.getString(userSession, "realname"));
        operator.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
        insertBean.put("operator", operator);
        insertBean.put("remark", content);
        
        DBObject pushBean = new BasicDBObject();
        pushBean.put("history", insertBean);
        
        return ccIndustryinfoDao.addHistroy(queryBean, pushBean);
    }
    
}
