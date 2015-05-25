package com.cc.core.servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.PropertyConfigurator;

import com.cc.core.timer.DispatcherTimer;

/**
 * Servlet implementation class InitServlet
 * @author Ron
 * @createTime 2014.08.30
 */
public class InitServlet extends HttpServlet {

    public static Log log = LogFactory.getLog(InitServlet.class);

    private static final long serialVersionUID = 841536975105971383L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitServlet() {

        super();
    }

    public void init() throws ServletException {

        super.init();
        this.doInit(getServletContext());
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        this.doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void doInit(ServletContext context) {

        // init log
        this.initLog();

        // init db :this must first for logic!!!
        this.initDB();

        this.initTimer();
        
        System.out.println("init system success!");
        log.info("init system success!");
        
    }

    /**
     * 初始化JDBC数据库连接
     */
    private void initDB() {

        //Properties props = PropertiesUtils.loadProperties(this.getServletContext().getRealPath("") + "/WEB-INF/config/jdbc.properties");
        //JDBCConnUtils.instance().init(PropertiesUtils.getValue(props, "db.driver"), PropertiesUtils.getValue(props, "db.url"), PropertiesUtils.getValue(props, "db.username"), PropertiesUtils.getValue(props, "db.password"));
        //log.info("init db success!");
    }

    /**
     * 初始化日志
     */
    private void initLog() {

        PropertyConfigurator.configure(this.getServletContext().getRealPath("") + "/WEB-INF/config/log4j.properties");

        log.info("init log success!");
    }
    
    /**
     * 初始化系统定时检测交费时间定时器
     */
    private void initTimer(){
    	
    	 DispatcherTimer dt = new DispatcherTimer();
         dt.execute();
    }
}
