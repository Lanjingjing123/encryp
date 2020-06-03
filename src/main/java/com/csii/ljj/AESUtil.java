package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author lanjingjing
 * @description AES
 * @date 2020/5/31
 */
public class AESUtil {

    private static String ivs = "0392039203920300";


    /**
     * 二进制byte[]转十六进制string
     */
    public static String byteToHexString(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3){
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2){
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return  sb.toString();
    }

    /**
     * 十六进制string转二进制byte[]
     */
    public static byte[] hexStringToByte(String s) {
        byte[] baKeyword = new byte[s.length() / 2];
        for (int i = 0; i < baKeyword.length; i++) {
            try {
                baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
            } catch (Exception e) {
                System.out.println("十六进制转byte发生错误！！！");// TODO
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * 随机生成AES128位密钥字符串
     * 注：秘钥经过Base64.encode,使用该秘钥进行加解密时需要
     */
    public  static String  genKeyString(){
        String key = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128,new SecureRandom());//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            key = Base64.encodeBase64String(b);
            key = key.replaceAll("[\\s*\t\n\r]", "");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");//TODO
        }
        return key;
    }

    /**
     * 生成128位秘钥数组
     * @return
     */
    public  static byte[]  genKeyBytes(){
        byte[] keyBytes= new byte[128];
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128,new SecureRandom());//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            keyBytes = sk.getEncoded();


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");//TODO
        }
        return keyBytes;
    }



    /**
     *  使用对称密钥进行加密
     * @param keyb  genKeyBytes()中生成的key字节数组
     * @param oriContent    原文（UTF-8编码）
     * @return
     * @throws Exception
     * 注：默认UTF-8编码，加密之后用BASE64进行转码
     */
    public static String  encrpt(byte[] keyb,String oriContent) throws Exception{

        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//向量iv，使用CBC模式需要指定iv

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,iv);
        byte[] encrptBytes = cipher.doFinal(oriContent.getBytes("UTF-8"));
        String encreptContent = Base64.encodeBase64String(encrptBytes);
        encreptContent = encreptContent.replaceAll("[\\s*\t\n\r]", "");
        return encreptContent;
    }

    /**
     *  使用对称密钥进行加密
     * @param key  genKeyString()中生成的key字符
     * @param oriContent    原文（UTF-8编码）
     * @return
     * @throws Exception
     * 注：默认UTF-8编码，加密之后用BASE64进行转码
     */
    public static String  encrpt(String key,String oriContent) throws Exception{

        byte[] keyb = Base64.decodeBase64(key);// 秘钥进行Base64 解码

        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//向量iv，使用CBC模式需要指定iv

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,iv);
        byte[] encrptBytes = cipher.doFinal(oriContent.getBytes("UTF-8"));
        String encreptContent = Base64.encodeBase64String(encrptBytes);
        encreptContent = encreptContent.replaceAll("[\\s*\t\n\r]", "");
        return encreptContent;
    }

    /**
     * 使用对称密钥进行解密
     * @param keyb genKeyBytes()中生成的key字节数组
     * @param encrptContent 经过Base64转码之后的密文
     * @throws Exception
     */
    public static void decrpt(byte[] keyb,String encrptContent) throws Exception{

        byte[] encrptBytes = Base64.decodeBase64(encrptContent);// 先将密文解码
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//使用CBC模式需要添加向量iv,非CBC可以不用增加向量IV

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec,iv);

        byte[] decrptContent = cipher.doFinal(encrptBytes);
        System.out.println("Decrpt:"+new String(decrptContent));
    }


    /**
     * 使用对称密钥进行解密
     * @param key genKeyString()中生成的key字节数组
     * @param encrptContent 经过Base64转码之后的密文
     * @throws Exception
     */
    public static void decrpt(String key,String encrptContent) throws Exception{

        byte[] encrptBytes = Base64.decodeBase64(encrptContent);// 先将密文解码
        byte[] keyb = Base64.decodeBase64(key);// key使用Base64 进行转码


        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//使用CBC模式需要添加向量iv,非CBC可以不用增加向量IV

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec,iv);

        byte[] decrptContent = cipher.doFinal(encrptBytes);
        System.out.println("Decrpt:"+new String(decrptContent));
    }
    public static void main(String[] args) throws Exception {

        String oriContent = "hello world! 你好世界";
        /* 方法1：使用生成key字节数组进行加解密*/
        byte[] keyBytes = genKeyBytes();// 生成key字节数组
        String encrptContent = encrpt(keyBytes, oriContent);
        System.out.println("encrpt:"+encrptContent);
        decrpt(keyBytes,encrptContent);

        /* 方法2：使用生成的key字符串进行加解密*/
        String key = genKeyString();
        String encrptContent2 = encrpt(key, oriContent);
        System.out.println("encrpt2:"+encrptContent2);
        decrpt(key,encrptContent2);


    }
}