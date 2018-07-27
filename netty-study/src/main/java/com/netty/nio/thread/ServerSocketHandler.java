package com.netty.nio.thread;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketHandler implements Runnable{

    private Selector selector = null;

    public ServerSocketHandler(Selector selector){
        this.selector = selector;
    }

    @Override
    public void run() {
        for (;;){
            try {
                selector.select();
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterable = selectionKeySet.iterator();
                while (iterable.hasNext()){
                    SelectionKey selectionKey = iterable.next();
                    if (selectionKey.isWritable()){
                        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
//                        socketChannel.write(socketChannel.re)
                        System.out.println("有数据写出");
                    }
                    if (selectionKey.isReadable()) {
                        System.out.println("有数据写入");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
