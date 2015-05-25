package com.cc.manage.dao;

import javax.servlet.http.HttpServletRequest;

import com.mongodb.DBObject;

public interface CcAuditDao {
    
    public Object list(HttpServletRequest request, boolean flag);
    
    public Object getProductAct(DBObject conditionBean);
}
