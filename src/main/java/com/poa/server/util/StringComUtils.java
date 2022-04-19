package com.poa.server.util;


import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

public class StringComUtils extends org.apache.commons.lang3.StringUtils {

    private static final char SEPARATOR = '_';

    private static final String UNKNOWN = "unknown";


    /**
     * get ip address
     */
    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            // get ip
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }


    /**
     * get week of day
     */
    public static String getWeekDay() {
        String[] weekDays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }



    /**
     * get local ip
     */
    public static String getLocalIp() {
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            return "unknown";
        }
        byte[] ipAddr = addr.getAddress();
        StringBuilder ipAddrStr = new StringBuilder();
        for (int i = 0; i < ipAddr.length; i++) {
            if (i > 0) {
                ipAddrStr.append(".");
            }
            ipAddrStr.append(ipAddr[i] & 0xFF);
        }
        return ipAddrStr.toString();
    }

    /**
     * Generate verify code
     */
    public static String randomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }

    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }

    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomLowerString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "abcdefghijklmnopqrstuvwxyz";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }

    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomUpperString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }


    /**
     * generate active code
     *
     * @param count
     * @return
     * @throws Exception
     */
    public static String randomNumberString(int count) {
        StringBuilder builder = new StringBuilder();
        String s = "1234567890";
        char[] c = s.toCharArray();
        Random random = new Random();
        for( int i = 0; i < count; i ++) {
            builder.append(c[random.nextInt(c.length)]);
        }

        return builder.toString();
    }

    /**
     * Get variables recursively
     *
     * @param temp          string with variables
     * @param variableMap   result map
     */
    public static void getFirstVariable(String temp, Map<String, String> variableMap) {
        String variable = "";
        String prefix = "${";
        if (!temp.contains(prefix)) {
            return;
        }
        // 获取变量的位置
        int prefixIndex = temp.indexOf(prefix);
        temp = temp.substring(prefixIndex + 2);
        int suffixIndex = temp.indexOf("}");
        variable = temp.substring(0, suffixIndex);
        variableMap.put(prefix + variable + "}", variable);

        getFirstVariable(temp, variableMap);
    }
}
