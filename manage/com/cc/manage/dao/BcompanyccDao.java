package com.cc.manage.dao;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.mongodb.DBObject;

public interface BcompanyccDao {

    public JSONObject list(HttpServletRequest request);

    /**
     * @param conditionBean 修改条件
     * @param bean 修改的内容
     * @return
     */
    public boolean update(DBObject conditionBean, DBObject bean);

    /**
     * 企业账号分配管理员
     * @param request
     * @return
     * @author Ron
     */
    public String addpower(HttpServletRequest request);

}
