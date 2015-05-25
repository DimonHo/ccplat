package com.cc.manage.dao.impl;

import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.manage.dao.CcRolelistDao;
import com.mongodb.DBObject;

/**
 * 岗位管理数据库操作实现类。
 * 包含岗位管理的增删改查数据库操作。
 * @author zzf
 * @since CcRolelistDaoImpll1.0
 */
@Component
public class CcRolelistDaoImpl implements CcRolelistDao {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(CcRolelistDaoImpl.class);

	/**
	 * 从数据库查询所有岗位数据集合
	 * @param queryBean 查询条件
	 * @param orderBean 排序对象
	 * @return 返回查询出的所有岗位数据集合
	 * @since 1.0
	 */
	public List<DBObject> listRole(DBObject queryBean,DBObject orderBean) {
		try{
			//查询所有岗位模版集合
			List<DBObject> templateList=MongoDBManager.getInstance().find("cc_rolelist",queryBean,orderBean);
			
			if(CollectionUtils.isNotEmpty(templateList)){
				return templateList;
			}
		}catch (Exception e) {
			log.error(e.getLocalizedMessage(), e);
		}
		return null;
	}
	
	/**
	 * 添加岗位模版数据到数据库
	 * @param insertBean 封装的插入数据
	 * @return 插入结果
	 * @since 1.0
	 */
	public String addRole(DBObject insertBean){
		try {
            MongoDBManager.getInstance().insert("cc_rolelist", insertBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 添加岗位数据到数据库
	 * @param queryBean 插入到哪条岗位模版数据
	 * @param insertBean 封装的插入数据
	 * @return 插入结果
	 * @since 1.0
	 */
	public String addRole(DBObject queryBean,DBObject insertBean){
		try {
            MongoDBManager.getInstance().updatePush("cc_rolelist", queryBean, insertBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 修改岗位数据到数据库
	 * @param queryBean 封装的条件数据
	 * @param updateBean 封装的修改数据
	 * @return 修改结果
	 * @since 1.0
	 */
	public String modifyRole(DBObject queryBean,DBObject updateBean){
		try {
            MongoDBManager.getInstance().update("cc_rolelist", queryBean, updateBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 删除岗位数据
	 * @param queryBean 封装的条件数据
	 * @param deleteBean 找对应删除数组条件数据
	 * @return 修改结果
	 * @since 1.0
	 */
	public String deleteRole(DBObject queryBean,DBObject deleteBean){
		try {
            MongoDBManager.getInstance().updateDel("cc_rolelist", queryBean, deleteBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 删除模版数据
	 * 这里是逻辑删除，将状态设置为未启用
	 * @param queryBean 封装的条件数据
	 * @param updateBean 更新状态数据
	 * @return 修改结果
	 * @since 1.0
	 */
	public String deleteRootRole(DBObject queryBean,DBObject updateBean){
		try {
            MongoDBManager.getInstance().update("cc_rolelist", queryBean, updateBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 保存数据库中的岗位树数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @param insertBean 如果没有根节点数据就新增
	 * @return
	 */
	public String saveRoleTree(DBObject queryBean,DBObject updateBean,DBObject insertBean){
		try {
			DBObject root=MongoDBManager.getInstance().findOne("cc_rolelist", queryBean);
			if(root==null){
				MongoDBManager.getInstance().insert("cc_rolelist",insertBean);
			}else{
				MongoDBManager.getInstance().update("cc_rolelist", queryBean, updateBean);
			}
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 增加历史记录
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	public String addHistroy(DBObject queryBean,DBObject insertBean){
		try {
            MongoDBManager.getInstance().updatePush("cc_rolelist", queryBean, insertBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
		return ResultConstant.FAIL;
	}

}
