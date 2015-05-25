package com.cc.manage.logic;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.constant.AplicationKeyConstant;
import com.cc.core.constant.ResultConstant;
import com.cc.core.constant.SessionKey;
import com.cc.core.constant.StateConstant;
import com.cc.core.constant.TableNameConstant;
import com.cc.core.utils.DBObejctUtils;
import com.cc.core.utils.HistoryUtils;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.SessionUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.impl.CcuserDaoImpl;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 单个账号控制Logic处理
 * 
 * @author yfm
 * @createTime 2014.09.15
 * 
 */
@Component
public class CcuserLogic {
    
    public static Log log = LogFactory.getLog(CcuserLogic.class);
    
    @Autowired
    private CcuserDaoImpl userDao;
    
    /*-------------------------------------------------------------------------------------------------*/

    /**
     * 启用、停用操作
     * 
     * @param request [ state 操作名，如：1启用、2停用,ccno 查询条件CC号 ,byname操作人]
     * @return
     */
    public String updateState(HttpServletRequest request) {
        
        DBObject conditionBean = new BasicDBObject();
        DBObject conditionBeannew = new BasicDBObject();
        
        String id = RequestUtils.getString(request, "data");
        String state = RequestUtils.getString(request, "operatestate");
        // 获得操作人
        DBObject user = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
        String name = null;
        if (user != null) {
            name = (String)user.get("realname");
            // 判断字符串是否含有，
            if (StringUtils.contains(id, AplicationKeyConstant.STRING_SPLIT_CHAR)) {
                String[] arr = StringUtils.stringToArray(id, AplicationKeyConstant.STRING_SPLIT_CHAR);
                
                for (int i = 0; i < arr.length; i++) {
                    // 修改状态
                    String idString = arr[i];
                    conditionBean.put("_id", new ObjectId(idString));
                    conditionBeannew.put("state", state);
                    userDao.updateState(conditionBean, conditionBeannew);
                    // 添加history
                    this.updateArr(request, conditionBean, state, name);
                    
                }
                return ResultConstant.SUCCESS;
                
            }
            else {
                conditionBean.put("_id", new ObjectId(id));
                conditionBeannew.put("state", state);
                userDao.updateState(conditionBean, conditionBeannew);
                // 添加history
                this.updateArr(request, conditionBean, state, name);
                
                return ResultConstant.SUCCESS;
                
            }
        }
        return ResultConstant.FAIL;
        
    }
    
    /**
     * 添加历史记录
     * 
     * @param request
     * @param conditionBean 查询条件CC帐号
     * @param state 修改状态
     * @param name 操作人
     * @author Ron
     */
    public void updateArr(HttpServletRequest request, DBObject conditionBean, String state, String name) {
        
        if (StringUtils.equals("state", "1")) {
            state = "启用";
        }
        else {
            state = "停用";
        }
        // 根据CC帐号查询得到被操作者
        List<DBObject> obj = (List<DBObject>)userDao.searchUser(conditionBean);
        String ccname = (String)obj.get(0).get("realname");// 被操作者的真实名字
        // 得到备注
        String content = name + state + ccname + "的cc号";
        HistoryUtils.saveHistory(content, request, TableNameConstant.T_USER, conditionBean);
        
    }
    
    /***
     * 增加用户操作的历史记录
     * 
     * @param request
     * @param conditionBean
     * @param starttime
     * @param endtime
     * @param remark
     * @param creteaTime
     * @author Ron
     */
    public void addHistory(HttpServletRequest request, String id, String remark, Date creteaTime) {
        
        DBObject historyBean = new BasicDBObject();
        DBObject userSession = SessionUtils.getSessionObject(request, SessionKey.SESSION_KEY_USER);
        DBObject operator = new BasicDBObject();
        operator.put("name", DBObejctUtils.getString(userSession, "realname"));
        operator.put("ccno", DBObejctUtils.getString(userSession, "ccno"));
        historyBean.put("createtime", creteaTime);
        historyBean.put("operator", operator);
        historyBean.put("remark", remark + "");
        DBObject history = new BasicDBObject();
        history.put("history", historyBean);
        userDao.updatePush(id, history);
    }
    
