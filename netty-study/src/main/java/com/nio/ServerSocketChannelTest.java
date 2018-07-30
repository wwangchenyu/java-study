package com.nio;

import com.nio.thread.ServerSocketHandler;

public class ServerSocketChannelTest {

    public static void main(String[] args) {
        int port = 5000;
        try{
            if(args != null && args.length > 0){
                port = Integer.valueOf(port);
            }
        }catch (NumberFormatException e){

        }
        ServerSocketHandler serverSocketHandler = new ServerSocketHandler(port);
        new Thread(serverSocketHandler).start();
    }

}
