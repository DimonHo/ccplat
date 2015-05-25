package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcProductslistDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 产品管理逻辑处理类。 包含产品管理的增删改查等逻辑操作。
 * 
 * @author zzf
 * @since CcProductslistLogic1.0
 */
@Component
public class CcProductslistLogic {
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcProductslistLogic.class);
    
    /**
     * 产品管理数据库接口
     */
    @Autowired
    private CcProductslistDao ccProductslistDao;
    
    /**
     * 查询行业对应产品模版数据集合
     * 
     * @param request 请求对象
     * @return 返回查询出的组织架构模版数据集合
     */
    public List<DBObject> listProduct(HttpServletRequest request) {
        // 获取选中的行业节点uuid
        String industryUUID = RequestUtils.getString(request, "industryuuid");
        String isDelete = RequestUtils.getString(request, "isDelete");
        
        DBObject queryBean = new BasicDBObject();
        queryBean.put("industryuuid", industryUUID);
        if ("true".equals(isDelete)) {
            queryBean.put("state", StateConstant.CC_NUMBER_STATE2);
        }
        else {
            queryBean.put("state", StateConstant.CC_NUMBER_STATE1);
        }
        
        DBObject orderBean = new BasicDBObject();
        orderBean.put("createtime", 1);
        
        return ccProductslistDao.listProduct(queryBean, orderBean);
    }
    
    /**
     * 保存产品树
     * 
     * @param request
     * @return
     */
    public String saveProduct(HttpServletRequest request) {
        String data = RequestUtils.getString(request, "data");
        // 历史记录
        String history = RequestUtils.getString(request, "history");
        // 当前选择行业id
        String industryuuid = RequestUtils.getString(request, "industryuuid");
        // 需要删除的模版id
        String deleteCode = RequestUtils.getString(request, "deleteCode");
        if (StringUtils.isNotBlank(data)) {
            JSONArray jsonBean = JSONArray.fromObject(data);
            JSONObject hisJson = JSONObject.fromObject(history);
            JSONArray deleteCodes = JSONArray.fromObject(deleteCode);
            for (int j = 0; j < deleteCodes.size(); j++) {
                String code = deleteCodes.getString(j);
                DBObject conditionBean = new BasicDBObject();
                conditionBean.put("code", code);
                // 将状态更新为2,逻辑删除
                DBObject delBean = new BasicDBObject();
                delBean.put("state", StateConstant.CC_NUMBER_STATE2);
                
                String result = ccProductslistDao.deleteProduct(conditionBean, delBean);
                
                if (ResultConstant.SUCCESS.equals(result)) {
                    // 历史记录
                    // addHistory(hisJson.get(code),code,request);
                    // 查询数据对象
                    DBObject hisQueryBean = new BasicDBObject();
                    hisQueryBean.put("code", code);
                    HistoryUtils.saveHistory(hisJson.get(code), request, "cc_productslist", hisQueryBean);
                }
            }
            
            for (int i = 0; i < jsonBean.size(); i++) {
                JSONObject rootProduct = jsonBean.getJSONObject(i);
                String code = JSONUtils.getString(rootProduct, "code");
                // 修改数据对象
                DBObject updateBean = new BasicDBObject();
                updateBean.put("code", code);
                updateBean.put("name", rootProduct.get("name"));
                updateBean.put("remark", rootProduct.get("remark"));
                updateBean.put("industryuuid", industryuuid);
                updateBean.put("state", StateConstant.CC_NUMBER_STATE1);
                updateBean.put("title", rootProduct.get("title"));
                updateBean.put("type", rootProduct.get("type"));
                updateBean.put("src", rootProduct.get("src"));
                updateBean.put("url", rootProduct.get("url"));
                updateBean.put("height", rootProduct.get("height"));
                updateBean.put("headHtml", rootProduct.get("headHtml"));
                updateBean.put("children", rootProduct.get("children"));
                // 查询数据对象
                DBObject queryBean = new BasicDBObject();
                queryBean.put("code", code);
                // 如果没有根节点就新增
                DBObject insertBean = new BasicDBObject();
                insertBean.put("code", code);
                insertBean.put("industryuuid", industryuuid);
                insertBean.put("name", rootProduct.get("name"));
                insertBean.put("remark", rootProduct.get("remark"));
                insertBean.put("state", StateConstant.CC_NUMBER_STATE1);
                insertBean.put("createtime", new Date());
                insertBean.put("title", rootProduct.get("title"));
                insertBean.put("type", rootProduct.get("type"));
                insertBean.put("src", rootProduct.get("src"));
                insertBean.put("url", rootProduct.get("url"));
                insertBean.put("height", rootProduct.get("height"));
                insertBean.put("headHtml", rootProduct.get("headHtml"));

                insertBean.put("children", rootProduct.get("children"));
                insertBean.put("history", new ArrayList<DBObject>());
                
                String result = ccProductslistDao.saveProduct(queryBean, updateBean, insertBean);
                if (ResultConstant.SUCCESS.equals(result)) {
                    // 历史记录
                    if (hisJson.get(code) != null) {
                        // addHistory(hisJson.get(code),code,request);
                        // 查询数据对象
                        DBObject hisQueryBean = new BasicDBObject();
                        hisQueryBean.put("code", code);
                        HistoryUtils.saveHistory(hisJson.get(code), request, "cc_productslist", hisQueryBean);
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
    public String addHistory(Object content, String code, HttpServletRequest request) {
        
        // 查询数据对象
        DBObject queryBean = new BasicDBObject();
        queryBean.put("code", code);
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
        
        return ccProductslistDao.addHistroy(queryBean, pushBean);
    }
	
	/***
	 * 通过code获取到所有产品的code以字符串分割的字符串1,2,3
	 * @param code
	 * @return String
	 * @author Ron
	 */
	@SuppressWarnings("unchecked")
    public JSONArray getProductslistCode(String code){
	    try{
	        DBObject productslistbean = ccProductslistDao.getByCode(code);
	        if(productslistbean != null){
	            List<DBObject> children = (List<DBObject>)productslistbean.get("children");
	            JSONArray result = new JSONArray();
	            this.buildProducts(result, children);
	            result.add(DBObejctUtils.getString(productslistbean, "code"));
	            return result;
	        }
	    }catch (Exception e) {
	        log.error(e.getLocalizedMessage(), e);
        }
	    return null;
	}
	
	/***
	 * 通过 children的List集合拼接处a,b,的产品code的字符串
	 * @param result
	 * @param children
	 * @author Ron
	 */
	@SuppressWarnings("unchecked")
    public void buildProducts(JSONArray result,List<DBObject> children){
	    if(result == null)
	        result = new JSONArray();
	    if(CollectionUtils.isNotEmpty(children)){
	        for(DBObject temp:children){
	            result.add(DBObejctUtils.getString(temp, "code"));
	            List<DBObject> childrenTemp = (List<DBObject>)temp.get("children");
	            if(CollectionUtils.isNotEmpty(childrenTemp)){
	                this.buildProducts(result, childrenTemp);
	            }
	        }
	    }
	}
	
}
