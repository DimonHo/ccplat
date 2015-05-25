package com.cc.validate.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.utils.DateUtils;
import com.cc.core.utils.EncrypUtil;
import com.cc.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Component
public class CccompanyccDao {

    public static Log log = LogFactory.getLog(CccompanyccDao.class);

    /***
     * 通过企业编号获取企业账号以及账号状态
     * @param companyno
     * @return
     */
    public static String bycccompanyno(String companyno) {

        String str = "";
        try {
            if (StringUtils.isNotBlank(companyno)) {
                DBObject bean = new BasicDBObject();
                bean.put("companyno", companyno);
                MongoDBManager mongoDb = MongoDBManager.getInstance();
                List<DBObject> list = mongoDb.find("b_companycc", bean);
                for (DBObject temp : list) {
                    Date start = (Date) temp.get("starttime");
                    Date end = (Date) temp.get("endtime");
                    str += temp.get("ccno") + AplicationKeyConstant.STRING_SPLIT_CHAR + temp.get("state") + AplicationKeyConstant.STRING_SPLIT_CHAR + DateUtils.dateToString(start, DateUtils.DATE_FORMAT_DD) + AplicationKeyConstant.STRING_SPLIT_CHAR + DateUtils.dateToString(end, DateUtils.DATE_FORMAT_DD) + ";";
                }
                if (StringUtils.isNotBlank(str) && str.length() > 0) {
                    return str.substring(0, str.length() - 1);
                }
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }

    /***
     * 修改企业账号cccompanycc的账号状态
     * @param companyno
     * @param ccno
     * @param state
     * @param operator
     * @param valicode
     * @return 0表示操作成功，1表示操作失败，2表示参数异常,3账号信息不存在
     */
    public static String updateCompanyCC(String companyno, String ccno, String state, String operator, String remark, String valicode) {

        try {
            if (StringUtils.isNotBlank(companyno) && StringUtils.isNotBlank(ccno) && StringUtils.isNotBlank(state) && StringUtils.isNotBlank(operator) && StringUtils.isNotBlank(valicode)) {
                DBObject bean = new BasicDBObject();
                bean.put("companyno", companyno);
                bean.put("ccno", ccno);

                MongoDBManager mongoDb = MongoDBManager.getInstance();
                DBObject temp = mongoDb.findOne("b_companycc", bean);
                if (temp == null) {
                    return "3";
                }
                DBObject value = new BasicDBObject();
                value.put("state", state);
                mongoDb.update("b_companycc", bean, value);
                Date date = new Date();

                DBObject historyBean = new BasicDBObject();

                historyBean.put("createtime", date);
                
                DBObject operatorBean = new BasicDBObject();
                operatorBean.put("name", operator);
                operatorBean.put("ccno", operator);
                historyBean.put("operator", operatorBean);
                historyBean.put("starttime", null);
                historyBean.put("endtime", null);
                historyBean.put("state", state);
                historyBean.put("deptid", null);
                historyBean.put("job", null);
                historyBean.put("remark", "cs端修改状态," + remark);

                DBObject history = new BasicDBObject();
                history.put("history", historyBean);

                mongoDb.updatePush("b_companycc", bean, history);

                if ("1".equals(state)) {//启用
                    DBObject user = CcuserDao.queryByccno(ccno);
                    if (user == null) {
                        CcuserDao.csAddUser(ccno, EncrypUtil.getMD5("123456"), state);
                    }
                }
                return "0";
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "1";
    }

}
