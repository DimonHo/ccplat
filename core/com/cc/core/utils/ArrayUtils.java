package com.cc.core.utils;

/**
 * 操作数组的工具类
 * @author Ron
 * @createTime 2014.08.30
 */
public class ArrayUtils {

    /**
     * 增加元素到数组
     * 
     * @param objs
     * @param obj
     * @return
     */
    public static Object[] addElement(Object[] objs, Object obj) {

        if (objs == null) {
            return new Object[] { obj };
        }
        Object[] reObjs = new Object[objs.length + 1];
        System.arraycopy(objs, 0, reObjs, 0, objs.length);
        reObjs[objs.length] = obj;
        return objs;
    }

    /**
     * 增加元素到数组
     * 
     * @param objs
     * @param obj
     * @return
     */
    public static long[] addElement(long[] objs, long obj) {

        if (objs == null) {
            return new long[] { obj };
        }
        long[] reObjs = new long[objs.length + 1];
        System.arraycopy(objs, 0, reObjs, 0, objs.length);
        reObjs[objs.length] = obj;
        return objs;
    }
}