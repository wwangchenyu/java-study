package com.asiainfo.util;

public class Test {

    public static void main(String[] args) {
//        byte a = 0xa;
//        int b = 10;
//        System.out.println(a == b);
        String accessSecret = "6df02c965a3342eb";

        System.out.println(accessSecret.getBytes().length * 8);

        System.out.println(Integer.toHexString(127));

        System.out.println(Integer.toBinaryString((127 & 0Xf0) >> 4));   //左移4位取字节的高4位

        System.out.println(Integer.toBinaryString(127 & 0x0f)); //获取低4位

    }
}
