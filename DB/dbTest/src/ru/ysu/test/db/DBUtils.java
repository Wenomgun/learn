package ru.ysu.test.db;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by user on 15.10.2016.
 */
public class DBUtils {

    public static JdbcConnectionPool getPool() {
        return pool;
    }

    private static JdbcConnectionPool pool;


    private static final Long INIT_ID = 1000L;


    private static final String GETID_SELECT_BY_TABLE =
            "SELECT NEXTID as \"NEXTID\" " +
            "FROM IDGEN "+
            "WHERE TABLENAME = ?";

    private static final String GETID_UPDATE_BY_TABLE =
            "UPDATE IDGEN SET " +
                    "NEXTID = NEXTID + ? "+
                    "WHERE TABLENAME = ? ";

    private static final String GETID_INSERT_BY_TABLE =
            "INSERT INTO IDGEN " +
                    "(TABLENAME, NEXTID) VALUES "+
                    "(?, ?) ";

    static {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        pool = JdbcConnectionPool.create("jdbc:h2:~/mydb", "sa", "");
        pool.setMaxConnections(20);
    }

    public static Long getNewId(String tableName) throws SQLException {
        return getNewId(tableName, 1);
    }

    public static Long getNewId(String tableName, int batchSize) throws SQLException {
        tableName = tableName.toUpperCase();
        Connection conn = pool.getConnection();
        PreparedStatement selSt =
                conn.prepareStatement(GETID_SELECT_BY_TABLE);
        selSt.setString(1, tableName);
        ResultSet rsSel = selSt.executeQuery();
        Long result;
        if(rsSel.next()){
            // взять пришедший ид*
            Long id = rsSel.getLong("NEXTID");
            // нарастить на batchSize
            PreparedStatement stUpd = conn.prepareStatement(GETID_UPDATE_BY_TABLE);
            stUpd.setLong(1, batchSize);
            stUpd.setString(2, tableName);
            // обновить по tableName
            stUpd.execute();
            // вернуть ид*
            result = id;
            stUpd.close();
        } else {
            // втавить по tableName INIT_ID+batchSize
            PreparedStatement stIns = conn.prepareStatement(GETID_INSERT_BY_TABLE);
            stIns.setString(1, tableName);
            stIns.setLong(2, INIT_ID + batchSize);
            stIns.execute();
            // вернуть INIT_ID
            result = INIT_ID;
            stIns.close();
        }
        selSt.close();
        conn.close();
        return result;
    }
}
