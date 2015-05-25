package com.cc.core.common;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Common {

    public static Log log = LogFactory.getLog(Common.class);
    private static String serviceURL = "";
    private static String sn = "";// 序列号
    private static String pwd = "";// 密码
    private static String dxqm = "";
    static {
        getConnectionArgs();
    }

    /*
    * 方法名称：mdSmsSend_u 
    * 功    能：发送短信 ,传多个手机号就是群发，一个手机号就是单条提交
    * 参    数：mobile,content(手机号，URL_UT8编码内容)
    * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
    */
    public static String sendMessage(String mobile, String content) {

        String result = "";
        String soapAction = "http://tempuri.org/mdSmsSend_u";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<SendSMS xmlns=\"http://tempuri.org/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + " " + dxqm + "</content>";
        xml += "</SendSMS>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";
        URL url;
        try {
            url = new URL(serviceURL);
            log.error("序列号=" + sn);
            log.error("密码=" + pwd);
            log.error("mobile=" + mobile);
            log.error("content=" + content);
            log.error("dxqm=" + dxqm);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes("GBK"));
            //如果您的系统是utf-8,这里请改成bout.write(xml.getBytes("GBK"));

            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine = null;
            log.error("执行流操作");
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<SendSMSResult>(.*)</SendSMSResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            log.error("短信结果==" + new String(result.getBytes("GBK"), "UTF-8"));
            return result;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return "";
    }

    public static void getConnectionArgs() {

        String fileName = "/db.properties";

        Properties p = new Properties();
        try {
            InputStream in = Common.class.getResourceAsStream(fileName);
            p.load(in);
            in.close();
            if (p.containsKey("serviceURL")) {
                serviceURL = p.getProperty("serviceURL");
            }
            if (p.containsKey("sn")) {
                sn = p.getProperty("sn");
            }
            if (p.containsKey("pwd")) {
                pwd = p.getProperty("pwd");
            }

            if (p.containsKey("dxqm")) {
                dxqm = p.getProperty("dxqm");
            }
        } catch (IOException ex) {
        }

    }
}
