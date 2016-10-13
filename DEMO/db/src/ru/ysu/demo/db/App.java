package ru.ysu.demo.db;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.*;

/**
 * Created by ilavrentev on 13.10.2016.
 */
public class App {

    static{
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {

        JdbcConnectionPool pool = JdbcConnectionPool.create("jdbc:h2:~/chat", "sa", "");
        pool.setMaxConnections(20);
        Long  start1 = System.nanoTime();
        for(int i = 1; i<1000; i++) {

            Connection conn = pool.getConnection();
            //conn.setTransactionIsolation(TRA);
            Statement sttmnt = conn.createStatement();
            ResultSet rs = sttmnt.executeQuery("SELECT * FROM IDGEN");
            /*while (rs.next()) {
                System.out.println(
                        rs.getString(1) + " " +
                                rs.getString(2)
                );
            }*/
            sttmnt.close();
            conn.close();
        }
        Long  end1 = System.nanoTime();
        pool.dispose();

        Long  start2 = System.nanoTime();
        for(int i = 1; i<1000; i++) {

            Connection conn = DriverManager.getConnection("jdbc:h2:~/chat", "sa", "");
            Statement sttmnt = conn.createStatement();
            ResultSet rs = sttmnt.executeQuery("SELECT * FROM IDGEN");
            /*while (rs.next()) {
                System.out.println(
                        rs.getString(1) + " " +
                                rs.getString(2)
                );
            }*/
            sttmnt.close();
            conn.close();
        }
        Long  end2 = System.nanoTime();


        System.out.println(end1-start1);
        System.out.println(end2-start2);
        System.out.println(((double)end2-start2) / ((double)end1-start1));



    }
}
