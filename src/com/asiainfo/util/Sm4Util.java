package com.asiainfo.util;

public class Sm4Util {

    private static final int ENCRYPT = 1;
    private static final int DECRYPT = 0;

    public static byte[] encodeSMS4(String plaintext, byte[] key) {
        if (plaintext == null || plaintext.equals("")) {
            return null;
        }
        //将字节补齐到16的整数倍
        for (int i = plaintext.getBytes().length % 16; i < 16; i++) {
            plaintext += '\0';
        }

        return Sm4Util.encodeSMS4(plaintext.getBytes(), key);
    }

    /**
     * 不限明文长度的SMS4加密
     */
    public static byte[] encodeSMS4(byte[] plaintext, byte[] key) {
        byte[] ciphertext = new byte[plaintext.length];

        int k = 0;
        int plainLen = plaintext.length;
        while (k + 16 <= plainLen) {
            byte[] cellPlain = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellPlain[i] = plaintext[k + i];
            }
            byte[] cellCipher = encode16(cellPlain, key);
            for (int i = 0; i < cellCipher.length; i++) {
                ciphertext[k + i] = cellCipher[i];
            }

            k += 16;
        }

        return ciphertext;
    }

    /**
     * 不限明文长度的SMS4解密
     */
    public static byte[] decodeSMS4(byte[] ciphertext, byte[] key) {
        byte[] plaintext = new byte[ciphertext.length];

        int k = 0;
        int cipherLen = ciphertext.length;
        while (k + 16 <= cipherLen) {
            byte[] cellCipher = new byte[16];
            for (int i = 0; i < 16; i++) {
                cellCipher[i] = ciphertext[k + i];
            }
            byte[] cellPlain = decode16(cellCipher, key);
            for (int i = 0; i < cellPlain.length; i++) {
                plaintext[k + i] = cellPlain[i];
            }

            k += 16;
        }

        return plaintext;
    }

    /**
     * 解密，获得明文字符串
     */
    public static String decodeSMS4toString(byte[] ciphertext, byte[] key) {
        byte[] plaintext = decodeSMS4(ciphertext, key);
        System.out.println(ciphertext[0]);
        if(ciphertext[ciphertext.length - 1] == 0){
            int count = 0;
            for(int index = 0,len = ciphertext.length; index < len; index++){
                if(ciphertext[index] == 0){
                    count = index + 1;
                    break;
                }
            }
            byte[] by = new byte[count];
            System.arraycopy(ciphertext,0,by,0,count);
            return new String(by);
        }
        return new String(plaintext);
    }

    /**
     * 只加密16位明文
     */
    private static byte[] encode16(byte[] plaintext, byte[] key) {
        byte[] cipher = new byte[16];
        SM4 sm4 = new SM4();
        sm4.sms4(plaintext, 16, key, cipher, ENCRYPT);

        return cipher;
    }

    /**
     * 只解密16位密文
     */
    private static byte[] decode16(byte[] ciphertext, byte[] key) {
        byte[] plain = new byte[16];
        SM4 sm4 = new SM4();
        sm4.sms4(ciphertext, 16, key, plain, DECRYPT);
        return plain;
    }


    public static void main(String[] args) {
        String accessSecret = "6df02c965a3342eb";
        byte[] key = accessSecret.getBytes();
        String inputStr = "我是 ";
        byte[] enOut = Sm4Util.encodeSMS4(inputStr, key);
        System.out.println(enOut);
        String result = Sm4Util.decodeSMS4toString(enOut,key);
        System.out.println(result);
    }

}
