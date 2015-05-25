package com.cc.manage.logic;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.manage.dao.CcbatchDao;
import com.cc.manage.dao.CcnumberDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 创建cc账号批次logic处理
 * @author ron
 * @createTime 2014.09.10
 */
@Component
public class CcbatchLogic {

    public static Log log = LogFactory.getLog(CcbatchLogic.class);

    @Autowired
    private CcbatchDao ccbatchDao;

    @Autowired
    private CcnumberDao ccnumberDao;

    @Autowired
    private CcnumberLogic ccnumberLogic;

    /***
     * 企业内的CC号管理列表通过企业ID查询结果
     * 
     * @param request
     * @return
     */
    public JSONObject list(HttpServletRequest request) {

        return ccbatchDao.list(request);
    }

    /**
     * 根据选择的账号类型中最大账号开始生成CC账号，超过最大类型数量则不增加
     * 账号生成添加,
     * @param request
     * @return
     */
    public String add(HttpServletRequest request) {

        String batchid = "";
        try {
            int type = RequestUtils.getInt(request, "type");
            if (type > 0) {
                int count = RequestUtils.getInt(request, "count");
                int max = this.ccnumberDao.maxCcNo(type);
                int length = max + count;

                boolean flas = true;
                if (type == 2) {//1000-99999(内部企业号码).（1-5位成聪内部员工）
                    if (length > 100000) {
                        length = 100000;
                    }

                    if (max == 100000) {//内部企业号码注册完
                        flas = false;
                    }
                } else if (type == 3) {//3、100000-9999999(外部企业号码).(6-7位号码企业帐号)
                    if (length > 10000000) {
                        length = 10000000;
                    }
                    if (max == 10000000) {//外部企业号码注册完
                        flas = false;
                    }
                } else if (type == 4) {//4、10000000以上(外部使用).(8位号码以上普通用户帐号)
                    if (length < 10000000) {
                        length = 10000000;
                    }
                }
                if (flas) {// 账号未分配完，可以分配账号
                    Date createtime = new Date();
                    DBObject batchbean = new BasicDBObject();
                    batchbean.put("start", max);
                    batchbean.put("end", (length-1));
                    batchbean.put("remark", RequestUtils.getString(request, "remark"));
                    batchbean.put("createtime", createtime);
                    DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
                    DBObject operator = new BasicDBObject();
                    operator.put("name", DBObejctUtils.getString(userSession, "realname"));
                    operator.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
                    batchbean.put("operator", operator);
                    this.ccbatchDao.add(batchbean);
                    batchid = batchbean.get("_id").toString();
                    for (int i = max; i < length; i++) {
                        DBObject bean = new BasicDBObject();
                        bean.put("ccno", String.valueOf(i));
                        bean.put("order", i);
                        bean.put("state", StateConstant.CC_NUMBER_STATE2);
                        bean.put("type", String.valueOf(type));
                        bean.put("level", String.valueOf(ccnumberLogic.special(String.valueOf(i), type)));
                        bean.put("ccbatchid", batchid);
                        bean.put("createtime", createtime);
                        bean.put("operator", operator);
                        this.ccnumberDao.add(bean);
                    }
                } else {
                    return batchid;
                }
                return ResultConstant.SUCCESS;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
}
