package com.cc.manage.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.cc.core.constant.ResultConstant;

public class WebService {
    public static Log log = LogFactory.getLog(WebService.class);

    // 成聪web地址
    private String WEBADDRESS;

    private String URL = "/businessplat/services/BusinessService";

    private String NAMESPACE = "http://cc.k.com/";

    private String METHOD_NAME = "";

    public String NO_DATA = "anyType{}";

    private int CON_TIME_OUT = 30000;

    private Object[] PROPERTY = new String[0];

    public void setAddress(String address) {

        WEBADDRESS = address;
    }

    public String getAddress() {

        return WEBADDRESS;
    }

    public void setMethodName(String name) {

        METHOD_NAME = name;
        // 针对一些特殊方法增加延时
        if (name.equals("getWebLogin")) {
            CON_TIME_OUT = 60000 * 60 * 3;
        } else if (name.equals("getEmployDate")) {
            CON_TIME_OUT = 10000;
        } else
            CON_TIME_OUT = 30000;
    }

    public void setProperty(Object[] property) {

        PROPERTY = property;
    }

    // 用于成聪Web服务器调用
    private synchronized SoapObject conWebService() {

        SoapObject result = null;
        try {
            SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
            for (int i = 0; i < PROPERTY.length; i++) {
                rpc.addProperty("param" + i, PROPERTY[i]);
            }
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.bodyOut = rpc;
            envelope.dotNet = true;
            envelope.setOutputSoapObject(rpc);

            String strAdd = WEBADDRESS;
            HttpTransportSE ht = new HttpTransportSE("http://" + strAdd + URL, CON_TIME_OUT);
            ht.debug = true;
            try {
                ht.call(NAMESPACE + METHOD_NAME, envelope);
                result = (SoapObject) envelope.bodyIn;
            } catch (Exception e) {
                log.error(e.getLocalizedMessage(), e);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return result;
    }

    // 成聪Web服务器返回对象------------------------------
    public String getString() {

        String ret = "";
        try {
            SoapObject result = conWebService();
            if (result != null) {
                ret = result.getProperty(0).toString();
            }else{
                return ResultConstant.SERVICE_EXCEPTION;
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        if (ret.equals("[]") || ret.equals("anyType{}"))
            ret = "";
        return ret;
    }

}
