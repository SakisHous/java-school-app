package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in insert
 * operation of a {@link gr.aueb.cf.schoolappsoa.model.User}
 *
 * @author Thanasis Chousiadas
 */
public class UserInsertDTO {
    private Long id;
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public UserInsertDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id       it is ignored, primary key is provided by the database in insert operation.
     * @param username the username of the new User to inserted.
     * @param password the password of the new User to be inserted.
     */
    public UserInsertDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * @return id of the user
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the username.
     *
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username.
     *
     * @param username the username of the User.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password of the User.
     *
     * @return the password of the User.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of the user.
     *
     * @param password the password of the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
