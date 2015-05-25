package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.manage.logic.CcbatchLogic;

/***
 * 生成账号批次
 * @author Ron
 * @createTime 2014.08.30
 */
@Controller
public class CcbatchController {

    @Autowired
    private CcbatchLogic ccbatchLogic;

    /***
     * 批次列表信息展示以及开放的账号列表展示
     * @param request
     * @param response
     */
    @RequestMapping()
    public void list(HttpServletRequest request, HttpServletResponse response) {

        JSONObject json = ccbatchLogic.list(request);
        Result.send(json.toString(), response);
    }

    /***
     * 生成批次账号，生成批次数据，以及批次下的账号数据
     * 操作ccbatch以及ccnumber
     * @param request
     * @param response
     */
    @RequestMapping()
    public void add(HttpServletRequest request, HttpServletResponse response) {

        String content = this.ccbatchLogic.add(request);
        Result.send(content, response);
        
    }
}
