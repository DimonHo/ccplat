package com.cc.validate.service;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.utils.StringUtils;
import com.cc.validate.api.CccompanyccApi;
import com.cc.validate.api.LoginApi;

/**
 * 登录接口实现类
 * 
 * @author wxj
 * @createtime 2014.9.1
 */
@Component
@WebService
public class CcServiceImpl implements ICcService {

    public static Log log = LogFactory.getLog(CcServiceImpl.class);

    /**
     * 企业登录验证
     */
    public String getWebLogin(String ccno, String ccpwd, String companyno) {

        try {
            String result = LoginApi.getWebLogin(ccno, ccpwd, companyno);
            return result;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return "99";
        }
    }

    /**
     * 企业用户修改密码
     */
    public String updatePwd(String ccno, String ccpwd, String newccpwd, String valicode) {

        try {
            String result = "";
            if (!LoginApi.isValiCode(valicode)) {
                result = AplicationKeyConstant.VALICODE;
            } else {
                result = LoginApi.updatePwd(ccno, ccpwd, newccpwd);
            }
            return result;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return "99";
        }
    }

    public String bycccompanyno(String companyno, String valicode) {

        try {
            if (!LoginApi.isValiCode(valicode)) {
                return AplicationKeyConstant.VALICODE;
            }
            return CccompanyccApi.bycccompanyno(companyno);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return "";
        }
    }

    public String updateCompanyCC(String companyno, String ccno, String state, String operate, String remark, String valicode) {

        if (!LoginApi.isValiCode(valicode)) {
            return AplicationKeyConstant.VALICODE;
        }
        if (StringUtils.equals(state, "1") || StringUtils.equals(state, "2")) {
            return CccompanyccApi.updateCompanyCC(companyno, ccno, state, operate, remark, valicode);
        } else {
            return "2";
        }
    }
}