package com.cc.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.utils.JSONUtils;
import com.cc.manage.logic.CcnumberLogic;
import com.mongodb.DBObject;

/***
 * 生成cc号码
 * @author ron
 * @createTime 2014.08.30
 */
@Controller
@RequestMapping("ccnumber")
public class CcnumberController {

    public static Log log = LogFactory.getLog(CcnumberController.class);

    @Autowired
    private CcnumberLogic ccnumberLogic;

    /**
     * 查询可用分配的CC号
     * @param request
     * @param response
     */
    @RequestMapping("notAllot")
    public void notAllot(HttpServletRequest request, HttpServletResponse response) {

        List<DBObject> list = ccnumberLogic.notAllot(request);
        send(response, JSONUtils.resertJSON(list).toString());
    }

    @RequestMapping("list")
    public void list(HttpServletRequest request, HttpServletResponse response) {

        JSONObject bean = ccnumberLogic.list(request);
        if(bean !=null)
        	send(response, bean.toString());
    }

    /***
     * 通过1，2,3,4种类型获取当前类型中最大的CCNO。
     * @param request
     * @param response
     */
    @RequestMapping("maxCcNo")
    public void maxCcNo(HttpServletRequest request, HttpServletResponse response) {

        String temp = this.ccnumberLogic.maxCcNo(request);
        send(response, temp);
    }

    @RequestMapping("modify")
    public void modify(HttpServletRequest request, HttpServletResponse response) {

        boolean temp = this.ccnumberLogic.modify(request);
        send(response, String.valueOf(temp));
    }

    public void send(HttpServletResponse response, String content) {

        response.setContentType("text/html;charset=UTF-8");
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
