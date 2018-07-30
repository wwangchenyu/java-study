package com.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {

    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("/Users/wangchenyu/Desktop/信任中心");
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        int readCount = 0;
        while ((readCount = fileChannel.read(readBuffer)) != -1){
            System.out.print(new String(readBuffer.array(),0,readCount));
            readBuffer.flip();
        }
        fileChannel.close();
    }

}
