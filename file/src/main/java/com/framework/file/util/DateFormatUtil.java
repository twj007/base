package com.framework.file.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateFormatUtil {

    public static String getNow(String pattern){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return now.format(formatter);
    }


    public static void main(String[] args) {
        System.out.println(getNow("yyyy-MM-dd"));
    }

}
