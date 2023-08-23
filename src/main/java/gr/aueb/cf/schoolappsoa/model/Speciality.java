package gr.aueb.cf.schoolappsoa.model;

/**
 * It declares the Speciality Entity which represents
 * the Specialities table in the database.
 *
 * @author Thanasis Chousiadas
 */
public class Speciality {
    private Long id;
    private String speciality;

    /**
     * Default constructor.
     */
    public Speciality() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id         the primary key of the Entity.
     * @param speciality the speciality attribute of the Entity.
     */
    public Speciality(Long id, String speciality) {
        this.id = id;
        this.speciality = speciality;
    }

    /**
     * Getter for the id.
     *
     * @return Long the id of the Specialty.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id.
     *
     * @param id Long the id of the Speciality.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the speciality attribute.
     *
     * @return the speciality attribute.
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Setter for the speciality attribute.
     *
     * @param speciality the speciality attribute.
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
