package com.demo.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

/***
 **@project: base
 **@description:
 **@Author: twj
 **@Date: 2019/10/24
 **/
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("【netty】 read msg from client: {}", msg);
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("【netty】 read msg complete: {}", ctx);
        super.channelReadComplete(ctx);
    }
}
