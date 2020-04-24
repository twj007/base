package com.demo;

import com.demo.util.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/24
 **/
@Slf4j
public class NettyServer {

    public void bind(int port) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        final EventLoopGroup masterGroup = new NioEventLoopGroup();
        final EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        log.info("[netty] init channel");
                        socketChannel.pipeline()
                                //.addLast() // 编码
                                //.addLast() // 解码
                                .addLast(new NettyServerHandler()); // 请求处理
                    }
                });
        ChannelFuture future = bootstrap.bind(port).sync();
        if(future.isSuccess()){
            log.info("【netty】 netty server start success in {} ms", stopWatch.getTotalTimeMillis());

        }else{
            log.error("【netty】 netty server start failed in {} ms", stopWatch.getTotalTimeMillis());
            log.error("【netty】 exception is {}", future.cause().getCause());
            masterGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

        stopWatch.stop();

    }
}
