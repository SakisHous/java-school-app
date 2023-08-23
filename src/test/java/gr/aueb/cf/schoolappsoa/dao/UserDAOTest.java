package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This unit test class tests the {@link UserDAOImpl}
 * class at Data Access Object Layer.
 *
 * @author Thanasis Chousiadas
 */
public class UserDAOTest {
    private static IUserDAO userDAO;

    /**
     * Before each testing cycle this method deletes
     * all the records from the database and initializes the
     * dependencies.
     *
     * @throws SQLException handles errors for database access error
     *                      or other errors related with the database.
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        userDAO = new UserDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * This method creates dummy users in the database for
     * testing.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    public static void createDummyUsers() throws UserDAOException {
        User user = new User();
        user.setUsername("user-01");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-02");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-03");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-04");
        user.setPassword("123456");
        userDAO.insert(user);
    }

    /**
     * Before each unit test, this method sets up the database in
     * the initial state.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @BeforeEach
    void setUp() throws UserDAOException {
        createDummyUsers();
    }

    /**
     * After each unit test clear all the tables in order to
     * the method {@link #setUp()} insert the data for the initial
     * state.
     *
     * @throws SQLException an exception that provides information on
     *                      a database access error or other errors.
     */
    @AfterEach
    void tearDown() throws SQLException {
        DBHelper.eraseData();
    }

    /**
     * This method tests the {@link UserDAOImpl#insert(User)} method
     * for the dao layer for inserting and retrieving a new user
     * from the database.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void persistAndGetUser() throws UserDAOException {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("123456abc");
        userDAO.insert(user);

        List<User> users = userDAO.getAll();
        assertEquals(5, users.size());
    }

    /**
     * This method tests the {@link UserDAOImpl#update(User)} method
     * for updating an old user.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void update() throws UserDAOException {
        User user = new User();
        user.setId(1L);
        user.setUsername("mockUser");
        user.setPassword("abcd123");
        userDAO.update(user);

        User modifiedUser = userDAO.getById(1L);

        assertEquals(modifiedUser.getUsername(), "mockUser");
    }

    /**
     * This method tests the {@link UserDAOImpl#delete(long)} method
     * for deleting a user from the database.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void delete() throws UserDAOException {
        userDAO.delete(1L);

        List<User> users = userDAO.getAll();

        assertEquals(users.size(), 3);
    }

    /**
     * This method tests the {@link UserDAOImpl#getAll()} method
     * for retrieving all the users.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void getAll() throws UserDAOException {
        userDAO.delete(1L);
        userDAO.delete(2L);

        List<User> users = userDAO.getAll();

        assertEquals(users.size(), 2);
    }

    /**
     * This method tests the exception that is thrown
     * when a duplicate user is inserted.
     */
    @Test
    void uniqueUsername() {
        User user = new User();
        user.setUsername("user-01");
        user.setPassword("123456");
        assertThrows(UserDAOException.class, () -> {
            userDAO.insert(user);
        });
    }

    /**
     * This method tests the {@link UserDAOImpl#getById(long)}
     * method for retrieving a user with an id.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void getById() throws UserDAOException {
        User user = userDAO.getById(1L);

        assertEquals(user.getUsername(), "user-01");
    }

    /**
     * This method test the {@link UserDAOImpl#getByUsername(String)}
     * method for retrieving a user with username equals to the
     * parameter's username.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void getByUsername() throws UserDAOException {
        User user = userDAO.getByUsername("user-01");

        assertEquals(user.getId(), 1);
    }

    /**
     * This method tests the {@link UserDAOImpl#getByUsername(String)}
     * method which retrieves a lis of users where their username
     * begins with the parameter's username.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    void getByUsernameLike() throws UserDAOException {
        List<User> users = userDAO.getByUsernameLike("user");

        assertEquals(users.size(), 4);
    }
}
