package gr.aueb.cf.schoolappsoa.service.exceptions;


import gr.aueb.cf.schoolappsoa.model.User;

/**
 * This exception class handles exceptions
 * for update or delete operations. If the
 * record to be updated  or the
 * record to be deleted does not exist.
 *
 * @author Thanasis Chousiadas
 */
public class UserNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives the object {@link User} to be updated of deleted.
     *
     * @param id a {@link User} object.
     */
    public UserNotFoundException(long id) {
        super("User with id = " + id + " was not found");
    }

    /**
     * Overloaded constructor that receives a custom message.
     *
     * @param s the message given by the client.
     */
    public UserNotFoundException(String s) {
        super(s);
    }
}
