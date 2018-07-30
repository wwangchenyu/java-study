package com.nio.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class ServerSocketHandler implements Runnable{

    private Selector selector = null;
    ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public ServerSocketHandler(int port){
        try{
            stop = false;
            this.selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);
        }catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        while (!stop){
            try {
                int num = selector.select(1000);
                if(num < 1){
                    continue;
                }
                Iterator<SelectionKey> itSelectionKeys = selector.selectedKeys().iterator();
                while (itSelectionKeys.hasNext()){
                    SelectionKey selectionKey = itSelectionKeys.next();
                    itSelectionKeys.remove();
                    handlerSelectionKey(selectionKey);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(selector != null){
            try{
                selector.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void handlerSelectionKey(SelectionKey selectionKey) throws IOException{
        if(selectionKey.isValid()){
            if(selectionKey.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel)selectionKey.channel();
                SocketChannel socketChannel = ssc.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_READ);
            }
            if (selectionKey.isReadable()){
                SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readCount = socketChannel.read(byteBuffer);
                /**
                 *  readCount > 0:读到内容
                 *  返回值为-1，链路已经关闭，需要关闭SocketChannel,释放资源
                 *  没有读取到字节，正常情况，不做处理
                 */
                if (readCount > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes,"utf-8");
                    doResponse(socketChannel,"\t服务端返回数据：" + body + "123456");
                }else if (readCount < 0){
                    selectionKey.cancel();
                    socketChannel.close();
                }else {

                }
            }
        }
    }

    private void doResponse(SocketChannel socketChannel, String body) throws IOException{
        if(body != null && body.trim().length() > 0){
            byte[] bytes = body.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        }
    }
}
