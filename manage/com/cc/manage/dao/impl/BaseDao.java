package com.cc.manage.dao.impl;

import com.cc.core.common.MongoDBManager;

/**
 * dao层基类 <br />
 * 可得到mongo 定义b_company(企业表),cc_user(用户表),p_subaction(个人职责联动功能表)<br/ >
 * p_userAction(功能信息表),cc_batch(账号批次),cc_framelist(组织架构管理)
 * 
 * @author zmx
 * 
 */
public class BaseDao {
    /**
     * mongo对象
     */
    protected MongoDBManager mongo = MongoDBManager.getInstance();
    
    /**
     * 企业表
     */
    protected final String B_COMPANY = "b_company";
    
    /**
     * cc用户表
     */
    protected final String CC_USER = "cc_user";
    
    /**
     * 个人职责联动功能表
     */
    protected final String P_SUBACTION = "p_subaction";
    
    /**
     * 功能信息表
     */
    protected final String P_USERACTION = "p_useraction";
    
    /**
     * 账号批次
     */
    protected final String CC_BATCH = "cc_batch";
    
    /**
     * 组织架构管理
     */
    protected final String CC_FRAMELIST = "cc_framelist";
    
    /**
     * cc账号池
     */
    protected final String CC_NUMBER = "cc_number";
    
    /**
     * 行业对应产品模版
     */
    protected final String CC_PRODUCTSLIST = "cc_productslist";
}
