package com.cc.manage.dao;

import com.mongodb.DBObject;

public interface CccompanyDao {
    
    /***
     * 通过企业编码获取企业对象
     * 
     * @param request
     * @return
     * @author admin
     */
    public DBObject getCompanyBycompanyno(String companyno);
    
    /**
     * 根据companyno修改单条B_company对象
     * 
     * @param DBObject updateBean 更新的数据对象
     * @param String companyno 修改条件
     * @return boolean 成功返回true，失败返回false;
     * @author Ron 2014.10.10修改
     */
    public boolean updateByCompanyno(DBObject updateBean, String companyno);
    
    /**
     * 企业注册通过审核添加到企业表
     * 
     * @param bean
     * @return
     * @author Suan
     */
    public boolean add(DBObject bean);
    
}
