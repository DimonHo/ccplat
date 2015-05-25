package com.cc.core.utils;

import org.apache.log4j.Logger;

/**
 * 字符串操作类，转换数据类型，切割字符串,对象比较等操作
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
public class StringUtils {
    
    // 替换字符串
    private static final String REPLACE_CHAR = "*";
    
    private static final int SAVA_CHAR_LENGTH = 4;
    
    private static final String EMPTY = "";
    
    public static Logger log = Logger.getLogger(StringUtils.class);
    
    /**
     * 将字符串中的某些值用指定字符代替
     * 
     * @param str 原始字符串
     * @param saveLength 保留位数
     * @param replaceChar 替换成的字符
     * @return
     */
    public static String replaceStr(String str, int saveLength, String replaceChar) {
        
        if (saveLength <= 0) {
            saveLength = SAVA_CHAR_LENGTH;
        }
        
        if (replaceChar == null) {
            replaceChar = REPLACE_CHAR;
        }
        
        if (isBlank(str) || str.length() < saveLength) {
            return "";
        }
        
        char[] chars = str.toCharArray();
        StringBuffer stb = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            if (i < (saveLength - 1) / 2 || (chars.length - i - 1) < (saveLength + 2) / 2) {
                stb.append(chars[i]);
            }
            else {
                stb.append(replaceChar);
            }
        }
        
        return stb.toString();
    }
    
    /**
     * 将字符串转换为整形
     * 
     * @param str
     * @return
     */
    public static int stringToInt(String str) {
        
        if (StringUtils.isBlank(str)) {
            return 0;
        }
        try {
            return Integer.valueOf(str).intValue();
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }
    
    /**
     * 是否为空，去除首尾空格后比较
     * 
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        
        if (str == null) {
            return true;
        }
        
        if (EMPTY.equals(str.trim())) {
            return true;
        }
        
        return false;
    }
    
    /**
     * 不为空，去除首尾空格
     * 
     * @param str
     * @return
     */
    public static boolean isNotBlank(String str) {
        
        if (str == null) {
            return false;
        }
        
        if (EMPTY.equals(str.trim())) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 去除字符串首尾空格
     * 
     * @param str
     * @return
     */
    public static String trim(String str) {
        
        if (isNotBlank(str)) {
            return str.trim();
        }
        
        return str;
    }
    
    /**
     * 两个字符串是否相等
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        
        if (a == null && b == null) {
            return true;
        }
        else if (a != null && b != null) {
            if (a.equals(b)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 两个字符串是否相等
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean notEquals(String a, String b) {
        
        return !equals(a, b);
    }
    
    /**
     * 忽略大小写后两个字符串是否相等
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        
        if (a == null && b == null) {
            return true;
        }
        else if (a != null && b != null) {
            if (a.toUpperCase().equals(b.toUpperCase())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 忽略大小写后两个字符串是否相等
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean notEqualsIgnoreCase(String a, String b) {
        
        return !equalsIgnoreCase(a, b);
    }
    
    /**
     * 用字符串split风格字符串str成数组
     * 特殊符号"|"不能使用切割
     * @param str
     * @param split
     * @return
     */
    public static String[] stringToArray(String str, String split) {
        
        if (isBlank(str) || isBlank(str)) {
            return null;
        }
        
        return str.split(split);
    }
    
    /**
     * 如果值为null或者是空格则显示空
     * 
     * @param str
     * @return
     */
    public static String getString(String str) {
        
        if (isBlank(str)) {
            return EMPTY;
        }
        
        return str.trim();
    }
    
    /**
     * 字符串如果为空或空格则返回null
     * 
     * @param str
     * @return
     */
    public static String getNull(String str) {
        
        if (isNotBlank(str)) {
            return str;
        }
        
        return null;
    }
    
    /**
     * 填充字符串到指定长度
     * 
     * @param source 原始字符串
     * @param length 填充到长度
     * @param chars 填充字符
     * @param fillLeft true-左填充，false-右填充
     * @return
     */
    public static String fillStringWithChar(String source, int length, char chars, boolean fillLeft) {
        
        if (StringUtils.isNotBlank(source) && length > 0) {
            int realLength = length - source.length();
            
            StringBuffer stb = new StringBuffer();
            for (; realLength > 0; realLength--) {
                stb.append(chars);
            }
            
            if (fillLeft) {
                return stb.toString() + source;
            }
            else {
                return source + stb.toString();
            }
        }
        return source;
    }
    
    /***
     * 
     * @param order 当order="asc"||""返回1，否则返回-1
     * @return
     */
    public static int setOrder(String order) {
        
        if ("".equals(order) || "asc".equals(order))
            return 1;
        else
            return -1;
    }
    
    /**
     * 字符串是否含有某个字符
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean contains(String a, CharSequence b) {
        
        if (StringUtils.isBlank(a) && b == null) {
            return true;
        }
        else if (StringUtils.isNotBlank(a) && b != null) {
            if (a.contains(b)) {
                return true;
            }
        }
        
        return false;
    }
    
    public static synchronized String getUniqueString(){
        
        if (generateCount > MAX_GENERATE_COUNT)
            
            generateCount = 0;
        
        String uniqueNumber = Long.toString(System.currentTimeMillis()) + Integer.toString(generateCount);
        
        generateCount++;
        
        return uniqueNumber;
    }
    
    private static final int MAX_GENERATE_COUNT = 99999;
    
    private static int generateCount = 0;
    
    public static void main(String[] args) {
        
        // System.out.println(StringUtils.replaceStr("13510190114", 7, "*"));
        for(int i=0;i<120;i++)
        	System.out.println(getUniqueString());
    }
}
