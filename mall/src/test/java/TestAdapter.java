/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/08/08
 **/
public class TestAdapter {

    public static void main(String[] args) {
        //对象适配器
        Adaptee adaptee = new Adaptee();
        Target adapter = new Adapter(adaptee);
        adapter.doTarget();
        //类适配器
        UsbAdapter adapter1 = new UsbAdapter();
        adapter1.doTarget();
        adapter1.doDefaultMethod();

    }

}

interface Target{

    default void doTarget(){
        System.out.println("default method");
    }

    default void doDefaultMethod(){
        System.out.println("default method");
    }
}

class Usb{

    public void charging(){
        System.out.println("use usb charging");
    }
}

class TypeC{

    public void charging(){
        System.out.println("use usb type c charging");
    }
}

class UsbAdapter extends Usb implements Target{

    @Override
    public void doTarget() {
        System.out.println("switch type C");
        super.charging();
        int i = 0;

    }
}

class Adaptee{

    public void doAction(){
        System.out.println("do action");
    }
}

class Adapter implements Target{

    private Adaptee adaptee;

    public Adapter(Adaptee adaptee){
        this.adaptee = adaptee;
    }

    @Override
    public void doTarget() {
        adaptee.doAction();
    }
}
