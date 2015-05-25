package com.cc.manage.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CommonController {

    public static Log log = LogFactory.getLog(CommonController.class);

    public void send(HttpServletResponse response, String content) {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(content);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            if (out != null)
                out.close();
        }
    }
}
