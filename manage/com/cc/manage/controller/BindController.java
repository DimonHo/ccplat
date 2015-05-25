package com.cc.manage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cc.core.common.Result;
import com.cc.core.utils.RequestUtils;
import com.cc.manage.logic.BindLogic;

/**
 * 绑定类，用于绑定设备，解绑机器  
 * @author Einstein
 *
 */
@Controller
@RequestMapping("bind")
public class BindController {
    @Autowired
    private BindLogic bindLogic;
    public static Log log = LogFactory.getLog(BindController.class);

    /**
     * 绑定设置
     * @param request  uuid,imei(当前设备的唯一编码) 
     * @param response -1:验证码无效;0：验证码过期;1:绑定成功;2:绑定不成功
     */
    @RequestMapping("bindDevice")
    public void bindDevice(HttpServletRequest request, HttpServletResponse response) {

        String uuid = RequestUtils.getString(request, "uuid");
        String imei = RequestUtils.getString(request, "imei");//imei
        String model = RequestUtils.getString(request, "model");//model

        if (bindLogic.getBindInfo(uuid, imei) != null) {
            //已有申请记录
            Result.send("0", response);
        } else {
            boolean flag = bindLogic.bindDevice(uuid, imei, model);
            if (flag) {
                Result.send("1", response);
            }
        }
    }
}
