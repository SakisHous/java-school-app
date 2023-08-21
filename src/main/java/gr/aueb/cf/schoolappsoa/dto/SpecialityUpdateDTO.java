package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in
 * insert operation of a {@link gr.aueb.cf.schoolappsoa.model.Speciality}
 *
 * @author Thanasis Chousiadas
 */
public class SpecialityUpdateDTO {
    private Long id;
    private String speciality;

    /**
     * Default constructor.
     */
    public SpecialityUpdateDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id         the primary key for the record to be updated.
     * @param speciality the new speciality.
     */
    public SpecialityUpdateDTO(Long id, String speciality) {
        this.id = id;
        this.speciality = speciality;
    }

    /**
     * Getter for the id.
     *
     * @return Long id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the speciality.
     *
     * @return the speciality name.
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Setter for the speciality.
     *
     * @param speciality the speciality name.
     */
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
}
