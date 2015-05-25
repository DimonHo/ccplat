package com.cc.manage.dao.impl;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcAuditDao;
import com.cc.manage.service.WebService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CcAuditDaoImpl implements CcAuditDao {
    
    @Autowired
    private CommonDaoImpl commonDaoImpl;
    
    @Autowired
    private BusinessService businessService;
    
    /**
     * 查询产品分配信息
     * 
     * @param request
     * @param flag
     * @return object
     * @author DK
     */
    public Object list(HttpServletRequest request, boolean flag) {
        
        String companyno = RequestUtils.getString(request, "companyno");
        
        // 获得ip和端口
        JSONObject addressObject =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject(
                "companyno", companyno)));
        String address = JSONUtils.getString(addressObject, "ipAddress");
        address += ":" + JSONUtils.getString(addressObject, "port");
        
        // 通过webservice调用企业内部的接口
        WebService ws = new WebService();
        ws.setAddress(address);
        ws.setMethodName("listProduct");
        ws.setProperty(new Object[] {companyno});
        String result = ws.getString();
        
        
        return JSONArray.fromObject(result);
        
    }
    
    /***
     * 去除类别中的产品信息
     * 
     * @param json
     * @param result
     * @param id
     */
    public void getArray(JSONArray json, JSONArray result, String id) {
        
        for (int i = 0; i < json.size(); i++) {
            JSONObject bean = json.getJSONObject(i);
            if (StringUtils.isNotBlank(JSONUtils.getString(bean, "state"))) {
                json.remove(i);
                // getArray(json, result, id);
                i--;
            }
            else {
                bean.put("index", i);
                bean.put("id", id);
                result.add(bean);
            }
            JSONArray json1 = JSONUtils.getJSONArray(bean, "children");
            if (!CollectionUtils.isEmptys(json1)) {
                getArray(json1, result, id);
            }
        }
    }
    
    /***
     * 去除类别中的产品信息
     * 
     * @param json
     * @param result
     * @param id
     */
    public void setJson(JSONArray json, String str, String mainId) {
        
        JSONArray children = JSONArray.fromObject(str);
        
        JSONObject childrenBean = null;
        JSONObject childrenleaf = null;
        
        // JSONArray jsonBean = new JSONArray();
        
        String id = "";
        
        for (int i = 0; i < children.size(); i++) {
            
            childrenBean = children.getJSONObject(i);
            childrenleaf = new JSONObject();
            
            id = JSONUtils.getString(childrenBean, "code");
            
            childrenleaf.put("id", id);
            childrenleaf.put("name", JSONUtils.getString(childrenBean, "name"));
            childrenleaf.put("pid", mainId);
            
            json.add(childrenleaf);
            
            setJson(json, JSONUtils.getString(childrenBean, "children"), id);
        }
        
    }
    
    public Object getProductAct(DBObject conditionBean) {
        
        return null;
    }
    
}
