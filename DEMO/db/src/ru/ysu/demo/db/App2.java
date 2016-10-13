package ru.ysu.demo.db;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by ilavrentev on 13.10.2016.
 */
public class App2 {
    public static void main(String[] args) throws SQLException {
        JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:~/chat", "sa", "");
        pool.setMaxConnections(20);

        Connection c = pool.getConnection();
        c.setAutoCommit(false);
        try {
            c.createStatement().execute("INSERT INTO IDGEN VALUES ('eee', 1000)");
            c.createStatement().execute("INSERT INTO IDGEN VALUES ('fff', 1000)");
            f();
            c.commit();
        } catch(Exception x){
            c.rollback();
        } finally {
            c.close();
        }

    }

    public static void f() throws Exception{
        throw new Exception();
    }
}
