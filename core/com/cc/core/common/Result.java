package com.cc.core.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author 
 * @createTime 2014.08.30
 */
public class Result {

    public static Log log = LogFactory.getLog(Result.class);

    public static void send(Object content, HttpServletResponse response) {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            if(content != null)
                out.write(content.toString());
            else
                out.write("");
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            if (out != null)
                out.close();
        }
    }

}
