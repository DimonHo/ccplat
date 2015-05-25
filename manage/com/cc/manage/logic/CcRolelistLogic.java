package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcRolelistDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 岗位管理逻辑处理类。 包含岗位管理的增删改查等逻辑操作。
 * 
 * @author zzf
 * @since CcRolelistLogic1.0
 */
@Component
public class CcRolelistLogic {
    
    /**
     * 岗位管理数据库接口
     */
    @Autowired
    private CcRolelistDao ccRolelistDao;
    
    /**
     * 查询所有岗位数据集合
     * 
     * @param request 请求对象
     * @return 返回查询出的所有岗位数据集合
     */
    @SuppressWarnings("unchecked")
    public List<DBObject> listRole(HttpServletRequest request) {
        DBObject orderBean = new BasicDBObject();
        orderBean.put("createtime", 1);
        DBObject queryBean = new BasicDBObject();
        String isDelete = RequestUtils.getString(request, "isDelete");
        if ("true".equals(isDelete)) {
            queryBean.put("state", StateConstant.CC_NUMBER_STATE2);
        }
        else {
            queryBean.put("state", StateConstant.CC_NUMBER_STATE1);
        }
        List<DBObject> roleList = ccRolelistDao.listRole(queryBean, orderBean);
        // 将岗位按级别排序
        if (CollectionUtils.isNotEmpty(roleList)) {
            for (DBObject roleRoot : roleList) {
                List<DBObject> roles = (List<DBObject>)roleRoot.get("position");
                // 按级别排序
                Collections.sort(roles, new Comparator() {
                    public int compare(Object obj1, Object obj2) {
                        DBObject role1 = (DBObject)obj1;
                        DBObject role2 = (DBObject)obj2;
                        int flag = ((Integer)role1.get("lev")).compareTo((Integer)role2.get("lev"));
                        return flag;
                    }
                });
            }
        }
        return roleList;
    }
    
