package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * AES加密，CBC模式，zeropadding填充，偏移量为key
 */
public class AESEncrypt {

    private static String ivs = "Ym888pPr888pc888";

    private static String key = "3Cdp488899rC888T";


    /**
     * 加密
     * @since  算法/模式/补码方式:AES/CBC/ZeroPadding
     * @param data
     * @return 加密的数据
     * @doc  ZeroPadding:不足16位的整数倍补0
     */
    public static String encrypt_CBC_ZeroPadding(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String ivString = ivs;
        //偏移量
        byte[] iv = ivString.getBytes();
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
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        //设置偏移量参数
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryped = cipher.doFinal(plaintext);
        return Base64.encodeBase64String(encryped).replaceAll("[\\s*\t\n\r]", "");
    }


    /**
     * 解密
     * @since 算法/模式/补码方式:AES/CBC/ZeroPadding
     * @param data
     * @return
     */
    public static String dencrypt_CBC_ZeroPadding(String data) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException, InvalidKeyException {
        byte[] iv = ivs.getBytes();

        byte[] encryp = Base64.decodeBase64(data);

        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] original = cipher.doFinal(encryp);

        return new String(original).trim();// 解密之后去除加密时补的0（空字符串）
    }
    /**
     * @since  使用对称密钥进行加密  算法/模式/不骂方式:AES/ECB/PKCS5Padding
     * @param key  genKeyString()中生成的key字符
     * @param oriContent    原文（UTF-8编码）
     * @return
     * @throws Exception
     * 注：默认UTF-8编码，加密之后用BASE64进行转码
     */
    public static String  encrpt_ECB_PKCS5Padding(String key,String oriContent) throws Exception{

        byte[] keyb = Base64.decodeBase64(key);// 秘钥进行Base64 解码
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec);// ECB 模式不需要设置iv
        byte[] encrptBytes = cipher.doFinal(oriContent.getBytes("UTF-8"));
        String encreptContent = Base64.encodeBase64String(encrptBytes);
        encreptContent = encreptContent.replaceAll("[\\s*\t\n\r]", "");
        return encreptContent;
    }
    /**
     * 使用对称密钥进行解密
     * @param key genKeyString()中生成的key字节数组
     * @param encrptContent 经过Base64转码之后的密文
     * @throws Exception
     */
    public static String decrpt_ECB_PKCS5Padding(String key,String encrptContent) throws Exception{

        byte[] encrptBytes = Base64.decodeBase64(encrptContent);// 先将密文解码
        byte[] keyb = Base64.decodeBase64(key);// key使用Base64 进行转码
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);// ECB模式不使用iv

        byte[] decrptContent = cipher.doFinal(encrptBytes);
       return new String(decrptContent);
    }
    //测试
    public static void main(String[] args) throws Exception {
        // AES/CBC/ZeroPadding
        String data = "hello world ~,你好，中国发骄傲了房间里撒娇砥砺奋进爱睡懒觉按实际发的垃圾四六级卡视角东方丽景爱上了就发啦老实交代冷风机案例所肩负的两三放两三点冷风机三街坊邻居了就两块三分段了就撒里的解放路卡机是两地分居";

        String encrypt = encrypt_CBC_ZeroPadding(data);
        String desencrypt =dencrypt_CBC_ZeroPadding (encrypt);
        System.out.println("加密后:"+encrypt);
        System.out.println("解密后:"+desencrypt);

//        // AES/ECB/PKCS5Padding
        String ikey = AESUtil.genKeyString();

        String encrptContent = encrpt_ECB_PKCS5Padding(ikey, data);
        System.out.println("AES-加密的数据："+encrptContent);
        String decrptContent = decrpt_ECB_PKCS5Padding(ikey, encrptContent);
        System.out.println("AES-解密的数据："+decrptContent);

    }
}
