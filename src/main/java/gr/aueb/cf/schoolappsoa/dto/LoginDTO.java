package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in
 * log in operation of a {@link gr.aueb.cf.schoolappsoa.model.User}
 *
 * @author Thanasis Chousiadas
 */
public class LoginDTO {
    private String username;
    private String password;

    /**
     * Default constructor.
     */
    public LoginDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param username the username received from application's user.
     * @param password the password received from application's user.
     */
    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Getter for the username of the
     * application's user.
     *
     * @return a String with username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter for the username of the
     * application's user.
     *
     * @param username a String given by the user.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter for the password of the
     * application's user.
     *
     * @return a String with password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter for the password of the
     * application's user.
     *
     * @param password a String given by the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
