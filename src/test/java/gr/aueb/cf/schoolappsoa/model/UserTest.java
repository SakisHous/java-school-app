package gr.aueb.cf.schoolappsoa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This unit test class tests the
 * {@link User} model class where
 * User Entity is declared.
 *
 * @author Thanasis Chousiadas
 */
public class UserTest {

    /**
     * This method tests the getters and setters
     * for each attribute of the {@link User} class.
     */
    @Test
    void gettersSetters() {
        User user = new User();
        user.setId(1L);
        user.setUsername("user-01");
        user.setPassword("123456abc");

        assertEquals(user.getId(), 1L);
        assertEquals(user.getUsername(), "user-01");
        assertEquals(user.getPassword(), "123456abc");
    }

    /**
     * This class tests the constructor of
     * {@link User} class.
     */
    @Test
    void overloadedConstructor() {
        User user = new User(1L, "user-01", "123456abc");

        assertEquals(user.getId(), 1L);
        assertEquals(user.getUsername(), "user-01");
        assertEquals(user.getPassword(), "123456abc");
    }
}


