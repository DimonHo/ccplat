package com.cc.manage.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.business.service.BusinessService;
import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.EncrypUtil;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.BcompanyccDao;
import com.cc.manage.dao.CcuserDao;
import com.cc.manage.logic.CcuserLogic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 创建企业cc分配的实现类
 * @author ron
 * @createTime 2014.08.30
 */
@Component
public class BcompanyccDaoImpl implements BcompanyccDao {

    public static Log log = LogFactory.getLog(BcompanyccDaoImpl.class);

    @Autowired
    private CcuserDao ccuserDao;
    
    /**
     * 企业端Webservice对象
     * @author Ron
     */
    @Autowired
    private BusinessService businessService;
    
    /**
     * cc_user对象
     * @author Ron
     */
    @Autowired
    private CcuserLogic ccuserLogic;
    
    public JSONObject list(HttpServletRequest request) {
        try {
            JSONObject jo = new JSONObject();
            String companyno = RequestUtils.getString(request, "companyno");
            if (StringUtils.isNotBlank(companyno)) {
                DBObject bean = new BasicDBObject();
                bean.put("companyno", companyno);
                DBObject temp = new BasicDBObject();
                temp.put("$ne", StateConstant.CC_USER_USESTATE2);
                bean.put("usestate", temp);
                
                int pageIndex = RequestUtils.getInt(request, "pageIndex");
                int pageSize = RequestUtils.getInt(request, "pageSize");
                String sortField = RequestUtils.getString(request,"sortField");
                String sortOrder = RequestUtils.getString(request,"sortOrder");
                BasicDBObject orderBy = new BasicDBObject();
                if (StringUtils.isNotBlank(sortField)) {
                    orderBy.put(sortField, StringUtils.setOrder(sortOrder));
                }else{
                    orderBy.put("ccno", StringUtils.setOrder(sortOrder));
                }
                String ccno = RequestUtils.getString(request, "ccno");
                if (StringUtils.isNotBlank(ccno)) {
                    bean.put("ccno", ccno);
                }
                MongoDBManager mongoDb = MongoDBManager.getInstance();
                long count = mongoDb.getCount("cc_user", bean);
                if(count > 0){
                    List<DBObject> list = mongoDb.find("cc_user", bean, orderBy, pageIndex + 1, pageSize);
                    jo.put("data", JSONUtils.resertJSON(list));
                }
                jo.put("total", count);
                return jo;
            }
            jo.put("total", 0);
            jo.put("data", null);
            return jo;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean update(DBObject conditionBean, DBObject bean) {

        try {
            MongoDBManager.getInstance().update("b_companycc", conditionBean, bean);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    /**
     * 设置管理员，1判断该CC号是否存在于ccuser表中，存在则修改ccuser状态，公司id，不存在创建该cc号
     * 修改cccompany中该cc号的状态
     * 插入权限
     * <br>
     * 其中的uuid用于企业端关联权限userpower数据，当cc_user表不存在的时候则关联数据
     * @param HttpServletRequest
     * @return String 字符串1表示成功，2表示失败，4表示操作异常
     * @author Ron
     */
    public String addpower(HttpServletRequest request) {
        
        try {
            String companyno = RequestUtils.getString(request, "companyno");
            String ccno = RequestUtils.getString(request, "ccno");
            String remark = RequestUtils.getString(request, "remark");
            String name = RequestUtils.getString(request, "name");
            String uuid = RequestUtils.getString(request, "uuid");
            if(StringUtils.isBlank(uuid)){
                uuid = UUID.randomUUID().toString();
            }
            String result = businessService.setPower(request, companyno, ccno, remark,uuid,name);
            if(StringUtils.equals(result, ResultConstant.SUCCESS)){//成功
                String id = RequestUtils.getString(request, "id");
                
                DBObject userBean = new BasicDBObject();
                userBean.put("state", StateConstant.CC_USER_STATE1);
                userBean.put("usestate", StateConstant.CC_USER_USESTATE1);
                userBean.put("ccpwd", EncrypUtil.getMD5(ccno));
                userBean.put("realname", name);
                userBean.put("uuid", uuid);
                ccuserDao.updateById(id, userBean);
                ccuserLogic.addHistory(request, id, "设置管理员", new Date());
                return ResultConstant.SUCCESS;
            }else if(StringUtils.equals(result, ResultConstant.FAIL)){//失败
                return ResultConstant.FAIL;
            }else{//返回异常
                return ResultConstant.EXCEPTION;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }

}
