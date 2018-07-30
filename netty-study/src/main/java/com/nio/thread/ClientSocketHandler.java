package com.nio.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class ClientSocketHandler implements Runnable{

    private String host;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private boolean stop;

    public ClientSocketHandler(String host,int port){
        this.host = host;
        this.port = port;
        try{
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            this.stop = false;
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            doConnect();
        }catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        try {
            while(!stop){
                selector.select(1000);
                Iterator<SelectionKey> itSelectedKeys = selector.selectedKeys().iterator();
                while (itSelectedKeys.hasNext()){
                    SelectionKey selectionKey = itSelectedKeys.next();
                    itSelectedKeys.remove();
                    try{
                        handlerSelectionKey(selectionKey);
                    }catch (IOException e){
                        if(selectionKey != null){
                            selectionKey.cancel();
                            if(selectionKey.channel() != null){
                                selectionKey.channel().close();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void handlerSelectionKey(SelectionKey selectionKey) throws IOException{
        if(selectionKey.isValid()){
            SocketChannel socketChannel = (SocketChannel)selectionKey.channel();
            if(selectionKey.isConnectable()){
                if(socketChannel.finishConnect()){
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    doWrite();
                }else{
                    System.exit(1);
                }
            }
            if(selectionKey.isReadable()){
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readCount = socketChannel.read(byteBuffer);
                if(readCount > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String response = new String(bytes,"utf-8");
                    System.out.println("服务器端返回消息:" + response);
                    this.stop = true;
                }else if (readCount < 0){
                    selectionKey.cancel();
                    socketChannel.close();
                }else{

                }
            }
        }

    }

    private void doConnect() throws IOException{
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite();
        }else{
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite() throws IOException{
        Scanner in = new Scanner(System.in);
        String inputStr = in.nextLine();
        byte[] bytes = inputStr.getBytes();
        ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
        byteBuffer.put(bytes);
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
    }
}
