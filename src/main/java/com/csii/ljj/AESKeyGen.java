package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESKeyGen {

    private static String ivs = "0392039203920300";
    /**
     * 自动生成AES128位密钥
     */
    public  static String  getA221(){
        String s1 = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128,new SecureRandom("3Cdp48899rC888T".getBytes()));//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();

            String s = byteToHexString(b);
            System.out.println("16进制:"+s);
            System.out.println("十六进制密钥长度为"+s.length());
            System.out.println("二进制密钥的长度为"+s.length()*4);


            s1 = Base64.encodeBase64String(b);
            System.out.println("base64:"+s1);
            System.out.println(s1.length());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
        return s1;
    }

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
                System.out.println("十六进制转byte发生错误！！！");
                e.printStackTrace();
            }
        }
        return baKeyword;
    }

    /**
     * 使用对称密钥进行加密
     */
    public static String  getA231(byte[] keyb) throws Exception{

        String mingwen = "hello word!世界你好";
        System.out.println("原文："+mingwen);//明文
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//向量iv
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec,iv);
        byte[] bjiamihou = cipher.doFinal(mingwen.getBytes("UTF-8"));

        String encreptContent = Base64.encodeBase64String(bjiamihou);
        System.out.println("BASE64 ENCRPT:"+encreptContent);
        return encreptContent;
    }

    /**
     * 解密
     * @param keyb 二进制秘钥
     * @param encrptContent 经过Base64转码之后的密文
     * @throws Exception
     */
    public static void getA232(byte[] keyb,String encrptContent) throws Exception{

        String sjiami = encrptContent;	//密文

        byte[] miwen = Base64.decodeBase64(sjiami);// 先将密文转码
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());//使用CBC模式需要添加向量iv,非CBC可以不用增加向量IV
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec,iv);

        byte[] bjiemihou = cipher.doFinal(miwen);
        System.out.println("Decrpt:"+new String(bjiemihou));
    }

    public static void main(String[] args) throws Exception {

        String key = getA221();
        byte[] bytes = Base64.decodeBase64(key);
        // 加密
        String encrptContent = getA231(bytes);
        // 解密
        getA232(bytes,encrptContent);

    }
}
