import java.time.Duration;
import java.util.Date;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/06/12
 **/
public class Test {

    public static void main(String[] args) {
        Duration d = Duration.ofMillis(new Date().getTime());
        System.out.println(d);
    }
}
