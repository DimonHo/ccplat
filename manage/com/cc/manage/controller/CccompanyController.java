package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.manage.logic.CccompanyLogic;

/**
 * 企业资料操作类
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
@Controller
public class CccompanyController {
    
    @Autowired
    private CccompanyLogic cccompanyLogic;
    
    /**
     * 获取公司
     * 
     * @param response
     * @param request
     */
    @RequestMapping()
    public void list(HttpServletResponse response, HttpServletRequest request) {
        
        cccompanyLogic.list(response, request);
    }
    
    /**
     * 企业修改资料 主要修改IP ，端口
     * @param request
     * @param response
     * @author Ron
     */
    @RequestMapping()
    public void modify(HttpServletRequest request, HttpServletResponse response) {
        Result.send(cccompanyLogic.modify(request), response);
    }
    
}
