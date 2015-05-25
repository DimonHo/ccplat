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

import com.cc.core.common.Result;
import com.cc.core.utils.AuditUtils;
import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.manage.logic.CcIndustryinfoLogic;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 行业管理后台控制器。
 * 包含行业管理的后台增删改查操作。
 * @author zzf
 * @since CcIndustryinfoController1.0
 */
@Controller
@RequestMapping("ccindustryinfo")
public class CcIndustryinfoController {
	
	/**
	 * 获取日志对象
	 */
	public static Log log = LogFactory.getLog(CcIndustryinfoController.class);
	
	/**
	 * 行业管理逻辑处理对象
	 */
    @Autowired
    private CcIndustryinfoLogic ccIndustryinfoLogic;
	
    /**
     * 获取以id,pid的单条树型行业结构数据
     * 
     * @param request
     * @return
     * @author Ron 
     */
    @RequestMapping("getIndustryInfo")
    public void getIndustryInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            JSONArray json = ccIndustryinfoLogic.getIndustryInfo(request);
            if (json != null) {
                Result.send(json.toString(), response);
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        
    }
    
    /**
     * 查询所有行业
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listIndustryinfo")
    public void listIndustryinfo(HttpServletRequest request, HttpServletResponse response) {
		//查询出所有行业
		List<DBObject> industryinfoList = ccIndustryinfoLogic.listIndustryinfo(request);
		
		if (CollectionUtils.isNotEmpty(industryinfoList)) {
            JSONArray json = JSONArray.fromObject(industryinfoList);
            send(response, json.toString());
		}
	}
	
	/**
     * 查询临时表数据
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listTemp")
    public void listTemp(HttpServletRequest request, HttpServletResponse response) {
		//查询出所有行业
		List<DBObject> industryinfoList = ccIndustryinfoLogic.listTemp(request);
		
		if (CollectionUtils.isNotEmpty(industryinfoList)) {
            JSONArray json = JSONArray.fromObject(industryinfoList);
            send(response, json.toString());
		}
	}
	
	/**
     * 增加行业
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("addIndustryinfo")
    public void addIndustryinfo(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccIndustryinfoLogic.addIndustryinfo(request);
        send(response, String.valueOf(temp));
    }
	
	/**
     * 修改行业
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("modifyIndustryinfo")
    public void modifyIndustryinfo(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccIndustryinfoLogic.modifyIndustryinfo(request);
        send(response, String.valueOf(temp));
    }
	
	/**
     * 删除行业
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("deleteIndustryinfo")
    public void deleteIndustryinfo(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccIndustryinfoLogic.deleteIndustryinfo(request);
        send(response, String.valueOf(temp));
    }
	
	/**
     * 保存行业树
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("saveIndustryinfo")
    public void saveIndustryinfo(HttpServletRequest request, HttpServletResponse response) {
        String temp = ccIndustryinfoLogic.saveIndustryinfo(request);
        send(response, String.valueOf(temp));
    }
	
	/**
     * 查询是否有权限
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("whetherAuthority")
    public void whetherAuthority(HttpServletRequest request, HttpServletResponse response) {
		int temp=AuditUtils.whetherAuthority(request);
        send(response, String.valueOf(temp));
    }
	
	/**
     * 提交审批
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("submit")
    public void submit(HttpServletRequest request, HttpServletResponse response) {
		String temp=ccIndustryinfoLogic.submit(request);
		send(response, String.valueOf(temp));
    }
	
	/**
     * 是否重复提交审批
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("isRepeat")
    public void isRepeat(HttpServletRequest request, HttpServletResponse response) {
		String temp=ccIndustryinfoLogic.isRepeat(request);
		send(response, String.valueOf(temp));
    }
	
	/**
     * 通过审批
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("agree")
    public void agree(HttpServletRequest request, HttpServletResponse response) {
		String temp=ccIndustryinfoLogic.agree(request);
		send(response, String.valueOf(temp));
    }
	
	/**
     * 查询审批人列表
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listPeople")
    public void listPeople(HttpServletRequest request, HttpServletResponse response) {
		JSONArray list=AuditUtils.personAction(request);
        send(response, String.valueOf(list));
    }
	
	
	/**
     * 查询历史记录列表
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("listHistory")
    public void listHistory(HttpServletRequest request, HttpServletResponse response) {
		List<DBObject> history = HistoryUtils.listHistory(new BasicDBObject(), "cc_industryinfo");
		JSONArray json = JSONArray.fromObject(history);
        send(response, json.toString());
    }
	
	
	/**
     * 是否有关联的数据
     * @param request 请求对象
     * @param response 响应对象
     * @since 1.0
     */
	@RequestMapping("hasAssociated")
    public void hasAssociated(HttpServletRequest request, HttpServletResponse response) {
		String result = ccIndustryinfoLogic.hasAssociated(request);
        send(response, String.valueOf(result));
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
