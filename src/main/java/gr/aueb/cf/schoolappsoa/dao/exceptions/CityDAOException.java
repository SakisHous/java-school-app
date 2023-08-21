package gr.aueb.cf.schoolappsoa.dao.exceptions;

/**
 * This exception is a wrapper exception to
 * the {@link java.sql.SQLException} class.
 * It used when errors occurred during CRUD
 * operations in Cities table.
 *
 * @author Thanasis Chousiadas
 */
public class CityDAOException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * It provides a custom message when error is
     * occurred.
     *
     * @param s a custom message
     */
    public CityDAOException(String s) {
        super(s);
    }
}
