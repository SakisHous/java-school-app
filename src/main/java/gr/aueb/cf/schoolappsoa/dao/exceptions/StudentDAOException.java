package gr.aueb.cf.schoolappsoa.dao.exceptions;

/**
 * This exception is a wrapper exception to
 * the {@link java.sql.SQLException} class.
 * It used when errors occurred during CRUD
 * operations in Students table.
 *
 * @author Thanasis Chousiadas
 */
public class StudentDAOException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * It provides a custom message when error is
     * occurred.
     *
     * @param s a custom message
     */
    public StudentDAOException(String s) {
        super(s);
    }
}
