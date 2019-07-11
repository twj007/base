package com.framework.file;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/04/12
 **/
public class Test {

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-mm-DD hh:MM:ss");
        Date a = sdf.parse("2019-04-12 13:45:50");
        Date b = sdf.parse("2019-04-12 13:45:50");
        int result = a.compareTo(b);
        if(result == -1){
            System.out.println("a < b");
        }else if(result == 0){
            System.out.println("a == b");
        }else if(result == 1){
            System.out.println("a > b");
        }
    }
}
