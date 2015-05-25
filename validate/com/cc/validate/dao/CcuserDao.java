package com.cc.validate.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CcuserDao {

    public static Log log = LogFactory.getLog(CcuserDao.class);

    /***
     * 通过账号查询的ccuser中的单条信息
     * @param ccno
     * @return 存在:只返回ccno字段；否则返回null
     */
    public static DBObject queryByccno(String ccno) {

        try {
            if (StringUtils.isNotBlank(ccno)) {
                DBObject bean = new BasicDBObject();
                bean.put("ccno", ccno);

                DBObject fileds = new BasicDBObject();
                fileds.put("ccno", 1);
                MongoDBManager mongoDb = MongoDBManager.getInstance();
                return mongoDb.findOne("cc_user", bean);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /***
     * 客户端水务通第一次分配账号，录入数据到cc_user保证登录验证存在数据
     * @param ccno
     * @param psw
     * @param state
     * @return
     */
    public static String csAddUser(String ccno, String psw, String state) {

        try {
            if (StringUtils.isNotBlank(ccno)) {
                DBObject bean = new BasicDBObject();
                bean.put("ccno", ccno);
                bean.put("ccpwd", psw);
                bean.put("state", state);
                MongoDBManager mongoDb = MongoDBManager.getInstance();
                mongoDb.insert("cc_user", bean);
                return "0";
            }
        } catch (Exception e) {
            //删除cccompanycc数据
            log.error(e.getLocalizedMessage(), e);
        }
        return "1";

    }
}
