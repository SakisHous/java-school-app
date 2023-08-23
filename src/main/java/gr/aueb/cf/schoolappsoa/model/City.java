package gr.aueb.cf.schoolappsoa.model;

/**
 * It declares the City Entity which represents
 * the Cities table in the database.
 *
 * @author Thanasis Chousiadas
 */
public class City {
    private Long id;
    private String city;

    /**
     * Default constructor.
     */
    public City() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id   the primary key of the City Entity.
     * @param city the city name of the City Entity.
     */
    public City(Long id, String city) {
        this.id = id;
        this.city = city;
    }

    /**
     * Getter for the id.
     *
     * @return Long the id of the city.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the city's id.
     *
     * @param id is the city's pk.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the city name.
     *
     * @return string the city name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for city's name.
     *
     * @param city a string with city's name.
     */
    public void setCity(String city) {
        this.city = city;
    }
}
