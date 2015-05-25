package com.cc.core.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mongodb.DBObject;

/**
 * 获取DBObejct中的键值对，转换各种不同的类型值
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
public class DBObejctUtils {
    
    public static Log log = LogFactory.getLog(DBObejctUtils.class);
    
    /**
     * 返回整形,如果不存在则返回0
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static int getInt(DBObject dbObject, String key) {
        
        try {
            Integer value = (Integer)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }
    
    /***
     * 返回整形,不存在则返回默认值defaultValue
     * 
     * @param dbObject
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInt(DBObject dbObject, String key, int defaultValue) {
        
        try {
            Integer value = (Integer)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }
    
    /**
     * 返回长整形,如果不存在则返回0
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static long getLong(DBObject dbObject, String key) {
        
        try {
            Long value = (Long)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
        return 0;
    }
    
    /**
     * 返回长整形
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static long getLong(DBObject dbObject, String key, Long defaultValue) {
        
        try {
            Long value = (Long)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }
    
    /***
     * 返回长整形 ;不存在则返回defaultValue
     * 
     * @param dbObject
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLong(DBObject dbObject, String key, int defaultValue) {
        
        try {
            Long value = (Long)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }
    
    /**
     * 返回货币类型,不处理小数点后的数值，不存在返回new BigDecimal(0)
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimal(DBObject dbObject, String key) {
        try {
            BigDecimal value = (BigDecimal)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return new BigDecimal(0);
    }
    
    /**
     * 返回货币类型,如果不存在则返回 defaultValue
     * 
     * @param dbObject
     * @param key
     * @param defaultValue
     * @return
     */
    public static BigDecimal getBigDecimal(DBObject dbObject, String key, BigDecimal defaultValue) {
        try {
            BigDecimal value = (BigDecimal)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }
    
    /**
     * 返回字符串,删除了首尾空格,如果不存在则返回null
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static String getString(DBObject dbObject, String key) {
        
        try {
            String value = (String)dbObject.get(key);
            if (StringUtils.isNotBlank(value)) {
                return StringUtils.trim(value);
            }
        }
        catch (Exception e) {
            return "";
        }
        return "";
    }
    
    /**
     * 返回字符串,删除了首尾空格
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static String getString(DBObject dbObject, String key, String defaultValue) {
        try {
            String value = (String)dbObject.get(key);
            if (StringUtils.isNotBlank(value)) {
                return StringUtils.trim(value);
            }
        }
        catch (Exception e) {
            return "";
        }
        return defaultValue;
    }
    
    /**
     * 返回日期,默认格式yyyy-MM-dd;不存在返回null 对象中存储的必须为日期格式的对象，否则返回null
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static Date getDate(DBObject dbObject, String key) {
        
        try {
            Date value = (Date)dbObject.get(key);
            if (value != null) {
                return value;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 获取boolean
     * 
     * @param dbObject
     * @param key
     * @return
     */
    public static boolean getBoolean(DBObject dbObject, String key) {
        try {
            Boolean value = (Boolean)dbObject.get(key);
            return value;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /****
     * 获取DBObject中的list对象，异常返回List没有值的对象
     * 
     * @param dbobject
     * @param key
     * @return 返回list<DBObject>对象，异常则长度为0
     */
    @SuppressWarnings("unchecked")
    public static List<DBObject> getList(DBObject dbobject, String key) {
        
        if (dbobject == null) {
            return new ArrayList<DBObject>();
        }
        try {
            return (List<DBObject>)dbobject.get(key);
        }
        catch (Exception e) {
            return new ArrayList<DBObject>();
        }
    }
}
