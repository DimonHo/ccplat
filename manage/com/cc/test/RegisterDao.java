package com.cc.test;

import org.springframework.stereotype.Component;

import com.cc.manage.dao.impl.CommonDaoImpl;

@Component
public class RegisterDao extends CommonDaoImpl {
    
    // 集合
    /**
     * 注册申请表
     */
    public static final String T_applyreg = "applyreg";
    
    /**
     * 申请审核表
     */
    public static final String T_ccapply = "cc_apply";
    
    /**
     * 流程表
     */
    public static final String T_ccflow = "p_flow";
    
    /**
     * 联动表
     */
    public static final String T_cclink = "p_subaction";
    
    /**
     * 已注册企业
     */
    public static final String T_company = "company";
    
    // 方法
    /**
     * 添加注册申请
     */
    public static final String M_ADD = "12345698";
    
    public static final String M_ADD_CONTENT = "申请注册，麻烦审核";
    
    public static final String M_ADD1 = "AAAAAAA1";
    
    public static final String M_SEARCH = "search";
    
    public static final String M_PERSON_ACTION = "personAction";
    
    public static final String M_CHECK = "12345699";
    
    /**
     * 审核注册申请
     */
    public static final String M_AUDIT = "12345699";
    
    // 页面传递参数
    public static final String P_param = "param";
    
    // 数据库字段
    public static final String name = "name";
    
    public static final String contact = "contact";
    
    public static final String phone = "phone";
    
    public static final String remark = "remark";
    
    public static final String flowno = "flowno";
    
}
