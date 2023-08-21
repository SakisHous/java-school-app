package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.util.DBUtil;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Public API of
 * the {@link IUserDAO} interface.
 *
 * @author Thanasis Chousiadas
 */
public class UserDAOImpl implements IUserDAO {

    /**
     * This method inserts a new record in the Users table.
     *
     * @param user          a {@link User} object, to be inserted.
     * @return              the inserted {@link User} object.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public User insert(User user) throws UserDAOException {
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());

            int n = ps.executeUpdate();

            if (n >= 1) {
//                JOptionPane.showMessageDialog(null, n + " rows affected", "Successfully Insert", JOptionPane  .PLAIN_MESSAGE);
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in User Insert " + user);
        }
    }

    /**
     * This method updates an old record in the Users table
     * with the new one.
     *
     * @param user          a {@link User} object.
     * @return              the updated {@link User} object.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public User update(User user) throws UserDAOException {
        String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setLong(3, user.getId());
            int n = ps.executeUpdate();

            if (n >= 1) {
//                JOptionPane.showMessageDialog(null, "Successful Update", "Update", JOptionPane.PLAIN_MESSAGE);
                return user;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in User Update " + user);
        }
    }

    /**
     * This method deletes a record from the Users table,
     * where the primary key is equal to the id given by the
     * client.
     *
     * @param id            the primary key given by the client.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public void delete(long id) throws UserDAOException
    {
        String sql = "DELETE FROM USERS WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            int n = ps.executeUpdate();
            if (n == 1) {
                System.out.println("...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in User delete with id " + id);
        }
    }

    /**
     * This method returns all the records from the
     * Users table.
     *
     * @return              an {@link ArrayList} of {@link User} objects.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public List<User> getAll() throws UserDAOException
    {
        String sql = "SELECT * FROM USERS";
        List<User> users = new ArrayList<>();
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getLong("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in Users, retrieve all users");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return users;
    }

    /**
     * This method returns the record where the primary key is
     * the same with the id given by the client.
     *
     * @param id            the id provided by the client.
     * @return              a {@link User} with the id.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public User getById(long id) throws UserDAOException
    {
        String sql = "SELECT * FROM USERS WHERE ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getLong("ID"),
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * This method returns the record from the Users
     * table where the username is equal to username provided
     * by the client.
     *
     * @param username      the username for searching in the database.
     * @return              a {@link User} with the username as given by the
     *                      client or null if not found.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public User getByUsername(String username) throws UserDAOException
    {
        String sql = "SELECT * FROM USERS WHERE USERNAME = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new User(
                    rs.getLong("ID"),
                    rs.getString("USERNAME"),
                    rs.getString("PASSWORD")
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in Users: No records found with username = " + username);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * This method returns the records from Users table
     * where their usernames begin with the username parameter
     * given by the client.
     *
     * @param username      the parameter for searching the usernames
     *                      in Users table.
     * @return              a {@link ArrayList} with {@link User} objects,
     *                      otherwise null.
     * @throws UserDAOException
     *                      if an error is occurred, this wrapper
     *                      exception to {@link SQLException} will be thrown.
     */
    @Override
    public List<User> getByUsernameLike(String username) throws UserDAOException {
        String sql = "SELECT * FROM USERS WHERE USERNAME LIKE ?";
        ResultSet rs = null;
        List<User> users = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, username + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(
                        rs.getLong("ID"),
                        rs.getString("USERNAME"),
                        rs.getString("PASSWORD")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UserDAOException("SQL Error in Users: No records found with username = " + username);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
