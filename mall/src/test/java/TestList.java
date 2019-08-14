import com.google.common.base.Stopwatch;


import java.util.*;
import java.util.concurrent.TimeUnit;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/13
 **/
public class TestList {

    public static void main(String[] args) {
        Stopwatch stopWatch = Stopwatch.createStarted();
        stopWatch.elapsed(TimeUnit.MILLISECONDS);
        System.out.println(stopWatch.isRunning());
        stopWatch.stop();
        List l = new ArrayList();
        l.add(1);l.add(3);l.add(5);
        List l2 = new ArrayList();
        l2.add(2);l2.add(3);l2.add(4);
        Map m = new HashMap();
        for(Object i : l){
            if(!m.containsKey(i)){
                m.put(i, i);
            }
        }
        for(Object j : l2){
            if(!m.containsKey(j)){
                m.put(j, j);
            }
        }
        System.out.println(m.keySet());


    }
}
