package gr.aueb.cf.schoolappsoa.service.exceptions;

import gr.aueb.cf.schoolappsoa.model.City;

/**
 * This exception class handles exceptions
 * for update or delete operations. If the
 * record to be updated  or the
 * record to be deleted does not exist.
 *
 * @author Thanasis Chousiadas
 */
public class CityNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives the object {@link City} to be updated and output a custom message.
     *
     * @param city the {@link City} for update.
     */
    public CityNotFoundException(City city) {
        super("City with id = " + city.getId() + " not found");
    }

    /**
     * Overloaded constructor for deletion or update of an object {@link City}.
     * It outputs a custom message.
     *
     * @param s the custom message.
     */
    public CityNotFoundException(String s) {
        super(s);
    }
}
