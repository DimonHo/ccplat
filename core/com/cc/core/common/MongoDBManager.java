package com.cc.core.common;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cc.core.utils.CollectionUtils;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

/**
 * @author
 * @createTime 2014.08.30
 */
public class MongoDBManager {

    private Mongo mg = null;

    private DB db = null;

    public static Log log = LogFactory.getLog(MongoDBManager.class);

    private final static Map<String, MongoDBManager> instances = new ConcurrentHashMap<String, MongoDBManager>();

    /**
     * 实例
     * 
     * @return MongoDBManager对象
     */
    static {
        getInstance("db");// 初始化默认的MongoDB数据
    }

    private static String ipstr = "";

    private static int portstr = 0;

    private static String dataname = "";

    private static String username = "";
    private static String password = "";

    public static void getConfig() {

        String fileName = "/db.properties";
        Properties p = new Properties();
        try {
            InputStream in = MongoDBManager.class.getResourceAsStream(fileName);
            p.load(in);
            in.close();
            if (p.containsKey("ipstr")) {
                ipstr = p.getProperty("ipstr");
            }
            if (p.containsKey("portstr")) {
                portstr = Integer.valueOf(p.getProperty("portstr"));
            }
            if (p.containsKey("dataname")) {
                dataname = p.getProperty("dataname");
            }
            if (p.containsKey("username")) {
                username = p.getProperty("username");
            }
            if (p.containsKey("password")) {
                password = p.getProperty("password");
            }

        } catch (IOException ex) {
            Logger.getLogger("").log(Level.SEVERE, null, ex);
            log.error(ex.getLocalizedMessage(), ex);
        }
    }

    public static MongoDBManager getInstance() {

        return getInstance("db");
        // 配置文件默认数据库前
    }

    public static MongoDBManager getInstance(String dbName) {

        MongoDBManager mm = instances.get(dbName);
        if (mm == null) {
            mm = getNewInstance(dbName);
            instances.put(dbName, mm);
        }
        return mm;
    }

    private static synchronized MongoDBManager getNewInstance(String dbName) {

        getConfig();
        MongoDBManager mm = new MongoDBManager();
        try {
            mm.mg = new Mongo(ipstr, portstr);
            //mm.db = mm.mg.getDB(dataname);
            mm.db = mm.mg.getDB("admin");
            mm.db.authenticate(username, password.toCharArray());
            mm.db = mm.mg.getDB(dataname);
        } catch (Exception e) {
            log.error("Can't connect " + dbName + " MongoDB!");
            log.error(e.getLocalizedMessage(), e);
        }
        return mm;
    }

    /**
     * 根据properties文件的key获取value
     * 
     * @param filePath properties文件路径
     * @param key 属key
     * @return 属value
     */
    @SuppressWarnings("unused")
    private static String getProperty(String filePath, String key) {

        Properties props = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(filePath));
            props.load(in);
            String value = props.getProperty(key);
            return value;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 获取集合（表
     * 
     * @param collection
     */
    public DBCollection getCollection(String collection) {

        return this.db.getCollection(collection);
    }

    /**
     * ----------------------------------分割-----------------------------------
     */

    /**
     * 插入
     * 
     * @param collection
     * @param o
     * 
     *            Insert和Save的区别是：如果插入的集合的“_id”值，在集合中已经存在,用Insert执行插入操作回报异常，
     *            已经存在"_id"的键。用Save如果系统中没有相同的"_id"就执行插入操作，有的话就执行覆盖掉原来的值。 相当于修改操作。我这里就不做演示了。 插入
     */
    public WriteResult insert(String collection, DBObject o) {

        return getCollection(collection).insert(o);
    }

    public String insertBean(String collection, DBObject o) {

        WriteResult bean = getCollection(collection).insert(o);
        return (String) bean.getField("_id");
    }

    /**
     * 批量插入
     * 
     * @param collection
     * @param list 插入的列
     */
    public void insertBatch(String collection, List<DBObject> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        getCollection(collection).insert(list);
    }

    /**
     * 删除
     * 
     * @param collection
     * @param q 查询条件
     */
    public void delete(String collection, DBObject q) {

        getCollection(collection).remove(q);
    }

