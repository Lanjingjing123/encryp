package com.csii.ljj;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author lanjingjing
 * @description md5
 * @date 2020/5/31
 */
public class MD5 {
    /*
     * 1.一个运用基本类的实例
     * MessageDigest 对象开始被初始化。该对象通过使用 update 方法处理数据。
     * 任何时候都可以调用 reset 方法重置摘要。
     * 一旦所有需要更新的数据都已经被更新了，应该调用 digest 方法之一完成哈希计算。
     * 对于给定数量的更新数据，digest 方法只能被调用一次。
     * 在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
     */
    public static void  encrypByMd5(String context) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(context.getBytes());//update处理
            byte [] encryContext = md.digest();//调用该方法完成计算

            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < encryContext.length; offset++) {//做相应的转化（十六进制）
                i = encryContext[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32result: " + buf.toString());// 32位的加密
            System.out.println("16result: " + buf.toString().substring(8, 24));// 16位的加密
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * 2.使用开发的jar直接应用
     *  使用外部的jar包中的类：import org.apache.commons.codec.digest.DigestUtils;
     *  对上面内容的一个封装使用方便
     *  返回16位
     */
    public static String encrypByMd5Jar(String context) {
        String md5Str = DigestUtils.md5Hex(context);

        System.out.println("32resultByjar: " + md5Str.toUpperCase().substring(8,24));
        return md5Str.toUpperCase().substring(8,24);
    }

    public static void main(String[] args) throws Exception {
        String str = "utf-8";
        byte[] bytes = str.getBytes("utf-8");
//        try {
//            System.out.println("你好牛".getBytes("UTF-8"));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
        MD5 md5 = new MD5();

        md5.encrypByMd5("nihaoma");
        md5.encrypByMd5Jar("nihaoma");
    }
}
