package com.cc.manage.dao;

import java.util.List;

import com.mongodb.DBObject;

public interface BcompanyapplyDao {
    public List<DBObject> list(String collection, DBObject q, DBObject fileds, DBObject orderBy, int pageNo,
        int perPageCount);
    
    public Object add(DBObject conditionBean);
    
    public List<DBObject> find(String collection, DBObject conditionBean);
}