    /**
     * 分页排序查询（高级查询）
     * 
     * @param request request [ inputname 输入参数（CC号、姓名）,state 1表示启动2表示停用,ccnostart 开始CC号,ccnoend 结束CC号 ],[ pageIndex
     *            每页显示数，pageSize 总条数， sortField 字段名 ，sortOrder 排序方向如asc,desc ]
     * @return
     */
    public JSONObject list(HttpServletRequest request) {
        
        DBObject conditionBean = new BasicDBObject();
        String inputname = RequestUtils.getString(request, "inputname");
        String state = RequestUtils.getString(request, "state");
        String ccnostart = RequestUtils.getString(request, "ccnostart");
        String ccnoend = RequestUtils.getString(request, "ccnoend");
        
        // 当输入关键字不为空时，进行模糊查询
        BasicDBList values = new BasicDBList();
        if (StringUtils.isNotBlank(inputname)) {
            Pattern pattern = Pattern.compile("^.*" + inputname + ".*$", Pattern.CASE_INSENSITIVE);// 字段进行模糊匹配
            values.add(new BasicDBObject("byname", pattern));
            values.add(new BasicDBObject("ccno", pattern));
            conditionBean.put("$or", values);
        }
        
        if (StringUtils.isNotBlank(state)) {
            conditionBean.put("state", state);
        }
        // 查询没有收回的帐号
        conditionBean.put("usestate", new BasicDBObject("$ne", StateConstant.CC_USER_USESTATE2));// 收回
        // 判断CC号段不为空时，查询从开始CC号到结束CC号的信息
        if (StringUtils.isNotBlank(ccnostart) && StringUtils.isNotBlank(ccnoend)) {
            conditionBean.put("ccno", new BasicDBObject("$lte", ccnoend).append("$gte", ccnostart));
            
        }
        // 分页
        int pageIndex = RequestUtils.getInt(request, "pageIndex") + 1;
        int pageSize = RequestUtils.getInt(request, "pageSize");
        // 字段排序
        
        // 判断排序是否为空
        String sortField = RequestUtils.getString(request, "sortField");
        DBObject orderBy = new BasicDBObject();
        if (StringUtils.isNotBlank(sortField)) {
            String sortOrder = RequestUtils.getString(request, "sortOrder");
            orderBy.put(sortField, StringUtils.setOrder(sortOrder));
        }
        long count = userDao.count(conditionBean);
        if(count > 0){
        //过滤不显示的字段
            DBObject field = new BasicDBObject();
            field.put("ccpwd", 0);
            List<DBObject> dblist = userDao.findLess(conditionBean, field, orderBy, pageIndex, pageSize);
            JSONObject result = new JSONObject();
            result.put("data", JSONUtils.resertJSON(dblist));
            result.put("total", count);
            return result;
        }
        return null;
    }
    
    /***
     * 构建空的cc_user的空对象，供分配给企业，赋值ccno,state为1启用，usestate为0未分配
     * 
     * @param userBean 为null则创建
     * @param companyno 
     * @param ccno
     * @param starttime 账号使用期限开始日期
     * @param endtime 账号使用期限结束日期
     * @return
     * @author Ron
     */
    public DBObject buildCcUserBean(DBObject userBean, List<DBObject> historyList, String companyno, String ccno,
        Date starttime, Date endtime) {
        
        if (userBean == null) {
            userBean = new BasicDBObject();
        }
        userBean.put("age", ""); // 年纪
        userBean.put("birthday", "");// 生日
        userBean.put("byname", "");// 别名
        userBean.put("companyno", companyno);// 企业编号
        userBean.put("ccno", ccno);// cc号
        userBean.put("ccpwd", "");// 密码MD加密
        userBean.put("collage", "");// 学校
        userBean.put("education", "");// 学历
        userBean.put("email", "");// 邮箱
        userBean.put("fax", "");// 传真
        userBean.put("hometown", "");// 家乡
        userBean.put("head", "");// 头像
        userBean.put("language", "");// 语言
        userBean.put("info", "");// 个人说明
        userBean.put("phone", "");// 手机
        userBean.put("realname", "");// 真实姓名
        userBean.put("sex", "");// 性别
        userBean.put("signature", "");// 个性签名
        userBean.put("tel", "");// 电话
        userBean.put("zip", "");// 邮编
        userBean.put("state", StateConstant.CC_USER_STATE1);// 状态
        userBean.put("usestate", StateConstant.CC_USER_USESTATE0);// 使用状态
        userBean.put("starttime", starttime);// 开始时间
        userBean.put("endtime", endtime);// 结束时间
        userBean.put("history", historyList);
        return userBean;
    }
}
