package com.cc.manage.logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcFramelistDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 组织架构管理逻辑处理类。
 * 包含组织架构管理的增删改查等逻辑操作。
 * @author zzf
 * @since CcFramelistLogic1.0
 */
@Component
public class CcFramelistLogic {
	
	/**
	 * 组织架构管理数据库接口
	 */
	@Autowired
    private CcFramelistDao ccFramelistDao;
	
	/**
	 * 查询行业对应组织架构模版数据集合
	 * @param request 请求对象
	 * @return 返回查询出的组织架构模版数据集合
	 */
	public List<DBObject> listFrame(HttpServletRequest request) {
		//获取选中的行业节点uuid
		String industryUUID = RequestUtils.getString(request, "industryuuid");
		String isDelete = RequestUtils.getString(request, "isDelete");
		DBObject queryBean=new BasicDBObject();
		queryBean.put("industryuuid", industryUUID);
		if("true".equals(isDelete)){
			queryBean.put("state", "2");
		}else{
			queryBean.put("state", "1");
		}
		
		DBObject orderBean=new BasicDBObject();
		orderBean.put("createtime", 1);
		
        return ccFramelistDao.listFrame(queryBean,orderBean);
    }
	
	/**
	 * 新增组织架构数据
	 * @param request 请求对象
	 * @return 返回操作是否成功
	 */
	public String addFrame(HttpServletRequest request) {
		String data = RequestUtils.getString(request, "data");
		if (StringUtils.isNotBlank(data)) {
			JSONObject jsonBean = JSONObject.fromObject(data);
			//新增名称
			String name = JSONUtils.getString(jsonBean, "name");
			//新增说明
			String remark = JSONUtils.getString(jsonBean, "remark");
			//选中行业树节点的uuid
			String industryUUID = JSONUtils.getString(jsonBean, "industryuuid");
			//选中组织结构树的模版节点的uuid
			String uuid = JSONUtils.getString(jsonBean, "uuid");
			//当前节点所处的位置
			String key = JSONUtils.getString(jsonBean, "key");
			//当前节点所处的树型结构中的下标
			String index = JSONUtils.getString(jsonBean, "index");
			
			if(StringUtils.isBlank(industryUUID)){
		        return ResultConstant.FAIL;
			}
			
			//判断是否为新增模版
			if(StringUtils.isBlank(uuid)){
				//封装需要插入的数据
				DBObject insertBean = new BasicDBObject();
				uuid=UUID.randomUUID().toString();
				insertBean.put("uuid", uuid);
				insertBean.put("name", name);
				insertBean.put("remark", remark);
				insertBean.put("industryuuid", industryUUID);
				insertBean.put("state", StateConstant.CC_NUMBER_STATE1);
		        insertBean.put("createtime", new Date());
				insertBean.put("children", new ArrayList<DBObject>());
				insertBean.put("history", new ArrayList<DBObject>());
				
				String result = ccFramelistDao.addFrame(insertBean);
				
				//历史记录
				StringBuilder content=new StringBuilder();
				content.append("新增,成功;");
				content.append("名称:"+name);
				content.append(";备注:"+remark);
				if(ResultConstant.SUCCESS.equals(result)){
					addHistory(content.toString(),uuid,request);
	            	return ResultConstant.SUCCESS;
				}
			}else{//判断是否为新增节点
				//条件对象,找到对应模版数据
				DBObject queryBean = new BasicDBObject();
				queryBean.put("uuid", uuid);
				
				DBObject insertBean = new BasicDBObject();
				insertBean.put("uuid", UUID.randomUUID().toString());
				insertBean.put("name", name);
				insertBean.put("remark", remark);
				insertBean.put("children", new ArrayList<DBObject>());
				//当前节点所处的位置
	            String[] keys={};
	            String[] indexs={};
	            if(StringUtils.isNotBlank(key)){
	            	keys=key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
	            }
	            //当前节点所处的树型结构中的下标
	            if(StringUtils.isNotBlank(index)){
	            	indexs=index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
	            }
	            //根据下标拼出要添加数据的数组条件
	            StringBuilder queryStr=new StringBuilder();
	            for(int i=0;i<keys.length;i++){
	            	if(i!=0){
	            		queryStr.append(".");
	                	queryStr.append(indexs[i-1]);
	            		queryStr.append(".");
	            	}
	            	queryStr.append(keys[i]);
	            }
	            //封装插入数据
	            DBObject pushBean = new BasicDBObject();
	            pushBean.put(queryStr.toString(), insertBean);
	            
	            String result=ccFramelistDao.addFrame(queryBean, pushBean);
	            //历史记录
				StringBuilder content=new StringBuilder();
				content.append("新增,成功;");
				content.append("名称:"+name);
				content.append(";备注:"+remark);
				if(ResultConstant.SUCCESS.equals(result)){
					addHistory(content.toString(),uuid,request);
	            	return ResultConstant.SUCCESS;
				}
			}
		}
        return ResultConstant.FAIL;
    }
	
