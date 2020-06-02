package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

public class AESKeyGen {
    /**
     * 自动生成AES128位密钥
     */
    public  static String  getA221(){
        String s1 = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();

            String s = byteToHexString(b);
            System.out.println("16进制:"+s);
            System.out.println("十六进制密钥长度为"+s.length());
            System.out.println("二进制密钥的长度为"+s.length()*4);

            System.out.println("=============:"+new String(b));
            s1 = Base64.encodeBase64String(b);
            System.out.println("base64:"+s1);
            System.out.println(s1.length());
            String s2 = Base64.encodeBase64URLSafeString(b);
            System.out.println("s2:"+s2);


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
    public static void getA231() throws Exception{
        String keys = "c0e9fcff59ecc3b8b92939a1a2724a44";	//密钥
        byte[] keyb = hexStringToByte(keys);
        String mingwen = "hello word!";							//明文
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);
        byte[] bjiamihou = cipher.doFinal(mingwen.getBytes());
        System.out.println(byteToHexString(bjiamihou));		//加密后数据为ecf0a6bc80dbaf657eac9b06ecd92962
    }

    /**
     * 使用对称密钥进行解密
     */
    public static void getA232() throws Exception{
        String keys = "c0e9fcff59ecc3b8b92939a1a2724a44";	//密钥
        byte[] keyb = hexStringToByte(keys);
        String sjiami = "ecf0a6bc80dbaf657eac9b06ecd92962";	//密文
        byte[] miwen = hexStringToByte(sjiami);
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);
        byte[] bjiemihou = cipher.doFinal(miwen);
        System.out.println(new String(bjiemihou));
    }

    public static void main(String[] args) {

        String key = getA221();
        byte[] bytes = Base64.decodeBase64(key);


    }
}
