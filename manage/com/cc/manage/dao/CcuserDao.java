package com.cc.manage.dao;

import java.util.List;

import com.mongodb.DBObject;

public interface CcuserDao {
    
    /***
     * 根据_id修改cc_user的对象数据
     * @param id
     * @param userBean
     * @return 成功true,失败false
     * @author Ron
     */
    public boolean updateById(String id,DBObject userBean);
    /**
     * 根据_id给对象数据增加记录，主要用于增加历史记录数据
     * @param conditionBean
     * @param bean
     * @return boolean
     * @author Ron
     */
    public boolean updatePush(String id, DBObject bean);
    
    public List<DBObject> searchUser(DBObject conditionBean);
    
    /**
     * 修改状态
     * 
     * @param q 查询条件
     * @param d 修改条件
     * @return
     */
    public Object updateState(DBObject q, DBObject d);
    
    public List<DBObject> findLess(DBObject q, DBObject fileds, DBObject orderBy, int pageNo, int perPageCount);
    
    /**
     * 根据cc账号获取cc_user中的信息state为1的启用的单条信息
     * 
     * @param ccno
     * @return String
     */
    public String queryByCcno(String ccno);
    
    /**
     * 根据cc账号获取cc_user中的信息state为1的启用的单条信息
     * 
     * @param ccno
     * @author Einstein
     * @return DBObject
     */
    public DBObject queryCcuserByCcno(String ccno);
    
    /**
     * 根据企业号和cc账号获取cc_user中的信息state为1的启用的单条信息
     * 
     * @param ccno
     * @author Einstein
     * @return DBObject
     */
    public DBObject queryCcuserByCompanyNoAndCcno(String companyno, String ccno);
    
    /***
     * ccuser表添加数据
     * @param user
     * @return 成功返回true，失败返回false
     * @author Ron 2014.1010
     */
    public boolean insert(DBObject user);
    
    /**
     * 根据uuid获取user数据
     * @param uuid
     * @return
     */
    public DBObject queryByUuid(String uuid);
    
}
