package com.poa.server.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateFormatUtil {

    public static final String FORMAT_STRING_ALL = "E, d MMM, yyyy 'at' h:mm:ss a";
    public static final String FORMAT_STRING_ONLY_M_D_Y = "MMM d, yyyy";

    public static String dateFormat(String pattern, Date date) {
        try {
            return new SimpleDateFormat(pattern, Locale.CANADA).format(date);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("date format error, pattern = [{}]", pattern);
            return "";
        }
    }

}
