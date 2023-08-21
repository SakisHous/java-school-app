package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in
 * insert operation of a {@link gr.aueb.cf.schoolappsoa.model.City}
 *
 * @author Thanasis Chousiadas
 */
public class CityInsertDTO {
    private String city;

    /**
     * Default constructor
     */
    public CityInsertDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param city the name of the city.
     */
    public CityInsertDTO(String city) {
        this.city = city;
    }

    /**
     * Getter for the name of the city.
     *
     * @return a String with the name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for the name of the city.
     *
     * @param city the name of the city.
     */
    public void setCity(String city) {
        this.city = city;
    }
}
