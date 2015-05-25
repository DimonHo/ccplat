package com.cc.manage.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcuserDao;
import com.cc.manage.dao.CommonDao;
import com.cc.manage.dao.impl.CommonDaoImpl;
import com.cc.manage.logic.BindLogic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Controller
@RequestMapping("login")
public class LoginController {

    public static Log log = LogFactory.getLog(LoginController.class);

    @Autowired
    private CcuserDao ccuserDao;

    private CommonDao common = new CommonDaoImpl();

    @Autowired
    private BindLogic bindLogic;

    /**
     * 查询所有岗位
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("getLogin")
    public void getLogin(HttpServletRequest request, HttpServletResponse response) {

        try {
            String s_ccno = request.getParameter("ccno");
            String s_ccpwd = request.getParameter("ccpwd");
            String s_imei = request.getParameter("imei");//设备号
            System.out.println("---ccno:" + s_ccno);
            DBObject user = ccuserDao.queryCcuserByCcno(s_ccno);
            JSONObject jsonObj = new JSONObject();
            if (user == null) {
                jsonObj.put("state", -1);
                Result.send(jsonObj.toString(), response);
                return;
            }

            if (!s_ccpwd.equalsIgnoreCase((String) user.get("ccpwd"))) {
                jsonObj.put("state", -1);
                Result.send(jsonObj.toString(), response);
                return;
            }
            String state = user.get("state").toString();
            // 平台 状态1-平台启用，2平台停用，3.历史使用记录
            if ("1".equals(state)) {
                jsonObj.put("ccno", user.get("ccno"));
                jsonObj.put("companyno", user.get("companyno"));
                jsonObj.put("phone", user.get("phone"));
                jsonObj.put("realname", user.get("realname"));
                jsonObj.put("byname", user.get("byname"));
                jsonObj.put("sex", user.get("sex"));
                jsonObj.put("uuid", user.get("uuid"));
                jsonObj.put("ipAddress", "");// 初始值
                jsonObj.put("port", "");// 初始值
                if (StringUtils.isNotBlank((String) user.get("companyno"))) {
                    DBObject company = (DBObject) common.searchCommonOne(TableNameConstant.T_COMPANY, new BasicDBObject("companyno", user.get("companyno")));
                    if (company != null) {
                        jsonObj.put("ipAddress", company.get("ipAddress"));
                        jsonObj.put("port", company.get("port"));
                    }
                }
                //增加设备绑定验证 add by Einstein 2015-01-03
                if (StringUtils.isNotBlank(s_imei)) {
                    String s_uuid = (String) user.get("uuid");
                    DBObject bindInfo = bindLogic.queryBind(s_uuid);
                    if (bindInfo == null) {
                        boolean flag = bindLogic.bindDeviceState1(s_uuid, s_imei, "");
                        System.out.println("flag:" + flag);
                    } else {
                        boolean hasImei = false;
                        List<DBObject> imeisList = DBObejctUtils.getList(bindInfo, "imeis");
                        for (DBObject imei : imeisList) {
                            if (imei.get("imei").equals(s_imei)) {
                                if (1 != (Integer) imei.get("state")) {
                                    jsonObj.put("state", "-3");
                                    Result.send(jsonObj.toString(), response);
                                }
                                if (1 == (Integer) imei.get("state")) {
                                    hasImei = true;
                                    break;
                                }
                            }
                        }
                        if (!hasImei) {
                            jsonObj.put("state", "-2");
                            Result.send(jsonObj.toString(), response);
                        }
                    }
                }
            }
            jsonObj.put("state", state);// 状态1-平台启用，2平台停用，3：平台历史记录;
            Result.send(jsonObj.toString(), response);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
}
