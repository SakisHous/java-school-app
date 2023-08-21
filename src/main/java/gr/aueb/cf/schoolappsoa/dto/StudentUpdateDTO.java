package gr.aueb.cf.schoolappsoa.dto;

import java.util.Date;

/**
 * The Data Transfer Object for data needed in update
 * operation of a {@link gr.aueb.cf.schoolappsoa.model.Student}
 *
 * @author Thanasis Chousiadas
 */
public class StudentUpdateDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private Character gender;
    private Date birthDate;
    private Long cityId;
    private Long userId;

    /**
     * Default constructor.
     */
    public StudentUpdateDTO() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id        the id (primary key) is needed for update the record.
     * @param firstname the firstname of the student.
     * @param lastname  the lastname of the student.
     * @param gender    the gender of the student.
     * @param birthDate the birthdate of the student.
     * @param cityId    foreign key of the city in Cities table.
     * @param userId    foreign key of the user in the Users table.
     */
    public StudentUpdateDTO(
            Long id,
            String firstname,
            String lastname,
            Character gender,
            Date birthDate,
            Long cityId,
            Long userId
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.cityId = cityId;
        this.userId = userId;
    }

    /**
     * Getter for the id of the student.
     *
     * @return the id of the student.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id of the student
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the firstname of the student.
     *
     * @return the firstname of the student.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for the lastname of the student.
     *
     * @param firstname the firstname.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for the lastname of the student.
     *
     * @return the lastname of the student.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for the lastname of the student.
     *
     * @param lastname the lastname of the student.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for the gender of the student.
     *
     * @return the gender of the student.
     */
    public Character getGender() {
        return gender;
    }

    /**
     * Setter for the gender of the student.
     *
     * @param gender the gender of the student.
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * Getter for the birthdate of the student.
     *
     * @return the birthdate of the student.
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Setter for the birthdate of the student.
     *
     * @param birthDate the brithdate of the student.
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Getter for the primary key of a specific
     * record in Cities table that Data Transfer
     * Object transfers for insert operation.
     *
     * @return the primary key incorporated in DTO.
     */
    public Long getCityId() {
        return cityId;
    }

    /**
     * Setter for the primary key of a specific
     * record in Cities table that Data Transfer
     * Object has to transfer for insert operation.
     *
     * @param cityId the primary key to be incorporated in DTO.
     */
    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    /**
     * Getter for the primary key of a specific
     * record in Users table that Data Transfer
     * Object transfers for update operation.
     *
     * @return the primary key incorporated in DTO.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Setter for the primary key of a specific
     * record in Users table that Data Transfer
     * Object has to transfer for update operation.
     *
     * @param userId the primary key to be incorporated in DTO.
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
