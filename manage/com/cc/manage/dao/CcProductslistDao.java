package com.cc.manage.dao;

import java.util.List;

import com.mongodb.DBObject;

/**
 * 产品管理数据库操作接口。
 * 包含产品管理的增删改查数据库操作。
 * @author zzf
 * @since CcProductslistDao1.0
 */
public interface CcProductslistDao {
	
	/**
	 * 从数据库查询行业对应产品模版数据集合
	 * @param queryBean 查询条件对象
	 * @param orderBean 排序条件对象
	 * @return 返回查询出的行业对应产品模版数据集合
	 * @since 1.0
	 */
	List<DBObject> listProduct(DBObject queryBean,DBObject orderBean);
	
	/***
	 * 通过code字段获取产品的功能
	 * @param code
	 * @return
	 * @author Ron
	 */
	DBObject getByCode(String code);
	
	/**
	 * 增加历史记录
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	String addHistroy(DBObject queryBean,DBObject insertBean);
	
	/**
	 * 保存数据库中的产品树数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @param insertBean 如果没有根节点数据就新增
	 * @return
	 */
	String saveProduct(DBObject queryBean,DBObject updateBean,DBObject insertBean);
	
	/**
	 * 删除数据库中的产品树模版
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @return
	 */
	String deleteProduct(DBObject queryBean,DBObject updateBean);

	
	
}
