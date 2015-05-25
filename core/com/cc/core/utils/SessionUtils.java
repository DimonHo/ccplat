package com.cc.core.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * session工具，获取对象，转换对象
 * @author Ron
 * @createTime 2014.08.30
 */
public class SessionUtils {

    /**
     * 从session取值
     * 
     * @param request
     * @param key
     * @return
     */
    public static Object getSessionValue(HttpServletRequest request, String key) {

        if (StringUtils.isBlank(key) || request == null) {
            return null;
        }

        Object obj = request.getSession().getAttribute(key);

        return obj;
    }

    /**
     * 从session取值
     * 
     * @param request
     * @param key
     * @return
     */
    public static Object getSessionValue(HttpServletRequest request, String key, Object defaultValue) {

        if (StringUtils.isBlank(key) || request == null) {
            return defaultValue;
        }

        Object obj = request.getSession().getAttribute(key);

        return obj;
    }

    /**
     * 存放值到session
     * 
     * @param request
     * @param key
     * @param value
     */
    public static void setSessionValue(HttpServletRequest request, String key, Object value) {

        if (StringUtils.isBlank(key) || request == null || value == null) {
            return;
        }

        request.getSession(true).setAttribute(key, value);
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param request
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionObject(HttpServletRequest request, String key) {

        Object obj = getSessionValue(request, key);
        if (obj == null) {
            return null;
        } else {
            return (T) obj;
        }
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param request
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionObject(HttpServletRequest request, String key, Object defaultValue) {

        Object obj = getSessionValue(request, key);
        if (obj == null) {
            obj = defaultValue;
        }
        return (T) obj;
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param request
     * @param key
     * @return
     */
    public static Long getSessionLong(HttpServletRequest request, String key) {

        Object obj = getSessionValue(request, key);
        if (obj == null) {
            return Long.valueOf(0);
        } else {
            return Long.valueOf(obj.toString());
        }
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param request
     * @param key
     * @return
     */
    public static Long getSessionLong(HttpServletRequest request, String key, Long defaultValue) {

        Object obj = getSessionValue(request, key);
        if (obj == null) {
            return defaultValue;
        }
        return Long.valueOf(obj.toString());
    }

    /**
     * 把session中key所对应的对象移除
     * @param request
     * @param key
     */
    public static void removeSessionObject(HttpServletRequest request, String key) {

        if (StringUtils.isNotBlank(key)) {
            request.getSession().removeAttribute(key);
        }
    }

    /**
     * 获取session对象
     */
    public static HttpSession getSession() {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getSession();
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionObject(String key) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            return null;
        }
        return (T) obj;
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getSessionObject(String key, Object defaultValue) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            obj = defaultValue;
        }
        return (T) obj;
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    public static String getSessionString(String key) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            return null;
        }
        return (String) obj;
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    public static String getSessionString(String key, String defaultValue) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            return defaultValue;
        }
        return (String) obj;
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    public static Long getSessionLong(String key) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            return Long.valueOf(0);
        }
        return Long.valueOf(obj.toString());
    }

    /**
     * 把session中Object对象转换成为bean
     * 
     * @param <T>
     * @param key
     * @return
     */
    public static Long getSessionLong(String key, Long defaultValue) {

        Object obj = getSession().getAttribute(key);
        if (obj == null) {
            return defaultValue;
        }
        return Long.valueOf(obj.toString());
    }

    /**
     * session失效
     */
    public static void invalidate(HttpServletRequest request) {

        request.getSession().invalidate();
    }

}
