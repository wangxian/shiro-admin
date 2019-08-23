package com.company.project;

import com.company.project.common.utils.AddressUtil;
import com.company.project.common.utils.MD5Util;

public class UserPassword {
    public static void main(String[] args) {

        String username = "admin";
        String password = "s6321";

        System.out.println("password=" + MD5Util.encrypt(username, password));

        System.out.println("md5(a)=" + MD5Util.MD5("a"));
        System.out.println("md5(111111)=" + MD5Util.MD5("111111"));

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
