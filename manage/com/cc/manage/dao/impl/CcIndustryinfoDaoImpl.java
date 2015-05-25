package com.cc.manage.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.manage.dao.CcIndustryinfoDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 行业管理数据库操作实现类。 包含行业管理的增删改查数据库操作。
 * 
 * @author zzf
 * @since CcIndustryinfoDaoImpl1.0
 */
@Component
public class CcIndustryinfoDaoImpl implements CcIndustryinfoDao {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcIndustryinfoDaoImpl.class);
    
    /**
     * 从数据库查询所有行业数据集合  返回children部分的数据
     * 
     * @return 返回查询出的所有行业数据集合
     * @since 1.0
     * @author Ron(modify2014.10.16)
     */
    @SuppressWarnings("unchecked")
    public List<DBObject> listIndustryinfo() {
        try {
            // 查询所有行业模版集合
            List<DBObject> templateList = MongoDBManager.getInstance().findAll("cc_industryinfo");
            
            if (CollectionUtils.isNotEmpty(templateList)) {
                DBObject templateDb = templateList.get(0);
                List<DBObject> industryinfoList = (List<DBObject>)templateDb.get("children");
                return industryinfoList;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 从数据库查询所有临时行业数据集合
     * 
     * @param queryBean 条件对象
     * @return 返回查询出的所有临时行业数据集合
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    public List<DBObject> listTemp(DBObject queryBean) {
        try {
            // 流程id对应的临时数据
            DBObject temp = MongoDBManager.getInstance().findOne("cc_industryinfo_temp", queryBean);
            
            if (temp != null) {
                List<DBObject> industryinfoList = (List<DBObject>)temp.get("children");
                return industryinfoList;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 将新增的行业数据添加到数据库中
     * 
     * @param queryBean 条件对象
     * @param insertBean 新增的数据对象
     * @return
     */
    public String addIndustryinfo(DBObject queryBean, DBObject insertBean) {
        try {
            MongoDBManager.getInstance().updatePush("cc_industryinfo", queryBean, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 修改数据库中的行业数据
     * 
     * @param queryBean 条件对象
     * @param updateBean 修改的数据对象
     * @return
     */
    public String modifyIndustryinfo(DBObject queryBean, DBObject updateBean) {
        try {
            MongoDBManager.getInstance().update("cc_industryinfo", queryBean, updateBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除数据库中的行业数据
     * 
     * @param queryBean 条件对象
     * @param deleteBean 修改的数据对象
     * @return
     */
    public String deleteIndustryinfo(DBObject queryBean, DBObject deleteBean) {
        try {
            MongoDBManager.getInstance().updateDel("cc_industryinfo", queryBean, deleteBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 保存行业树到临时表
     * 
     * @param insertBean 插入数据
     * @return
     * @since 1.0
     */
    public String saveTemp(DBObject insertBean) {
        try {
            MongoDBManager.getInstance().insert("cc_industryinfo_temp", insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 获取保存临时表的行业临时数据
     * 
     * @param queryBean 条件对象
     * @return
     */
    public DBObject getTempData(DBObject queryBean) {
        try {
            // 查询行业表临时数据
            DBObject temp = MongoDBManager.getInstance().findOne("cc_industryinfo_temp", queryBean);
            return temp;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     *删除临时表的行业临时数据
     * 
     * @param queryBean 条件对象
     * @return
     */
    public String deleteTemp(DBObject queryBean) {
        try {
            MongoDBManager.getInstance().delete("cc_industryinfo_temp", queryBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 保存数据库中的行业树数据
     * 
     * @param queryBean 条件对象
     * @param updateBean 修改的数据对象
     * @return
     */
    public String saveIndustryinfo(DBObject queryBean, DBObject updateBean) {
        try {
        	List<DBObject> list=MongoDBManager.getInstance().find("cc_industryinfo", queryBean);
        	if(!CollectionUtils.isNotEmpty(list)){
        		DBObject insertBean=new BasicDBObject();
        		insertBean.put("children", new ArrayList<DBObject>());
        		insertBean.put("history", new ArrayList<DBObject>());
        		MongoDBManager.getInstance().insert("cc_industryinfo", insertBean);
        	}
            MongoDBManager.getInstance().update("cc_industryinfo", queryBean, updateBean);
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
            MongoDBManager.getInstance().updatePush("cc_industryinfo", queryBean, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
}
