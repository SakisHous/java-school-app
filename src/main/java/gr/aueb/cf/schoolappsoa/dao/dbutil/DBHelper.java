package gr.aueb.cf.schoolappsoa.dao.dbutil;

import gr.aueb.cf.schoolappsoa.service.util.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a utility class which clears the database.
 *
 * @author Thanasis Chousiadas
 */
public class DBHelper {

    /**
     * Private constructor. No instances of this class should not be allowed.
     */
    private DBHelper() {
    }

    public static void eraseData() throws SQLException {
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = DBUtil.getConnection();
            conn.prepareStatement("SET @@foreign_key_checks = 0").executeUpdate();

            rs = conn.prepareStatement("SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = 'schooldbpro'").executeQuery();

            List<String> tables = mapRSToList(rs);

            for (String table : tables) {
                conn.prepareStatement("DELETE FROM " + table).executeUpdate();
                conn.prepareStatement("ALTER TABLE " + table + " AUTO_INCREMENT = 1").executeUpdate();

            }
            conn.prepareStatement("SET @@foreign_key_checks = 1").executeUpdate();
        } finally {
            try {
                if (rs != null) rs.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static List<String> mapRSToList(ResultSet rs) throws SQLException {
        List<String> list = new ArrayList<>();

        while (rs.next()) {
            list.add(rs.getString("TABLE_NAME"));
        }

        return list;
    }
}
