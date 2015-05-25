package com.cc.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/**
 * 日期格式化，转换工具类
 * @author Ron
 * @createTime 2014.08.30
 */
public class DateUtils {

    public static Logger log = Logger.getLogger(DateUtils.class);

    public static final String DATE_FORMAT_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_STS = "yyyy-MM-dd'T'HH:mm:ss";
    public static final String DATE_FORMAT_MM = "yyyy-MM-dd HH:mm";
    public static final String DATE_FORMAT_HH = "yyyy-MM-dd HH";
    public static final String DATE_FORMAT_DD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_SPLITE_DD = "yyyy.MM.dd";
    public static final String DATE_FORMAT_NO_SPLITE_DD = "yyyyMMdd";

    public static final String DATE_FORMAT_MM_NO_DD = "yyyyMM";

    public static final String DATE_FORMAT_NO_SPLITE_MM = "yyyyMMddHHmm";
    public static final String DATE_FORMAT_NO_SPLITE_MM_HH = "yyyyMMddHH";
    public static final String YEAR = "yyyy";

    public static final String DATE_FORMAT_MMDD = "M月d日";
    public static final String DATE_FORMAT_WEEK = "星期";

    public static final String DATE_TIME_MORNING = "早上";
    public static final String DATE_TIME_AFTERNOON = "下午";
    public static final String DATE_TIME_NIGHT = "晚上";

    public static final String CENTRE_SCRIBING = "-";

    protected static final String EMPTY = "";
    protected static final String ZERO = "0";
    protected static final String SPLITE_CHAR = ":";

    protected static final String START_TIME = " 00:00:00";// 空格不能删除
    protected static final String END_TIME = " 23:59:59";// 空格不能删除

    protected static final int WEEK_DAYS = 7;
    public static String yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    protected static final String[] weeks = { "一", "二", "三", "四", "五", "六", "日" };

