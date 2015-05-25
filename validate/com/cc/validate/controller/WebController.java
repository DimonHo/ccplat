package com.cc.validate.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cc.core.constant.ConstantKey;

/***
 * 
 * @author Ron
 * 测试控制层
 */
@Controller
public class WebController {

    public static Log log = LogFactory.getLog(WebController.class);

    /***
     * 请求测试
     * @return 返回index.jsp
     */
    @RequestMapping
    public String index() {

        log.error("ssss");
        return "index";
    }

    /***
     * 请求测试
     * @return 返回json格式的字符串
     */
    @RequestMapping
    public ModelAndView json() {

        return new ModelAndView(ConstantKey.JSON_VIEW_KEY, ConstantKey.AJAX_JSON_DATA_KEY, "ss");
    }
}
