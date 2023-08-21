package gr.aueb.cf.schoolappsoa.dto;

/**
 * The Data Transfer Object for data needed in insert
 * operation of a {@link gr.aueb.cf.schoolappsoa.model.Teacher}
 *
 * @author Thanasis Chousiadas
 */
public class TeacherInsertDTO {
    private Long id;
    private Long ssn;
    private String firstname;
    private String lastname;
    private Long specialityId;
    private Long userId;

    /**
     * Default constructor.
     */
    public TeacherInsertDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id           it is ignored, database provides the primary key.
     * @param ssn          the Social Security Number of the teacher.
     * @param firstname    the firstname of the teacher.
     * @param lastname     the lastname of the teacher.
     * @param specialityId foreign key of the speciality in Specialities table.
     * @param userId       foreign key of the user in Users table.
     */
    public TeacherInsertDTO(
            Long id,
            Long ssn,
            String firstname,
            String lastname,
            Long specialityId,
            Long userId
    ) {
        this.id = id;
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.specialityId = specialityId;
        this.userId = userId;
    }

    /**
     * Getter for id of the teacher.
     *
     * @return the id of the teacher.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for SSN of the teacher.
     *
     * @return the SSN of the teacher.
     */
    public Long getSsn() {
        return ssn;
    }

    /**
     * Setter for the SSN of the teacher.
     *
     * @param ssn the SSN of the teacher.
     */
    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }

    /**
     * Getter for the firstname of the teacher.
     *
     * @return the firstname of the teacher.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for the firstname of the teacher.
     *
     * @param firstname the firstname of the teacher.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for the lastname of the teacher.
     *
     * @return the lastname of the teacher.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for the lastname of the teacher.
     *
     * @param lastname the lastname of the teacher.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for the primary key of a specific
     * record in Specialities table that Data Transfer
     * Object transfers for insert operation.
     *
     * @return the primary key incorporated in DTO.
     */
    public Long getSpecialityId() {
        return specialityId;
    }

    /**
     * Setter for the primary key of a specific
     * record in Specialities table that Data Transfer
     * Object has to transfer for insert operation.
     *
     * @param specialtyId the primary key to be incorporated in DTO.
     */
    public void setSpecialityId(Long specialtyId) {
        this.specialityId = specialtyId;
    }

    /**
     * Getter for the primary key of a specific
     * record in Users table that Data Transfer
     * Object transfers for insert operation.
     *
     * @return the primary key incorporated in DTO.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Setter for the primary key of a specific
     * record in Users table that Data Transfer
     * Object has to transfer for insert operation.
     *
     * @param userId the primary key to be incorporated in DTO.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
