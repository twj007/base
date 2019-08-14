package com.framework.web;

import java.util.HashMap;
import java.util.Map;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/09
 **/
public class DynamicPlan {

    private Map map = new HashMap();

    public int climb(int x){
        if(x <= 0){
            return 0;
        }
        else if(x == 1){
            return 1;
        }
        else if(x == 2){
            return 2;
        }
        else if(map.containsKey(x)) {
            return (Integer) map.get(x);
        }else{
            int result = climb(x-2) + climb(x-1);
            map.put(x, result);
            return result;
        }
    }

    public static void main(String[] args) {
        DynamicPlan fb = new DynamicPlan();
        System.out.println(fb.climb(10));
    }
}
