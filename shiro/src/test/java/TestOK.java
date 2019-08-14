import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.Collectors;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/07/31
 **/
public class TestOK {
    public static void main(String[] args) {
        List<Integer> l = new ArrayList<>();
        l.add(1);l.add(2);l.add(5);l.add(9);l.add(10);

        List<Integer> l2 = Arrays.asList(1, 3, 5, 8, 10);
        l.addAll(l2);
        l = l.stream().distinct().collect(Collectors.toList());
        System.out.println(l);
    }


}
