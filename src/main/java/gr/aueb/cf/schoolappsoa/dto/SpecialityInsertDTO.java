package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in
 * insert operation of a {@link gr.aueb.cf.schoolappsoa.model.Speciality}
 *
 * @author Thanasis Chousiadas
 */
public class SpecialityInsertDTO {
    private String speciality;

    /**
     * Default constructor.
     */
    public SpecialityInsertDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param speciality the speciality to be inserted
     */
    public SpecialityInsertDTO(String speciality) {
        this.speciality = speciality;
    }

    /**
     * Getter for the speciality.
     *
     * @return the speciality.
     */
    public String getSpeciality() {
        return speciality;
    }

    /**
     * Setter for the speciality.
     *
     * @param specialty the speciality.
     */
    public void setSpeciality(String specialty) {
        this.speciality = specialty;
    }
}
