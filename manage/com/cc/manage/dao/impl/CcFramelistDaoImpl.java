package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.manage.dao.CcFramelistDao;
import com.mongodb.DBObject;

/**
 * 组织架构管理数据库操作实现类。 包含组织架构管理的增删改查数据库操作。
 * 
 * @author zzf
 * @since CcFramelistDaoImpl1.0
 */
@Component
public class CcFramelistDaoImpl extends BaseDao implements CcFramelistDao {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcFramelistDaoImpl.class);
    
    /**
     * 从数据库查询行业对应组织架构模版数据集合
     * 
     * @param queryBean 查询条件对象
     * @param orderBean 排序条件对象
     * @return 返回查询出的行业对应组织架构模版数据集合
     * @since 1.0
     */
    public List<DBObject> listFrame(DBObject queryBean, DBObject orderBean) {
        try {
            // 查询对应行业所有组织架构模版集合
            List<DBObject> templateList = mongo.find(CC_FRAMELIST, queryBean, orderBean);
            
            if (CollectionUtils.isNotEmpty(templateList)) {
                return templateList;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 新增行业对应组织架构模版到数据库
     * 
     * @param insertBean 插入的数据
     * @return 返回操作是否成功
     * @since 1.0
     */
    public String addFrame(DBObject insertBean) {
        try {
            mongo.insert(CC_FRAMELIST, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 新增组织架构节点到数据库
     * 
     * @param queryBean 查询条件
     * @param insertBean 插入数据
     * @return 返回操作是否成功
     * @since 1.0
     */
    public String addFrame(DBObject queryBean, DBObject insertBean) {
        try {
            mongo.updatePush(CC_FRAMELIST, queryBean, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 修改组织架构节点到数据库
     * 
     * @param queryBean 查询条件
     * @param updateBean 修改的数据
     * @return 返回操作是否成功
     * @since 1.0
     */
    public String modifyFrame(DBObject queryBean, DBObject updateBean) {
        try {
            mongo.update(CC_FRAMELIST, queryBean, updateBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除数据库中组织架构模版数据
     * 
     * @param queryBean 查询条件数据
     * @param deleteBean 删除条件数据
     * @return 返回操作是否成功
     * @since 1.0
     */
    public String deleteRootFrame(DBObject queryBean, DBObject deleteBean) {
        try {
            // 此处为逻辑删除 将状态更新为未启用
            mongo.update(CC_FRAMELIST, queryBean, deleteBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除数据库中组织架构节点数据
     * 
     * @param queryBean 查询条件数据
     * @param deleteBean 删除条件数据
     * @return 返回操作是否成功
     * @since 1.0
     */
    public String deleteFrame(DBObject queryBean, DBObject deleteBean) {
        try {
            mongo.updateDel(CC_FRAMELIST, queryBean, deleteBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 保存数据库中的组织架构树数据
     * 
     * @param queryBean 条件对象
     * @param updateBean 修改的数据对象
     * @param insertBean 如果没有根节点数据就新增
     * @return
     */
    public String saveFrameTree(DBObject queryBean, DBObject updateBean, DBObject insertBean) {
        try {
            DBObject root = mongo.findOne(CC_FRAMELIST, queryBean);
            if (root == null) {
                mongo.insert(CC_FRAMELIST, insertBean);
            }
            else {
                mongo.update(CC_FRAMELIST, queryBean, updateBean);
            }
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 增加历史记录
     * 
     * @param queryBean 条件对象
     * @param insertBean 新增的数据对象
     * @return
     */
    public String addHistroy(DBObject queryBean, DBObject insertBean) {
        try {
            mongo.updatePush(CC_FRAMELIST, queryBean, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
}
