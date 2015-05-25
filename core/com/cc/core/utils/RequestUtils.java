package com.cc.core.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 获取前台传入数据，转换各种不同的类型值
 * @author Ron
 * @createTime 2014.08.30
 */
public class RequestUtils {
    private static final String FALSE = "false";
    private static final String TRUE = "true";
    public static Log log = LogFactory.getLog(RequestUtils.class);

    /**
     * 返回整形,如果不存在则返回0
     * 
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return Integer.valueOf(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    /**
     * 返回整形
     * 
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key, int defaultValue) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return Integer.valueOf(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }

    /**
     * 返回长整形,如果不存在则返回0
     * 
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return Long.valueOf(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return 0;
    }

    /**
     * 返回长整形
     * 
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key, Long defaultValue) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return Long.valueOf(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }

    /**
     * 返回长整形
     * 
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key, int defaultValue) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return Long.valueOf(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }

    /**
     * 返回货币类型
     * 
     * @param request
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimal(HttpServletRequest request, String key) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return new BigDecimal(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return new BigDecimal(0);
    }

    /**
     * 返回货币类型,如果不存在则返回0
     * 
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static BigDecimal getBigDecimal(HttpServletRequest request, String key, BigDecimal defaultValue) {

        try {
            String value = request.getParameter(key);
            if (StringUtils.isNotBlank(value)) {
                return new BigDecimal(StringUtils.trim(value));
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return defaultValue;
    }

    /**
     * 返回字符串,删除了首尾空格,如果不存在则返回null
     * 
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key) {

        String value = request.getParameter(key);
        if (StringUtils.isNotBlank(value)) {
            return StringUtils.trim(value);
        }

        return value;
    }

    /**
     * 返回字符串,删除了首尾空格
     * 
     * @param request
     * @param key
     * @return
     */
    public static String getString(HttpServletRequest request, String key, String defaultValue) {

        String value = request.getParameter(key);
        if (StringUtils.isNotBlank(value)) {
            return StringUtils.trim(value);
        }

        return defaultValue;
    }

    /**
     * 返回复选框值,删除了首尾空格,如果不存在则返回null
     * 
     * @param request
     * @param key
     * @return
     */
    public static List<String> getList(HttpServletRequest request, String key) {

        String[] values = request.getParameterValues(key);
        if (values != null) {
            List<String> relist = new ArrayList<String>(values.length);
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    relist.add(StringUtils.trim(value));
                }
            }
            return relist;
        }

        return null;
    }

    /**
     * 返回复选框值,删除了首尾空格
     * 
     * @param request
     * @param key
     * @return
     */
    public static List<String> getList(HttpServletRequest request, String key, List<String> defaultValue) {

        String[] values = request.getParameterValues(key);
        if (values != null) {
            List<String> relist = new ArrayList<String>(values.length);
            for (String value : values) {
                if (StringUtils.isNotBlank(value)) {
                    relist.add(StringUtils.trim(value));
                }
            }
            return relist;
        }

        return defaultValue;
    }

    /**
     * 返回复选框值,删除了首尾空格,如果不存在则返回null
     * 
     * @param request
     * @param key
     * @return
     */
    public static String[] getArray(HttpServletRequest request, String key) {

        String[] values = request.getParameterValues(key);
        if (values != null && values.length > 0) {
            return values;
        }

        return null;
    }

    /**
     * 返回复选框值,删除了首尾空格
     * 
     * @param request
     * @param key
     * @return
     */
    public static String[] getArray(HttpServletRequest request, String key, String[] defaultValue) {

        String[] values = request.getParameterValues(key);
        if (values != null && values.length > 0) {
            return values;
        }

        return defaultValue;
    }

    /**
     * 返回日期,如果不存在则返回null,默认格式yyyy-MM-dd
     * 
     * @param request
     * @param key
     * @return
     */
    public static Date getDate(HttpServletRequest request, String key, String dateFormat) {

        String value = request.getParameter(key);

        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DateUtils.DATE_FORMAT_DD;
        }

        try {
            if (StringUtils.isNotBlank(value)) {
                return DateUtils.stringToDate(StringUtils.trim(value), dateFormat);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 返回日期,默认格式yyyy-MM-dd
     * 
     * @param request
     * @param key
     * @return
     */
    public static Date getDate(HttpServletRequest request, String key, String dateFormat, Date defaultValue) {

        String value = request.getParameter(key);

        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DateUtils.DATE_FORMAT_DD;
        }

        try {
            if (StringUtils.isNotBlank(value)) {
                return DateUtils.stringToDate(StringUtils.trim(value), dateFormat);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return defaultValue;
    }

    /**
     * 返回日期,默认格式yyyy-MM-dd如果没有则new一个date
     * 
     * @param request
     * @param key
     * @return
     */
    public static Date getDate(HttpServletRequest request, String key) {

        String value = request.getParameter(key);

        try {
            if (StringUtils.isNotBlank(value)) {
                return DateUtils.stringToDate(StringUtils.trim(value), DateUtils.DATE_FORMAT_DD);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 获取dateTime
     * 
     * @param request
     * @param key
     * @return
     */
    public static Timestamp getTimestamp(HttpServletRequest request, String key) {

        String value = request.getParameter(key);

        try {
            if (StringUtils.isNotBlank(value)) {
                return DateUtils.dateToTimestamp(DateUtils.stringToDate(StringUtils.trim(value), DateUtils.DATE_FORMAT_SS), null);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 获取boolean
     * 
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key) {

        String value = request.getParameter(key);

        try {
            if (StringUtils.equalsIgnoreCase(value, TRUE)) {
                return true;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return false;
    }

    /**
     * 获取boolean 默认值defaultValue
     * 
     * @param request
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key, boolean defaultValue) {

        String value = request.getParameter(key);

        try {
            if (StringUtils.equalsIgnoreCase(value, TRUE)) {
                return true;
            } else if (StringUtils.equalsIgnoreCase(value, FALSE)) {
                return false;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return defaultValue;
    }

    /**
     * 获取国际化对象类型：zh_CN，en-US
     * 
     * @param request
     * @return
     */
    public static String getLocalStr(HttpServletRequest request) {

        return request.getLocale().getLanguage() + "_" + request.getLocale().getCountry();
    }
}