    /**
     * 批量删除
     * 
     * @param collection
     * @param list 删除条件列表
     */
    public void deleteBatch(String collection, List<DBObject> list) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            getCollection(collection).remove(list.get(i));
        }
    }

    /**
     * 计算集合总条
     * 
     * @param collection
     */
    public long getCount(String collection) {

        return getCollection(collection).find().count();
    }

    /**
     * 计算满足条件条数
     * 
     * @param collection
     * @param q 查询条件
     */

    public long getCount(String collection, DBObject q) {

        return getCollection(collection).getCount(q);
    }

    /**
     * 更新
     * 
     * @param collection
     * @param q 查询条件
     * @param d 更新对象
     */
    public boolean update(String collection, DBObject q, DBObject d) {

        boolean result = false;
        WriteResult writeResult = getCollection(collection).updateMulti(q, new BasicDBObject("$set", d));
        if (null != writeResult) {
            if (writeResult.getN() > 0) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 更新 $push--向文档的某个数组类型的键添加一个数组元素，不过滤重复的数据。添加时键存在，要求键值类型必须是数组；键不存在，则创建数组类型的键。
     * 
     * @param collection
     * @param q 查询条件
     * @param d 更新对象
     * @author Ron
     */
    public void updatePush(String collection, DBObject q, DBObject d) {

        getCollection(collection).updateMulti(q, new BasicDBObject("$push", d));

    }

    /**
     * 更新删除数组
     * 
     * @param collection
     * @param q 查询条件
     * @param d 更新对象
     */
    public void updateDel(String collection, DBObject q, DBObject d) {

        getCollection(collection).updateMulti(q, new BasicDBObject("$pull", d));

    }

    /**
     * 查找集合对象
     * 
     * @param collection
     */
    public List<DBObject> findAll(String collection) {

        return getCollection(collection).find().toArray();
    }

    /**
     * 按顺序查找集合所有对
     * 
     * @param collection 数据
     * @param orderBy 排序
     */
    public List<DBObject> findAll(String collection, DBObject orderBy) {

        return getCollection(collection).find().sort(orderBy).toArray();
    }

    /**
     * 按顺序查找集合所有对
     * 
     * @param collection 数据
     * @param orderBy 排序
     */
    public List<DBObject> findAllPage(String collection, DBObject orderBy, int pageNo, int perPageCount) {

        return getCollection(collection).find().sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * 查找（返回一个对象）
     * 
     * @param collection
     * @param q 查询条件
     */
    public DBObject findOne(String collection, DBObject q) {

        return getCollection(collection).findOne(q);
    }

    /**
     * 查找（返回一个对象）
     * 
     * @param collection
     * @param q 查询条件
     * @param fileds 返回字段
     */
    public DBObject findOne(String collection, DBObject q, DBObject fileds) {

        return getCollection(collection).findOne(q, fileds);
    }

    /**
     * 查找返回特定字段（返回一个List<DBObject>
     * 
     * @param collection
     * @param q 查询条件
     * @param fileds 返回字段
     */
    public List<DBObject> findLess(String collection, DBObject q, DBObject fileds) {

        DBCursor c = getCollection(collection).find(q, fileds);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 查找返回特定字段（返回一个List<DBObject>
     * 
     * @param collection
     * @param q 查询条件
     * @param fileds 返回字段
     * @param orderBy 排序
     */
    public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, DBObject orderBy) {

        DBCursor c = getCollection(collection).find(q, fileds).sort(orderBy);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 分页查找集合对象，返回特定字
     * 
     * @param collection
     * @param q 查询条件
     * @param fileds 返回字段
     * @pageNo 第n
     * @perPageCount 每页记录
     */
    public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, int pageNo, int perPageCount) {

        return getCollection(collection).find(q, fileds).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * 按顺序分页查找集合对象，返回特定字段
     * 
     * @param collection 集合
     * @param q 查询条件
     * @param fileds 返回字段  可以指定要包含的字段（例如：｛field:1｝）或者指定要排除的字段（例如：｛field：0｝）
     * @param orderBy 排序
     * @param pageNo 第n页
     * @param perPageCount 每页记录条
     */
    public List<DBObject> findLess(String collection, DBObject q, DBObject fileds, DBObject orderBy, int pageNo, int perPageCount) {

        return getCollection(collection).find(q, fileds).sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * 查找（返回一个List<DBObject>)
     * 
     * @param collection
     * @param q 查询条件
     */
    public List<DBObject> find(String collection, DBObject q) {

        DBCursor c = getCollection(collection).find(q);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 查找（返回一个List<DBObject>
     * 
     * @param collection
     * @param q 查询条件
     */
    public List<DBObject> findPage(String collection, DBObject q, DBObject orderBy, int pageNo, int perPageCount) {

        DBCursor c = getCollection(collection).find(q).skip((pageNo - 1) * perPageCount).limit(perPageCount);

        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 按顺序查找（返回List<DBObject>
     * 
     * @param collection
     * @param q 查询条件
     * @param orderBy 排序
     */
    public List<DBObject> find(String collection, DBObject q, DBObject orderBy) {

        DBCursor c = getCollection(collection).find(q).sort(orderBy);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 按顺序查找（返回List<DBObject>
     * 
     * @param collection
     * @param q 查询条件
     * @param orderBy 排序
     */
    public List<DBObject> find(String collection, DBObject q, DBObject orderBy, int limit) {

        DBCursor c = getCollection(collection).find(q).limit(limit).sort(orderBy);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    /**
     * 分页查找集合对象
     * 
     * @param collection
     * @param q 查询条件
     * @pageNo 第n
     * @perPageCount 每页记录
     */
    public List<DBObject> find(String collection, DBObject q, int pageNo, int perPageCount) {

        return getCollection(collection).find(q).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * 按顺序分页查找集合对
     * 
     * @param collection 集合
     * @param q 查询条件
     * @param orderBy 排序
     * @param pageNo 第n
     * @param perPageCount 每页记录
     */
    public List<DBObject> find(String collection, DBObject q, DBObject orderBy, int pageNo, int perPageCount) {

        return getCollection(collection).find(q).sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * distinct操作
     * 
     * @param collection 集合
     * @param field distinct字段名称
     */
    public Object[] distinct(String collection, String field) {

        return getCollection(collection).distinct(field).toArray();
    }

    /**
     * distinct操作
     * 
     * @param collection 集合
     * @param field distinct字段名称
     * @param q 查询条件
     */
    public Object[] distinct(String collection, String field, DBObject q) {

        return getCollection(collection).distinct(field, q).toArray();
    }

    /**
     * group分组查询操作，返回结果少0,000keys时可以使
     * 
     * @param collection 集合
     * @param key 分组查询字段
     * @param q 查询条件
     * @param reduce reduce Javascript函数，如：function(obj, out){out.count++;out.csum=obj.c;}
     * @param finalize reduce function返回结果处理Javascript函数，如：function(out){out.avg=out.csum/out.count;}
     */
    public BasicDBList group(String collection, DBObject key, DBObject q, DBObject initial, String reduce, String finalize) {

        return ((BasicDBList) getCollection(collection).group(key, q, initial, reduce, finalize));
    }

    /**
     * group分组查询操作，返回结果大0,000keys时可以使
     * 
     * @param collection 集合
     * @param map 映射javascript函数字符串，如：function(){ for(var key in this) { emit(key,{count:1}) } }
     * @param reduce reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var i in emits){ total+=emits[i].count;
     *            } return {count:total}; }
     * @param q 分组查询条件
     * @param orderBy 分组查询排序
     */
    // public Iterable<DBObject> mapReduce(String collection, String map, String reduce,
    // DBObject q, DBObject orderBy) {
    //
    // DBCollection coll = db.getCollection(collection);
    // MapReduceCommand cmd = new MapReduceCommand(coll, map, reduce, null, MapReduceCommand.OutputType.INLINE, q);
    // return coll.mapReduce(cmd).results();
    // MapReduceOutput out = getCollection(collection).mapReduce(map, reduce, null, q);
    // return out.getOutputCollection().find().sort(orderBy).toArray();
    // }

    /**
     * group分组分页查询操作，返回结果大0,000keys时可以使
     * 
     * @param collection 集合
     * @param map 映射javascript函数字符串，如：function(){ for(var key in this) { emit(key,{count:1}) } }
     * @param reduce reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var i in emits){ total+=emits[i].count;
     *            } return {count:total}; }
     * @param q 分组查询条件
     * @param orderBy 分组查询排序
     * @param pageNo 第n
     * @param perPageCount 每页记录
     */
    public JSONArray mapReduce(String collection, String map, String reduce, DBObject q) {

        MapReduceCommand cmd = new MapReduceCommand(getCollection(collection), map, reduce, null, MapReduceCommand.OutputType.INLINE, q);

        MapReduceOutput out = getCollection(collection).mapReduce(cmd);

        JSONArray json = new JSONArray();
        JSONObject joBean = null;
        for (DBObject o : out.results()) {
            joBean = JSONObject.fromObject(o.toString());
            json.add(joBean);
        }

        return json;
    }

    /**
     * group分组查询操作，返回结果大0,000keys时可以使
     * 
     * @param collection 集合
     * @param map 映射javascript函数字符串，如：function(){ for(var key in this) { emit(key,{count:1}) } }
     * @param reduce reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var i in emits){ total+=emits[i].count;
     *            } return {count:total}; }
     * @param outputCollectionName 输出结果表名
     * @param q 分组查询条件
     * @param orderBy 分组查询排序
     */
    public List<DBObject> mapReduce(String collection, String map, String reduce, String outputCollectionName, DBObject q, DBObject orderBy) {

        if (!db.collectionExists(outputCollectionName)) {
            getCollection(collection).mapReduce(map, reduce, outputCollectionName, q);
        }

        return getCollection(outputCollectionName).find(null, new BasicDBObject("_id", false)).sort(orderBy).toArray();
    }

    /**
     * group分组分页查询操作，返回结果大0,000keys时可以使
     * 
     * @param collection 集合
     * @param map 映射javascript函数字符串，如：function(){ for(var key in this) { emit(key,{count:1}) } }
     * @param reduce reduce Javascript函数字符串，如：function(key,emits){ total=0; for(var i in emits){ total+=emits[i].count;
     *            } return {count:total}; }
     * @param outputCollectionName 输出结果表名
     * @param q 分组查询条件
     * @param orderBy 分组查询排序
     * @param pageNo 第n
     * @param perPageCount 每页记录
     */
    public List<DBObject> mapReduce(String collection, String map, String reduce, String outputCollectionName, DBObject q, DBObject orderBy, int pageNo, int perPageCount) {

        if (!db.collectionExists(outputCollectionName)) {
            getCollection(collection).mapReduce(map, reduce, outputCollectionName, q);
        }

        return getCollection(outputCollectionName).find(null, new BasicDBObject("_id", false)).sort(orderBy).skip((pageNo - 1) * perPageCount).limit(perPageCount).toArray();
    }

    /**
     * 添加修改数组元素
     * 
     * @param connection 表
     * @param d 查询条件
     * @param q 新增（修改）数组字段
     * */
    public void updateArr(String collection, DBObject d, DBObject q) {

        getCollection(collection).updateMulti(d, new BasicDBObject("$addToSet", q));

    }

    public static void main(String[] args) {

        try {
            MongoDBManager db = MongoDBManager.getInstance();
            BasicDBObject a = new BasicDBObject();
            a.put("companyno", "9381336092232");
            db.update("b_companycc", new BasicDBObject(), a);
            // getInstance().insert("user",
            // new BasicDBObject().append("name", "admin3").append("type", "2").append("score", 70)
            // .append("level", 2).append("inputTime", new Date().getTime()));
            // getInstance().update("user", new BasicDBObject().append("status", 1), new
            // BasicDBObject().append("status", 2));
            // === group start =============
            // StringBuilder sb = new StringBuilder(100);
            // sb.append("function(obj, out){out.count++;out.")
            // .append("scoreSum").append("+=obj.")
            // .append("score").append(";out.")
            // .append("levelSum").append("+=obj.")
            // .append("level").append('}');
            // String reduce = sb.toString();
            // BasicDBList list = getInstance().group("user", new BasicDBObject("type", true),
            // new BasicDBObject(),
            // new BasicDBObject().append("count", 0).append("scoreSum", 0).append("levelSum", 0).append("levelAvg",
            // (Double) 0.0),
            // reduce,
            // "function(out){ out.levelAvg = out.levelSum / out.count }");
            // for (Object o : list) {
            // DBObject obj = (DBObject)o;
            // System.out.println(obj);
            // }
            // ======= group end=========
            // === mapreduce start =============
            // Iterable<DBObject> list2 = getInstance().mapReduce("user",
            // "function(){emit( {type:this.type}, {score:this.score, level:this.level} );}",
            // "function(key,values){var result={score:0,level:0};var count = 0;values.forEach(function(value){result.score += value.score;result.level += value.level;count++});result.level = result.level / count;return result;}",
            // new BasicDBObject(),
            // new BasicDBObject("score",1));
            // for (DBObject o : list2) {
            // System.out.println(o);
            // }

            // List<DBObject> list3 = getInstance().mapReduce("user",
            // "function(){emit({type:this.type},{type:this.type,score:this.score,level:this.level});}",
            // "function(key,values){var result={type:key.type,score:0,level:0};var count=0;values.forEach(function(value){result.score+=value.score;result.level+=value.level;count++});result.level=result.level/count;return result;}",
            // "group_temp_user",
            // new BasicDBObject(),
            // new BasicDBObject("score",1));
            // for (DBObject o : list3) {
            // System.out.println(o);
            // }
            // ======= mapreduce end=========
            // System.out.print(getInstance().findAll("user"));
            // System.out.print(getInstance().find("user",
            // new BasicDBObject("inputTime", new BasicDBObject("$gt",1348020002890L)),
            // new BasicDBObject().append("_id","-1"), 1, 2));
            // getInstance().delete("user", new BasicDBObject());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
