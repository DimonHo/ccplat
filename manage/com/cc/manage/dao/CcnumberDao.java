package com.cc.manage.dao;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.mongodb.DBObject;
/**
 * 创建批次账号中生成的账号池接口
 * 
 * @author Ron
 * @createTime 2014.09.10
 */
public interface CcnumberDao {

	/***
	 * 根据批次的id获取CC账号池cc_number中的列表数据
	 * 当不存在排序字段时按照ccno进行顺序排列
	 * @param request
	 * @return JSONObject
	 */
    public JSONObject list(HttpServletRequest request);

    /**
     * 添加批次信息
     * @param bean
     * @return
     */
    public boolean add(DBObject bean);

    /**
     * 修改cc账号池中的账号使用状态
     * @param conditionBean 修改条件
     * @param bean 修改的内容
     * @return boolean
     */
    public boolean modify(DBObject conditionBean, DBObject bean);
    
    /***
    * 根据id修改账号状态
    * @param state
    * @param id
    * @return
    * @author Ron 20143.10.10
    */
    public boolean modifyStateById(String state, String id);
    
    /***
     * 根据id增加cc_Number中的历史记录
     * @param id
     * @param historyBean
     * @return
     * @author Ron 20143.10.10
     */
    public String addHistroyById(String id, DBObject historyBean);

    /**
     * 通过当前类型（1,预留;2,内部企业号码;3,外部企业号码;4,外部使用）获取最小的待添加的CC账号
     * @param type
     * @return int
     */
    public int maxCcNo(int type);
}
