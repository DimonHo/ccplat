package com.cc.manage.dao;

import java.util.List;

import com.mongodb.DBObject;

/**
 * 行业管理数据库操作接口。
 * 包含行业管理的增删改查数据库操作。
 * @author zzf
 * @since CcIndustryinfoDao1.0
 */
public interface CcIndustryinfoDao {
	
	/**
	 * 从数据库查询所有行业数据集合,返回children部分的数据
	 * @return 返回查询出的所有行业数据集合
	 * @since 1.0
	 */
	List<DBObject> listIndustryinfo();
	
	/**
	 * 从数据库查询所有临时行业数据集合
	 * @param request 请求对象
	 * @return 返回查询出的所有临时行业数据集合
	 * @since 1.0
	 */
	List<DBObject> listTemp(DBObject queryBean);
	
	/**
	 * 保存行业树到临时表
	 * @param insertBean 插入数据
	 * @return
	 * @since 1.0
	 */
	String saveTemp(DBObject insertBean);
	
	
	
	/**
	 * 将新增的行业数据添加到数据库中
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	String addIndustryinfo(DBObject queryBean,DBObject insertBean);
	
	/**
	 * 修改数据库中的行业数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @return
	 */
	String modifyIndustryinfo(DBObject queryBean,DBObject updateBean);
	
	/**
	 * 删除数据库中的行业数据
	 * @param queryBean 条件对象
	 * @param deleteBean 修改的数据对象
	 * @return
	 */
	String deleteIndustryinfo(DBObject queryBean,DBObject deleteBean);
	
	/**
	 * 保存数据库中的行业树数据
	 * @param queryBean 条件对象
	 * @param updateBean 修改的数据对象
	 * @return
	 */
	String saveIndustryinfo(DBObject queryBean,DBObject updateBean);
	
	/**
	 * 获取保存临时表的行业临时数据
	 * @param queryBean 条件对象
	 * @return
	 */
	DBObject getTempData(DBObject queryBean);
	
	/**
	 *删除临时表的行业临时数据
	 * @param queryBean 条件对象
	 * @return
	 */
	String deleteTemp(DBObject queryBean);
	
	/**
	 * 增加历史记录
	 * @param queryBean 条件对象
	 * @param insertBean 新增的数据对象
	 * @return
	 */
	String addHistroy(DBObject queryBean,DBObject insertBean);
	
}
