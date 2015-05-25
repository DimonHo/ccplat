package com.cc.manage.dao;

import java.util.List;


import com.mongodb.DBObject;

/**
 * 岗位管理数据库操作接口。
 * 包含岗位管理的增删改查数据库操作。
 * @author zzf
 * @since CcRolelistDao1.0
 */
public interface CcRolelistDao {
	
	/**
	 * 从数据库查询所有岗位数据集合
	 * @param queryBean 查询条件
	 * @param orderBean 排序对象
	 * @return 返回查询出的所有岗位数据集合
	 * @since 1.0
	 */
	List<DBObject> listRole(DBObject queryBean,DBObject orderBean);
	
	/**
	 * 添加岗位模版数据到数据库
	 * @param insertBean 封装的插入数据
	 * @return 插入结果
	 * @since 1.0
	 */
	String addRole(DBObject insertBean);
	
	/**
	 * 添加岗位数据到数据库
	 * @param queryBean 插入到哪条岗位模版数据
	 * @param insertBean 封装的插入数据
	 * @return 插入结果
	 * @since 1.0
	 */
	String addRole(DBObject queryBean,DBObject insertBean);
	
	/**
	 * 修改岗位数据到数据库
	 * @param queryBean 封装的条件数据
	 * @param updateBean 封装的修改数据
	 * @return 修改结果
	 * @since 1.0
	 */
	String modifyRole(DBObject queryBean,DBObject updateBean);
	
	/**
	 * 删除岗位数据
	 * @param queryBean 封装的条件数据
	 * @param deleteBean 找对应删除数组条件数据
	 * @return 修改结果
	 * @since 1.0
	 */
	String deleteRole(DBObject queryBean,DBObject deleteBean);
	
	/**
	 * 删除模版数据
	 * 这里是逻辑删除，将状态设置为未启用
	 * @param queryBean 封装的条件数据
	 * @param updateBean 更新状态数据
	 * @return 修改结果
	 * @since 1.0
	 */
	String deleteRootRole(DBObject queryBean,DBObject updateBean);
	
	/**
	 * 保存数据库中的岗位树数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @param insertBean 如果没有根节点数据就新增
	 * @return
	 */
	String saveRoleTree(DBObject queryBean,DBObject updateBean,DBObject insertBean);
	
	/**
	 * 增加历史记录
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	String addHistroy(DBObject queryBean,DBObject insertBean);

}
