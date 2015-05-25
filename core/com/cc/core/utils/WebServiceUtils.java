package com.cc.core.utils;

import com.cc.manage.service.WebService;


/**
 * WebService工具类
 * 
 * @author zmx
 * 
 */
public class WebServiceUtils {
    /**
     * 调用WebService公共方法
     * 
     * @param methodName
     * @param param
     * @return
     * @author Einstein
     */
    public static String getWebService(String methodName, Object[] param,String address) {
        WebService ws = new WebService();
        ws.setAddress(address);
        ws.setMethodName(methodName);
        ws.setProperty(param);
        String result = ws.getString();
        return result;
    }
}
