package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author lanjingjing
 * @description AES
 * @date 2020/5/31
 *
 *
 * 此加解密仅涉及：AES/CBC/PKCS5Padding
 *              AES/ECB/ZeroPadding
 *
 *
 * 注：CBC 模式 需要设置 key(秘钥) ,iv（初始化向量）
 *    ECB 模式 仅需要设置key(秘钥),无需设置 iv
 *
 *  特殊说明：补码方式-ZeroPadding：java中无此补码方式，原理是将分组密码不足分组长度的整数倍时以0填充
 *
 *
 *
 */
public class AESUtil {

    /**
     * 初始化向量
     */
    private static String ivs = "Ym888pPr888pc888";
    /**
     * 秘钥
     */
    private static String key = "3Cdp488899rC888T";

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

//        byte[] keyb = Base64.decodeBase64(key);// 秘钥进行Base64 解码
        byte[] keyb = key.getBytes();
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
//        byte[] keyb = Base64.decodeBase64(key);// key使用Base64 进行转码行转码
        byte[] keyb = key.getBytes();
        SecretKeySpec sKeySpec = new SecretKeySpec(keyb, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);// ECB模式不使用iv

        byte[] decrptContent = cipher.doFinal(encrptBytes);
        return new String(decrptContent);
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



        // AES/CBC/ZeroPadding 进行加解密
        String data = "hello world ~,你好，中国发骄傲了房间里撒娇砥砺奋进爱睡懒觉按实际发的垃圾四六级卡视角东方丽景爱上了就发啦老实交代冷风机案例所肩负的两三放两三点冷风机三街坊邻居了就两块三分段了就撒里的解放路卡机是两地分居";
        String encrypt = encrypt_CBC_ZeroPadding(data);
        String desencrypt =dencrypt_CBC_ZeroPadding (encrypt);
        System.out.println("AES/CBC/ZeroPadding-加密后:"+encrypt);
        System.out.println("AES/CBC/ZeroPadding-解密后:"+desencrypt);

//        // AES/ECB/PKCS5Padding 进行加解密 这里使用随机生成的key
        String ikey = AESUtil.genKeyString();
//        String ikey = "abcdefghjdlmnhjd";
        System.out.println("abcdefghjdlmnhjd");
        String encrptContent3 = encrpt_ECB_PKCS5Padding(ikey, data);
        System.out.println("AES/ECB/PKCS5Padding -加密的数据："+encrptContent3);
        String decrptContent = decrpt_ECB_PKCS5Padding(ikey, encrptContent3);
        System.out.println("AES/ECB/PKCS5Padding -解密的数据："+decrptContent);



    }
}