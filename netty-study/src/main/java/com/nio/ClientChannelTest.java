package com.nio;

import com.nio.thread.ClientSocketHandler;

public class ClientChannelTest {

    public static void main(String[] args) {
        ClientSocketHandler clientSocketHandler = new ClientSocketHandler("localhost",5000);
        new Thread(clientSocketHandler).start();
    }

}
