package com.cc.core.utils;

import java.math.BigDecimal;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 数字操作类，类型转换等
 * @author Ron
 * @createTime 2014.08.30
 */
public class NumberUtils {

    public static Log log = LogFactory.getLog(NumberUtils.class);

    /**
     * 生成0~maxValue的随机数
     * 
     * @param maxValue
     * @return
     */
    public static int random(int maxValue) {

        if (maxValue == 0) {
            return 0;
        }
        Random random = new Random();
        if (maxValue < 0) {
            return -(random.nextInt(-maxValue));
        }
        return random.nextInt(maxValue);
    }

    /**
     * String转换long，默认值为0
     * 
     * @param str
     * @return
     */
    public static long stringToLong(String str) {

        try {
            return Long.valueOf(str).longValue();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }

    /**
     * String转换long，默认值为0
     * 
     * @param str
     * @return
     */
    public static int stringToint(String str) {

        try {
            return Integer.valueOf(str).intValue();
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            return 0;
        }
    }

    /**
     * 将字符串转换为BidDecaiml类型 str为空返回0
     * 
     * @param str
     * @return
     */
    public static BigDecimal stringToBigDecimal(String str) {

        try {
            if (StringUtils.isNotBlank(str)) {
                return new BigDecimal(str);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        }

        return new BigDecimal(0);
    }

    // 检查字符串s是否全为数字
    public static boolean checkIsNumbers(String x) {

        if (null == x) {
            return false;
        }
        for (Character c : x.toCharArray()) {
            if (c.compareTo('0') < 0 || c.compareTo('9') > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 数字1,2,3转成中文数字壹,贰,叁
     * 
     * @param x
     * @return
     */
    public static String NumberTransfromCN(String x) {

        String[] unms = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String[] digits = new String[] { "", "拾", "佰", "仟" };
        String[] units = new String[] { "", "[万]", "[亿]", "[万亿]" };
        return transfrom(x, unms, digits, units);
    }

    /**
     * 数字1,2,3转成中文数字一,二,三
     * 
     * @param x
     * @return
     */
    public static String NumberTransfrom(String x) {

        String[] unms = new String[] { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        String[] digits = new String[] { "", "十", "百", "千" };
        String[] units = new String[] { "", "万", "亿", "万亿" };
        return transfrom(x, unms, digits, units);
    }

    /**
     * 数字转成中文数字
     * 
     * @param x
     * @return
     */
    public static String transfrom(String x, String[] unms, String[] digits, String[] units) {

        if (null == x) {
            return "您输入的字符串地址为null！";
        }
        if (0 == x.length()) {
            return "您输入的字符串长度为0，请输入要转换的数字！";
        }
        if (false == checkIsNumbers(x)) {
            return "您输入的字符不都为数字，无法转换！";
        }
        if (x.length() > 16) {
            return "您输入的字符串长度大于16，无法转换！";
        }
        // 去除字符串首部的0，例如：0010->10
        int fm;
        for (fm = 0; fm < x.length(); fm++) {
            if (x.charAt(fm) != '0') {
                break;
            }
        }
        x = x.substring(fm);// 去除完毕

        // 把字符串看作一些组，例如：123456789->1,2345,6789
        String result = "";
        int p = 0;
        int m = x.length() % 4;
        int k = (m > 0 ? x.length() / 4 + 1 : x.length() / 4);
        // 从最左边的那组开始，向右循环
        for (int i = k; i > 0; i--) {
            int len = 4;
            if (i == k && m != 0)// 当i为最左边的那组时，组长度可能有变化
            {
                len = m;
            }
            String s = x.substring(p, p + len);
            int le = s.length();
            for (int j = 0; j < le; j++) {
                int n = java.lang.Integer.parseInt(s.substring(j, j + 1));
                if (0 == n) {
                    if (j < le - 1 && java.lang.Integer.parseInt(s.substring(j + 1, j + 2)) > 0 && !result.endsWith(unms[0])) {// 加零的条件：不为最后一位
                        // &&
                        // 下一位数字大于0
                        // &&
                        // 以前没有加过“零”
                        result += unms[0];
                    }
                } else {
                    if (!(n == 1 && (result.endsWith(unms[0]) || result.length() == 0) && j == le - 2)) {// 处理1013一千零"十三"，1113
                        // 一千一百"一十三"
                        result += unms[n];
                    }
                    result += digits[le - j - 1];
                }
            }
            if (0 != java.lang.Integer.parseInt(s))// 如果这组数字不全是 0 ，则加上单位：万，亿，万亿
            {
                result += units[i - 1];
            }
            p += len;
        }
        return result;
    }
}
