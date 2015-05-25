package com.cc.manage.logic;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.common.Result;
import com.cc.core.constant.ResultConstant;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CccompanyDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 企业资料操作类
 * 
 * @author Ron
 * @createTime 2014.08.30
 */
@Component
public class CccompanyLogic {
    
    public static Log log = LogFactory.getLog(CccompanyLogic.class);
    
    
    /**
     * 公司列表
     * 
     * @param response
     * @param request
     */
    public void list(HttpServletResponse response, HttpServletRequest request) {
        BasicDBObject condition = new BasicDBObject();
        // 这个可能需要继续完善
        String industryid = RequestUtils.getString(request,"industryid");
        if (StringUtils.isNotBlank(industryid)){
            condition.put("industryuuid", industryid);
        }
        String industryuuid = RequestUtils.getString(request, "industryinfo");
        if (StringUtils.isNotBlank(industryuuid)) {
            condition.put("industryuuid", industryuuid);
        }
        String companyname = RequestUtils.getString(request, "company");

        if(StringUtils.isNotBlank(companyname)){
        	Pattern pattern=Pattern.compile(companyname, Pattern.CASE_INSENSITIVE);
        	condition.put("name", pattern);
        }
        
        String name = RequestUtils.getString(request,"name");
        if (StringUtils.isNotBlank(name)){
            condition.put("name", Pattern.compile(name));
        }
        String sortField = RequestUtils.getString(request, "sortField");
        BasicDBObject orderBy = new BasicDBObject();
        if (StringUtils.isNotBlank(sortField)) {
            orderBy.put(sortField, StringUtils.setOrder(RequestUtils.getString(request, "sortOrder")));
        }
        JSONObject jo = new JSONObject();
        MongoDBManager dbmanage = MongoDBManager.getInstance();
        long count = dbmanage.getCount("b_company", condition);
        if(count > 0 ){
            List<DBObject> dblist = dbmanage.find("b_company", condition, orderBy, RequestUtils.getInt(request, "pageIndex") + 1, RequestUtils.getInt(request, "pageSize"));
            jo.put("data", JSONUtils.resertJSON(dblist));
        }
        jo.put("total", count);
        Result.send(jo.toString(), response);
    }
    
    @Autowired
    private CccompanyDao cccompanyDao;
    
    /***
     * 根据企业编号修改了企业的ip，port信息
     * @param request
     * 
     * @author Ron
     */
    public String modify(HttpServletRequest request) {
        try{
            String data = RequestUtils.getString(request, "data");
            if (StringUtils.isNotBlank(data)) {
                JSONObject jsonBean = JSONObject.fromObject(data);
                String companyno = JSONUtils.getString(jsonBean, "companyno");
                if (StringUtils.isNotBlank(companyno)) {
                    DBObject companyBean = new BasicDBObject();
                    companyBean.put("port", JSONUtils.getString(jsonBean, "port"));
                    companyBean.put("ipAddress", JSONUtils.getString(jsonBean, "ipAddress"));
                    if(cccompanyDao.updateByCompanyno(companyBean, companyno)){
                        DBObject condition = new BasicDBObject();
                        condition.put("companyno", companyno);
                        HistoryUtils.saveHistory("修改企业的端口，ip地址", request, "b_company", condition);
                        return ResultConstant.SUCCESS;
                    }
                }
            }
        }catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return ResultConstant.FAIL;
    }
}
