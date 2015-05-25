package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.manage.logic.CcAreaLogic;

/***
 * 地区操作控制层
 * @author Ron
 * @createTime 2014.09.18
 */
@Controller
public class CcAreaController {

    @Autowired
    private CcAreaLogic ccAreaLogic;

    /***
     * 地区树型展示列表
     * @param response
     * @return
     */
    @RequestMapping()
    public void list(HttpServletResponse response) {

        ccAreaLogic.listTree(response);
    }

    /***
     * 创建地区树节点
     * @param param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping()
    public String add(String param, HttpServletRequest request, HttpServletResponse response) {

        ccAreaLogic.add(param, request, response);

        return "";
    }
    /***
     * 修改地区树节点
     * @param param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping()
    public void modify(String param, HttpServletRequest request, HttpServletResponse response) {
        ccAreaLogic.modify(param, request, response);
    }

    /***
     * 删除地区树节点
     * @param param
     * @param request
     * @param response
     * @return
     */
    @RequestMapping()
    public void delete(String param, HttpServletRequest request, HttpServletResponse response) {
        ccAreaLogic.delete(request, response);
    }
    
}
