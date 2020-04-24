import com.demo.NettyServer;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/24
 **/
public class TestNetty {

    public static void main(String[] args) throws Exception {
        new NettyServer().bind(8081);
    }
}
