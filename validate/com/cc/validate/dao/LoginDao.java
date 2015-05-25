package com.cc.validate.dao;

import java.util.Date;
import java.util.List;

import com.cc.core.common.MongoDBManager;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 登录实现层
 * 
 * @author wxj
 * @createtime 2014.9.1
 */
public class LoginDao {

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 企业登录验证cccompanycc
     * 
     * @param ccno		cc账号
     * @param companyno	企业编号
     * @return
     */
    public static String validateCccompanycc(String ccno, String companyno) {

        MongoDBManager db = MongoDBManager.getInstance();

        DBObject conditionBean = null; //条件Bean
        DBObject objBean = null; //临时存放Bean

        try {
            conditionBean = new BasicDBObject();
            conditionBean.put("ccno", ccno);
            conditionBean.put("companyno", companyno);
            objBean = db.findOne("b_companycc", conditionBean);

            if (objBean == null) {
                return "1";
            } else {

                Date now = new Date();
                Date endtime = (Date) objBean.get("endtime");

                if (now.after(endtime)) {
                    return "2"; //超过使用时间
                }

                if (!objBean.get("state").equals("1")) {
                    return "3"; //未启用或已停用
                }
                return "0";
            }

        } catch (Exception e) {
            return "99";//查询异常
        }
    }

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 企业登录验证ccuser
     * 
     * @param ccno		cc账号
     * @param ccpwd		cc密码
     * @return
     */
    public static String validateCcuser(String ccno, String ccpwd) {

        MongoDBManager db = MongoDBManager.getInstance();
        DBObject conditionBean = null;
        //判断多种情况
        DBObject obj = null;

        try {

            conditionBean = new BasicDBObject();
            conditionBean.put("ccno", ccno);
            obj = db.findOne("cc_user", conditionBean);

            if (!obj.get("state").equals("1")) {
                return "4"; //停用
            }

            if (!obj.get("ccpwd").toString().toLowerCase().equals(ccpwd.toLowerCase())) {
                return "5";//密码验证有误
            } else {
                return "0";//验证通过
            }
        } catch (Exception e) {
            return "99";//查询异常
        }
    }

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 企业用户修改密码
     * 
     * @param ccno		cc账号
     * @param ccpwd		cc原密码
     * @param newccpwd	cc新账号
     * @return
     */
    public static String updatePwd(String ccno, String ccpwd, String newccpwd) {

        MongoDBManager db = MongoDBManager.getInstance();
        DBObject conditionBean = null;
        DBObject updateBean = null;
        DBObject obj = null;
        try {
            conditionBean = new BasicDBObject();
            conditionBean.put("ccno", ccno);
            obj = db.findOne("b_companycc", conditionBean);
            if (!obj.get("state").equals("1")) {
                return "2";//账号已内部停用
            }
            obj = db.findOne("cc_user", conditionBean);
            if (!obj.get("state").equals("1")) {
                return "3";//账号已被系统停用
            }
            if (!obj.get("ccpwd").toString().toLowerCase().equals(ccpwd.toLowerCase())) {
                return "4";//密码验证有误
            }
            if (newccpwd.length() != 32) {
                return "5";//新密码没有进行加密
            }
            updateBean = new BasicDBObject();
            updateBean.put("ccpwd", newccpwd);
            db.update("cc_user", conditionBean, updateBean);
            return "0"; //修改成功
        } catch (Exception e) {
            return "99";//异常
        }
    }

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 通过CC号获得b_companycc数据
     * 
     * @param ccno		cc账号
     * @return
     */
    public static DBObject getCCcompanyccByCCno(String ccno) {

        MongoDBManager db = MongoDBManager.getInstance();
        DBObject conditionBean = null;
        DBObject result = null;
        try {
            conditionBean = new BasicDBObject();
            conditionBean.put("ccno", ccno);
            result = db.findOne("b_companycc", conditionBean);

        } catch (Exception e) {
        }
        return result;
    }

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 验证号是否匹配
     * 
     * @param valicode	验证号
     * @return
     */
    public static String getValiCode() {

        MongoDBManager db = MongoDBManager.getInstance();
        try {
            List<DBObject> list = db.findAll("ccvalicode");
            String code = list.get(0).get("valicode").toString(); //获得验证码
            return code;
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }
}
