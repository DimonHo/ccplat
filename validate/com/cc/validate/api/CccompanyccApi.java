package com.cc.validate.api;

import org.springframework.stereotype.Component;

import com.cc.validate.dao.CccompanyccDao;

/**
 * 企业账号servece接口
 * @author Ron
 * @createTime 2014.09.01
 */
@Component
public class CccompanyccApi {

    //@Autowired
    //private CccompanyccDao cccompanyccDao;

    /***
     * 通过企业编号获取企业账号以及账号状态
     * @param companyno
     * @return
     */
    /*
    public String bycccompanyno(String companyno) {

     return cccompanyccDao.bycccompanyno(companyno);
    }*/

    /***
     * 通过企业编号获取企业账号以及账号状态
     * @param companyno
     * @return
     */
    public static String bycccompanyno(String companyno) {

        return CccompanyccDao.bycccompanyno(companyno);
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

        return CccompanyccDao.updateCompanyCC(companyno, ccno, state, operator, remark, valicode);
    }
}