	/**
	 * 修改组织架构数据
	 * @param request 请求对象
	 * @return 返回操作是否成功
	 */
	public String modifyFrame(HttpServletRequest request){
		String data = RequestUtils.getString(request, "data");
		if (StringUtils.isNotBlank(data)) {
			JSONObject jsonBean = JSONObject.fromObject(data);
			//新增名称
			String name = JSONUtils.getString(jsonBean, "name");
			//新增说明
			String remark = JSONUtils.getString(jsonBean, "remark");
			//选中组织结构树的模版节点的uuid
			String rootuuid = JSONUtils.getString(jsonBean, "rootuuid");
			//当前节点所处的位置
			String key = JSONUtils.getString(jsonBean, "key");
			//当前节点所处的树型结构中的下标
			String index = JSONUtils.getString(jsonBean, "index");
			
			String[] keys={};
            String[] indexs={};
            if(StringUtils.isNotBlank(key)){
            	keys=key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            //当前节点所处的树型结构中的下标
            if(StringUtils.isNotBlank(index)){
            	indexs=index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
            }
            //根据下标拼出要添加数据的数组条件
            StringBuilder queryStr=new StringBuilder();
            for(int i=0;i<keys.length;i++){
            		queryStr.append(keys[i]);
            		queryStr.append(".");
                	queryStr.append(indexs[i]);
            		queryStr.append(".");
            }
            //封装修改后的数据
            DBObject updateBean = new BasicDBObject();
            updateBean.put(queryStr.toString()+"name", name);
            updateBean.put(queryStr.toString()+"remark", remark);
            
            //条件数据对象
            DBObject queryBean = new BasicDBObject();
            queryBean.put("uuid", rootuuid);
            
            String result=ccFramelistDao.modifyFrame(queryBean,updateBean);
            
            //历史记录
			StringBuilder content=new StringBuilder();
			content.append("修改,成功;");
			content.append("名称:"+name);
			content.append(";备注:"+remark);
			if(ResultConstant.SUCCESS.equals(result)){
				addHistory(content.toString(),rootuuid,request);
            	return ResultConstant.SUCCESS;
			}
			
		}
		return ResultConstant.FAIL;
	}
	
	/**
	 * 删除组织架构数据
	 * @param request 请求对象
	 * @return 返回操作是否成功
	 */
	public String deleteFrame(HttpServletRequest request){
		//选中的节点uuid
		String uuid = RequestUtils.getString(request, "uuid");
		//根节点uuid
		String rootuuid = RequestUtils.getString(request, "rootuuid");
		//节点位置
		String key = RequestUtils.getString(request, "key");
		//下标
		String index = RequestUtils.getString(request, "index");
		if (StringUtils.isNotBlank(uuid)) {
			if(uuid.equals(rootuuid)){
				DBObject queryBean = new BasicDBObject();
				queryBean.put("uuid", uuid);
				//封装更新条件  逻辑删除，状态更新为未启用
				DBObject deleteBean = new BasicDBObject();
				deleteBean.put("state", StateConstant.CC_NUMBER_STATE2);
				String result=ccFramelistDao.deleteRootFrame(queryBean,deleteBean);
				//历史记录
				StringBuilder content=new StringBuilder();
				content.append("删除,成功;");
				content.append("uuid:"+uuid);
				if(ResultConstant.SUCCESS.equals(result)){
					addHistory(content.toString(),rootuuid,request);
	            	return ResultConstant.SUCCESS;
				}
			}else{
				String[] keys={};
	            String[] indexs={};
	            if(StringUtils.isNotBlank(key)){
	            	keys=key.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
	            }
	            if(StringUtils.isNotBlank(index)){
	            	indexs=index.split(AplicationKeyConstant.STRING_SPLIT_CHAR);
	            }
	            //根据下标拼出要添加数据的数组条件
	            StringBuilder queryStr=new StringBuilder();
	            for(int i=0;i<keys.length;i++){
	            	if(i!=0){
	            		queryStr.append(".");
	                	queryStr.append(indexs[i-1]);
	            		queryStr.append(".");
	            	}
	            	queryStr.append(keys[i]);
	            }
	            //删除数据对象
	            DBObject conditionBean = new BasicDBObject();
	            conditionBean.put("uuid", uuid);
	            DBObject deleteBean = new BasicDBObject();
	            deleteBean.put(queryStr.toString(), conditionBean);
	            
	            //查询数据对象
	            DBObject queryBean = new BasicDBObject();
	            queryBean.put("uuid", rootuuid);
	            
	            String result=ccFramelistDao.deleteFrame(queryBean,deleteBean);
	            
	            //历史记录
				StringBuilder content=new StringBuilder();
				content.append("删除,成功;");
				content.append("uuid:"+uuid);
				if(ResultConstant.SUCCESS.equals(result)){
					addHistory(content.toString(),rootuuid,request);
	            	return ResultConstant.SUCCESS;
				}
			}
		}
		return ResultConstant.FAIL;
	}
	
	/**
	 * 保存组织架构树
	 * @param request
	 * @return
	 */
	public String saveAll(HttpServletRequest request){
		String data = RequestUtils.getString(request, "data");
		//历史记录
		String history = RequestUtils.getString(request, "history");
		//当前选择行业id
		String industryuuid = RequestUtils.getString(request, "industryuuid");
		//需要删除的模版id
		String deleteCode = RequestUtils.getString(request, "deleteCode");
        if (StringUtils.isNotBlank(data)) {
        	JSONArray jsonBean = JSONArray.fromObject(data);
        	JSONObject hisJson = JSONObject.fromObject(history);
        	JSONArray deleteCodes = JSONArray.fromObject(deleteCode);
        	for(int j=0;j<deleteCodes.size();j++){
        		String uuid=deleteCodes.getString(j);
        		DBObject conditionBean=new BasicDBObject();
        		conditionBean.put("uuid", uuid);
        		//将状态更新为2,逻辑删除
        		DBObject delBean=new BasicDBObject();
        		delBean.put("state", StateConstant.CC_NUMBER_STATE2);
        		
        		String result=ccFramelistDao.deleteRootFrame(conditionBean, delBean);
        		
        		if(ResultConstant.SUCCESS.equals(result)){
        			//查询数据对象
                    DBObject hisQueryBean = new BasicDBObject();
                    hisQueryBean.put("uuid", uuid);
                    HistoryUtils.saveHistory(hisJson.get(uuid), request, "cc_framelist", hisQueryBean);
                	//历史记录
//        			addHistory(hisJson.get(uuid),uuid,request);
        		}
        	}
        	
        	for(int i=0;i<jsonBean.size();i++){
        		JSONObject rootFrame=(JSONObject) jsonBean.get(i);
        		String uuid=(String) rootFrame.get("uuid");
        		//修改数据对象
                DBObject updateBean = new BasicDBObject();
                updateBean.put("name", rootFrame.get("name"));
                updateBean.put("remark", rootFrame.get("remark"));
                updateBean.put("state",StateConstant.CC_NUMBER_STATE1 );
                updateBean.put("industryuuid",industryuuid);
                updateBean.put("children", rootFrame.get("children"));
                //查询数据对象
                DBObject queryBean = new BasicDBObject();
                queryBean.put("uuid", uuid);
                //如果没有根节点就新增
                DBObject insertBean = new BasicDBObject();
                insertBean.put("uuid", uuid);
                insertBean.put("industryuuid", industryuuid);
                insertBean.put("name", rootFrame.get("name"));
                insertBean.put("remark", rootFrame.get("remark"));
                insertBean.put("state", StateConstant.CC_NUMBER_STATE1);
                insertBean.put("createtime", new Date());
                insertBean.put("children", rootFrame.get("children"));
                insertBean.put("history", new ArrayList<DBObject>());
                
                String result=ccFramelistDao.saveFrameTree(queryBean,updateBean,insertBean);
                if(ResultConstant.SUCCESS.equals(result)){
                	//历史记录
                	if(hisJson.get(uuid)!=null){
                		//查询数据对象
                        DBObject hisQueryBean = new BasicDBObject();
                        hisQueryBean.put("uuid", uuid);
                        HistoryUtils.saveHistory(hisJson.get(uuid), request, "cc_framelist", hisQueryBean);
                		//addHistory(hisJson.get(uuid),uuid,request);
                	}
        		}
			}
        	return ResultConstant.SUCCESS;
        }
		return ResultConstant.FAIL;
	}
	
	/**
	 * 记录历史操作
	 * @param type 操作类型
	 * @param content 操作内容
	 * @return
	 */
	public String addHistory(Object content,String uuid,HttpServletRequest request){
        
		//查询数据对象
        DBObject queryBean = new BasicDBObject();
        queryBean.put("uuid", uuid);
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
		
		return ccFramelistDao.addHistroy(queryBean,pushBean);
	}
}
