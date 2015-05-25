package com.cc.core.constant;

/**
 * 审核键值
 * 
 * @author COCO
 * @createTime 2014.10.07
 */
public class AuditKey {
    /*----------------------------操作类型-----------------------------------------------------------*/

    /**
     * 处理提交
     */
    public static String SUBMIT_DEAL_TYPE = "3";
    
    /**
     * 提交
     */
    public static String SUBMIT_TYPE = "0";
    
    /**
     * 同意
     */
    public static String AGREE_TYPE = "1";
    
    /**
     * 拒绝
     */
    public static String REFUSE_TYPE = "2";
    
    /*----------------------------提交类型-----------------------------------------------------------*/

    /**
     * 正常提交
     */
    public static String COMMON_SUBMIT_TYPE = "0";
    
    /**
     * 强制提交
     */
    public static String COMMON_FORCE_SUBMIT = "1";
    
    /**
     * 强制不提交
     */
    public static String COMMON_FORCE_NOTSUBMIT = "2";
    
    /**
     * 普通数字类型
     */
    public static String COMMON_DIGITAL_TYPE = "1";
    
    /**
     * 普通字符类型
     */
    public static String COMMON_CHARACTER_TYPE = "2";
    
    /**
     * 数字类型 同意需提交
     */
    public static String DIGITAL_TYPE_SUBMIT = "3";
    
    /**
     * 字符类型 同意需提交
     */
    public static String CHARACTER_TYPE_SUBMIT = "4";
    
    /**
     * 数字类型 拒绝需提交
     */
    public static String DIGITAL_REFUSE_SUBMIT = "5";
    
    /**
     * 字符类型 拒绝需提交
     */
    public static String CHARACTER_REFUSE_SUBMIT = "6";
    
    // 拥有权限
    public static String HAVE_AUDIT_POWER = "1";
    
    // 没有权限
    public static String HAVENOT_AUDIT_POWER = "0";
    
    public static String REFUSE_AUDIT = "2";
    
    /**
     * 时间类型 0为单笔计算
     */
    public static String SINGLE_PEN = "0";
    
    /**
     * 时间类型 1 单位为年
     */
    public static String TIME_YEAR = "1";
    
    /**
     * 时间类型 1 单位为年
     */
    public static String TIME_QUARTER = "2";
    
    /**
     * 时间类型 2 单位为月
     */
    public static String TIME_MONTH = "3";
    
    /**
     * 时间类型 2 单位为周
     */
    public static String TIME_WEEK = "4";
    
    /**
     * 时间类型 3 单位为日
     */
    public static String TIME_DAY = "5";
    
    /**
     * 时间类型4 单位为hour
     */
    public static String TIME_HOUR = "6";
    
    public static String SINGLE_PEN_DISSATISFY = "单笔超出您拥有的权限!";
}
