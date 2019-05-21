package com.joe.joespringboot.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline cp = sc.pipeline();
        cp.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        cp.addLast("decoder", new StringDecoder());
        cp.addLast("encoder", new StringEncoder());
        cp.addLast("handler", new NettyClientHandler());

    }

}
