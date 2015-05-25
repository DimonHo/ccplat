package com.cc.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.core.utils.CollectionUtils;
import com.cc.manage.logic.CcRolelistLogic;
import com.mongodb.DBObject;

/**
 * 岗位管理后台控制器。 包含岗位管理的后台增删改查操作。
 * 
 * @author zzf
 * @since CcRolelistController1.0
 */
@Controller
@RequestMapping("ccrolelist")
public class CcRolelistController {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcRolelistController.class);
    
    /**
     * 岗位管理逻辑处理对象
     */
    @Autowired
    private CcRolelistLogic ccRolelistLogic;
    
    /**
     * 查询所有岗位
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("listRole")
    public void listRole(HttpServletRequest request, HttpServletResponse response) {
        List<DBObject> roleList = ccRolelistLogic.listRole(request);
        if (CollectionUtils.isNotEmpty(roleList)) {
            JSONArray json = JSONArray.fromObject(roleList);
            send(response, json.toString());
        }
    }
    
    /**
     * 新增岗位
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("addRole")
    public void addRole(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.addRole(request);
        send(response, String.valueOf(temp));
    }
    
    /**
     * 修改岗位
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("modifyRole")
    public void modifyRole(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.modifyRole(request);
        send(response, String.valueOf(temp));
    }
    
    /**
     * 删除岗位
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("deleteRole")
    public void deleteRole(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.deleteRole(request);
        send(response, String.valueOf(temp));
    }
    
    /**
     * 保存岗位树
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("saveAll")
    public void saveAll(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.saveAll(request);
        send(response, String.valueOf(temp));
    }
    
    /**
     * 将输出内容响应到前台
     * 
     * @param response 响应对象
     * @param content 输出内容
     * @since 1.0
     */
    public void send(HttpServletResponse response, String content) {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(content);
        }
        catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        finally {
            if (out != null)
                out.close();
        }
    }
    
    /**
     * 提交审批
     * 
     * @author Suan
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("submit")
    public void submit(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.submit(request);
        Result.send(temp, response);
    }
    
    /**
     * 审批处理
     * 
     * @author Suan
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccRolelistLogic.check(request);
        Result.send(temp, response);
    }
}
