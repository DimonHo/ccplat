package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.manage.dao.CcProductslistDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 组织架构管理数据库操作实现类。 包含组织架构管理的增删改查数据库操作。
 * 
 * @author zzf
 * @since CcFramelistDaoImpl1.0
 */
@Component
public class CcProductslistDaoImpl extends BaseDao implements CcProductslistDao {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcIndustryinfoDaoImpl.class);
    
    /**
     * 从数据库查询行业对应产品模版数据集合
     * 
     * @param queryBean 查询条件对象
     * @param orderBean 排序条件对象
     * @return 返回查询出的行业对应产品模版数据集合
     * @since 1.0
     */
    public List<DBObject> listProduct(DBObject queryBean, DBObject orderBean) {
        try {
            // 查询对应行业所有产品模版集合
            List<DBObject> templateList = mongo.find(CC_PRODUCTSLIST, queryBean, orderBean);
            
            if (CollectionUtils.isNotEmpty(templateList)) {
                return templateList;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /***
     * 通过code字段获取产品的功能
     * @param code
     * @return
     * @author Ron
     */
    public DBObject getByCode(String code){
        try {
            
            DBObject bean = new BasicDBObject();
            bean.put("code", code);
            return mongo.findOne(CC_PRODUCTSLIST, bean);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 保存数据库中的产品树数据
     * 
     * @param queryBean 条件对象
     * @param updateBean 修改的数据对象
     * @param insertBean 如果没有根节点数据就新增
     * @return
     */
    public String saveProduct(DBObject queryBean, DBObject updateBean, DBObject insertBean) {
        try {
            DBObject root = mongo.findOne(CC_PRODUCTSLIST, queryBean);
            if (root == null) {
                mongo.insert(CC_PRODUCTSLIST, insertBean);
            }
            else {
                mongo.update(CC_PRODUCTSLIST, queryBean, updateBean);
            }
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /**
     * 删除数据库中的产品树模版
     * 
     * @param queryBean 条件对象
     * @param updateBean 修改的数据对象
     * @return
     */
    public String deleteProduct(DBObject queryBean, DBObject updateBean) {
        try {
            mongo.update(CC_PRODUCTSLIST, queryBean, updateBean);
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
            mongo.updatePush(CC_PRODUCTSLIST, queryBean, insertBean);
            return ResultConstant.SUCCESS;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
    
    /*public static void main(String[] args) {
    	
    	List<DBObject> list = MongoDBManager.getInstance().findAll("cc_productslist");
    	new CcProductslistDaoImpl().build(list);
    	for(DBObject temp : list){
    		String id = temp.get("_id").toString();
    		List<DBObject> children = DBObejctUtils.getList(temp, "children");
    		DBObject q = new BasicDBObject();
    		q.put("_id", new ObjectId( id));
    		DBObject d = new BasicDBObject();
    		d.put("children", children);
    		MongoDBManager.getInstance().update("cc_productslist", q, d);
    	}
    	System.out.println(list.toString());
    	
	}*/
    
    public void build(List<DBObject> list){
	    if(CollectionUtils.isNotEmpty(list)){	
	    	for(DBObject temp:list){
	    		List<DBObject> listtemp = DBObejctUtils.getList(temp, "children");
	    		if(CollectionUtils.isNotEmpty(listtemp)){
	    			temp.put("ico", "");
	    			this.build(listtemp);
	    		}
	    	}
	    }
    }
    
    
    
}
