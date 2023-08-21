package gr.aueb.cf.schoolappsoa.service.exceptions;


import gr.aueb.cf.schoolappsoa.model.User;

/**
 * This exception class handles exceptions
 * for insert operations. If the inserted record has
 * a primary key that already exists.
 *
 * @author Thanasis Chousiadas
 */
public class UserAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives a custom message for this kind of exceptions.
     *
     * @param user a {@link User} object which represents the record to be inserted.
     */
    public UserAlreadyExistsException(User user) {
        super("User with id = " + user.getId() + " already exists");
    }
}
