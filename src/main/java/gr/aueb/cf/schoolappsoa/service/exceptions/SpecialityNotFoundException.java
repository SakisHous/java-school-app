package gr.aueb.cf.schoolappsoa.service.exceptions;

/**
 * This exception class handles exceptions
 * for update or delete operations. If the
 * record to be updated  or the
 * record to be deleted does not exist.
 *
 * @author Thanasis Chousiadas
 */
public class SpecialityNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor that receives the primary key (id) and outputs a custom message.
     * It is used for update or delete operations.
     *
     * @param id the primary key of the record.
     */
    public SpecialityNotFoundException(long id) {
        super("Specialty with id = " + id + " was not found");
    }

    /**
     * Overloaded constructor for a custom message.
     *
     * @param s the message given by the client.
     */
    public SpecialityNotFoundException(String s) {
        super(s);
    }
}
