package com.joe.joespringboot.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NettyClient {
    private final int port;
    private String host;

    public NettyClient(String host, int port){
        this.port = port;
        this.host = host;
    }

    public void run(){
        Bootstrap sb = new Bootstrap();
        NioEventLoopGroup nlg = new NioEventLoopGroup();
        try {
            sb.group(nlg)
                    .channel(NioSocketChannel.class)
                    .handler(new NettyClientChannelInitializer());

            ChannelFuture f       = sb.connect(host, port).sync();
            Channel       channel = f.channel();

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            while (true){
                channel.writeAndFlush(in.readLine() + "\r\n");
            }

        } catch (Exception e) {
            System.out.println("===============com.benlai.bicrm.netty client Exception");
            e.printStackTrace();
        } finally {
            nlg.shutdownGracefully();
        }
    }

    public static void ss(){
        System.out.println(2353536);
    }

    public static void main (String[] args){
        new NettyClient("localhost", 8081).run();
    }
}
