package gr.aueb.cf.schoolappsoa.model;

/**
 * It declares the Teacher Entity which represents
 * the Teachers table in the database.
 *
 * @author Thanasis Chousiadas
 */
public class Teacher {
    private Long id;
    private Long ssn;
    private String firstname;
    private String lastname;
    private Speciality teacherSpeciality;
    private User user;

    /**
     * Default constructor.
     */
    public Teacher() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id         the primary key of the Entity.
     * @param ssn        the Social Security Number of the Entity.
     * @param firstname  the firstname attribute of the Entity.
     * @param lastname   the lastname attribute of the Entity.
     * @param speciality dependency injection of {@link Speciality} Entity.
     * @param user       dependency injection of {@link User} Entity.
     */
    public Teacher(
            Long id,
            Long ssn,
            String firstname,
            String lastname,
            Speciality speciality,
            User user
    ) {
        this.id = id;
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.teacherSpeciality = speciality;
        this.user = user;
    }

    /**
     * Getter for the id.
     *
     * @return Long the id of the teacher.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter for the id of the teacher.
     *
     * @param id Long the id of the teacher.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the Social Security Number.
     *
     * @return the SSN of the Entity.
     */
    public Long getSsn() {
        return ssn;
    }

    /**
     * Setter for the Social Security Number.
     *
     * @param ssn the SSN of the Entity.
     */
    public void setSsn(Long ssn) {
        this.ssn = ssn;
    }

    /**
     * Getter for the firstname of the Entity.
     *
     * @return the firstname attribute.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter fot the firstname of the Entity.
     *
     * @param firstname a String with the firstname.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for the lastname of the Entity.
     *
     * @return the lastname attribute.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for the lastname of the Entity.
     *
     * @param lastname a String with the lastname.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns an instance of {@link Speciality} Entity,
     * related with this {@link Teacher} Entity.
     *
     * @return a {@link Speciality} object.
     */
    public Speciality getTeacherSpeciality() {
        return teacherSpeciality;
    }

    /**
     * Setter for the {@link Speciality} Entity,
     * related with this {@link Teacher} entity.
     *
     * @param teacherSpeciality a {@link Speciality} object.
     */
    public void setTeacherSpeciality(Speciality teacherSpeciality) {
        this.teacherSpeciality = teacherSpeciality;
    }

    /**
     * Returns an instance of {@link User} Entity,
     * related with this {@link Teacher} Entity.
     *
     * @return a {@link User} object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the {@link User} Entity,
     * related with this {@link Teacher} entity.
     *
     * @param user a {@link User} object.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