    /**
     * 返回年份
     * 
     * @param date
     *            日期
     * @return 返回年份
     */
    public static int getYear(java.util.Date date) {

        try {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(date);
            return c.get(java.util.Calendar.YEAR);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return 0;
    }

    /**
     * 返回月份
     * 
     * @param date
     *            日期
     * @return 返回月份
     */
    public static int getMonth(java.util.Date date) {

        try {
            java.util.Calendar c = java.util.Calendar.getInstance();
            c.setTime(date);
            return c.get(java.util.Calendar.MONTH) + 1;
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return 0;
    }

    /**
     * 日期转字符串
     * 
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {

        if (date == null) {
            return EMPTY;
        }
        DateFormat fmt = new SimpleDateFormat(format);
        return fmt.format(date);
    }

    /**
     * 字符串转日期
     * 
     * @param dateStr
     * @param format
     * @return
     */
    public static Date stringToDate(String dateStr, String format) {

        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(format)) {
            return null;
        }
        DateFormat fmt = new SimpleDateFormat(format);
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }

    /**
     * 比较两个日期是否一致
     * 比较标准：天 yyyy-MM-dd
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean equals(Date dateA, Date dateB) {

        if (dateA == null || dateB == null) {
            return false;
        }
        String strA = dateToString(dateA, DATE_FORMAT_DD);
        String strB = dateToString(dateB, DATE_FORMAT_DD);

        if (StringUtils.equals(strA, strB)) {
            return true;
        }

        return false;
    }

    /**
     * 比较两个日期是否一致
     * 比较标准：天 yyyy-MM-dd
     * @param dateA
     * @param dateB
     * @return
     */
    public static boolean notEquals(Date dateA, Date dateB) {

        return !equals(dateA, dateB);
    }

    /**
     * 判断给定的日期是一周中的第几天，注意：按照中国的习惯，周日是第七天
     * 
     * @param date
     * @return
     */
    public static int dateToWeek(Date date) {

        if (date == null) {
            return 0;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == 1) {
            return 7;
        } else {
            return cal.get(Calendar.DAY_OF_WEEK) - 1;
        }
    }

    public static String dateOfWeek(Date date) {

        return DATE_FORMAT_WEEK + weeks[dateToWeek(date) - 1];
    }

    /**
     * 指定时间的下一天
     * 
     * @param date
     * @return
     */
    public static Date nextDate(Date date) {

        if (date == null) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.add(Calendar.DATE, 1);
            return cal.getTime();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 指定时间的前一天
     * 
     * @param date
     * @return
     */
    public static Date previousDate(Date date) {

        if (date == null) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.add(Calendar.DATE, -1);
            return cal.getTime();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 指定时间的下N天
     * 
     * @param date
     * @return
     */
    public static Date nextNDate(Date date, int nDay) {

        if (date == null) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.add(Calendar.DATE, nDay);
            return cal.getTime();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 指定时间的前N天
     * 
     * @param date
     * @return
     */
    public static Date previousNDate(Date date, int nDay) {

        if (date == null) {
            return date;
        }

        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.add(Calendar.DATE, -nDay);
            return cal.getTime();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return null;
    }

    /**
     * 获取一天的起始时间
     * 
     * @param date
     * @return
     */
    public static Date getStartDate(Date date) {

        if (date == null) {
            return date;
        }

        DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_DD);
        String dateStr = fmt.format(date);
        dateStr = dateStr + START_TIME;
        fmt = new SimpleDateFormat(DATE_FORMAT_SS);
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return date;
    }

    /**
     * 获取一天的结束时间
     * 
     * @param date
     * @return
     */
    public static Date getEndDate(Date date) {

        if (date == null) {
            return date;
        }

        DateFormat fmt = new SimpleDateFormat(DATE_FORMAT_DD);
        String dateStr = fmt.format(date);
        dateStr = dateStr + END_TIME;
        fmt = new SimpleDateFormat(DATE_FORMAT_SS);
        try {
            return fmt.parse(dateStr);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return date;
    }

    /**
     * currentDat是否在referenceDate日期之前
     * 
     * @param referenceDate
     * @param currentDat
     * @return
     */
    public static boolean isBeforeDate(Date referenceDate, Date currentDate) {

        if (currentDate == null) {
            return false;
        }
        if (referenceDate == null) {
            return true;
        }
        return currentDate.before(referenceDate);
    }

    /**
     * currentDat是否在referenceDate日期之后
     * 
     * @param referenceDate
     * @param currentDat
     * @return
     */
    public static boolean isAffterDate(Date referenceDate, Date currentDate) {

        if (currentDate == null) {
            return false;
        }
        if (referenceDate == null) {
            return true;
        }
        return currentDate.after(referenceDate);
    }

    /**
     * 判断currentDate是否在startDate和endDate之间，不包括startDate和endDate
     * 
     * @param startDate
     * @param endDate
     * @param currentDate
     * @return
     */
    public static boolean isDuringDate(Date startDate, Date endDate, Date currentDate) {

        if (currentDate == null) {
            return false;
        }

        if (isAffterDate(startDate, currentDate) && isBeforeDate(endDate, currentDate)) {
            return true;
        }
        return false;
    }

    /**
     * 判断currentDate是否在startDate和endDate之间，包括startDate和endDate
     * 
     * @param startDate
     * @param endDate
     * @param currentDate
     * @return
     */
    public static boolean isBetweenDate(Date startDate, Date endDate, Date currentDate) {

        if (currentDate == null) {
            return false;
        }

        if (isAffterDate(startDate, currentDate) && isBeforeDate(endDate, currentDate)) {
            return true;
        }
        // 开始接受日期判断
        else if (equals(startDate, currentDate) || equals(endDate, currentDate)) {
            return true;
        }
        return false;
    }

    /**
     * 获取startDate到endDate之间的星期day（中文星期）不包括startDate和endDate
     * 
     * @param startDate
     * @param endDate
     * @param day
     * @return
     */
    public static List<Date> findDayDuringDate(Date startDate, Date endDate, int day) {

        List<Date> listDate = new ArrayList<Date>();
        int startDay = dateToWeek(startDate);

        Date date = null;
        if (startDay == day) {
            date = nextNDate(startDate, WEEK_DAYS);
        } else {
            date = nextNDate(startDate, day - startDay);
        }
        while (isDuringDate(startDate, endDate, date)) {
            listDate.add(date);
            date = nextNDate(date, WEEK_DAYS);
        }

        return listDate;
    }

    /**
     * 获取startDate到endDate之间的星期day（中文星期）包括startDate和endDate
     * 
     * @param startDate
     * @param endDate
     * @param day
     * @return
     */
    public static List<Date> findDayBetweenDate(Date startDate, Date endDate, int day) {

        List<Date> listDate = new ArrayList<Date>();
        int startDay = dateToWeek(startDate);

        Date date = null;
        if (startDay == day) {
            date = startDate;
        } else {
            date = nextNDate(startDate, day - startDay);
        }
        while (isBetweenDate(startDate, endDate, date)) {
            listDate.add(date);
            date = nextNDate(date, WEEK_DAYS);
        }

        return listDate;
    }

    /**
     * date转换成Timestamp
     * 
     * @param date
     * @param format
     * @return
     */
    public static Timestamp dateToTimestamp(Date date, String format) {

        if (date == null) {
            return null;
        }

        if (StringUtils.isBlank(format)) {
            format = DATE_FORMAT_SS;
        }

        DateFormat fmt = new SimpleDateFormat(format);

        return Timestamp.valueOf(fmt.format(date));
    }

    /**
     * 获取早中晚
     * 
     * @param time
     * @return
     */
    public static String getDateTime(int time) {

        // 早上
        if (time == 1) {
            return DateUtils.DATE_TIME_MORNING;
        }
        // 下午
        else if (time == 2) {
            return DateUtils.DATE_TIME_AFTERNOON;
        }
        // 晚上
        else if (time == 3) {
            return DateUtils.DATE_TIME_NIGHT;
        }
        return null;
    }

    /**
     * 获取早中晚的开始时间
     * 
     * @param date
     * @param time
     * @return
     */
    public static Date getMeetTimeStart(String date, int time) {

        // 早上
        if (time == 1) {
            return DateUtils.stringToDate(date + " 06:00", DateUtils.DATE_FORMAT_MM);
        }
        // 下午
        else if (time == 2) {
            return DateUtils.stringToDate(date + " 13:00", DateUtils.DATE_FORMAT_MM);
        }
        // 晚上
        else if (time == 3) {
            return DateUtils.stringToDate(date + " 19:00", DateUtils.DATE_FORMAT_MM);
        }
        return null;
    }

    /**
     * 获取早中晚的结束时间
     * 
     * @param date
     * @param time
     * @return
     */
    public static Date getMeetTimeEnd(String date, int time) {

        // 早上
        if (time == 1) {
            return DateUtils.stringToDate(date + " 13:00", DateUtils.DATE_FORMAT_MM);
        }
        // 下午
        else if (time == 2) {
            return DateUtils.stringToDate(date + " 19:00", DateUtils.DATE_FORMAT_MM);
        }
        // 晚上
        else if (time == 3) {
            return DateUtils.stringToDate(date + " 23:00", DateUtils.DATE_FORMAT_MM);
        }
        return null;
    }

    public static String getCurrentTime() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        return sdf.format(new Date());
    }

    /**
     * 拼装一个时间段内的DBObject对象
     * @param stimeFile 开始时间字段
     * @param etimeFile 结束时间字段
     * @param StartTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    public static BasicDBObject getDbObject(String stimeFile, String etimeFile, String StartTime, String endTime) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date sdate = null;
        java.sql.Date edate = null;

        try {
            sdate = new java.sql.Date(sdf.parse(StartTime).getTime());
            edate = new java.sql.Date(sdf.parse(endTime).getTime());
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
        }

        //stime>startTime and etime <endTime
        BasicDBObject query1 = new BasicDBObject();
        query1.put(stimeFile, new BasicDBObject("$gte", sdate));
        query1.put(etimeFile, new BasicDBObject("$lte", edate));

        //stime >startTime and stime<endtime
        BasicDBObject query2 = new BasicDBObject();
        query2.put(stimeFile, new BasicDBObject("$gte", sdate).append("$lte", edate));

        //etime >starttime and etime<endtime
        BasicDBObject query3 = new BasicDBObject();
        query3.put(etimeFile, new BasicDBObject("$gte", sdate).append("$lte", edate));

        //stime<startTime and etime>endtime
        BasicDBObject query4 = new BasicDBObject();
        query4.put(stimeFile, new BasicDBObject("$lte", sdate));
        query4.put(etimeFile, new BasicDBObject("$gte", edate));

        BasicDBList all = new BasicDBList();
        all.add(query1);
        all.add(query2);
        all.add(query3);
        all.add(query4);

        BasicDBObject condition = new BasicDBObject();
        condition.put("$or", all);

        return condition;

    }

    public static void main(String[] args) {

        String da = DateUtils.dateToString(new Date(), DateUtils.DATE_FORMAT_SS);

        DateFormat fmt = new SimpleDateFormat(DateUtils.DATE_FORMAT_SS);
        da = fmt.format(new Date());

        Date time;
        try {
            time = fmt.parse(da);
            DBObject temp = new BasicDBObject();
            temp.put("qqq", time);
            System.out.println(time);
        } catch (ParseException e) {
            log.error(e.getLocalizedMessage(), e);
        }

    }

    /**
     * 指定时间的多少秒后的时间
     * 
     * @param date
     * @return
     * @author Einstein
     */
    public static Date nextSecond(Date date, int second) {

        if (date == null) {
            return date;
        }
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(date);
            cal.add(Calendar.SECOND, second);
            return cal.getTime();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }
        return null;
    }
}
