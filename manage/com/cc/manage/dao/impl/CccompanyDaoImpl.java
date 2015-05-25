package com.cc.manage.dao.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CccompanyDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CccompanyDaoImpl implements CccompanyDao {
    public static Log log = LogFactory.getLog(CccompanyDaoImpl.class);
    
    public List<DBObject> getCompany(DBObject queryBean) {
        MongoDBManager db = MongoDBManager.getInstance();
        return db.find("b_company", queryBean);
    }
    
    public boolean addBatch(List<DBObject> beans) {
        
        try {
            MongoDBManager.getInstance().insertBatch("b_company", beans);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /**
     * 根据companyno修改单条B_company对象
     * 
     * @param DBObject updateBean 更新的数据对象
     * @param String companyno 修改条件
     * @return boolean 成功返回true，失败返回false;
     * @author Ron 2014.10.10修改
     */
    public boolean updateByCompanyno(DBObject updateBean, String companyno) {
        try {
            if (StringUtils.isBlank(companyno)) {
                return false;
            }
            DBObject queryBean = new BasicDBObject();
            queryBean.put("companyno", companyno);
            MongoDBManager.getInstance().update("b_company", queryBean, updateBean);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /***
     * 通过企业编码获取企业对象
     * 
     * @param request
     * @return
     * @author admin
     */
    public DBObject getCompanyBycompanyno(String companyno) {
        try {
            DBObject queryBean = new BasicDBObject();
            queryBean.put("companyno", companyno);
            return MongoDBManager.getInstance().findOne("b_company", queryBean);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /**
     * 添加企业信息
     * 
     * @param bean 企业申请表信息
     * @return
     * @author Suan
     */
    public boolean add(DBObject bean) {
        
        try {
            MongoDBManager.getInstance().insert(TableNameConstant.T_COMPANY, bean);// T_COMPANY
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
}
