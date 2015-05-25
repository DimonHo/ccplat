package com.cc.manage.dao.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.manage.dao.CommonDao;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 通用dao层
 * 
 * @author coco
 * @version 1.0
 * @createTime 2014-10-01
 * 
 */
@Component
public class CommonDaoImpl implements CommonDao {
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchCommon(String collection, DBObject conditionBean) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.find(collection, conditionBean);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchLessCommon(String collection, DBObject conditionBran) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.findOne(collection, conditionBran);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchLessCommonOne(String collection, DBObject conditionBran, DBObject less) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.findOne(collection, conditionBran, less);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public DBObject searchCommonOne(String collection, DBObject conditionBean) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.findOne(collection, conditionBean);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object insertCommon(String collection, DBObject insertBean) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        boolean flg = true;
        
        try {
            db.insert(collection, insertBean);
        }
        catch (Exception e) {
            flg = false;
        }
        
        return flg;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object updateCommon(String collection, DBObject conditionBean, DBObject updateBean) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        boolean flg = true;
        
        try {
            db.update(collection, conditionBean, updateBean);
        }
        catch (Exception e) {
            flg = false;
        }
        
        return flg;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchCommonAll(String collection) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.findAll(collection);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchLessCommonAll(String collection, DBObject less) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        DBObject conditionBean = new BasicDBObject();
        
        return db.findLess(collection, conditionBean, less);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object insertBatchCommon(String collection, BasicDBList insertBatch) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        boolean flg = true;
        
        try {
            
            for (int i = 0; i < insertBatch.size(); i++) {
                db.insert(collection, (DBObject)insertBatch.get(i));
            }
            
        }
        catch (Exception e) {
            flg = false;
        }
        
        return flg;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object searchLessCommon(String collection, DBObject conditionBran, DBObject less) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.find(collection, conditionBran, less);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public Object updateCommonPush(String collection, DBObject conditionBean, DBObject updateBean) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        boolean flg = true;
        
        try {
            db.updatePush(collection, conditionBean, updateBean);
        }
        catch (Exception e) {
            flg = false;
        }
        
        return flg;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public boolean deleteCommon(String collection, DBObject condition) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        boolean flg = true;
        try {
            db.delete(collection, condition);
        }
        catch (Exception e) {
            flg = false;
        }
        
        return flg;
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public JSONArray mapReduceCommon(String collection, String map, String reduce, DBObject q) {
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.mapReduce(collection, map, reduce, q);
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public long getCount(String collection) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        return db.getCount(collection);
    }
    
    public Object searchCommonAllpage(String collection, DBObject orderBy, int pageNo, int perPageCount) {
        MongoDBManager db = MongoDBManager.getInstance();
        
        // 分页查询数据
        List<DBObject> obj = db.findAllPage(collection, orderBy, pageNo, perPageCount);
        
        // 总条数
        long count = db.getCount(collection);
        
        JSONObject jo = new JSONObject();
        jo.put("data", obj.toString());
        jo.put("total", count);
        
        return jo;
    }
    
    public Object searchCommonPage(String collection, DBObject conditionBean, DBObject orderBy, int pageNo,
        int perPageCount) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        // 分页查询数据
        List<DBObject> obj = db.findPage(collection, conditionBean, orderBy, pageNo, perPageCount);
        
        // 总条数
        long count = db.getCount(collection, conditionBean);
        
        JSONObject jo = new JSONObject();
        jo.put("data", obj.toString());
        jo.put("total", count);
        
        return jo;
    }
    
    public Object searchLessCommonAllPage(String collection, DBObject less, DBObject orderBy, int pageNo,
        int perPageCount) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        DBObject conditionBean = new BasicDBObject();
        
        // 分页查询数据
        List<DBObject> obj = db.findLess(collection, conditionBean, less, orderBy, pageNo, perPageCount);
        
        // 总条数
        long count = db.getCount(collection);
        
        JSONObject jo = new JSONObject();
        jo.put("data", obj.toString());
        jo.put("total", count);
        
        return jo;
    }
    
    public Object searchLessCommonPage(String collection, DBObject conditionBran, DBObject less, DBObject orderBy,
        int pageNo, int perPageCount) {
        
        MongoDBManager db = MongoDBManager.getInstance();
        
        // 分页查询数据
        List<DBObject> obj = db.findLess(collection, conditionBran, less, orderBy, pageNo, perPageCount);
        
        // 总条数
        long count = db.getCount(collection, conditionBran);
        
        JSONObject jo = new JSONObject();
        jo.put("data", obj.toString());
        jo.put("total", count);
        
        return jo;
    }
    
    /*-------------------------------------------------------------------------------------------*/
}
