package ru.ysu.test.db;

import org.h2.jdbcx.JdbcConnectionPool;
import ru.ysu.test.db.exptions.UserAccountLoginAlreadyExists;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by user on 15.10.2016.
 */
public class UAUtils {

    private static final String Q_INSERT =
            "INSERT INTO USERACCOUNT "+
            "( USERACCOUNTID, LOGIN, PASSWORDHASH, ISBLOCKED) VALUES "+
            " (?, ?, ?, ?)";


    public static Long userAccountCreate(String login, String password)
            throws SQLException, UserAccountLoginAlreadyExists {

        if(login==null || "".equals(login) ||
                password==null || "".equals(password)){
            throw new IllegalArgumentException(
                    "Login and password can not be empty");
        }

        //TODO check exist
        //if()... throw new UserAccountLoginAlreadyExists();

        JdbcConnectionPool pool = DBUtils.getPool();
        Connection conn = pool.getConnection();
        PreparedStatement pst = conn.prepareStatement(Q_INSERT);
        Long id = DBUtils.getNewId("USERACCOUNT");
        pst.setLong(1, id);
        pst.setString(2, login);
        pst.setString(3, getDigest(password));
        pst.setLong(4, 0);
        pst.execute();
        pst.close();
        conn.close();
        return id;
    }

    public static String getDigest(String s) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] dgst = md.digest(s.getBytes());
            char[] cd = new char[dgst.length];
            for (int i = 0; i < dgst.length; i++) {
                cd[i] = (char) (dgst[i] + 256);
            }
            return new String(cd);
        } catch(Exception x){
            x.printStackTrace();
        }
        return "xxx";
    }
}
