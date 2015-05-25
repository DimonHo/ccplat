package com.cc.core.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import net.sf.json.JSONArray;

/**
 * 集合工具类包括集合转换，判断空等
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
public class CollectionUtils {
    
    /**
     * 将map转为list
     * 
     * @param map
     * @return
     */
    public static <T> List<T> mapToList(Map<String, T> map) {
        
        if (map == null || map.size() < 1) {
            return null;
        }
        List<T> result = new ArrayList<T>();
        for (Entry<String, T> entry : map.entrySet()) {
            result.add(entry.getValue());
        }
        return result;
    }
    
    /**
     * 将list转为set，注意：有肯存在覆盖情况
     * 
     * @param list
     * @return
     */
    public static <T> Set<T> listToSet(List<T> list) {
        
        if (list == null || list.size() < 1) {
            return null;
        }
        Set<T> result = new HashSet<T>();
        for (T t : list) {
            result.add(t);
        }
        return result;
    }
    
    /**
     * 把src中的添加到target
     * 
     * @param target
     * @param src
     * @return
     */
    public static <T> Set<T> mergeSet(Set<T> target, Set<T> src) {
        
        if (target == null) {
            return src;
        }
        
        if (src == null) {
            return target;
        }
        
        for (T t : src) {
            target.add(t);
        }
        
        return target;
    }
    
    /**
     * 把src中的添加到target
     * 
     * @param target
     * @param src
     * @return
     */
    public static <T> List<T> mergeList(List<T> target, List<T> src) {
        
        if (target == null) {
            return src;
        }
        
        if (src == null) {
            return target;
        }
        
        for (T t : src) {
            target.add(t);
        }
        
        return target;
    }
    
    /**
     * 把src中的添加到target
     * 
     * @param target
     * @param src
     * @return
     */
    public static <T> Map<Object, T> mergeMap(Map<Object, T> target, Map<Object, T> src) {
        
        if (target == null) {
            return src;
        }
        
        if (src == null) {
            return target;
        }
        
        for (Entry<Object, T> t : src.entrySet()) {
            target.put(t.getKey(), t.getValue());
        }
        
        return target;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(List<T> list) {
        
        if (list == null || list.size() <= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(Object obj) {
        
        if (obj == null) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(List<T> list) {
        
        return !isEmpty(list);
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static <T> boolean isEmpty(Map<String, T> map) {
        
        if (map == null || map.size() <= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static <T> boolean isNotEmpty(Map<String, T> map) {
        
        return !isEmpty(map);
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static boolean isEmpty(Object[] list) {
        
        if (list == null || list.length <= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static boolean isEmptys(JSONArray json) {
        
        if (json == null || json.size() <= 0) {
            return true;
        }
        return false;
    }
    
    /**
     * 是否为空
     * 
     * @param list
     * @return
     */
    public static boolean isNotEmpty(Object[] list) {
        
        return !isEmpty(list);
    }
    
    /**
     * 拷贝
     * 
     * @param dest
     * @param src
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> copy(List<? super T> dest, List<? extends T> src) {
        
        if (dest == null || dest.size() < src.size()) {
            dest = new ArrayList<Object>(Arrays.asList(new Object[src.size()]));
        }
        Collections.copy(dest, src);
        return (List<T>)dest;
    }
    
    /**
     * 将dest中的包含src中部分删除
     * 
     * @param dest
     * @param src
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> findExclude(List<? extends T> dest, List<? extends T> src) {
        
        if (dest == null) {
            return null;
        }
        
        if (src == null) {
            return (List<T>)dest;
        }
        
        List<T> list = new ArrayList<T>();
        boolean flag = false;
        for (T destT : dest) {
            flag = false;
            for (T srcT : src) {
                if (srcT.equals(destT)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                list.add(destT);
            }
        }
        
        return list;
    }
}
