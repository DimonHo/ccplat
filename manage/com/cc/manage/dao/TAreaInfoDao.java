package com.cc.manage.dao;

import com.mongodb.DBObject;
import java.util.List;
/***
 * 地区表操作
 * @author Ron
 *
 */
public interface TAreaInfoDao {
    
    /***
     * 通过父Id
     * @author Ron
     * @param pId 父id
     * @return
     */
    List<DBObject> getAreaInfo(int pId);
    
}
