package com.cc.manage.logic;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.cc.core.constant.TableNameConstant;
import com.cc.manage.dao.CommonDao;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 绑定逻辑类
 * @author Einsteiin
 *
 */
@Component
public class BindLogic {
    private CommonDao common = new CommonDaoImpl();

    /**
     * 绑定设备
     * @param request  uuid,,imei(当前设备的唯一编码)
     * @param response true:成功；false:失败
     */
    public boolean bindDeviceState1(String uuid, String imei, String model) {

        DBObject bind = new BasicDBObject();
        bind.put("imei", imei);
        bind.put("time", new Date());
        bind.put("model", model);
        bind.put("state", 1);//审核通过

        DBObject bindInfo = (DBObject) common.searchCommonOne(TableNameConstant.T_BIND, new BasicDBObject("uuid", uuid));
        if (bindInfo == null) {
            common.insertCommon(TableNameConstant.T_BIND, new BasicDBObject("uuid", uuid));
        }
        return (Boolean) common.updateCommonPush(TableNameConstant.T_BIND, new BasicDBObject("uuid", uuid), new BasicDBObject("imeis", bind));
    }

    /**
     * 绑定设备
     * @param request  uuid,,imei(当前设备的唯一编码)
     * @param response true:成功；false:失败
     */
    public boolean bindDevice(String uuid, String imei, String model) {

        DBObject bind = new BasicDBObject();
        bind.put("imei", imei);
        bind.put("time", new Date());
        bind.put("model", model);
        bind.put("state", 0);//待审核 
        //需要调用公共审核方法
        bind.put("state", 1);//临时代码，默认审核通过
        return (Boolean) common.updateCommonPush(TableNameConstant.T_BIND, new BasicDBObject("uuid", uuid), new BasicDBObject("imeis", bind));
    }

    /**
     * 根据UUID得到绑定信息
     * @param request
     * @param response
     */
    public DBObject getBindInfo(String uuid, String imei) {

        DBObject condition = new BasicDBObject();
        condition.put("uuid", uuid);
        condition.put("imeis.imei", imei);
        DBObject fields = new BasicDBObject();
        fields.put("imeis.state", 1);
        fields.put("imeis.model", 1);
        fields.put("imeis.time", 1);
        return (DBObject) common.searchLessCommonOne(TableNameConstant.T_BIND, condition, fields);
    }

    /**
     * 根据UUID得到绑定信息
     * @param request
     * @param response
     */
    public DBObject queryBind(String uuid) {

        DBObject condition = new BasicDBObject();
        condition.put("uuid", uuid);
        return (DBObject) common.searchCommonOne(TableNameConstant.T_BIND, condition);
    }
}
