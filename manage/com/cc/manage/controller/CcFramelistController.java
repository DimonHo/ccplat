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
import com.cc.manage.logic.CcFramelistLogic;
import com.mongodb.DBObject;

/**
 * 组织架构管理后台控制器。
 * 包含组织架构管理的后台增删改查操作。
 * @author zzf
 * @since CcFramelistController1.0
 */
@Controller
@RequestMapping("ccframelist")
public class CcFramelistController {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(CcFramelistController.class);
	
	/**
	 * 组织架构管理逻辑处理对象
	 */
    @Autowired
    private CcFramelistLogic ccFramelistLogic;
	
	/**
     * 查询对应行业的所有组织架构模版
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listFrame")
    public void listFrame(HttpServletRequest request, HttpServletResponse response) {
		//查询出对应行业的所有组织架构模版
		List<DBObject> frameList = ccFramelistLogic.listFrame(request);
		if (CollectionUtils.isNotEmpty(frameList)) {
            JSONArray json = JSONArray.fromObject(frameList);
            send(response, json.toString());
		}
	}
	
	/**
     * 新增组织结构
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("addFrame")
    public void addFrame(HttpServletRequest request, HttpServletResponse response) {
		String temp = ccFramelistLogic.addFrame(request);
		send(response, String.valueOf(temp));
	}
	
	/**
     * 修改组织结构
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("modifyFrame")
    public void modifyFrame(HttpServletRequest request, HttpServletResponse response) {
		String temp = ccFramelistLogic.modifyFrame(request);
		send(response, String.valueOf(temp));
	}
	
	/**
     * 删除组织结构
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("deleteFrame")
    public void deleteFrame(HttpServletRequest request, HttpServletResponse response) {
		String temp = ccFramelistLogic.deleteFrame(request);
		send(response, String.valueOf(temp));
	}
	
	/**
     * 保存组织结构树
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("saveAll")
    public void saveAll(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccFramelistLogic.saveAll(request);
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
