import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/10
 **/
public class DaemonTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new Thread1());
        thread.start();
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start timer");
                while(true) {
                }
            }
        };
        timer.schedule(task, 1000, 10000);
    }

}

/***
 * timer的实现，实际通过创建一条新的线程去执行任务
 *
 */
class TimerRunner implements Runnable{

    private Long wait_time;

    @Override
    public void run() {

        while(true){
            doSomething();
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doSomething(){

    }
}

class DeamonThread implements Runnable{

    @Override
    public void run() {
        while(true){
            System.out.println(Thread.currentThread().getName() + " daemon start"+ LocalDateTime.now().getSecond());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Thread1 implements Runnable{

    private final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public void run() {
//        Thread daemon = new Thread(new DeamonThread());
//        daemon.setDaemon(true);
//        daemon.start();
        while (counter.get() < 100){
            counter.incrementAndGet();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ": " +counter.get());
    }
}