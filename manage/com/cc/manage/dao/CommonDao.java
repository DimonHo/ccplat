package com.cc.manage.dao;

import net.sf.json.JSONArray;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

/**
 * 共通接口层
 * 
 * @author clare
 * @version 1.0
 * @createTime 2014-08-29
 */
public interface CommonDao {
    
    /**
     * 普通的查询
     * 
     * @param conllection 表名
     * @param conditionBean 查询条件
     * 
     * @return JSONArray
     */
    public Object searchCommon(String collection, DBObject conditionBean);
    
    /**
     * 普通的分页查询
     * 
     * @param collection 表名
     * @param conditionBean 查询条件
     * @param orderBy 排序
     * @param pageNo 分页size
     * @param perPageCount size
     * 
     * @return JSONArray
     */
    public Object searchCommonPage(String collection, DBObject conditionBean, DBObject orderBy, int pageNo,
        int perPageCount);
    
    /**
     * 普通的查询（返回特定字段）
     * 
     * @param collection 表名
     * @param conditionBran 查新条件
     * @param less 返回特定字段集合
     * 
     * @return JSONArray
     */
    public Object searchLessCommon(String collection, DBObject conditionBran, DBObject less);
    
    /**
     * 普通的查询（返回特定字段）
     * 
     * @param collection 表名
     * @param conditionBran 条件
     * @param less 特定字段
     * @param orderBy 排序
     * @param pageNo 分页size
     * @param perPageCount size
     * 
     * @return JSONArray
     */
    public Object searchLessCommonPage(String collection, DBObject conditionBran, DBObject less, DBObject orderBy,
        int pageNo, int perPageCount);
    
    /**
     * 查询单条（返回特定字段）
     * 
     * @param collection 表名
     * @param conditionBran 条件
     * @param less 返回特定字段
     * 
     * @return JOSNObject
     */
    public Object searchLessCommonOne(String collection, DBObject conditionBran, DBObject less);
    
    /**
     * 查询单条
     * 
     * @param collection
     * @param conditionBran
     * 
     * @return JOSNObject
     */
    public Object searchCommonOne(String collection, DBObject conditionBran);
    
    /**
     * 普通的查询全部
     * 
     * @param conllection 表名
     * @param conditionBean 查询条件
     * @return
     */
    public Object searchCommonAll(String collection);
    
    /**
     * 普通的查询全部字段 分页
     * 
     * @param collection
     * @param orderBy 排序
     * @param pageNo 分页size
     * @param perPageCount size
     * @return
     */
    public Object searchCommonAllpage(String collection, DBObject orderBy, int pageNo, int perPageCount);
    
    /**
     * 普通的查询全部（返回特定字段）
     * 
     * @param conllection 表名
     * @param conditionBean 查询条件
     * @param less 返回特定字段集合
     * @return
     */
    public Object searchLessCommonAll(String collection, DBObject less);
    
    /**
     * 普通的查询全部（返回特定字段） 分页
     * 
     * @param collection 表名
     * @param less
     * @param orderBy 排序
     * @param pageNo 分页size
     * @param perPageCount size
     * 
     * @return
     */
    public Object searchLessCommonAllPage(String collection, DBObject less, DBObject orderBy, int pageNo,
        int perPageCount);
    
    /**
     * 普通的插入
     * 
     * @param conllection 表名
     * @param insertBean 插入对象
     * @return
     */
    public Object insertCommon(String collection, DBObject insertBean);
    
    /**
     * 普通的批量插入
     * 
     * @param conllection 表名
     * @param insertBean 插入对象
     * @return
     */
    public Object insertBatchCommon(String collection, BasicDBList insertBatch);
    
    /**
     * 普通的修改
     * 
     * @param collection 表名
     * @param conditionBean 条件
     * @param updateBean 修改内容
     * @return
     */
    public Object updateCommon(String collection, DBObject conditionBean, DBObject updateBean);
    
    /**
     * 修改添加
     * 
     * @param collection 表名
     * @param conditionBean 条件
     * @param updateBean 修改内容
     * @return
     */
    public Object updateCommonPush(String collection, DBObject conditionBean, DBObject updateBean);
    
    /**
     * 普通删除
     * 
     * @param collection
     * @param condition
     * @return
     */
    public boolean deleteCommon(String collection, DBObject condition);
    
    /**
     * 
     * 
     * @param collection 表名
     * @param map
     * @param reduce
     * @param q
     * @return
     */
    public JSONArray mapReduceCommon(String collection, String map, String reduce, DBObject q);
    
}
