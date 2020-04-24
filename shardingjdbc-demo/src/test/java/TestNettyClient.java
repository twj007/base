import com.demo.NettyClient;
import io.netty.channel.Channel;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/24
 **/
public class TestNettyClient {

    public static void main(String[] args) throws Exception {
        NettyClient client = new NettyClient("127.0.0.1", 8081);
        client.start();
        Channel channel = client.getChannel();
        channel.writeAndFlush("haha baoqingtian");
        channel.read();
    }
}
