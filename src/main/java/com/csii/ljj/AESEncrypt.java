package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密，CBC模式，zeropadding填充，偏移量为key
 */
public class AESEncrypt {

    private static String ivs = "Ym888pPr888pc888";

    private static String key = "3Cdp488899rC888T";
    //加密
    public static String encrypt(String data) {
        String ivString = ivs;
        //偏移量
        byte[] iv = ivString.getBytes();
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int length = dataBytes.length;
            //计算需填充长度
            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            //填充
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            for (byte b:plaintext) {
                System.out.println(b);
            }

            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            //设置偏移量参数
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            byte[] encryped = cipher.doFinal(plaintext);

            return Base64.encodeBase64String(encryped);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //解密
    public static String desEncrypt(String data) {

        String ivString = ivs;
        byte[] iv = ivString.getBytes();

        try {
            byte[] encryp = Base64.decodeBase64(data);

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            byte[] original = cipher.doFinal(encryp);
            return new String(original);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //测试
    public static void main(String[] args) {
        String data = "hello world ~,你好，中国";
        String key = "4385095701807607";
        String encrypt = encrypt(data);
        String desencrypt = desEncrypt(encrypt);
        System.out.println("加密后:"+encrypt);
        System.out.println("解密后:"+desencrypt);


        byte[] arr = new byte[10];

        arr[0] = 64;
        arr[1] = 54;
        arr[2] = 0x00;
        for (Byte item:arr ) {
            System.out.println(item);
        }


    }
}
