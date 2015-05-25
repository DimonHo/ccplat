package com.cc.manage.dao;

import java.util.List;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/***
 * 地区操作数据处理层
 * @author Ron
 * @createTime 2014.09.18
 * 
 */
public interface CcAreaDao {

	/**
     * 获取所有区域
     * 
     */
	public List<DBObject> listTree();
	
	/***
	 * 创建地区节点 
	 * @param collection
	 * @param insertData
	 * @return
	 */
    public String add(String collection, BasicDBObject insertData);

    /***
     * 编辑地区节点
     * @param collection
     * @param condition
     * @param changeBeaen
     * @return
     */
    public boolean update(String collection, BasicDBObject condition, BasicDBObject changeBeaen);

    /**
     * 删除地区节点
     * @param collection
     * @param condition
     * @return
     */
    public boolean delete(String collection, BasicDBObject condition);
}
