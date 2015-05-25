package com.cc.manage.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cc.core.constant.SessionKey;

public class SesssionInterceptor implements HandlerInterceptor {
    
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
        throws Exception {
        
        // System.out.println("afterCompletion!!");
        
    }
    
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
        throws Exception {
        
        // System.out.println("postHandle!!");
        
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        
        // System.out.println("postHandle!!");
        
        // 后台session控制
        String uri = request.getRequestURI();
        // System.out.println(WebUtils.getContextPath());
        // System.out.println(WebUtils.getDeployPath());
        // System.out.println(WebUtils.getWebPath());
        if (uri.indexOf("login") != -1) {
            return true;
        }
        
        Object object = request.getSession().getAttribute(SessionKey.SESSION_KEY_USER);
        
        if (uri.indexOf("login") == -1 && object != null) {
            return true;
        }
        
        if (object == null)// 判断session里是否有用户信息
        {
            if (request.getHeader("x-requested-with") != null
                && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))// 如果是ajax请求响应头会有，x-requested-with；
            {
                response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
                
                return false;
            }
            
        }
        
        return true;
    }
    
}
