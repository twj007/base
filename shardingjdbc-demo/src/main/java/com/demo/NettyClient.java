package com.demo;

import com.demo.util.ClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/24
 **/
@Slf4j
public class NettyClient {

    private int port;
    private String host;
    private Channel channel;

    public NettyClient(String host, int port){
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception{
        final EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        log.info("[netty] init channel");
                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        future.addListener(f -> {
            if(f.isSuccess()){
                log.info("【netty】 connect to netty server success");
            }else{
                log.error("【netty】 connect to netty server failed");
                log.error("【netty】 exception is {}", f.cause().getCause());
                group.shutdownGracefully();
            }
        });
        this.channel = future.channel();
    }

    public Channel getChannel(){
        return this.channel;
    }
}
