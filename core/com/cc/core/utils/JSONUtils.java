package com.cc.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.DBObject;

/**
 * JSON对象操作工具类，格式转化，获取值（防止空异常）
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
public class JSONUtils {
    
    public static Log log = LogFactory.getLog(JSONUtils.class);
    
    public static String getString(JSONObject jsonparam, String key) {
        
        if (jsonparam == null) {
            return "";
        }
        try {
            return jsonparam.getString(key);
        }
        catch (Exception e) {
            return "";
        }
    }
    
    public static String getString(List<String> list, int i) {
        
        if (list == null) {
            return "";
        }
        
        try {
            return list.get(i);
        }
        catch (Exception e) {
            return "";
        }
        
    }
    
    /****
     * 
     * @param jsonparam
     * @param key
     * @param str 为null返回默认值
     * @return
     */
    public static String getString(JSONObject jsonparam, String key, String str) {
        
        if (jsonparam == null) {
            return str;
        }
        try {
            String s = jsonparam.getString(key);
            if (StringUtils.isNotBlank(s)) {
                return StringUtils.trim(s);
            }
            return str;
        }
        catch (Exception e) {
            return str;
        }
    }
    
    public static int getInt(JSONObject jsonparam, String key) {
        
        if (jsonparam == null) {
            return 0;
        }
        try {
            return jsonparam.getInt(key);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    /**
     * 序列化对象
     * 
     * @param obj
     * @return
     */
    public static String setSer(Object obj) {
        
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream out;
        String serStr = "";
        try {
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);
            serStr = outputStream.toString("ISO-8859-1");
            serStr = java.net.URLEncoder.encode(serStr, "UTF-8");
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return serStr;
    }
    
    /**
     * 反序列化对象
     * 
     * @param serStr
     * @return
     */
    public static Object getSer(String serStr) {
        
        String redStr;
        InputStream inputStream = null;
        try {
            redStr = java.net.URLDecoder.decode(serStr, "UTF-8");
            inputStream = new ByteArrayInputStream(redStr.getBytes("ISO-8859-1"));
        }
        catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(inputStream);
            return in.readObject();
        }
        catch (Exception e) {
            System.out.println("对象存在问题，请重新读取！");
        }
        return null;
    }
    
    public static long getLong(JSONObject jsonparam, String key) {
        
        if (jsonparam == null) {
            return 0;
        }
        try {
            return jsonparam.getLong(key);
        }
        catch (Exception e) {
            return 0;
        }
    }
    
    public static int getInt(JSONObject jsonparam, String key, int number) {
        
        if (jsonparam == null) {
            return number;
        }
        try {
            return jsonparam.getInt(key);
        }
        catch (Exception e) {
            return number;
        }
    }
    
    public static JSONArray getJSONArray(JSONObject jsonparam, String key) {
        
        if (jsonparam == null) {
            return new JSONArray();
        }
        try {
            return jsonparam.getJSONArray(key);
        }
        catch (Exception e) {
            return new JSONArray();
        }
    }
    
    public static JSONArray getJSONArray(String key) {
        
        if (StringUtils.isBlank(key)) {
            return new JSONArray();
        }
        try {
            return JSONArray.fromObject(key);
        }
        catch (Exception e) {
            return new JSONArray();
        }
    }
    
    public static JSONObject getJsonObject(String key) {
        
        if (StringUtils.isBlank(key))
            return null;
        try {
            return JSONObject.fromObject(key);
        }
        catch (Exception e) {
            return null;
        }
    }
    
    public static Double getDouble(JSONObject jsonparam, String key) {
        
        try {
            Double value = jsonparam.getDouble(key);
            return value;
        }
        catch (Exception e) {
        }
        return 0.0;
    }
    
    /**
     * 转_id加根节点
     * 
     * @param dbList
     * @param johead
     * @return
     */
    public static String resertJSONAddhead(List<DBObject> dbList, JSONObject johead) {
        
        DBObject dbData = null;
        String id = "";
        JSONObject jo = null;
        
        JSONArray json = new JSONArray();
        
        for (int i = 0; i < dbList.size(); i++) {
            
            dbData = dbList.get(i);
            id = dbData.get("_id").toString();
            dbData.removeField("_id");
            jo = JSONObject.fromObject(dbData.toString());
            jo.put("id", id);
            
            json.add(jo);
        }
        
        json.add(johead);
        
        return json.toString();
    }
    
    /**
     * 转_id
     * 
     * @param dbList
     * @param johead
     * @return
     */
    public static JSONArray resertJSON(List<DBObject> dbList) {
        
        if (CollectionUtils.isEmpty(dbList)) {
            return new JSONArray();
        }
        DBObject dbData = null;
        String id = "";
        JSONObject jo = null;
        JSONArray json = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            dbData = dbList.get(i);
            id = dbData.get("_id").toString();
            dbData.removeField("_id");
            jo = JSONObject.fromObject(dbData.toString());
            jo.put("id", id);
            json.add(jo);
        }
        return json;
    }
    
    /**
     * 转_id
     * 
     * @param dbList
     * @param johead
     * @return
     */
    public static JSONObject resertJSON(DBObject dbData) {
        
        if (dbData == null) {
            return new JSONObject();
        }
        
        String id = dbData.get("_id").toString();
        dbData.removeField("_id");
        
        JSONObject jo = JSONObject.fromObject(dbData.toString());
        jo.put("id", id);
        
        return jo;
    }
    
    public static JSONArray resertJSON(List<DBObject> dbList, String[] strTime) {
        
        if (CollectionUtils.isEmpty(dbList)) {
            return new JSONArray();
        }
        
        DBObject dbData = null;
        String id = "";
        JSONObject jo = null;
        JSONArray json = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            dbData = dbList.get(i);
            id = dbData.get("_id").toString();
            dbData.removeField("_id");
            
            jo = JSONObject.fromObject(dbData.toString());
            for (int j = 0; j < strTime.length; j++) {
                jo.put(strTime[j], DateUtils.dateToString((Date)dbData.get(strTime[j]), DateUtils.DATE_FORMAT_DD));
            }
            jo.put("id", id);
            
            json.add(jo);
        }
        
        return json;
    }
    
    public static JSONArray resertJSON(List<DBObject> dbList, String[] strTime, String formartDate) {
        
        if (CollectionUtils.isEmpty(dbList)) {
            return new JSONArray();
        }
        
        DBObject dbData = null;
        String id = "";
        JSONObject jo = null;
        JSONArray json = new JSONArray();
        for (int i = 0; i < dbList.size(); i++) {
            dbData = dbList.get(i);
            id = dbData.get("_id").toString();
            dbData.removeField("_id");
            
            jo = JSONObject.fromObject(dbData.toString());
            for (int j = 0; j < strTime.length; j++) {
                jo.put(strTime[j], DateUtils.dateToString((Date)dbData.get(strTime[j]), formartDate));
            }
            
            jo.put("id", id);
            
            json.add(jo);
        }
        
        return json;
    }
    
    public static String getJsontimeToString(String timeStr) {
        
        try {
            JSONObject jo = JSONObject.fromObject(timeStr);
            String dateStr = jo.get("$date").toString();
            
            Date temp2 = DateUtils.stringToDate(dateStr, DateUtils.DATE_FORMAT_STS);
            return DateUtils.dateToString(temp2, DateUtils.DATE_FORMAT_SS);
        }
        catch (Exception e) {
            return "";
        }
    }
    
    public static List<DBObject> resertDBList(List<DBObject> dbList, String[] strTime) {
        
        if (dbList == null || dbList.size() <= 0) {
            return new ArrayList<DBObject>();
        }
        
        DBObject dbData = null;
        
        List<DBObject> dbList1 = new ArrayList<DBObject>();
        for (int i = 0; i < dbList.size(); i++) {
            dbData = dbList.get(i);
            
            for (int j = 0; j < strTime.length; j++) {
                dbData.put(strTime[j], DateUtils.dateToString((Date)dbData.get(strTime[j]), "yyyy-MM-dd HH:mm:ss"));
            }
            
            dbList1.add(dbData);
        }
        
        return dbList1;
    }
}
