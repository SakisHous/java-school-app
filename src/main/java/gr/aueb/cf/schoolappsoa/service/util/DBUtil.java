package gr.aueb.cf.schoolappsoa.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class is a utility class and provides
 * connections with the database using
 * a JDBC implementation for MySQL database.
 *
 * @author Thanasis Chousiadas
 */
public class DBUtil {
    private static final BasicDataSource ds = new BasicDataSource();
    private static Connection connection;

    static {
        ds.setUrl("jdbc:mysql://localhost:3306/schooldbpro?serverTimezone=UTC");
        ds.setUsername("schoolpro_user");
        ds.setPassword(System.getenv("SCHOOL_PRO_PASSWD"));
        ds.setInitialSize(8);
        ds.setMaxTotal(32);
        ds.setMinIdle(8);
        ds.setMaxIdle(10);
        ds.setMaxOpenPreparedStatements(100);
    }

    /**
     * No instances of this class should be available.
     */
    private DBUtil() {
    }

    /**
     * Returns a new {@link Connection} type object with
     * the database.
     *
     * @return a {@link Connection} type object.
     * @throws SQLException an exception that handles errors
     *                      with database connection.
     */
    public static Connection getConnection() throws SQLException {
        connection = ds.getConnection();
        return connection;
    }

    /**
     * This method closes a connection and
     * frees up the resources.
     */
    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
