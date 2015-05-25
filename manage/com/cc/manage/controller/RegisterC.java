package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.test.Register;
import com.cc.test.RegisterDao;

@Controller
@RequestMapping("reg")
public class RegisterC extends CommonController {
    @Autowired
    private Register reg;
    
    @RequestMapping("add")
    public void functionList(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, reg.Collect(RegisterDao.M_ADD, request).toString());
    }
    
    @RequestMapping("add1")
    public void functionList1(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, reg.Collect(RegisterDao.M_ADD1, request).toString());
    }
    
    @RequestMapping("getFlowPerson")
    public void getFlowPerson(HttpServletRequest request, HttpServletResponse response) {
        
        JSONObject jo = new JSONObject();
        jo.put("total", 1);
        jo.put("data", reg.Collect(RegisterDao.M_SEARCH, request).toString());
        
        send(response, jo.toString());
    }
    
    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, reg.Handle(RegisterDao.M_CHECK, request).toString());
    }
    
    @RequestMapping("personAction")
    public void personAction(HttpServletRequest request, HttpServletResponse response) {
        
        send(response, reg.Collect(RegisterDao.M_PERSON_ACTION, request).toString());
    }
    
}
