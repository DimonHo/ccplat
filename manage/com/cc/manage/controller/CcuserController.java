package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.manage.logic.CcuserLogic;

/**
 * 单个账号控制(查询、状态变动)
 * 
 * @author yfm
 * @since CcuserController1.0
 * @createTime 2014.09.15
 * 
 */
@Controller
@RequestMapping("user")
public class CcuserController {
    
    @Autowired
    private CcuserLogic userLogic;
    
    /**
     * 帐号信息展示列表（排序）
     * 
     * @param response
     * @return
     */
    @RequestMapping("list")
    public void list(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result = userLogic.list(request);
        if(result != null)
            Result.send(result.toString(), response);
    }
    
    /**
     * 手动修改状态
     * 
     * @param request
     * @return
     */
    @RequestMapping("update")
    public void updateState(HttpServletRequest request, HttpServletResponse response) {
        
        String result = userLogic.updateState(request);
        Result.send(result, response);
    }
    
}
