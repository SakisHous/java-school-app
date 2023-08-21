package gr.aueb.cf.schoolappsoa.service.exceptions;

import gr.aueb.cf.schoolappsoa.model.City;

/**
 * This exception class handles exceptions
 * for insert operations. If the inserted record has
 * a primary key that already exists.
 *
 * @author Thanasis Chousiadas
 */
public class CityAlreadyExistsException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives a custom message for this kind of exceptions.
     *
     * @param city a {@link City} object which represents the record to be inserted.
     */
    public CityAlreadyExistsException(City city) {
        super("City with the name " + city.getCity() + " already exists");
    }
}
