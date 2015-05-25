package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.manage.logic.CcAuditLogic;

/**
 * 审核模块
 * 
 * @author clare
 * @version 1.0
 * @createTime 2014-09-01
 * 
 */
@Controller
@RequestMapping("audit")
public class AuditController extends CommonController {
    public static Log log = LogFactory.getLog(AuditController.class);
    
    @Autowired
    private CcAuditLogic ccAuditLogic;
    
    /*-------------------------------------------------------------------------------------------*/

    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取权限的下拉框
     * 
     * @param request
     * @param response
     */
    @RequestMapping("accessList")
    public void functionAceess(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.functionAceess(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取权限的下拉框
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getLink")
    public void getLink(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getLink(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取规则类型的下拉框
     * 
     * @param request
     * @param response
     */
    @RequestMapping("ruleType")
    public void ruleType(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.ruleType(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取规则类型的下拉框
     * 
     * @param request
     * @param response
     */
    @RequestMapping("personList")
    public void personList(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.personList(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 保存权限规则
     * 
     * @param request
     * @param response
     */
    @RequestMapping("saveFuncRule")
    public void saveFuncRule(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.saveFuncRule(request, response).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 保存功能联动
     * 
     * @param request
     * @param response
     */
    @RequestMapping("saveLink")
    public void saveLink(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.saveLink(request, response).toString());
    }
    
    /**
     * 保存功能联动
     * 
     * @param request
     * @param response
     */
    @RequestMapping("updateLink")
    public void updateLink(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.updateLink(request, response).toString());
    }
    
    /**
     * 保存功能联动
     * 
     * @param request
     * @param response
     */
    @RequestMapping("deleteLink")
    public void delteteLink(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.saveLink(request, response).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取产品树
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getProduct")
    public void getProduct(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getProducts(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取产品树
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getProductAct")
    public void getProductAct(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getProductAct(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取功能（两个ccno都同时拥有的）
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getProductActSome")
    public void getProductActSome(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getProductActSome(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取功能（第一个存在，第二个不存在的功能）
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getProductActdeff")
    public void getProductActdeff(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getProductActdeff(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    public void getRulePerson(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getRulePerson(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 通过功能编码获取规则类型
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getTypeForAct")
    public void getTypeForAct(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getTypeForAct(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取部门
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getPersonForPer")
    public void getPersonForPer(HttpServletRequest request, HttpServletResponse response) {
        send(response, ccAuditLogic.getPersonForPer(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 获取人员
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getDepArrayForDepno")
    public void getDepArrayForDepno(HttpServletRequest request, HttpServletResponse response) {
        send(response, ccAuditLogic.getDepArrayForDepno(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 根据人员保存功能
     * 
     * @param request
     * @param response
     */
    @RequestMapping("SaveFunForCcno")
    public void SaveFunForCcno(HttpServletRequest request, HttpServletResponse response) {
        send(response, ccAuditLogic.SaveFunForCcno(request).toString());
    }
    
    /**
     * 获取个人对于某个功能的权限规则信息
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getPersonRuleInfo")
    public void getPersonRuleInfo(HttpServletRequest request, HttpServletResponse response) {
        send(response, ccAuditLogic.getPersonRuleInfo(request).toString());
    }
    
    /**
     * 获取当前功能定义的规则模板
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getRule")
    public void getRule(HttpServletRequest request, HttpServletResponse response) {
        send(response, ccAuditLogic.getRule(request).toString());
    }
    
    /**
     * 保存规则到用户功能表
     * 
     * @param request
     * @param response
     */
    @RequestMapping("saveRuleToUserAction")
    public void saveRuleToUserAction(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.saveRuleToUserAction(request).toString());
    }
    
    /**
     * 获取待办事项列表
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getSchedule")
    public void getSchedule(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getSchedule(request).toString());
    }
    
    /**
     * 根据功能编码获取待办事项
     * 
     * @param request
     * @param response
     */
    @RequestMapping("scheduleList")
    public void scheduleList(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.scheduleList(request).toString());
    }
    
    /**
     * 根据功能编码获取待办事项
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getProductListName")
    public void getProductListName(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject json = ccAuditLogic.getProductListName(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 保存规则到用户功能表
     * 
     * @param request
     * @param response
     */
    @RequestMapping("saveRuleSubmit")
    public void saveRuleSubmit(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.saveRuleSubmit(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 查询规则权限
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getAuditPower")
    public void getAuditPower(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getAuditPower(request).toString());
    }
    
    /*-------------------------------------------------------------------------------------------*/

    /**
     * 规则记录
     * 
     * @param request
     * @param response
     */
    @RequestMapping("getRuleRecord")
    public void getRuleRecord(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, ccAuditLogic.getRuleRecord(request).toString());
    }
    
}
