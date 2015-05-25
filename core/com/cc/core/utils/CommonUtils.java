package com.cc.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.TableNameConstant;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CommonUtils {
    
    // 存放临时的字符串
    private static String temp;
    
    private static JSONObject tempObjedct = null;
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取树结构
     * 
     * @param json 需要返回的对象
     * @param jsonArray 需要处理的数据
     * @param list 两个必须参数,顺序写死 第一个参数:id 第二个参数:显示的名称
     * @param pid 父级id
     * @param isCheck 是否需要选中
     * @param checkString 选中值
     * @return
     */
    public static JSONArray childrenList(JSONArray jsonArray, List<String> list, boolean isCheck,
        JSONArray checkStringList, String idAdd) {
        
        // 保存产品信息，便于根据id查找
        HashMap<String, JSONObject> productMap = new HashMap<String, JSONObject>();
        
        JSONObject childrenBean = null;
        JSONObject childrenleaf = null;
        
        JSONArray json = new JSONArray();
        JSONArray jarray = null;
        String id = "";
        String key = "";
        String keyValue = "";
        for (int k = 0; k < jsonArray.size(); k++) {
            
            // 获取子节点
            jarray = JSONArray.fromObject(JSONUtils.getString(jsonArray.getJSONObject(k), "children"));
            
            for (int i = 0; i < jarray.size(); i++) {
                
                childrenBean = jarray.getJSONObject(i);
                childrenleaf = new JSONObject();
                
                // 是否显示拼接数据
                key = JSONUtils.getString(list, 0);
                String[] keyAry = key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
                id = JSONUtils.getString(childrenBean, keyAry[0]);
                
                keyValue = "";
                for (int j = 0; j < keyAry.length; j++) {
                    keyValue += JSONUtils.getString(childrenBean, keyAry[0]) + AplicationKeyConstant.STRING_SPLIT_CHAR;
                }
                
                if (!"".equals(keyValue))
                    keyValue = keyValue.substring(0, keyValue.length() - 1);
                
                childrenleaf.put("id", keyValue + idAdd);
                childrenleaf.put("name", JSONUtils.getString(childrenBean, JSONUtils.getString(list, 1)));
                childrenleaf.put("pid", "");
                
                // 设置是否选中
                setData(isCheck, id, childrenleaf, checkStringList, null, json);
                
                String key1 = "";
                
                for (int j = 2; j < list.size(); j++) {
                    key1 = JSONUtils.getString(list, j);
                    childrenleaf.put(key1, JSONUtils.getString(childrenBean, key1));
                }
                
                json.add(childrenleaf);
                
                productMap.put(JSONUtils.getString(childrenleaf, "id"), childrenleaf);
                
                if (StringUtils.isNotBlank(JSONUtils.getString(childrenBean, "children")))
                    setJson(json,
                        productMap,
                        JSONUtils.getString(childrenBean, "children"),
                        keyValue + idAdd,
                        list,
                        isCheck,
                        checkStringList,
                        idAdd);
            }
            
        }
        
        return json;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /***
     * 去除类别中的产品信息
     * 
     * @param json
     * @param result
     * @param id
     */
    public static void setJson(JSONArray json, HashMap<String, JSONObject> map, String str, String mainId,
        List<String> list, boolean isCheck, JSONArray checkStringList, String idAdd) {
        
        JSONArray children = JSONArray.fromObject(str);
        
        JSONObject childrenBean = null;
        JSONObject childrenleaf = null;
        
        String id = "";
        String key = "";
        String keyValue = "";
        
        for (int i = 0; i < children.size(); i++) {
            
            childrenBean = children.getJSONObject(i);
            childrenleaf = new JSONObject();
            
            key = JSONUtils.getString(list, 0);
            String[] keyAry = key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            id = JSONUtils.getString(childrenBean, keyAry[0]);
            keyValue = "";
            for (int j = 0; j < keyAry.length; j++) {
                keyValue += JSONUtils.getString(childrenBean, keyAry[0]) + AplicationKeyConstant.STRING_SPLIT_CHAR;
            }
            if (!"".equals(keyValue))
                keyValue = keyValue.substring(0, keyValue.length() - 1);
            
            childrenleaf.put("id", keyValue + idAdd);
            childrenleaf.put("name", JSONUtils.getString(childrenBean, JSONUtils.getString(list, 1)));
            childrenleaf.put("pid", mainId);
            
            setData(isCheck, id, childrenleaf, checkStringList, map, json);
            
            String key1 = "";
            for (int j = 2; j < list.size(); j++) {
                key1 = JSONUtils.getString(list, j);
                childrenleaf.put(key1, JSONUtils.getString(childrenBean, key1));
            }
            
            json.add(childrenleaf);
            
            // 保存产品信息
            map.put((String)childrenleaf.get("id"), childrenleaf);
            
            if (StringUtils.isNotBlank(JSONUtils.getString(childrenBean, "children")))
                setJson(json,
                    map,
                    JSONUtils.getString(childrenBean, "children"),
                    keyValue + idAdd,
                    list,
                    isCheck,
                    checkStringList,
                    idAdd);
        }
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 查询对应节点对象
     * 
     * @param json Json对象
     * @param key 条件字段
     * @param value 条件值
     * @return
     */
    public static JSONObject getResult(JSONArray json, String key, String value) {
        
        JSONObject childrenBean = null;
        
        JSONArray jarray = null;
        
        JSONObject temp = null;
        
        String str = "";
        
        for (int k = 0; k < json.size(); k++) {
            
            jarray = JSONArray.fromObject(JSONUtils.getString(json.getJSONObject(k), "children"));
            
            for (int i = 0; i < jarray.size(); i++) {
                childrenBean = jarray.getJSONObject(i);
                
                str = JSONUtils.getString(childrenBean, key);
                
                if (value.equals(str)) {
                    System.out.println(str);
                    return childrenBean;
                }
                
                if (StringUtils.isNotBlank(JSONUtils.getString(childrenBean, "children"))) { // 是否存在子children
                
                    temp = getResult(JSONArray.fromObject(childrenBean), key, value);
                    
                    if (temp != null && temp.size() != 0) { // 大于0就返回值
                        return temp;
                    }
                }
            }
        }
        
        return null;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 查询对应节点对象
     * 
     * @param json Json对象
     * @param key 条件字段
     * @param value 条件值
     * @return
     */
    public static String getResultString(JSONArray json, String key, String value) {
        
        JSONObject childrenBean = null;
        
        JSONArray jarray = null;
        
        String str = "";
        
        String resultStr = "";
        
        for (int k = 0; k < json.size(); k++) {
            
            tempObjedct = json.getJSONObject(k);
            
            temp = JSONUtils.getString(tempObjedct, "url");
            
            jarray = JSONArray.fromObject(JSONUtils.getString(tempObjedct, "children"));
            
            for (int i = 0; i < jarray.size(); i++) {
                
                childrenBean = jarray.getJSONObject(i);
                
                str = JSONUtils.getString(childrenBean, key);
                
                if (value.equals(str)) {
                    return temp;
                }
                
                if (StringUtils.isNotBlank(JSONUtils.getString(childrenBean, "children"))) { // 是否存在子children
                
                    resultStr = getResultString(JSONArray.fromObject(childrenBean), key, value);
                    
                    if (resultStr != null && resultStr.length() != 0) { // 大于0就返回值
                        return resultStr;
                    }
                }
            }
        }
        
        return null;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取当前下面的所有id
     * 
     * @param children 需要处理的对象
     * @param key 匹配的key
     * @param flag 是否已经匹配成功
     * @param value 匹配的值
     * @param list 返回的结果
     * @return
     */
    public static List<String> getUUIDArray(JSONArray children, String key, boolean flag, String value,
        List<String> list) {
        
        JSONObject childrenBean = null;
        
        String keyValue = "";
        
        for (int i = 0; i < children.size(); i++) {
            
            childrenBean = children.getJSONObject(i);
            
            keyValue = JSONUtils.getString(childrenBean, key);
            
            if (flag) {
                list.add(keyValue);
            }
            else {
                if (value.equals(keyValue)) { // 如果相等，将下面的所有的节点都查询出来
                    flag = true;
                }
                
            }
            
            if (StringUtils.isNotBlank(JSONUtils.getString(childrenBean, "children")))
                return getUUIDArray(JSONArray.fromObject(JSONUtils.getString(childrenBean, "children")),
                    key,
                    flag,
                    value,
                    list);
        }
        
        return list;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 
     * @param isCheck
     * @param id
     * @param joBean
     * @param checkString
     */
    @SuppressWarnings("unchecked")
    private static void setData(boolean isCheck, String id, JSONObject joBean, JSONArray checkStringList, HashMap map,
        JSONArray json) {
        
        boolean flag = true;
        JSONObject tempBean = null;
        String key = "";
        
        if (isCheck)
            for (int i = 0; i < checkStringList.size(); i++) {
                tempBean = checkStringList.getJSONObject(i);
                if (id.equals(JSONUtils.getString(tempBean, "code"))) {
                    flag = false;
                    joBean.put("checked", true);
                    Iterator it = tempBean.keys();
                    while (it.hasNext()) {
                        
                        key = String.valueOf(it.next());
                        
                        joBean.put(key, JSONUtils.getString(tempBean, key));
                    }
                    
                    break;
                }
            }
        
        if (flag) {
            joBean.put("checked", false);
            // 查找父级节点，由于子节点为不选中状态，父级节点也会为不选中状态
            String pid = joBean.getString("pid");
            setCheckFalse(map, pid, json);
        }
        
    }
    
    /**
     *将节点的所有上级选中状态改为false
     */
    private static void setCheckFalse(HashMap<String, JSONObject> map, String pid, JSONArray json) {
        if (StringUtils.isNotBlank(pid)) {
            JSONObject parent = map.get(pid);
            if (parent != null) {
                json.remove(parent);
                map.remove(pid);
                parent.put("checked", "false");
                json.add(parent);
                setCheckFalse(map, parent.getString("pid"), json);
            }
        }
    }
    
    /**
     * 获取ipAddress和端口号port
     * 
     * @author Suan
     * @param request 公司编码
     * @return 返回地址和端口号
     */
    public static String getIpAddress(String companyno) {
        DBObject queryBean = new BasicDBObject();
        queryBean.put("companyno", companyno);
        // 获得ip和端口
        DBObject companyBean = MongoDBManager.getInstance().findOne(TableNameConstant.T_COMPANY, queryBean);
        if (companyBean == null) {
            return ResultConstant.IP_PORT_EXCEPTION;// 访问webService的服务ip与端口为空 5
        }
        String address =
            DBObejctUtils.getString(companyBean, "ipAddress") + ":" + DBObejctUtils.getString(companyBean, "port");
        return address;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * @param args
     */
    /**
     * @param args
     */
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        MongoDBManager mongoDBManager = MongoDBManager.getInstance();
        
        DBObject conditionBean = new BasicDBObject();
        conditionBean.put("industryuuid", "A6C3C5CC-0AB4-4047-933E-347EE90D7F97");
        
        // List<DBObject> dbObjectList = mongoDBManager.find("cc_productslist", conditionBean);
        
        // JSONArray productsList = JSONArray.fromObject(dbObjectList);
        
        conditionBean = new BasicDBObject();
        conditionBean.put("companyno", "4301241990");
        
        JSONArray productsArray =
            JSONArray.fromObject(mongoDBManager.find(TableNameConstant.T_PRODUCT_ITEM, conditionBean));
        
        List<String> checkArray = new ArrayList<String>();
        
        for (int i = 0; i < productsArray.size(); i++) {
            checkArray.add(JSONUtils.getString(productsArray.getJSONObject(i), "code"));
        }
        
        List<String> list = new ArrayList<String>();
        list.add("code");
        list.add("name");
        list.add("open");
        list.add("remark");
        list.add("url");
        
        // childrenList(productsList, list, true, checkArray, "");
        
    }
}
