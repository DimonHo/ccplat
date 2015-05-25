package com.cc.manage.service;


/**
 * WebService接口
 * 
 * @author zmx
 * @createtime 2014-09-27 this WebService is service for Android
 */
public interface ICcService {
    /****************************************** for Android ******************************************/
    /**
     * 登陆
     * 
     * @param ccno
     * @param ccpwd
     * @return String
     * @author Einstein
     */
    public String getWebLogin(String ccno, String ccpwd);
    
    /**
     * 企业列表
     * 
     * @param access_token 验证码
     * @param pageIndex 起始页
     * @param pageSize 记录数
     * @return
     * @author Einstein
     */
    public String getAllCompanys(String access_token, int pageIndex, int pageSize);
    
    /**
     * 企业账号，更改用户状态
     * 
     * @param access_token
     * @param companyno
     * @param ccno
     * @param state
     * @return String
     * @author Einstein
     */
    public String updateCcuserState(String access_token, String companyno, String ccno, String state);
    
    /****************************************** for Businessplat ******************************************/
    /**
     * 更新企业
     * 
     * @param companyno
     * @param updateStr
     * @return String
     * @author Einstein
     */
    public String updateCompany(String companyno, String updateStr, String historyStr);
    
    /**
     * 根据cc号 重置Cc_user密码
     * 
     * @param companyno
     * @param ccno
     * @param historyJson
     * @return 1:更改成功；2：更改失败；9：不能操作
     * @author Einstein
     */
    public String resetPsw(String companyno, String ccno, String historyJson);
    
    /**
     * 根据企业号，cc号，更改用户(使用json数据)
     * 
     * @param companyno
     * @param ccno
     * @param updateJson
     * @param historyJson
     * @return 1:成功;2:失败;9:不能操作
     * @author Einstein
     */
    public String updateUser(String companyno, String ccno, String updateJson, String historyJson);
    
    /**
     * 回收账号
     * 
     * @param operatorCcno 操作人账号
     * @param companyno 企业号
     * @param ccno
     * @return "1":回收成功;"2":回收失败; "9":不能操作
     * @author Einstein
     */
    public String recycleCcuser(String operatorCcno, String companyno, String ccno);
    
    /**
     * 获取cc_user帐号状态是否停用
     * 
     * @param ccno 帐号编码
     * @param companyno 公司编码
     * @return
     * @author admin
     */
    public String isDisable(String ccno, String companyno);
    
    /**
     * 更换帐号
     * 
     * @param oldccno 旧帐号编码
     * @param newccno 新帐号编码
     * @param companyno 公司编码
     * @return
     * @author admin
     */
    public String changeUser(String oldccno, String newccno, String companyno);
    
    /**
     * 根据uuid获取帐号的ccno
     * 
     * @param uuid 帐号对应的uuid
     * @return
     * @author admin
     */
    public String getCcnoByUuid(String uuid);
    
    /**
     *得到可分配的账号List
     * 
     * @param companyno
     * @return
     * @author Einstein
     */
    public String getCcuserListCanAdd(String companyno, int pageindex, int pagesize);
    
    /**
     * 获得当前联动功能的版本
     * 
     * @return
     * @author DK
     */
    public String getSubActionVersion();
    
    /**
     * 获得平台端的联动数据
     * 
     * @return
     * @author DK
     */
    public String getSubAction();
    
    /***
     * 查询可以被分配CC账号
     * 
     * @param num分配数量
     * @param start号码起始位
     * @param type企业号码位数
     * @return
     * @author Suan
     */
    public String notAllot(int num, String start, String type);
    
    /**
     * 修改密码
     * @param pwd
     * @param uuid
     * @param newPwd
     * @return 1 失败 2成功
     */
    public String contrastPwd(String pwd,String uuid,String newPwd);
}
