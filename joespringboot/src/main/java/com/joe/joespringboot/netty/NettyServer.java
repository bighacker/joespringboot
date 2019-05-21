package com.joe.joespringboot.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private final int port;

    public NettyServer(int port){
        this.port = port;
    }

    public void run(){
        ServerBootstrap sb = new ServerBootstrap();
        NioEventLoopGroup nlg = new NioEventLoopGroup();
        NioEventLoopGroup wnlg = new NioEventLoopGroup();
        try {
            sb.group(nlg, wnlg)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerChannelInitializer())
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE);

            System.out.println("SimpleChatServer 启动了");
            ChannelFuture f = sb.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            System.out.println("===============com.benlai.bicrm.netty Server Exception");
            e.printStackTrace();
        } finally {
            nlg.shutdownGracefully();
            wnlg.shutdownGracefully();
        }
    }

    public static void main (String[] args){
        new NettyServer(8081).run();
    }
}
