package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.TableNameConstant;
import com.cc.manage.dao.BcompanyapplyDao;
import com.mongodb.DBObject;

/**
 * 企业注册实现类
 * 
 * @author yfm
 * @createTime 2014.09.17
 * 
 */
@Component
public class BcompanyapplyDaoImpl implements BcompanyapplyDao {
    
    public static Log log = LogFactory.getLog(BcompanyapplyDaoImpl.class);
    
    /**
     * 分页排序查询
     */
    public List<DBObject> list(String collection, DBObject q, DBObject fileds, DBObject orderBy, int pageNo,
        int perPageCount) {
        
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        List<DBObject> companyapplyList = mongoDb.findLess(collection, q, fileds, orderBy, pageNo, perPageCount);
        
        return companyapplyList;
        
    }
    
    /**
     * 查询资料信息总数
     * 
     * @param conditionBean 查询条件
     * @return
     */
    public long count(String collection, DBObject conditionBean) {
        
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        long count = mongoDb.getCount(collection, conditionBean);
        return count;
    }
    
    /**
     * 添加
     * 
     * @param conditionBean 添加对象
     * @return
     */
    public Object add(DBObject conditionBean) {
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        
        return mongoDb.insert(TableNameConstant.T_COMPANY_APPLY, conditionBean);
    }
    
    /**
     * 修改
     * 
     * @param conditionBean 查询条件
     * @param conditionBeannew 修改对象
     * @return
     */
    public Object update(DBObject conditionBean, DBObject conditionBeannew) {
        
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        mongoDb.update(TableNameConstant.T_COMPANY_APPLY, conditionBean, conditionBeannew);
        return null;
    }
    
    public List<DBObject> find(String collection, DBObject conditionBean) {
        
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        List<DBObject> companyapply = mongoDb.find(collection, conditionBean);
        return companyapply;
    }
    
    /**
     * 修改数组
     * 
     * @param q 查询条件
     * @param d 修改条件
     * @return
     */
    public Object updateArr(DBObject q, DBObject d) {
        
        MongoDBManager mongoDb = MongoDBManager.getInstance();
        mongoDb.updateArr(TableNameConstant.T_COMPANY_APPLY, q, d);
        
        return null;
    }
    
    /**
     * 删除
     * 
     * @param bean 删除对象
     * @return
     */
    public boolean delete(DBObject bean) {
        
        try {
            MongoDBManager.getInstance().delete(TableNameConstant.T_COMPANY_APPLY, bean);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /**
     * 批量删除
     * 
     * @param bean 删除集合
     * @return
     */
    public boolean deleteBatch(List<DBObject> beans) {
        
        try {
            MongoDBManager.getInstance().deleteBatch(TableNameConstant.T_COMPANY_APPLY, beans);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