    /**
     * 新增岗位数据
     * 
     * @param request 请求对象
     * @return 返回操作是否成功
     */
    public String addRole(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 新增名称
            String name = JSONUtils.getString(jsonBean, "name");
            // 新增备注
            String remark = JSONUtils.getString(jsonBean, "remark");
            // 新增级别
            int lev = JSONUtils.getInt(jsonBean, "lev");
            // 选中的模版uuid
            String uuid = JSONUtils.getString(jsonBean, "uuid");
            if (StringUtils.isNotBlank(uuid)) {// uuid不为空，则为新增模版下的岗位
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                
                DBObject insertBean = new BasicDBObject();
                String newuuid = UUID.randomUUID().toString();
                insertBean.put("uuid", newuuid);
                insertBean.put("name", name);
                insertBean.put("remark", remark);
                insertBean.put("lev", lev);
                
                DBObject pushBean = new BasicDBObject();
                pushBean.put("position", insertBean);
                
                String result = ccRolelistDao.addRole(queryBean, pushBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("新增岗位,成功;");
                content.append("名称:" + name);
                content.append(";备注:" + remark);
                content.append(";级别:" + lev);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), uuid, request);
                    return ResultConstant.SUCCESS;
                }
                
            }
            else {// uuid为空，则为新增模版
                DBObject insertBean = new BasicDBObject();
                uuid = UUID.randomUUID().toString();
                insertBean.put("uuid", uuid);
                insertBean.put("name", name);
                insertBean.put("remark", remark);
                insertBean.put("createtime", new Date());
                insertBean.put("state", StateConstant.CC_NUMBER_STATE1);
                insertBean.put("position", new ArrayList<DBObject>());
                insertBean.put("history", new ArrayList<DBObject>());
                
                String result = ccRolelistDao.addRole(insertBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("新增模版,成功;");
                content.append("名称:" + name);
                content.append(";备注:" + remark);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), uuid, request);
                    return ResultConstant.SUCCESS;
                }
            }
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 修改岗位数据
     * 
     * @param request 请求对象
     * @return 返回操作是否成功
     */
    public String modifyRole(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        if (StringUtils.isNotBlank(data)) {
            JSONObject jsonBean = JSONObject.fromObject(data);
            // 修改后的名称
            String name = JSONUtils.getString(jsonBean, "name");
            // 修改后的备注
            String remark = JSONUtils.getString(jsonBean, "remark");
            // 修改后的级别
            int lev = JSONUtils.getInt(jsonBean, "lev");
            // 选中的模版uuid
            String uuid = JSONUtils.getString(jsonBean, "uuid");
            // 当前修改岗位的uuid
            String currentuuid = JSONUtils.getString(jsonBean, "currentuuid");
            if (uuid.equals(currentuuid)) {// 修改节点为模版时
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                
                DBObject updateBean = new BasicDBObject();
                updateBean.put("name", name);
                updateBean.put("remark", remark);
                
                String result = ccRolelistDao.modifyRole(queryBean, updateBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("修改模版,成功;");
                content.append("名称:" + name);
                content.append(";备注:" + remark);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), uuid, request);
                    return ResultConstant.SUCCESS;
                }
                
            }
            else {// 修改节点为岗位时
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                queryBean.put("position.uuid", currentuuid);
                
                DBObject updateBean = new BasicDBObject();
                updateBean.put("position.$.name", name);
                updateBean.put("position.$.remark", remark);
                updateBean.put("position.$.lev", lev);
                
                String result = ccRolelistDao.modifyRole(queryBean, updateBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("修改岗位,成功;");
                content.append("名称:" + name);
                content.append(";备注:" + remark);
                content.append(";级别:" + lev);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), uuid, request);
                    return ResultConstant.SUCCESS;
                }
            }
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除岗位数据
     * 
     * @param request 请求对象
     * @return 返回操作是否成功
     */
    public String deleteRole(HttpServletRequest request) {
        // 当前选择节点
        String uuid = RequestUtils.getString(request, "uuid");
        // 当前选中节点所在模版
        String rootuuid = RequestUtils.getString(request, "rootuuid");
        if (StringUtils.isNotBlank(uuid)) {
            if (uuid.equals(rootuuid)) {// 选中节点为模版时
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                
                DBObject updateBean = new BasicDBObject();
                // 此处为逻辑删除，将模版设置为未启用
                updateBean.put("state", StateConstant.CC_NUMBER_STATE2);
                
                String result = ccRolelistDao.deleteRootRole(queryBean, updateBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("删除模版,成功;");
                content.append("uuid:" + uuid);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), uuid, request);
                    return ResultConstant.SUCCESS;
                }
            }
            else {// 选中节点为岗位时
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", rootuuid);
                queryBean.put("position.uuid", uuid);
                
                // 删除数据对象
                DBObject conditionBean = new BasicDBObject();
                conditionBean.put("uuid", uuid);
                DBObject deleteBean = new BasicDBObject();
                deleteBean.put("position", conditionBean);
                
                String result = ccRolelistDao.deleteRole(queryBean, deleteBean);
                
                // 历史记录
                StringBuilder content = new StringBuilder();
                content.append("删除岗位,成功;");
                content.append("uuid:" + uuid);
                if (ResultConstant.SUCCESS.equals(result)) {
                    addHistory(content.toString(), rootuuid, request);
                    return ResultConstant.SUCCESS;
                }
            }
        }
        
        return ResultConstant.FAIL;
    }
    
    /**
     * 保存岗位树
     * 
     * @param request
     * @return
     */
    public String saveAll(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        // 历史记录
        String history = RequestUtils.getString(request, "history");
        // 需要删除的模版id
        String deleteCode = RequestUtils.getString(request, "deleteCode");
        if (StringUtils.isNotBlank(data)) {
            JSONArray jsonBean = JSONArray.fromObject(data);
            JSONObject hisJson = JSONObject.fromObject(history);
            JSONArray deleteCodes = JSONArray.fromObject(deleteCode);
            for (int j = 0; j < deleteCodes.size(); j++) {
                String uuid = deleteCodes.getString(j);
                DBObject conditionBean = new BasicDBObject();
                conditionBean.put("uuid", uuid);
                // 将状态更新为2,逻辑删除
                DBObject delBean = new BasicDBObject();
                delBean.put("state", StateConstant.CC_NUMBER_STATE2);
                
                String result = ccRolelistDao.deleteRootRole(conditionBean, delBean);
                
                if (ResultConstant.SUCCESS.equals(result)) {
                    // 历史记录
                    // addHistory(hisJson.get(uuid),uuid,request);
                    // 查询数据对象
                    DBObject hisQueryBean = new BasicDBObject();
                    hisQueryBean.put("uuid", uuid);
                    HistoryUtils.saveHistory(hisJson.get(uuid), request, "cc_rolelist", hisQueryBean);
                }
            }
            
            for (int i = 0; i < jsonBean.size(); i++) {
                JSONObject rootFrame = (JSONObject)jsonBean.get(i);
                String uuid = (String)rootFrame.get("uuid");
                // 修改数据对象
                DBObject updateBean = new BasicDBObject();
                updateBean.put("name", rootFrame.get("name"));
                updateBean.put("remark", rootFrame.get("remark"));
                updateBean.put("state", StateConstant.CC_NUMBER_STATE1);
                updateBean.put("position", rootFrame.get("position"));
                
                // 查询数据对象
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                // 如果没有根节点就新增
                DBObject insertBean = new BasicDBObject();
                insertBean.put("uuid", uuid);
                insertBean.put("name", rootFrame.get("name"));
                insertBean.put("remark", rootFrame.get("remark"));
                insertBean.put("state", StateConstant.CC_NUMBER_STATE1);
                insertBean.put("createtime", new Date());
                insertBean.put("position", rootFrame.get("position"));
                insertBean.put("history", new ArrayList<DBObject>());
                
                String result = ccRolelistDao.saveRoleTree(queryBean, updateBean, insertBean);
                if (ResultConstant.SUCCESS.equals(result)) {
                    // 历史记录
                    if (hisJson.get(uuid) != null) {
                        // 查询数据对象
                        DBObject hisQueryBean = new BasicDBObject();
                        hisQueryBean.put("uuid", uuid);
                        HistoryUtils.saveHistory(hisJson.get(uuid), request, "cc_rolelist", hisQueryBean);
                        // addHistory(hisJson.get(uuid),uuid,request);
                    }
                }
            }
            return ResultConstant.SUCCESS;
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 记录历史操作
     * 
     * @param type 操作类型
     * @param content 操作内容
     * @return
     */
    public String addHistory(Object content, String uuid, HttpServletRequest request) {
        
        // 查询数据对象
        DBObject queryBean = new BasicDBObject();
        queryBean.put("uuid", uuid);
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
        
        return ccRolelistDao.addHistroy(queryBean, pushBean);
    }
    
    /**
     * 提交审批
     * 
     * @return
     */
    public String submit(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        String usercode = RequestUtils.getString(request, "usercode");
        
        if (StringUtils.isNotBlank(data)) {
            
            // 获取当前登录用户
            JSONObject userSession =
                JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
            String ccno = JSONUtils.getString(userSession, "ccno");
            
            JSONObject joBean = new JSONObject();
            joBean.put("ccno", ccno);
            joBean.put("submitno", usercode);
            joBean.put("content", "申请提交");
            joBean.put("sub", "gangweiapply");
            // 提交审批
            String[] result = AuditUtils.register(joBean);
            // DBObject conditionBean = new BasicDBObject();
            if (ResultConstant.SUCCESS.equals(result[0])) {
                
                return ResultConstant.SUCCESS;
                
            }
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 审核同意
     * 
     * @param request
     */
    public String check(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "param");
        if (StringUtils.isNotBlank(data)) {
            AuditUtils.AuditRegister(data);
            return ResultConstant.SUCCESS;
        }
        return ResultConstant.FAIL;
    }
}
