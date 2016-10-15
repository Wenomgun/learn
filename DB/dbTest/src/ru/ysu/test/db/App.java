package ru.ysu.test.db;

import ru.ysu.test.db.exptions.UserAccountLoginAlreadyExists;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by user on 15.10.2016.
 */
public class App {

    public static void main(String[] args) throws SQLException, UserAccountLoginAlreadyExists {
        System.out.println(UAUtils.userAccountCreate("iiiilavrentev","12345678"));
    }
}
