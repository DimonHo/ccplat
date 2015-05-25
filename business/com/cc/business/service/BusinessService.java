package com.cc.business.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.core.utils.WebServiceUtils;
import com.cc.manage.dao.CccompanyDao;
import com.mongodb.DBObject;

/**
 * 
 * 平台调用企业端的webService服务
 * 
 * @author Ron
 * @createTime:2014-10-13 下午04:20:58
 */
@Component
public class BusinessService {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(BusinessService.class);
    
    @Autowired
    private CccompanyDao cccompanyDao;
    
    /***
     * 功能：设置管理员<br>
     * 调用企业端的设置管理员的WebService：服务返回：字符串1表示成功，操作失败返回字符串2表示失败
     * 
     * @param request
     * @param companyno
     * @param ccno
     * @param remark
     * @param uuid 当前账号是否存在uuid
     * @param name 设置管理员的名称
     * @return String 方法返回：1.表示成功；2表示失败；4表示异常
     * @author Ron
     */
    public String setPower(HttpServletRequest request, String companyno, String ccno, String remark, String uuid,
        String name) {
        
        try {
            DBObject companyBean = cccompanyDao.getCompanyBycompanyno(companyno);
            if (companyBean == null)
                return ResultConstant.FAIL;
            String ip =
                DBObejctUtils.getString(companyBean, "ipAddress") + ":" + DBObejctUtils.getString(companyBean, "port");
            DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
            Object arr[] =
                new Object[] {userSession.get("realname"), userSession.get("ccno"), companyno, ccno, remark, uuid, name};
            return WebServiceUtils.getWebService("setPower", arr, ip);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.EXCEPTION;
    }
    
    /**
     * 功能：分配账号到企业管理<br>
     * 调用企业端的分配账号的WebService：服务返回：字符串1表示成功，操作失败返回字符串2表示失败
     * 
     * @param ccjson
     * @param companyno
     * @param remark
     * @param starttime
     * @param endtime
     * @param ipAddress
     * @param port
     * @return String 方法返回：1.表示成功；2表示失败；4表示异常,6表示访问WebService请求异常
     */
    public String initCcAccount(String ccjson, String companyno, String remark, String starttime, String endtime,
        String ipAddress, String port) {
        try {
            return WebServiceUtils.getWebService("initCcAccount", new Object[] {ccjson, companyno, remark, starttime,
                endtime}, ipAddress + ":" + port);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return ResultConstant.SERVICE_EXCEPTION;
        }
    }
    
    /**
     * 查询公司组织架构
     * 
     * @param companyno 公司编码
     * @param address 公司服务器地址
     * @return
     * @author admin
     */
    public String listFrame(String companyno, String address) {
        Object[] param = new Object[] {companyno};
        return WebServiceUtils.getWebService("listFrame", param, address);
    }
    
    /**
     * 查询公司岗位
     * 
     * @param companyno 公司编码
     * @param pageIndex 分页页号
     * @param pageSize 分页大小
     * @param address 公司服务器地址
     * @return
     * @author admin
     */
    public String listRole(String companyno, int pageIndex, int pageSize, String address) {
        Object[] param = new Object[] {companyno, pageIndex, pageSize};
        return WebServiceUtils.getWebService("listRole", param, address);
    }
    
    /**
     * 初始化公司组织架构
     * 
     * @param data 组织架构数据
     * @param companyno 公司编码
     * @param industryuuid 行业uuid
     * @param companyData 公司数据
     * @param user 操作人
     * @param address 公司服务器地址
     * @return
     * @author admin
     */
    public String initFrame(String data, String companyno, String industryuuid, String companyData, String user,
        String address) {
        Object[] param = new Object[] {data, companyno, industryuuid, companyData, user};
        return WebServiceUtils.getWebService("initFrame", param, address);
    }
    
    /**
     * 初始化公司岗位
     * 
     * @param data 岗位数据
     * @param companyno 公司编码
     * @param user 操作人
     * @param address 公司服务器地址
     * @return
     * @author admin
     */
    public String initRole(String data, String companyno, String user, String address) {
        Object[] param = new Object[] {data, companyno, user};
        return WebServiceUtils.getWebService("initRole", param, address);
    }
    
    /**
     * 登录获取公司功能编码进行权限设置
     * 
     * @param uuid 登录人ccno
     * @param companyno 公司编码
     * @param ipAddressAndPort ip地址和端口号
     * @return
     * @author DK
     */
    public String getUserPower(String uuid, String companyno, String ipAddressAndPort) {
        try {
            String result =
                WebServiceUtils.getWebService("getUserPower", new Object[] {uuid, companyno}, ipAddressAndPort);
            if (result == null) {
                return result;
            }
            return result.substring(1, result.length() - 1);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }
    
    /****
     * 初始化企业的产品结构
     * 
     * @param address ip地址
     * @param productList 产品字符串
     * @param companyno 企业号码
     * @param stime 授权时间
     * @param etime 结束时间
     * @return
     * @author Ron
     */
    public String initProduct(String address, String productList, String companyno, String stime, String etime,
        String newVersion, String subAction) {
        try {
            return WebServiceUtils.getWebService("initProduct", new Object[] {productList, companyno, stime, etime,
                newVersion, subAction}, address);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            
        }
        return "";
        
    }
    
    /****
     * 根据企业账号以及ip地址，得到企业的产品单条结构集合数据
     * 
     * @param address
     * @param companyno
     * @return
     * @author Ron
     */
    public String listProductItem(String address, String companyno) {
        try {
            return WebServiceUtils.getWebService("listProductItem", new Object[] {companyno}, address);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            
        }
        return "";
        
    }
    
    /***
     * 根据企业账号以及ip地址，树型结构的产品集合
     * 
     * @param address
     * @param companyno
     * @return
     * @author Ron
     */
    public String listProduct(String address, String companyno) {
        try {
            return WebServiceUtils.getWebService("listProduct", new Object[] {companyno}, address);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            
        }
        return "";
    }
    
    public String updateProduct(String address, String productList, String companyno, String removenodes,
        String addnodes) {
        try {
            return WebServiceUtils.getWebService("updateProduct", new Object[] {productList, companyno, removenodes,
                addnodes}, address);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            
        }
        return "";
    }
    
    /**
     * 获取审批人
     * 
     * @param uuid 登录人ccno
     * @param actno 功能编码
     * @param ipAddressAndPort ip地址和端口号
     * @return
     * @author Suan
     */
    public String personAction(String actno, String uuid, String ipAddressAndPort) {
        try {
            String result = WebServiceUtils.getWebService("personAction", new Object[] {actno, uuid}, ipAddressAndPort);
            if (StringUtils.isBlank(result)) {
                return result;
            }
            return result.substring(1, result.length() - 1);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }
    
    /**
     * 代办事项
     * 
     * @param jsonArray 流程数据
     * @param ipAddressAndPort ip地址和端口号
     * @return
     * @author Suan
     */
    public String getSchedule(String jsonArray, String ipAddressAndPort) {
        try {
            String result = WebServiceUtils.getWebService("getSchedule", new Object[] {jsonArray}, ipAddressAndPort);
            if (StringUtils.isBlank(result)) {
                return result;
            }
            return result.substring(1, result.length() - 1);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }
    
    /**
     * 获取当前功能下的待办事项
     * 
     * @param obj 流程数据
     * @param actno 功能编码
     * @return 具体功能点的待办事项
     */
    public String scheduleList(String obj, String actno, String ipAddressAndPort) {
        try {
           String result = WebServiceUtils.getWebService("scheduleList", new Object[] {obj, actno}, ipAddressAndPort);
            if (StringUtils.isBlank(result)) {
                return result;
            }
            return result.substring(1, result.length() - 1);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }
}
