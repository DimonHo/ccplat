package com.cc.validate.service;

/**
 * 登录接口
 * 
 * @author wxj
 * @createtime 2014.9.1
 */
public interface ICcService {

    /**
     * 企业登录验证
     * @param ccno		cc号
     * @param ccpwd		MD5加密密码
     * @param companyno	企业编号
     * @return
     */
    public String getWebLogin(String ccno, String ccpwd, String companyno);

    /**
     * 企业用户修改密码
     * @param ccno		cc号
     * @param ccpwd		MD5加密密码
     * @param valicode	验证码
     * @return
     */
    public String updatePwd(String ccno, String ccpwd, String newccpwd, String valicode);

    /***
     * 通过企业CC编号获取企业账号,状态
     * @param companyno
     * @param valicode
     * @return
     */
    public String bycccompanyno(String companyno, String valicode);

    /***
     * 修改企业账号cccompanycc的账号状态
     * @param companyno
     * @param ccno
     * @param state 0,1,2,3《未启用，启用，企业停用，平台禁用》
     * @param operator
     * @param valicode
     * @return 0表示操作成功，1表示操作失败，2表示参数异常
     */
    public String updateCompanyCC(String companyno, String ccno, String state, String operator, String remark, String valicode);

}
