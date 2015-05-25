package com.cc.core.utils;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.SessionKey;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 保存历史记录工具类
 * @author zzf
 *
 */
public class HistoryUtils {
	
	/**
	 * 保存历史记录方法
	 * @param content 保存数据
	 * @param request 请求对象
	 * @param table 保存表名
	 * @param queryBean 保存到表中哪条数据
	 */
	public static void saveHistory(Object content,HttpServletRequest request,String table,DBObject queryBean){
        
        //新增数据对象
        DBObject insertBean = new BasicDBObject();
        insertBean.put("createtime", new Date());
        DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
        DBObject operator = new BasicDBObject();
        operator.put("name", DBObejctUtils.getString(userSession, "realname"));
        operator.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
        insertBean.put("operator", operator);
        insertBean.put("remark", content);
        
        DBObject pushBean = new BasicDBObject();
        pushBean.put("history", insertBean);
		
        MongoDBManager.getInstance().updatePush(table, queryBean, pushBean);
	}  
	
	@SuppressWarnings("unchecked")
	public static List<DBObject> listHistory(DBObject queryBean,String table){
		DBObject data=MongoDBManager.getInstance().findOne(table, queryBean);
		if(data!=null){
			List<DBObject> history=(List<DBObject>) data.get("history");
			return history;
		}
		return null;
	}

}
