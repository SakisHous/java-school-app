package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This unit test class tests the {@link StudentDAOImpl}
 * class at Data Access Object Layer.
 *
 * @author Thanasis Chousiadas
 */
public class StudentDAOTest {
    private static IStudentDAO studentDAO;
    private static ICityDAO cityDAO;
    private static IUserDAO userDAO;

    /**
     * Before each testing cycle this method deletes
     * all the records from the database and initializes the
     * dependencies.
     *
     * @throws SQLException handles errors for database access error
     *                      or other errors related with the database.
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        studentDAO = new StudentDAOImpl();
        cityDAO = new CityDAOImpl();
        userDAO = new UserDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * Before each unit test, this method sets up the database in
     * the initial state.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws ParseException      this exception is used if an error is occurred during date
     *                             format process.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     */
    @BeforeEach
    public void setUp() throws StudentDAOException, ParseException, UserDAOException, CityDAOException {
        createDummyEntities();
    }

    /**
     * After each unit test clear all the tables in order to
     * the method {@link #setUp()} insert the data for the initial
     * state.
     *
     * @throws SQLException an exception that provides information on
     *                      a database access error or other errors.
     */
    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseData();
    }

    /**
     * This method tests the {@link StudentDAOImpl#insert(Student)} for inserting
     * a new student in the database.
     *
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     * @throws ParseException      this exception is used if an error is occurred during date
     *                             format process.
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     */
    @Test
    public void insert() throws CityDAOException, UserDAOException, ParseException, StudentDAOException {
        List<City> cities = cityDAO.getAll();
        List<User> users = userDAO.getAll();

        Student student = new Student();
        student.setFirstname("newStudent");
        student.setLastname("studentlastname");
        student.setGender('M');
        java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("02-09-1990"));
        student.setBirthDate(sqlDate);
        student.setUser(users.get(2));
        student.setStudentCity(cities.get(0));
        studentDAO.insert(student);
        List<Student> students = studentDAO.getByLastname("");
        assertEquals(students.size(), 3);
    }

    /**
     * This method tests the {@link StudentDAOImpl#update(Student)} method
     * for updating a student.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void update() throws StudentDAOException, CityDAOException, UserDAOException {
        List<Student> students = studentDAO.getByLastname("");
        Student student = students.get(0);
        student.setLastname("modifiedLastname");
        studentDAO.update(student);
        Student modifiedStudent = studentDAO.getById(1L);

        assertEquals(modifiedStudent.getLastname(), "modifiedLastname");
    }

    /**
     * This method tests the {@link StudentDAOImpl#delete(long)} method for
     * deleting a student from the database.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void delete() throws StudentDAOException, CityDAOException, UserDAOException {
        Long id = 2L;
        studentDAO.delete(id);
        List<Student> students = studentDAO.getByLastname("");
        assertEquals(students.size(), 1);
    }

    /**
     * This method test the {@link TeacherDAOImpl#getByLastname(String)} method
     * for retrieving student with their lastname begins with lastname's parameters.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void getByLastname() throws StudentDAOException, CityDAOException, UserDAOException {
        List<Student> students = studentDAO.getByLastname("std-lastn");
        assertEquals(students.size(), 2);
    }

    /**
     * This method tests the {@link StudentDAOImpl#getById(long)} method for
     * retrieving a student with certain id from the database.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void getById() throws StudentDAOException, CityDAOException, UserDAOException {
        Student student = studentDAO.getById(1L);
        assertEquals(student.getFirstname(), "student-01");
    }

    /**
     * This method tests the {@link StudentDAOImpl#getByUserId(long)} method
     * for retrieving a student with user id.
     *
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void getByUserId() throws StudentDAOException, CityDAOException, UserDAOException {
        Student student = studentDAO.getByUserId(2L);
        assertEquals(student.getFirstname(), "student-02");
    }

    /**
     * This method creates dummy records in the database for testing.
     *
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws ParseException      this exception is used if an error is occurred during date
     *                             format process.
     */
    public void createDummyEntities() throws UserDAOException, CityDAOException, StudentDAOException, ParseException {
        User user01 = new User(1L, "user-01", "123456");
        userDAO.insert(user01);
        User user02 = new User(2L, "user-02", "123456");
        userDAO.insert(user02);
        User user03 = new User(3L, "user-03", "123456");
        userDAO.insert(user03);
        User user04 = new User(4L, "user-04", "123456");
        userDAO.insert(user04);

        City city01 = new City(1L, "Athens");
        cityDAO.insert(city01);
        City city02 = new City(2L, "Volos");
        cityDAO.insert(city02);
        City city03 = new City(3L, "Chalkida");
        cityDAO.insert(city03);
        City city04 = new City(2L, "Lamia");
        cityDAO.insert(city04);

        java.sql.Date date01 = DateUtil.toSQLDate(DateUtil.toDate("22-04-1989"));
        Student student01 = new Student(1L, "student-01", "std-lastname01", 'F', date01, city03, user01);
        studentDAO.insert(student01);

        java.sql.Date date02 = DateUtil.toSQLDate(DateUtil.toDate("12-08-1997"));
        Student student02 = new Student(2L, "student-02", "std-lastname02", 'M', date02, city01, user02);
        studentDAO.insert(student02);
    }
}