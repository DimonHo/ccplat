package com.cc.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cc.core.common.RandomValidateCode;
import com.cc.manage.logic.CcloginLogic;

/***
 * 登录
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
@Controller
@RequestMapping("login")
public class CcLoginController {
    
    public static Log log = LogFactory.getLog(CcLoginController.class);
    
    @Autowired
    private CcloginLogic ccloginLogic;
    
    /**
     * 登录
     * 
     * @param param 用户名、密码、校验码
     * @param request
     * @param response
     * @author DK（最后一次修改）
     */
    @RequestMapping("loginCheck")
    @ResponseBody
    public String loginCheck(String param, HttpServletRequest request, HttpServletResponse response) {
        return ccloginLogic.checkLogin(param, response, request);
    }
    
    @RequestMapping("exit")
    public String exit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:..";
    }
    
    @RequestMapping("getRanValidateCode")
    public String getRanValidateCode(HttpServletRequest request, HttpServletResponse response) {
        
        response.setContentType("image/jpeg");// 设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");// 设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        
        randomValidateCode.getRandcode(request, response);// 输出图片方法
        
        return null;
    }
    
    public void send(HttpServletResponse response, String content) {
        
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
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
    
}
