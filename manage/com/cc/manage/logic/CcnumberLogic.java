package com.cc.manage.logic;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cc.core.common.MongoDBManager;
import com.cc.core.constant.StateConstant;
import com.cc.core.utils.JSONUtils;
import com.cc.core.utils.RequestUtils;
import com.cc.core.utils.StringUtils;
import com.cc.manage.dao.CcnumberDao;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 创建批次账号中生成的账号池logic处理
 * 
 * @author ron
 * @createTime 2014.09.10
 */
@Component
public class CcnumberLogic {
    
    public static Log log = LogFactory.getLog(CcnumberLogic.class);
    
    @Autowired
    private CcnumberDao ccnumberDao;
    
    /***
     * 根据传入的json串修改类型以及选中的状态
     * 
     * @param request
     * @return boolean
     */
    public boolean modify(HttpServletRequest request) {
        try {
            String json = RequestUtils.getString(request, "json");
            if (StringUtils.isNotBlank(json)) {
                JSONArray jsonarr = JSONArray.fromObject(json);
                for (int i = 0; i < jsonarr.size(); i++) {
                    JSONObject jsonparam = jsonarr.getJSONObject(i);
                    // 需要改变的值
                    DBObject bean = new BasicDBObject();
                    bean.put("special", RequestUtils.getInt(request, "special"));
                    bean.put("state", RequestUtils.getInt(request, "state"));
                    DBObject conditionBean = new BasicDBObject();
                    conditionBean.put("_id", new ObjectId(JSONUtils.getString(jsonparam, "id")));
                    this.ccnumberDao.modify(conditionBean, bean);
                }
                return true;
            }
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return false;
    }
    
    /***
     * 通过传入的类型type获取当前类型最小的待添加的CC账号
     * 
     * @param request
     * @return String 最小的待添加的CC账号
     */
    public String maxCcNo(HttpServletRequest request) {
        try {
            int type = RequestUtils.getInt(request, "type");
            int max = this.ccnumberDao.maxCcNo(type);
            return String.valueOf(max);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "0";
    }
    
    public JSONObject list(HttpServletRequest request) {
        return ccnumberDao.list(request);
    }
    
    /***
     * 查询可以被分配CC账号
     * 
     * @param request
     * @return
     * @author Ron
     */
    public List<DBObject> notAllot(HttpServletRequest request) {
        try {
            int endNumber = RequestUtils.getInt(request, "num", 100);
            String start = RequestUtils.getString(request, "start");// 号段起始数
            MongoDBManager db = MongoDBManager.getInstance();
            DBObject temp = new BasicDBObject();
            String ctype = request.getParameter("ctype");
            if (StringUtils.isNotBlank(ctype)) {
                if (ctype.equals("aabbcc")) {
                    temp.put("ccno", Pattern.compile("^(?:(\\d)\\1)+$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("aaabbb")) {
                    temp.put("ccno", Pattern.compile("^(?:(\\d)\\1\\1)+$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("ababab")) {
                    temp.put("ccno", Pattern.compile("^(\\d\\d)\\1+$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("abcabc")) {
                    temp.put("ccno", Pattern.compile("^(\\d\\d\\d)\\1+$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("3a")) {
                    temp.put("ccno", Pattern.compile("^(?:(\\d)\\1\\1).*$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("abc")) {
                    temp.put("ccno",
                        Pattern.compile("(.*?(123)|(234)|(345)|(567)|(678)|(789)(.*)?)", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("4a")) {
                    temp.put("ccno", Pattern.compile("^(?:(\\d)\\1\\1\\1).*$", Pattern.CASE_INSENSITIVE));
                }
                else if (ctype.equals("abcd")) {
                    temp.put("ccno",
                        Pattern.compile("(.*?(1234)|(2345)|(3456)|(5678)|(6789)(.*)?)", Pattern.CASE_INSENSITIVE));
                }
            }
            if (StringUtils.isNotBlank(start)) {
                temp.put("ccno", new BasicDBObject("$gte", start));
            }
            temp.put("type", RequestUtils.getString(request, "type"));
            temp.put("state", StateConstant.CC_NUMBER_STATE2);// 未使用的
            DBObject orderBy = new BasicDBObject();
            orderBy.put("order", StringUtils.setOrder(""));
            return db.findLess("cc_number", temp, new BasicDBObject("ccno", 1), orderBy, 1, endNumber);
        }
        catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
    
    /***
     * 判断ccno是否为特殊号码，是返回2，不是返回1
     * 
     * @param ccno
     * @param type 不同类型的账号的特殊账号格式不一样用于区分1,2,3,4中不同的类型
     * @return
     */
    public int special(String ccno, int type) {
        
        if (type == 2) {
            return specialinternal(ccno);
        }
        else if (type == 3) {
            return specialExternal(ccno);
        }
        else if (type == 4) {
            return specialFree(ccno);
        }
        return 1;
    }
    
    /**
     * 第2类使用CC号内部公司使用 4-5
     * 
     * @param ccno
     * @return
     */
    public int specialinternal(String ccno) {
        
        if (Pattern.matches("^.*(?:(\\d)\\1)+.*$", ccno)) {// 1122
            return 2;
        }
        else if (Pattern.matches("^.*(\\d\\d)\\1+.*$", ccno)) {// *abab*
            return 2;
        }
        else if (Pattern.matches("^.*(?:(\\d)\\1\\1).*$", ccno)) {// *aaa*
            return 2;
        }
        else if (Pattern.matches("(.*?(123)|(234)|(345)|(456)|(567)|(678)|(789)(.*)?)", ccno)) {// abc
            return 2;
        }
        return 1;
    }
    
    /**
     * 第3类使用CC号外部企业使用 验证 6-7
     * 
     * @param ccno
     * @return
     */
    public int specialExternal(String ccno) {
        
        if (Pattern.matches("^(?:(\\d)\\1){2,}$", ccno)) {// 112233
            return 2;
        }
        else if (Pattern.matches("^(?:(\\d)\\1\\1)+$", ccno)) {// aaabbb
            return 2;
        }
        else if (Pattern.matches("^(\\d\\d)\\1{2,}$", ccno)) {// ababab
            return 2;
        }
        else if (Pattern.matches("^(\\d\\d\\d)\\1+$", ccno)) {// abcabc
            return 2;
        }
        else if (Pattern.matches("^(?:(\\d)\\1\\1).*$", ccno)) {// 3a
            return 2;
        }
        else if (Pattern.matches("(.*?(123)|(234)|(345)|(567)|(678)|(789)(.*)?)", ccno)) {// abc
            return 2;
        }
        else if (Pattern.matches("^(?:(\\d)\\1\\1\\1).*$", ccno)) {// 4a
            return 2;
        }
        else if (Pattern.matches("(.*?(1234)|(2345)|(3456)|(5678)|(6789)(.*)?)", ccno)) {// abcd
            return 2;
        }
        return 1;
    }
    
    /**
     * 第4类使用CC号
     * 
     * @param ccno
     * @return
     */
    public int specialFree(String ccno) {
        
        if (Pattern.matches("^.*(?:(\\d)\\1){4,}?.*$", ccno)) {// *11223344*
            return 2;
        }
        else if (Pattern.matches("^.*(?:(\\d)\\1\\1){2,}.*$", ccno)) {// *aaabbb*
            return 2;
        }
        else if (Pattern.matches("^.*(\\d\\d)\\1{2,}.*$", ccno)) {// ababab
                                                                  // ab开始结尾任意个数字符，中间ab重复1次或者多次
            return 2;
        }
        else if (Pattern.matches("^.*(\\d\\d\\d\\d)\\1+.*$", ccno)) {// abcdabcd
            return 2;
        }
        else if (Pattern.matches("(.*?(1234)|(2345)|(3456)|(4567)|(5678)|(6789)(.*)?)", ccno)) {// abc重复0次或1次，加上任意字符
            return 2;
        }
        else if (Pattern.matches("(.*?(4321)|(5432)|(6543)|(7654)|(8765)|(9876)(.*)?)", ccno)) {// abc重复0次或1次，加上任意字符
            return 2;
        }
        else if (Pattern.matches("^.*(?:(\\d)\\1\\1\\1).*$", ccno)) {// *1111*
            return 2;
        }
        else if (Pattern.matches("^([1-9]\\d{3}((0[1-9]|1[012])(0[1-9]|1\\d|2[0-9]|3[0-1] )))$", ccno)) {
            return 2;
        }
        return 1;
    }
}
