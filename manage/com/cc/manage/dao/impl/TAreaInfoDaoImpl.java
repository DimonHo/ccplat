package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.manage.dao.TAreaInfoDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class TAreaInfoDaoImpl implements TAreaInfoDao {
    
    public static Log log = LogFactory.getLog(TAreaInfoDaoImpl.class);
    
    public List<DBObject> getAreaInfo(int pId) {
        
        DBObject condition = new BasicDBObject();
        condition.put("pId", pId);
        return MongoDBManager.getInstance().find("tAreaInfo", condition);
    }
    
}
