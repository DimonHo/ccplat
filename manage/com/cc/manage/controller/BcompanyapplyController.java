package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.manage.logic.BcompanyapplyLogic;

/**
 * 企业注册、查询
 * 
 * @author yfm
 * @since BcompanyapplyController1.0
 * @createTime 2014.09.15
 * 
 */
@Controller
@RequestMapping("companyapply")
public class BcompanyapplyController {
    
    public static Log log = LogFactory.getLog(CcuserController.class);
    
    @Autowired
    private BcompanyapplyLogic companyapplyLogic;
    
    /*-------------------------------------------------------------------------------------------------*/

    /**
     * 帐号信息展示列表
     * 
     * @param request
     * @return
     */
    @RequestMapping("list")
    public void list(HttpServletResponse response, HttpServletRequest request) {
        
        try {
            JSONObject json = companyapplyLogic.list(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 保存企业申请资料
     */
    @RequestMapping("add")
    public void add(HttpServletRequest request, HttpServletResponse response) {
        companyapplyLogic.add(request);
    }
    
    /**
     * 查询企业申请资料
     */
    @RequestMapping("query")
    public void query(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONArray json = companyapplyLogic.query(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 查询全部的历史记录
     * 
     * @param request
     * @param response
     */
    @RequestMapping("show")
    public void history(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject json = companyapplyLogic.history(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 编辑历史记录
     */
    @RequestMapping("edit")
    public void edit(HttpServletRequest request, HttpServletResponse response) {
        companyapplyLogic.editHistory(request);
    }
    
    /**
     * 审核列表详细页面
     */
    @RequestMapping("detail")
    public void detail(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        try {
            json = companyapplyLogic.getDetailInfo(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
            
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 查询审批人列表
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("listPeople")
    public void listPeople(HttpServletRequest request, HttpServletResponse response) {
        String listpeople = companyapplyLogic.personAction(request);
        Result.send(listpeople, response);
    }
    
    /**
     * 是否需要审核
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("islocked")
    public void islocked(HttpServletRequest request, HttpServletResponse response) {
        String listpeople = companyapplyLogic.islocked(request);
        Result.send(listpeople, response);
    }
    
    /**
     * 提交申请流程
     */
    @RequestMapping("submit")
    public void submit(HttpServletRequest request, HttpServletResponse response) {
        Result.send(companyapplyLogic.submit(request), response);
        
    }
    
    /**
     * 审核处理流程
     * 
     * @param response
     * @return
     */
    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response) {
        Result.send(companyapplyLogic.check(request), response);
        
    }
    
    /**
     * 提交审核，修改审核状态为1，把审核通过的企业注册资料复制到企业表
     * 
     * @param request
     * @param response
     */
    @RequestMapping("apply")
    public void apply(HttpServletRequest request, HttpServletResponse response) {
        Result.send(companyapplyLogic.apply(request), response);
    }
    
    /**
     * 提交审核，修改审核状态为1，把审核通过的企业注册资料复制到企业表
     * 
     * @param request
     * @param response
     */
    @RequestMapping("supercheck")
    public void supercheck(HttpServletRequest request, HttpServletResponse response) {
        Result.send(companyapplyLogic.supercheck(request), response);
    }
    
    /**
     * 获取代办审核数
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("getSchedule")
    public void getSchedule(HttpServletRequest request, HttpServletResponse response) {
        String getSchedule = companyapplyLogic.getSchedule(request);
        Result.send(getSchedule, response);
    }
    
    /**
     * 获取权限规则
     */
    @RequestMapping("getAuditPower")
    public void getAuditPower(HttpServletRequest request, HttpServletResponse response) {
        String getAuditPower = companyapplyLogic.getAuditPower(request);
        Result.send(getAuditPower, response);
        
    }
}
