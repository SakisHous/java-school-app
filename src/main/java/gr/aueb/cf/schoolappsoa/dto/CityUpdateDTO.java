package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in
 * update operation of a {@link gr.aueb.cf.schoolappsoa.model.City}
 *
 * @author Thanasis Chousiadas
 */
public class CityUpdateDTO {
    private Long id;
    private String city;

    /**
     * Default constructor.
     */
    public CityUpdateDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id   the id (primary key) of the record to be updated.
     * @param city the city name to be updated
     */
    public CityUpdateDTO(
            Long id,
            String city
    ) {
        this.id = id;
        this.city = city;
    }

    /**
     * Getter for the id name.
     *
     * @return the id of the city.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the city name.
     *
     * @return the city name.
     */
    public String getCity() {
        return city;
    }

    /**
     * Setter for the city name.
     *
     * @param city the city name.
     */
    public void setCity(String city) {
        this.city = city;
    }
}
