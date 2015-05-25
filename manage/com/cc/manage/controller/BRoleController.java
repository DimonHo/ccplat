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
import com.cc.manage.logic.BRoleLogic;

/**
 * 分配岗位后台控制器。
 * @author zzf
 * @since BRoleController1.0
 */
@Controller
@RequestMapping("brole")
public class BRoleController {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(BRoleController.class);
	
	/**
	 * 分配岗位逻辑处理对象
	 */
	@Autowired
	private BRoleLogic bRoleLogic;
	
	/**
     * 显示已分配岗位
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listRole")
    public void listRole(HttpServletRequest request, HttpServletResponse response) {
		//查询出已分配的所有岗位
		String roleList = bRoleLogic.listRole(request);
		if (StringUtils.isNotBlank(roleList)) {
            send(response, roleList);
		}
	}
	
	/**
     * 分配岗位
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("addallot")
    public void addallot(HttpServletRequest request, HttpServletResponse response) {
		String temp = bRoleLogic.addallot(request);
		send(response, String.valueOf(temp));
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
