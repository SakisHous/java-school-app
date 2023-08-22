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

public class UserDAOTest {
    private static IUserDAO userDAO;

    @BeforeAll
    public static void setupClass() throws SQLException {
        userDAO = new UserDAOImpl();
        DBHelper.eraseData();
    }

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

    @BeforeEach
    void setUp() throws UserDAOException {
        createDummyUsers();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DBHelper.eraseData();
    }

    @Test
    void persistAndGetUser() throws UserDAOException {
        User user = new User();
        user.setUsername("newUser");
        user.setPassword("123456abc");
        userDAO.insert(user);

        List<User> users = userDAO.getAll();
        assertEquals(5, users.size());
    }

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

    @Test
    void delete() throws UserDAOException {
        userDAO.delete(1L);

        List<User> users = userDAO.getAll();

        assertEquals(users.size(), 3);
    }

    @Test
    void getAll() throws UserDAOException {
        userDAO.delete(1L);
        userDAO.delete(2L);

        List<User> users = userDAO.getAll();

        assertEquals(users.size(), 2);
    }

    @Test
    void uniqueUsername() {
        User user = new User();
        user.setUsername("user-01");
        user.setPassword("123456");
        assertThrows(UserDAOException.class, () -> {
            userDAO.insert(user);
        });
    }

    @Test
    void getById() throws UserDAOException {
        User user = userDAO.getById(1L);

        assertEquals(user.getUsername(), "user-01");
    }

    @Test
    void getByUsername() throws UserDAOException {
        User user = userDAO.getByUsername("user-01");

        assertEquals(user.getId(), 1);
    }

    @Test
    void getByUsernameLike() throws UserDAOException {
        List<User> users = userDAO.getByUsernameLike("user");

        assertEquals(users.size(), 4);
    }
}
