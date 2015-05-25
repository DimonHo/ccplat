package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.CommonUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CcProductLogic {
    
    @Autowired
    private CommonDaoImpl commonDaoImpl;
    
    @Autowired
    private BusinessService businessService;
    
    /**
     * 根据行业查询产品树
     * 
     * @param request
     * @return
     */
    public Object productListTree(HttpServletRequest request) {
        
        DBObject conditionBean = new BasicDBObject();
        
        String industryno = RequestUtils.getString(request, "industryno");
        
        // 从页面上获取行业编码
        conditionBean.put("industryuuid", industryno);
        
        // 获取行业树
        JSONArray productList =
            JSONArray.fromObject(commonDaoImpl.searchCommon(TableNameConstant.T_PRODUCT_LIST, conditionBean));
        
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        list.add("open");
        list.add("remark");
        list.add("url");
        list.add("ico");
        list.add("height");
        list.add("type");
        list.add("src");
        list.add("headHtml");

        JSONArray result = CommonUtils.childrenList(productList, list, false, null, "");
        return result;
    }
    
    /**
     * 查询行业信息，根据公司编码查询产品树形结构
     * 
     * @param request
     * @return Object
     * @author DK
     */
    public Object productSelect(HttpServletRequest request) {
        
        String companyno = RequestUtils.getString(request, "companyno");
        JSONArray checkJSON = new JSONArray();
        if (companyno != null) {
            
            // 获得ip和端口
            JSONObject addressObject =
                JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject(
                    "companyno", companyno)));
            String address = JSONUtils.getString(addressObject, "ipAddress");
            address += ":" + JSONUtils.getString(addressObject, "port");
            // 通过webservice调用企业内部的接口
            String result = this.businessService.listProductItem(address, companyno);
            if (StringUtils.isBlank(result)) {
                result = "[]";
            }
            // 查询当前公司的所有产品
            JSONArray productsArray = JSONArray.fromObject(result);
            for (int i = 0; i < productsArray.size(); i++) {
                JSONObject jo = productsArray.getJSONObject(i);
                if ("true".equals(JSONUtils.getString(jo, "checked"))) {
                    JSONObject tempBean = new JSONObject();
                    tempBean.put("code", JSONUtils.getString(jo, "code"));
                    tempBean.put("starttime", JSONUtils.getString(jo, "starttime"));
                    tempBean.put("endtime", JSONUtils.getString(jo, "endtime"));
                    checkJSON.add(tempBean);
                }
            }
        }
        
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        list.add("open");
        list.add("remark");
        list.add("url");
        list.add("ico");
        
        list.add("title");
        list.add("height");
        list.add("src");
        list.add("type");
        list.add("headHtml");
        // 查询当前行业下的产品列表
        DBObject conditionBean = new BasicDBObject();
        // conditionBean.put("industryuuid", RequestUtils.getString(request, "industryno"));
        Object dbObjectList = commonDaoImpl.searchCommon(TableNameConstant.T_PRODUCT_LIST, conditionBean);
        JSONArray productsList = JSONArray.fromObject(dbObjectList);
        JSONArray result = CommonUtils.childrenList(productsList, list, true, checkJSON, "");
        return result;
    }
    
    /**
     * 初始化产品（分配产品）
     * 
     * @param request
     * @return object 是否分配成功
     * @author DK
     */
    public Object initProduct(HttpServletRequest request) {
        
        JSONObject param = JSONObject.fromObject(RequestUtils.getString(request, "param"));
        
        String productList = JSONUtils.getString(param, "value");
        String companyno = JSONUtils.getString(param, "companyno");
        String stime = JSONUtils.getString(param, "stime");
        String etime = JSONUtils.getString(param, "etime");
        
        // 获得初始化版本号
        String newVersion = "";
        JSONObject versionBean =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_SUBACTION_VERSION,
                new BasicDBObject()));
        if (versionBean.isEmpty()) {
            
            DBObject versionObject = new BasicDBObject();
            versionObject.put("createtime", new Date());
            versionObject.put("code", "0");
            commonDaoImpl.insertCommon(TableNameConstant.T_SUBACTION_VERSION, versionObject);
            newVersion = "0";
            
        }
        else {
            newVersion = JSONUtils.getString(versionBean, "code");
        }
        
        // 想要得到的字段
        DBObject lessBean = new BasicDBObject();
        lessBean.put("sub", 1);
        lessBean.put("deal", 1);
        lessBean.put("_id", 0);
        
        // 获得平台的联动数据用于给企业初始化
        JSONArray subData = JSONArray.fromObject(commonDaoImpl.searchLessCommonAll("p_subaction", lessBean));
        String subAction = subData.toString();
        
        // 获得ip和端口
        JSONObject addressObject =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject(
                "companyno", companyno)));
        
        String address = JSONUtils.getString(addressObject, "ipAddress");
        address += ":" + JSONUtils.getString(addressObject, "port");
        
        String result =
            businessService.initProduct(address, productList, companyno, stime, etime, newVersion, subAction);
        
        if ("1".equals(result)) { // 判断是否分配成功
        
            // 分配成功，修改分配状态
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("companyno", companyno);
            DBObject productState = new BasicDBObject();
            productState.put("state.product", StateConstant.B_COMPANY_APPLY_STATE1);
            commonDaoImpl.updateCommon(TableNameConstant.T_COMPANY, conditionBean, productState);
            
            return true;
        }
        
        return false;
    }
    
    /**
     * 修改产品树
     * 
     * @param request
     * @return object 是否修改成功
     * @author DK
     */
    public Object updateProduct(HttpServletRequest request) {
        
        JSONObject param = JSONObject.fromObject(RequestUtils.getString(request, "param"));
        
        String productList = JSONUtils.getString(param, "value");
        String companyno = JSONUtils.getString(param, "companyno");
        
        // 移除掉的功能
        String removenodes = JSONUtils.getString(param, "removenodes");
        // 添加的功能
        String addnodes = JSONUtils.getString(param, "addnodes");
        
        // 获得ip和端口
        JSONObject addressObject =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject(
                "companyno", companyno)));
        String address = JSONUtils.getString(addressObject, "ipAddress");
        address += ":" + JSONUtils.getString(addressObject, "port");
        
        //address="127.0.0.1:8080";
        
        // 通过webservice调用企业内部的接口
        String result = this.businessService.updateProduct(address, productList, companyno, removenodes, addnodes);
        
        // 判断是否修改成功
        if ("1".equals(result)) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 产品树的展示
     * 
     * @param request
     * @return
     */
    public Object productTree(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        String companyno = JSONUtils.getString(userBean, "cccompanyno");
        
        // 获得ip和端口
        JSONObject addressObject =
            JSONObject.fromObject(commonDaoImpl.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject(
                "companyno", companyno)));
        String address =
            JSONUtils.getString(addressObject, "ipAddress") + ":" + JSONUtils.getString(addressObject, "port");
        
        // 通过webservice调用企业内部的接口
        String result = this.businessService.listProduct(address, companyno);
        
        JSONArray productList = JSONArray.fromObject(result);
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        list.add("remark");
        list.add("open");
        list.add("url");
        
        return CommonUtils.childrenList(productList, list, false, null, "");
    }
    
    /**
     * 修改产品树
     * 
     * @param request
     * @return
     */
    public Object updateproductTree(HttpServletRequest request) {
        
        JSONObject userBean = JSONObject.fromObject(request.getSession().getAttribute(SessionKey.SESSION_KEY_USER));
        
        String companyno = JSONUtils.getString(userBean, "cccompanyno");
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("companyno", companyno);
        
        JSONArray productList =
            JSONArray.fromObject(commonDaoImpl.searchCommon(TableNameConstant.T_PRODUCTS, conditionBean));
        
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        list.add("remark");
        list.add("open");
        list.add("url");
        
        return CommonUtils.childrenList(productList, list, false, null, "");
    }
    
}
