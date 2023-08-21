package gr.aueb.cf.schoolappsoa.model;

/**
 * It declares the User Entity which represents
 * the Users table in the database.
 *
 * @author Thanasis Chousiadas
 */
public class User {
    private Long id;
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public User() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id       the primary key of the Entity.
     * @param username the username of the User Entity.
     * @param password the password of the User Entity.
     */
    public User(
            Long id,
            String username,
            String password
    ) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for the id.
     *
     * @return Long the id of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id.
     *
     * @param id Long the id of the user.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the username of this Entity.
     *
     * @return the username attribute.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for username attribute.
     *
     * @param username a String with username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter fot the password of this Entity.
     *
     * @return the password attribute.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password attribute.
     *
     * @param password a String with the password.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
