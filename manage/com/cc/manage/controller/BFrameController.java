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

import com.cc.core.utils.StringUtils;
import com.cc.manage.logic.BFrameLogic;

/**
 * 分配组织架构后台控制器。
 * @author zzf
 * @since BFrameController1.0
 */
@Controller
@RequestMapping("bframe")
public class BFrameController {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(BFrameController.class);
	
	/**
	 * 分配组织架构逻辑处理对象
	 */
	@Autowired
	private BFrameLogic bFrameLogic;
	
	/**
     * 查询已分配的所有组织架构模版
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listFrame")
    public void listFrame(HttpServletRequest request, HttpServletResponse response) {
		//查询出已分配的所有组织架构
		String frameList = bFrameLogic.listFrame(request);
		if (StringUtils.isNotBlank(frameList)) {
            send(response, frameList);
		}
	}
	
	/**
     * 分配组织结构
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("addFrame")
    public void addFrame(HttpServletRequest request, HttpServletResponse response) {
		send(response, bFrameLogic.addFrame(request));
	}
	
	/**
	 * 将输出内容响应到前台
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
	    } catch (IOException e) {
	        log.error(e.getLocalizedMessage(), e);
	    } finally {
	        if (out != null)
	            out.close();
	    }
	}
}
