package com.joe.joespringboot.netty;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String str) throws Exception {
        System.out.println(str);
    }

}
