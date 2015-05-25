package com.cc.manage.dao.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcbatchDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 创建cc账号批次实现类
 * 
 * @author ron
 * @createTime 2014.08.30
 */
@Component
public class CcbatchDaoImpl extends BaseDao implements CcbatchDao {
    
    public static Log log = LogFactory.getLog(CcbatchDaoImpl.class);
    
    public boolean add(DBObject bean) {
        
        try {
            mongo.insert(CC_BATCH, bean);
            return true;
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    public JSONObject list(HttpServletRequest request) {
        
        JSONObject jo = new JSONObject();
        try {
            DBObject bean = new BasicDBObject();
            String sortField = RequestUtils.getString(request, "sortField");
            BasicDBObject orderBy = new BasicDBObject();
            if (StringUtils.isNotBlank(sortField)) {
                orderBy.put(sortField, StringUtils.setOrder(RequestUtils.getString(request, "sortOrder")));
            }
            long count = mongo.getCount(CC_BATCH, bean);
            if(count > 0){
                List<DBObject> list =
                    mongo.find(CC_BATCH,
                        bean,
                        orderBy,
                        RequestUtils.getInt(request, "pageIndex") + 1,
                        RequestUtils.getInt(request, "pageSize"));
                jo.put("total", count);
                jo.put("data", JSONUtils.resertJSON(list));
                return jo;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        jo.put("total", 0);
        jo.put("data", null);
        return jo;
    }
}
