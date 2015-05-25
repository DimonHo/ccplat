package com.cc.manage.dao;

import java.util.List;

import com.mongodb.DBObject;

/**
 * 组织架构管理数据库操作接口。
 * 包含组织架构管理的增删改查数据库操作。
 * @author zzf
 * @since CcFramelistDao1.0
 */
public interface CcFramelistDao {
	
	/**
	 * 从数据库查询行业对应组织架构模版数据集合
	 * @param queryBean 查询条件对象
	 * @param orderBean 排序条件对象
	 * @return 返回查询出的行业对应组织架构模版数据集合
	 * @since 1.0
	 */
	List<DBObject> listFrame(DBObject queryBean,DBObject orderBean);
	
	/**
	 * 新增行业对应组织架构模版到数据库
	 * @param insertBean 插入的数据
	 * @return 返回操作是否成功
	 * @since 1.0
	 */
	String addFrame(DBObject insertBean);
	
	/**
	 * 新增组织架构节点到数据库
	 * @param queryBean 查询条件
	 * @param insertBean 插入的数据
	 * @return 返回操作是否成功
	 * @since 1.0
	 */
	String addFrame(DBObject queryBean,DBObject insertBean);
	
	/**
	 * 删除数据库中组织架构模版数据
	 * @param queryBean 查询条件数据
	 * @param deleteBean 删除条件数据
	 * @return 返回操作是否成功
	 * @since 1.0
	 */
	String deleteRootFrame(DBObject queryBean,DBObject deleteBean);
	
	/**
	 * 删除数据库中组织架构节点数据
	 * @param queryBean 查询条件数据
	 * @param deleteBean 删除条件数据
	 * @return 返回操作是否成功
	 * @since 1.0
	 */
	String deleteFrame(DBObject queryBean,DBObject deleteBean);
	
	/**
	 *  修改组织架构节点到数据库
	 * @param queryBean 查询条件
	 * @param updateBean 修改的数据
	 * @return 返回操作是否成功
	 * @since 1.0
	 */
	String modifyFrame(DBObject queryBean,DBObject updateBean);
	
	/**
	 * 保存数据库中的组织架构树数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @param insertBean 如果没有根节点数据就新增
	 * @return
	 */
	String saveFrameTree(DBObject queryBean,DBObject updateBean,DBObject insertBean);
	
	/**
	 * 增加历史记录
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	String addHistroy(DBObject queryBean,DBObject insertBean);

}
