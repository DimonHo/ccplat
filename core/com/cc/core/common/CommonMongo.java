package com.cc.core.common;

import java.util.List;

import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 自定义的条件查询
 * @author ron
 * @createTime 2014.08.30
 */
@Component
public class CommonMongo {

    /***
     * 根据表，条件返回集合
     * @param collection
     * @param condition
     * @return
     */
    public List<DBObject> queryCommon(String collection, BasicDBObject condition) {

        MongoDBManager db = MongoDBManager.getInstance();
        return db.find(collection, condition);
    }

    /***
     * 根据表，条件返回对象
     * @param collection
     * @param condition
     * @return
     */
    public DBObject queryCommonOne(String collection, BasicDBObject condition) {

        MongoDBManager db = MongoDBManager.getInstance();
        return db.findOne(collection, condition);
    }

}
