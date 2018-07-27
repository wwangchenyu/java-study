package com.netty.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Hello world!
 * @author:wcy
 */
public class Test
{
    public static void main( String[] args ) throws FileNotFoundException,IOException {
//        FileInputStream fileInputStream = new FileInputStream("");
//        FileChannel fileChannel = fileInputStream.getChannel();

        Selector selector = Selector.open();

        SocketChannel socketChannel = null;

        socketChannel.configureBlocking(false);
        socketChannel.register(selector,SelectionKey.OP_WRITE | SelectionKey.OP_READ);

        /*
        selector.select();  //阻塞到至少一个channel就绪
        selector.select(1000);  //阻塞到至少一个channel就绪，或者超时
        selector.selectNow();   //不阻塞，马上返回，如果返回时没有就绪的channel，则返回0
         */

        for (;;){
            selector.select();
            Set<SelectionKey> selectionKeySet = selector.selectedKeys();
            Iterator<SelectionKey> iterable = selectionKeySet.iterator();
            while (iterable.hasNext()){
                SelectionKey selectionKey = iterable.next();
                if (selectionKey.isReadable()){
                    //selectionKey.channel();
                }
                if (selectionKey.isWritable()){
                    //
                }
                iterable.remove();
            }

        }
        /*
        selector.wakeup();

        */


    }
}
