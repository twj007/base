
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/07
 **/
public class TestProxy {

    public static void main(String[] args) {
        MyProxy proxy = new MyProxy();
        TimeLine timer = (TimeLine) proxy.newInstance(new Timer());
        timer.compute();
    }


}

interface TimeLine{
    void compute();
}

class Timer implements TimeLine{
    @Override
    public void compute() {
        System.out.println("compute time line");
    }
}

class MyProxy implements InvocationHandler{

    private Object target;

    public Object newInstance(Object target){
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("do before");
        Object obj = method.invoke(target, args);
        System.out.println("do after");
        return obj;
    }
}
