package example;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private static BasicDataSource ds = new BasicDataSource();

    static {
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        // remote db
        ds.setUrl("jdbc:mysql://remotemysql.com/sLt3weyB5E");
        ds.setUsername("sLt3weyB5E");
        ds.setPassword("Mzy5UQlFrH");
        ds.setMinIdle(1);
        ds.setMaxIdle(5);
        ds.setMaxOpenPreparedStatements(100);
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    private ConnectionPool() {
    }
}