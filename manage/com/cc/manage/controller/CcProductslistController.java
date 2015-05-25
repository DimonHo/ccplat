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

import com.cc.core.utils.CollectionUtils;
import com.cc.manage.dao.CcAuditDao;
import com.cc.manage.logic.CcProductslistLogic;
import com.mongodb.DBObject;

/**
 * 产品管理后台控制器。 包含产品管理的后台增删改查操作。
 * 
 * @author zzf
 * @since CcProductslistController1.0
 */
@Controller
@RequestMapping("ccproductslist")
public class CcProductslistController {
    
    /**
     * 获取日志对象
     */
    public static Log log = LogFactory.getLog(CcFramelistController.class);
    
    /**
     * 产品管理逻辑处理对象
     */
    @Autowired
    private CcProductslistLogic ccProductslistLogic;
    
    @Autowired
    private CcAuditDao ccAuditDao;;
    
    /**
     * 查询对应行业的所有产品模版
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("listProduct")
    public void listProduct(HttpServletRequest request, HttpServletResponse response) {
        
        // 查询出对应行业的所有产品模版
        List<DBObject> productList = ccProductslistLogic.listProduct(request);
        
        if (CollectionUtils.isNotEmpty(productList)) {
            
            JSONArray json = JSONArray.fromObject(productList);
            send(response, json.toString());
        }
    }
    
    /**
     * 查询公司对应产品
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("listalloted")
    public void listalloted(HttpServletRequest request, HttpServletResponse response) {
        List<DBObject> productList = (List<DBObject>)ccAuditDao.list(request, true);
        send(response, productList.toString());
    }
    
    /**
     * 保存产品树
     * 
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
    @RequestMapping("saveProduct")
    public void saveProduct(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccProductslistLogic.saveProduct(request);
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
    
}
