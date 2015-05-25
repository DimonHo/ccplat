package com.cc.core.timer;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.cc.core.common.MongoDBManager;
import com.cc.core.utils.DateUtils;
import com.cc.core.utils.StringUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 系统定时检测交费时间
 * 
 * @author yfm
 * @createTime 2014.09.15
 * 
 */
public class DispatcherTimer {
	
    class mytask extends TimerTask {
        
        @Override
        public void run() {
            
            // TODO Auto-generated method stub
            System.out.println("定时检测交费时间");
            MongoDBManager mongoDb = MongoDBManager.getInstance();
            DBObject conditionBean = new BasicDBObject();
            
            // 查询企业关系表
            List<DBObject> dbList = mongoDb.find("b_companycc", conditionBean);
            // 得到企业用户表中的元素
            for (int i = 0; i < dbList.size(); i++) {
                
                DBObject project = dbList.get(i);
                String ccno = (String)project.get("ccno");
                Date d = (Date)project.get("endtime");
                Date currenttime = new Date();
                // 判断当前时间是否大于交费时间（是否超期）
                if (currenttime.getTime() > d.getTime()) {
                    
                    // 根据cc号查询用户表
                    conditionBean.put("ccno", ccno);
                    List<DBObject> userList = mongoDb.find("cc_user", conditionBean);
                    // 判断ccno号是否在用户表
                    if (userList.size() > 0) {
                        DBObject obj = userList.get(0); // cc号唯一
                        String state = (String)obj.get("state");
                        // 停用超期还在启用的帐号
                        if (StringUtils.equals(state, "1")) {
                            
                            // 根据超期的ccno修改用户表帐号状态
                            DBObject conditionBeannew = new BasicDBObject();
                            conditionBeannew.put("state", "2");// 2表示停用
                            mongoDb.update("cc_user", conditionBean, conditionBeannew);
                            
                            // 添加history历史记录
                            //DBObject doc = new BasicDBObject();
                            DBObject res = new BasicDBObject();
                            DBObject conditionBeannew1 = new BasicDBObject();
                            
                            res.put("createtime", new Date());
                            res.put("opertor", "");
                            res.put("remark", "cs端修改状态为停用");
                            conditionBeannew1.put("history", res);
                            mongoDb.updatePush("cc_user", conditionBean, conditionBeannew1);
                        }
                    }
                }
                
            }
            
        }
    }
    
    /**
     * 创建一个Timer对象，指定一天的某个时刻调用系统检测方法
     */
    public void execute() {
        
        Timer timer = new Timer();
        mytask task = new mytask();
        // 获取一天的结束时间
        Date endDate = DateUtils.getEndDate(new Date());
        
        // 执行调用系统检测方法
        timer.schedule(task, endDate);
    }
    
    public static void main(String[] args) {
        
        DispatcherTimer dt = new DispatcherTimer();
        dt.execute();
        Date endDate = DateUtils.getEndDate(new Date());
        System.out.println(endDate);
    }
}
