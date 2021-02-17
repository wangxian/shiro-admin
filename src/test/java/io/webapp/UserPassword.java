package io.webapp;

import io.webapp.common.util.AddressUtil;
import io.webapp.common.util.Md5Util;

public class UserPassword {
    public static void main(String[] args) {

        String username = "admin";
        String password = "s6321";

        System.out.println("password=" + Md5Util.encrypt(username, password));

        System.out.println("md5(a)=" + Md5Util.MD5("a"));
        System.out.println("md5(111111)=" + Md5Util.MD5("111111"));

        // 根据IP解析城市
        System.out.println(AddressUtil.getCityInfo("202.106.0.20"));
        System.out.println(AddressUtil.getCityInfo("127.0.0.1"));

        String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
        System.out.println("java.io.tmpdir=" + tmpDir);

        // 获取dbPath
        String dbPath = AddressUtil.class.getResource("/ip2region/ip2region.db").getPath();
        System.out.println("dbPath=" + dbPath);

    }
}
