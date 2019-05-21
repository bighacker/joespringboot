package com.joe.joespringboot.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline cp = sc.pipeline();
        cp.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
        cp.addLast("decoder", new StringDecoder());
        cp.addLast("encoder", new StringEncoder());
        cp.addLast("handler", new NettyServerHandler());
//        cp.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
//        cp.addLast("chunkedWriter", new ChunkedWriteHandler());
//        cp.addLast("deflater", new HttpContentCompressor());

        System.out.println("[SERVER] - " + sc.remoteAddress() + " 连接上 \n");
    }

}
