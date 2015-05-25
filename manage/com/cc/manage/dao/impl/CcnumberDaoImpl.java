package com.cc.manage.dao.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.NumberUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcnumberDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * cc账号池的实现类
 * 
 * @author ron
 * @createTime 2014.08.30
 */
@Component
public class CcnumberDaoImpl extends BaseDao implements CcnumberDao {

    public static Log log = LogFactory.getLog(CcnumberDaoImpl.class);

    public boolean add(DBObject bean) {

        try {
            mongo.insert(CC_NUMBER, bean);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

    public JSONObject list(HttpServletRequest request) {

        try {
            String ccbatchid = RequestUtils.getString(request, "ccbatchid");
            if (StringUtils.isNotBlank(ccbatchid)) {
                DBObject bean = new BasicDBObject();
                bean.put("ccbatchid", ccbatchid);

                String sortField = RequestUtils.getString(request,"sortField");
                String sortOrder = RequestUtils.getString(request,"sortOrder");
                BasicDBObject orderBy = new BasicDBObject();
                if (StringUtils.isNotBlank(sortField)) {
                    orderBy.put(sortField, StringUtils.setOrder(sortOrder));
                } else {
                    orderBy.put("order", StringUtils.setOrder(sortOrder));
                }
                long count = mongo.getCount(CC_NUMBER, bean);
                List<DBObject> list = null;
                if (count != 0) {
                    list = mongo.find("cc_number", bean, orderBy, RequestUtils.getInt(request, "pageIndex") + 1, RequestUtils.getInt(request, "pageSize"));
                }
                JSONObject jsonBean = new JSONObject();
                jsonBean.put("total", count);
                jsonBean.put("data", JSONUtils.resertJSON(list));
                return jsonBean;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    public boolean modify(DBObject conditionBean, DBObject bean) {

        try {
            mongo.update(CC_NUMBER, conditionBean, bean);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /***
    * 根据id修改账号状态
    * @param state
    * @param id
    * @return
    * @author Ron 20143.10.10
    */
    public boolean modifyStateById(String state, String id) {

        try {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            DBObject bean = new BasicDBObject();
            bean.put("state", state);
            mongo.update(CC_NUMBER, conditionBean, bean);
            return true;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }

     /***
     * 根据id增加cc_Number中的历史记录
     * @param id
     * @param historyBean
     * @return
     * @author Ron 20143.10.10
     */
    public String addHistroyById(String id, DBObject histroyBean) {
        try {
            DBObject conditionBean = new BasicDBObject();
            conditionBean.put("_id", new ObjectId(id));
            MongoDBManager.getInstance().updatePush(CC_NUMBER,conditionBean ,histroyBean);
            return ResultConstant.SUCCESS;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }

    /***
     * 通过当前类型（1,预留;2,内部企业号码;3,外部企业号码;4,外部使用）获取最小的待添加的CC账号
     * 
     * @param type
     */
    public int maxCcNo(int type) {

        DBObject bean = new BasicDBObject();
        bean.put("type", String.valueOf(type));
        BasicDBObject orderBy = new BasicDBObject();
        orderBy.put("order", StringUtils.setOrder("-1"));
        List<DBObject> list = mongo.find(CC_NUMBER, bean, orderBy, 1, 1);
        int max = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            max = NumberUtils.stringToint(DBObejctUtils.getString(list.get(0), "ccno"));
            if (max != 0) {
                return ++max;
            }
        }
        if (type == 2) {
            return 1000;
        } else if (type == 3) {
            return 100000;
        } else if (type == 4) {
            return 10000000;
        }
        return 0;
    }
}
