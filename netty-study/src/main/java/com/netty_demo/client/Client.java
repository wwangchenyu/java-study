package com.netty_demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

import java.util.Scanner;

public class Client {

    public void connect(String host,int port){
        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //以下两行代码解决TCP粘包/拆包问题
//                        socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
                        //ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                        socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(3));
                        socketChannel.pipeline().addLast(new StringDecoder());

                        socketChannel.pipeline().addLast(new ClientHandler());
                    }
                });
        try{
            ChannelFuture future = bootstrap.connect(host,port).sync();
            future.channel().closeFuture().sync();
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connect("127.0.0.1",5000);
    }
}
