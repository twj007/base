import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/06
 **/
public class ConditionLock{



    public static void main(String[] args) throws Exception{
        ConditionTest test = new ConditionTest();

        test.second(new Thread(()->{
            System.out.println("second");
        }));
        test.thrid(new Thread(()->{
            System.out.println("third");
        }));
        test.first(new Thread(()->{
            System.out.println("first");
        }));

    }

}

class ConditionTest{
    private volatile int nextThread = 1;

    public void first(Thread runnable){

            System.out.println(Thread.currentThread().getName());
            runnable.start();
            nextThread = 2;


    }

    public void second(Thread runnable) throws Exception{

            while(nextThread != 2){
                Thread.sleep(1000);
            }

            System.out.println(Thread.currentThread().getName());
            runnable.start();
            nextThread = 3;

    }

    public void thrid(Thread runnable) throws InterruptedException{

            while(nextThread != 3){
                Thread.sleep(1000);
            }

            System.out.println(Thread.currentThread().getName());
            runnable.start();
            nextThread = 4;

    }

}
