package gr.aueb.cf.schoolappsoa.model;

/**
 * It declares the Student Entity which represents
 * the Students table in the database.
 *
 * @author Thanasis Chousiadas
 */
public class Student {
    private Long id;
    private String firstname;
    private String lastname;
    private Character gender;
    private java.sql.Date birthDate;
    private City studentCity;
    private User user;

    /**
     * Default constructor.
     */
    public Student() {
    }

    /**
     * Overloaded constructor.
     *
     * @param id          the primary key of the Entity.
     * @param firstname   the firstname attribute of the Entity.
     * @param lastname    the lastname attribute of the Entity.
     * @param gender      the gender attribute of the Entity.
     * @param birthDate   the birthdate attribute of the Entity.
     * @param studentCity dependency injection of {@link City} Entity.
     * @param user        dependency injection of {@link User} Entity.
     */
    public Student(
            Long id,
            String firstname,
            String lastname,
            Character gender,
            java.sql.Date birthDate,
            City studentCity,
            User user
    ) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthDate = birthDate;
        this.studentCity = studentCity;
        this.user = user;
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
     * @param id the id of the student
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the firstname attribute.
     *
     * @return the firstname attribute.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Setter for the firstname attribute.
     *
     * @param firstname the firstname attribute.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Getter for the lastname attribute.
     *
     * @return the lastname attribute.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Setter for the lastname attribute.
     *
     * @param lastname the lastname attribute.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Getter for the gender attribute.
     *
     * @return the gender attribute.
     */
    public Character getGender() {
        return gender;
    }

    /**
     * Setter for the gender attribute.
     *
     * @param gender the gender attribute.
     */
    public void setGender(Character gender) {
        this.gender = gender;
    }

    /**
     * Getter for the birthdate attribute.
     *
     * @return the birthdate attribute in date
     * format as stored in the database.
     */
    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    /**
     * Setter for the birthdate attribute.
     *
     * @param birthDate the birthdate in a date format
     *                  compatible with database's date.
     */
    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    /**
     * Returns an instance of {@link City} Entity,
     * related with this {@link Student} Entity.
     *
     * @return a {@link City} object.
     */
    public City getStudentCity() {
        return studentCity;
    }

    /**
     * Setter for the {@link City} Entity,
     * related with this {@link Student} entity.
     *
     * @param studentCity a {@link City} object.
     */
    public void setStudentCity(City studentCity) {
        this.studentCity = studentCity;
    }

    /**
     * Returns an instance of {@link User} Entity,
     * related with this {@link Student} Entity.
     *
     * @return a {@link User} object.
     */
    public User getUser() {
        return user;
    }

    /**
     * Setter for the {@link User} Entity,
     * related with this {@link Student} entity.
     *
     * @param user a {@link User} object.
     */
    public void setUser(User user) {
        this.user = user;
    }
}
