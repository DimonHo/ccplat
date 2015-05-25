package com.cc.manage.logic;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.utils.CollectionUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.manage.dao.CcAreaDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/***
 * 地区操作逻辑层
 * @author Ron
 * @createTime 2014.09.18
 */
@Component
public class CcAreaLogic {

    public static Log log = LogFactory.getLog(CcAreaLogic.class);

    /**
     * 地区表
     */
    private String CC_AREA="cc_area";
    
    @Autowired
    private CcAreaDao ccAreaDao;

    /**
     * 地区树结构 默认添加一个地区显示
     * @param response
     */
    public void listTree(HttpServletResponse response) {

        List<DBObject> dbList = ccAreaDao.listTree();

        JSONObject jo = new JSONObject();
        jo.put("id", "0");
        jo.put("name", "地区");
        jo.put("pid", "");
        jo.put("pidaddress", "0");

        try {
            send(JSONUtils.resertJSONAddhead(dbList, jo), response);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }
    
    /**
     * 构建一个以id为key的单条对象的map<String,JSONObject>对象
     * 
     * @return Map<String,DBObject>
     * @author Ron
     * 
     */
    public Map<String,DBObject> getAreaMap() {
        try {
            List<DBObject> list = ccAreaDao.listTree();
            if(CollectionUtils.isNotEmpty(list)){
                Map<String,DBObject> result = new HashMap<String,DBObject>();
                for(DBObject temp:list){
                    temp.put("id", temp.get("_id").toString());
                    temp.removeField("_id");
                    result.put(temp.get("id").toString(), temp);
                }
                return result;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /***
     * 增加地区，
     * @param param
     * @param request
     * @param response
     */
    public void add(String param, HttpServletRequest request, HttpServletResponse response) {

        BasicDBObject insertData = new BasicDBObject();

        JSONObject jsonBean = JSONObject.fromObject(param);
        insertData.put("name", JSONUtils.getString(jsonBean, "name"));
        insertData.put("pid", JSONUtils.getString(jsonBean, "pid"));
        insertData.put("pidaddress", JSONUtils.getString(jsonBean, "pidaddress"));

        try {
            send(String.valueOf(ccAreaDao.add(CC_AREA, insertData)), response);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * 修改地区信息
     * @param param
     * @param request
     * @param response
     */
    public void modify(String param, HttpServletRequest request, HttpServletResponse response) {

        BasicDBObject changeBeaen = new BasicDBObject();

        JSONObject jsonBean = JSONObject.fromObject(param);
        changeBeaen.put("name", JSONUtils.getString(jsonBean, "name"));
        changeBeaen.put("pid", JSONUtils.getString(jsonBean, "pid"));
        changeBeaen.put("pidaddress", JSONUtils.getString(jsonBean, "pidaddress"));

        try {
        	BasicDBObject condition = new BasicDBObject("_id", new ObjectId(JSONUtils.getString(jsonBean, "id")));
            send(String.valueOf(ccAreaDao.update(CC_AREA,condition, changeBeaen)), response);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    /***
     * 删除地区节点
     * @param request
     * @param response
     */
    public void delete(HttpServletRequest request, HttpServletResponse response) {

        String id = request.getParameter("id");

        try {
            send(String.valueOf(ccAreaDao.delete(CC_AREA, new BasicDBObject("_id", new ObjectId(id)))), response);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        }
    }

    public void send(String content, HttpServletResponse response) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.write(content);
        out.close();
    }

}
