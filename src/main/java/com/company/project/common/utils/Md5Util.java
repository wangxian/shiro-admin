package com.company.project.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author ADMIN
 */
public class Md5Util {
    /**
     * MD5
     *
     * @param str 字符串
     * @return MD5后字符串
     */
    public static String MD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md5 = MessageDigest.getInstance("MD5");

            // Calculate Message Digest as bytes
            byte[] digest = md5.digest(str.getBytes("UTF-8"));

            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值,数值从1开始
            // BigInteger会把0省略掉，需补全至32位，重写一个方法将16位数转换为32位数
            return String.format("%032x%n", new BigInteger(1, digest));
        } catch (Exception e) {
            return "";
        }
    }

    private static final String ALGORITH_NAME = "md5";

    private static final int HASH_ITERATIONS = 5;

    /**
     * 生成密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 新密码
     */
    public static String encrypt(String username, String password) {
        String source = StringUtils.lowerCase(username);
        password = StringUtils.lowerCase(password);
        return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(source), HASH_ITERATIONS).toHex();
    }
}
