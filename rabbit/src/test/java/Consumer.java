import java.util.concurrent.DelayQueue;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/06
 **/
public class Consumer implements Runnable{
    private DelayQueue<Message> queue;

    public Consumer(DelayQueue<Message> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                Message msg = queue.take();
                System.out.println("id:{" + msg.getId() + "}content:{"+ msg.getBody()+ "}");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
