package com.cc.manage.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.AplicationKeyConstant;
import com.cc.manage.dao.CcAreaDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/***
 * 地区操作数据处理层
 * @author Ron
 * @createTime 2014.09.18
 * 
 */
@Component
public class CcAreaDaoImpl implements CcAreaDao {
    /**
     * 地区表
     */
    private String CC_AREA = "cc_area";

    public List<DBObject> listTree() {

        MongoDBManager db = MongoDBManager.getInstance();
        return db.findAll(CC_AREA);
    }

    public boolean update(String collection, BasicDBObject condition, BasicDBObject changeBeaen) {

        MongoDBManager db = MongoDBManager.getInstance();
        boolean flg = true;

        try {
            db.update(collection, condition, changeBeaen);
        } catch (Exception e) {
            flg = false;
        }

        return flg;
    }

    public boolean delete(String collection, BasicDBObject condition) {

        MongoDBManager db = MongoDBManager.getInstance();
        boolean flg = true;
        try {
            db.delete(collection, condition);
        } catch (Exception e) {
            flg = false;
        }
        return flg;
    }

    public String add(String collection, BasicDBObject insertData) {

        MongoDBManager db = MongoDBManager.getInstance();
        String flg = "";
        try {
            db.insert(collection, insertData);
            String id = insertData.getString("_id").toString();
            String pidaddress = insertData.get("pidaddress") + id;
            BasicDBObject obj = new BasicDBObject();
            obj.put("pidaddress", pidaddress);
            db.update(collection, new BasicDBObject("_id", new ObjectId(id)), obj);
            flg = id + AplicationKeyConstant.STRING_SPLIT_CHAR + pidaddress + AplicationKeyConstant.STRING_SPLIT_CHAR + insertData.get("pid");
        } catch (Exception e) {
            flg = "false";
        }
        return flg;
    }
}
