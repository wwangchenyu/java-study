package com.netty.nio;

import com.netty.nio.thread.ServerSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelTest {

    public static void main(String[] args) {
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            Selector selector = Selector.open();
            ServerSocketHandler serverSocketHandler = new ServerSocketHandler(selector);
            Thread serverThread = new Thread(serverSocketHandler);
            serverThread.setDaemon(true);
            serverThread.start();
            serverSocketChannel.bind(new InetSocketAddress(5000));
            while (true){
                System.out.println("!2312313123");
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                selector.wakeup();
                socketChannel.register(selector,SelectionKey.OP_WRITE);
                System.out.println("22222222222");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
