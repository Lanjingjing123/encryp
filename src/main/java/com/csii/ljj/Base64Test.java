package com.csii.ljj;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author lanjingjing
 * @description Base64
 * @date 2020/5/31
 */
public class Base64Test {
    public static void main(String[] args) {
        String content = "你好";
        String encode = encode(content);
        String decode = decode(encode);
        System.out.println(encode);
        System.out.println(decode);
    }

    /**
     * 普通字符串进行Base64编码
     * @param content
     * @return
     * 注意：需要将原字符串使用UTF-8编码转换成字节数组
     */
    public static String encode(String content){
        Base64 base64 = new Base64();

        try {
            content = base64.encodeToString(content.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return content;
    }

    /**
     * Base64的字符串进行解析解码
     * @param base64Content base64的字符串
     * @return
     */
    public static String decode(String base64Content){
        String oriContent = null;
        try {
            oriContent = new String(Base64.decodeBase64(base64Content),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return oriContent;
    }
}
