package com.cc.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.constant.StateConstant;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.logic.BcompanyccLogic;

/**
 * 企业管理的cc管理
 * @author Ron
 * @createTime 2014.08.30
 */
@Controller
@RequestMapping("companycc")
public class BcompanyccController {

    public static Log log = LogFactory.getLog(BcompanyccController.class);

    @Autowired
    private BcompanyccLogic bcompanyccLogic;
    
    /***
     * 平台给企业分配企业CC号码
     * @param request
     * @param response
     * @author Ron
     */
    @RequestMapping("add")
    public void add(HttpServletRequest request, HttpServletResponse response) {
        try {
            send(response, this.bcompanyccLogic.add(request));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
       
    }
    
    /***
     * 企业分配账号的列表根据企业号获取
     * @param request
     * @param response
     * @author Ron
     */
    @RequestMapping("list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONObject list = bcompanyccLogic.list(request);
            send(response, list.toString());
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /***
     * 调整企业账号的启用的开始时间以及结束时间
     * 参数格式：
     * @param request
     * @param response
     */
    @RequestMapping("modify")
    public void modify(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean temp = this.bcompanyccLogic.modify(request);
            send(response, String.valueOf(temp));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /***
     * 企业账号分配管理员
     * @param request
     * @param response
     */
    @RequestMapping("addpower")
    public void addpower(HttpServletRequest request, HttpServletResponse response) {
        try {
            String usestate = RequestUtils.getString(request, "usestate");
            if(StringUtils.equals(usestate, StateConstant.CC_USER_USESTATE1)){
                send(response, "4");
            }else{
                String temp = this.bcompanyccLogic.addpower(request);
                send(response, temp);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 停用，启用操作
     * @param request
     * @param response
     */
    @RequestMapping("updateState")
    public void updateState(HttpServletRequest request, HttpServletResponse response) {
        try {
            boolean temp = this.bcompanyccLogic.updateState(request);
            send(response, String.valueOf(temp));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 设置管理员时查询账户信息
     * @param request
     * @param response
     */
    @RequestMapping("queryuser")
    public void queryuser(HttpServletRequest request, HttpServletResponse response) {
        try {
            String str = bcompanyccLogic.queryuser(request);
            send(response, str);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
    }

    public void send(HttpServletResponse response, String content) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(content);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            if (out != null)
                out.close();
        }
    }

}
