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

import com.cc.manage.logic.CcProductLogic;

@Controller
@RequestMapping("product")
public class CcProductController {
    
    public static Log log = LogFactory.getLog(CcProductController.class);
    
    @Autowired
    private CcProductLogic ccProductLogic;
    
    /**
     * 产品属性结构
     * 
     * @param request
     * @param response
     */
    @RequestMapping("productlisttree")
    public void productListTree(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.productListTree(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 获取产品树，选中节点
     * 
     * @param request
     * @param response
     */
    @RequestMapping("productSelect")
    public void productSelect(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.productSelect(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
    }
    
    /**
     * 初始化产品架构
     * 
     * @param request
     * @param response
     */
    @RequestMapping("initproduct")
    public void initProduct(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.initProduct(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 修改产品树
     * 
     * @param request
     * @param response
     */
    @RequestMapping("updateProduct")
    public void updateProduct(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.updateProduct(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
    }
    
    /**
     * 产品树的展示
     * 
     * @param request
     * @param response
     */
    @RequestMapping("productTree")
    public void productTree(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.productTree(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 产品树的展示
     * 
     * @param request
     * @param response
     */
    @RequestMapping("updateproductTree")
    public void updateproductTree(HttpServletRequest request, HttpServletResponse response) {
        try{
            send(response, ccProductLogic.productTree(request).toString());
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
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
}
