package com.company.project;

import com.company.project.common.utils.MD5Util;

public class UserPassword {
    public static void main(String[] args) {

        String username = "admin";
        String password = "s6321";

        System.out.println("password=" + MD5Util.encrypt(username, password));

    }
}
