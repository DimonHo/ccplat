package com.cc.manage.dao;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.mongodb.DBObject;
/**
 * 批次数据操作层
 * @author Ron
 * @createTime 2014.10.17
 */
public interface CcbatchDao {

    /***
     * 账号批次翻页显示返回对象{"total":0,data:[]}
     * @param request
     * @return
     */
	public JSONObject list(HttpServletRequest request);
	
	/***
	 * 增加一条批次数据
	 * @param bean
	 * @return
	 */
	public boolean add(DBObject bean);

}
