package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcuserDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 单个账号控制实现类
 * 
 * @author yfm
 * @createTime 2014.09.15
 * 
 */
@Component
public class CcuserDaoImpl extends BaseDao implements CcuserDao {
    
    public static Log log = LogFactory.getLog(CcuserDaoImpl.class);
    
    /***
     * 根据_id修改cc_user的对象数据
     * 
     * @param id
     * @param userBean
     * @return 成功true,失败false
     * @author Ron
     */
    public boolean updateById(String id, DBObject userBean) {
        try {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            mongo.update(CC_USER, conditionBean, userBean);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /**
     * 根据_id给对象数据增加记录，主要用于增加历史记录数据updateArr
     * 
     * @param conditionBean
     * @param bean
     * @return
     */
    public boolean updatePush(String id, DBObject bean) {
        try {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            MongoDBManager.getInstance().updatePush(CC_USER, conditionBean, bean);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /**
     * 查询
     * 
     * @param conditionBean 查询条件
     * @return
     */
    public List<DBObject> searchUser(DBObject conditionBean) {
        
        List<DBObject> userList = mongo.find(CC_USER, conditionBean);
        
        return userList;
    }
    
    /**
     * 修改状态
     * 
     * @param q 查询条件
     * @param d 修改条件
     * @return
     */
    public Object updateState(DBObject q, DBObject d) {
        
        mongo.update(CC_USER, q, d);
        
        return null;
    }
    
    public String queryByCcno(String ccno) {
        if (StringUtils.isNotBlank(ccno)) {
            DBObject bean = new BasicDBObject();
            bean.put("ccno", ccno);
            bean.put("state", StateConstant.CC_USER_STATE1);
            DBObject reseltBean = mongo.findOne(CC_USER, bean);
            if (reseltBean != null) {
                return reseltBean.toString();
            }
        }
        return "";
    }
    
    /**
     * 排序 按顺序分页查找集合对象，返回特定字段
     * 
     * @param response
     * @return
     */
    public List<DBObject> findLess(DBObject q, DBObject fileds, DBObject orderBy, int pageNo, int perPageCount) {
        return mongo.findLess(CC_USER, q, fileds, orderBy, pageNo, perPageCount);
    }
    
    /**
     * 查询资料信息总数
     * 
     * @param conditionBean 查询条件
     * @author Einstein
     * @return long
     */
    public long count(DBObject conditionBean) {
        return mongo.getCount(CC_USER, conditionBean);
    }
    
    /**
     * 根据cc账号获取cc_user中的信息state为1的启用的单条信息
     * 
     * @param ccno
     * @return DBObject
     * @author Einstein
     */
    public DBObject queryCcuserByCcno(String ccno) {
        if (StringUtils.isNotBlank(ccno)) {
            DBObject bean = new BasicDBObject();
            bean.put("ccno", ccno);
            bean.put("state", StateConstant.CC_USER_STATE1);
            bean.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
            DBObject reseltBean = mongo.findOne(CC_USER, bean);
            if (reseltBean != null) {
                return reseltBean;
            }
        }
        return null;
    }
    
    /**
     * 根据企业号和cc账号获取cc_user中的信息state为1的启用的单条信息
     * 
     * @param ccno
     * @author Einstein
     * @return DBObject
     * @author admin
     */
    public DBObject queryCcuserByCompanyNoAndCcno(String companyno, String ccno) {
        if (StringUtils.isNotBlank(ccno)) {
            DBObject bean = new BasicDBObject();
            bean.put("ccno", ccno);
            bean.put("companyno", companyno);
            bean.put("state", StateConstant.CC_USER_STATE1);
            bean.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));
            DBObject reseltBean = mongo.findOne(CC_USER, bean);
            if (reseltBean != null) {
                return reseltBean;
            }
        }
        return null;
    }
    
    /***
     * ccuser表添加数据
     * 
     * @param user
     * @return 成功返回true，失败返回false
     * @author Ron 2014.10.10
     */
    public boolean insert(DBObject user) {
        try {
            if (user != null) {
                mongo.insert(CC_USER, user);
                return true;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /**
     * 根据uuid获取user数据
     * 
     * @param uuid
     * @return
     */
    public DBObject queryByUuid(String uuid) {
        if (StringUtils.isNotBlank(uuid)) {
            DBObject bean = new BasicDBObject();
            bean.put("uuid", uuid);
            bean.put("usestate", StateConstant.CC_USER_USESTATE1);
            return mongo.findOne(CC_USER, bean);
        }
        return null;
    }
    
}
