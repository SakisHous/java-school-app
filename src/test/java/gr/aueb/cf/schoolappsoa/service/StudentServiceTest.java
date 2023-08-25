package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.StudentInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.exceptions.StudentNotFoundException;
import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StudentServiceTest {
    private static final IUserDAO userDAO = new UserDAOImpl();
    private static final ICityDAO cityDAO = new CityDAOImpl();
    private static final IStudentDAO studentDAO = new StudentDAOImpl();
    private static IStudentService studentService;

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
        studentService = new StudentServiceImpl(studentDAO, cityDAO, userDAO);
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
     * This method tests the {@link StudentServiceImpl#insertStudent(StudentInsertDTO)}
     * method for inserting a new student in the database.
     *
     * @throws ParseException      this exception is used if an error is occurred during date
     *                             format process.
     * @throws StudentDAOException is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link Student} Entity.
     * @throws CityDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link City} Entity.
     * @throws UserDAOException    is a wrapper exception to {@link SQLException}
     *                             and handles errors for {@link User} Entity.
     */
    @Test
    public void insertStudent() throws ParseException, StudentDAOException, CityDAOException, UserDAOException {
        StudentInsertDTO dto = new StudentInsertDTO();
        dto.setFirstname("student-03");
        dto.setLastname("std-lastname-03");
        dto.setGender('M');
        java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("12-12-2000"));
        dto.setBirthDate(sqlDate);
        dto.setCityId(1L);
        dto.setUserId(3L);
        studentService.insertStudent(dto);

        List<Student> students = studentService.getStudentByLastname("");
        assertEquals(students.size(), 3);
    }

    /**
     * This method tests the {@link StudentServiceImpl#updateStudent(StudentUpdateDTO)}
     * method for updating an student in the database.
     *
     * @throws ParseException           this exception is used if an error is occurred during date
     *                                  format process.
     * @throws StudentDAOException      is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link Student} Entity.
     * @throws StudentNotFoundException this exception is thrown for updating a
     *                                  student that does not exist in the database.
     * @throws CityDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link City} Entity.
     * @throws UserDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link User} Entity.
     */
    @Test
    public void updateStudent() throws ParseException, StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException {
        StudentUpdateDTO dto = new StudentUpdateDTO();
        dto.setId(1L);
        dto.setFirstname("UpdatedFirstname");
        dto.setLastname("LastUpd");
        dto.setGender('F');
        java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("17-12-2000"));
        dto.setBirthDate(sqlDate);
        dto.setCityId(2L);
        dto.setUserId(1L);
        studentService.updateStudent(dto);

        Student student = studentDAO.getById(1L);

        assertEquals(student.getFirstname(), "UpdatedFirstname");
        assertEquals(student.getLastname(), "LastUpd");
        assertEquals(student.getGender(), 'F');
        assertEquals(DateUtil.toString(student.getBirthDate()), "17-12-2000");
    }

    /**
     * This method tests the {@link StudentNotFoundException} for
     * updating a non-existing student in the database.
     */
    @Test
    public void studentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> {
            StudentUpdateDTO dto = new StudentUpdateDTO();
            dto.setId(10L);
            dto.setFirstname("UpdatedFirstname");
            dto.setLastname("LastUpd");
            dto.setGender('F');
            java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("17-12-2000"));
            dto.setBirthDate(sqlDate);
            dto.setCityId(2L);
            dto.setUserId(1L);
            studentService.updateStudent(dto);
        });
    }

    /**
     * This method tests the {@link StudentServiceImpl#deleteStudent(long)} method
     * for deleting a user from the database.
     *
     * @throws StudentDAOException      is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link Student} Entity.
     * @throws StudentNotFoundException this exception is thrown for updating a
     *                                  student that does not exist in the database.
     * @throws CityDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link City} Entity.
     * @throws UserDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link User} Entity.
     */
    @Test
    void deleteStudent() throws StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException {
        Long id = 2L;
        studentService.deleteStudent(id);
        List<Student> students = studentService.getStudentByLastname("");
        assertEquals(students.size(), 1);
    }

    /**
     * This method tests the {@link StudentServiceImpl#getStudentByLastname(String)}
     * method for retrieving student with their lastname begins with the parameter's
     * lastname.
     *
     * @throws StudentDAOException      is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link Student} Entity.
     * @throws CityDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link City} Entity.
     * @throws UserDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link User} Entity.
     */
    @Test
    public void getStudentByLastname() throws StudentDAOException, CityDAOException, UserDAOException {
        List<Student> students = studentService.getStudentByLastname("");
        assertEquals(students.size(), 2);
    }

    /**
     * This method tests the {@link StudentServiceImpl#getStudentById(long)}
     * method for retrieving the student based on the id.
     *
     * @throws StudentDAOException      is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link Student} Entity.
     * @throws CityDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link City} Entity.
     * @throws UserDAOException         is a wrapper exception to {@link SQLException}
     *                                  and handles errors for {@link User} Entity.
     */
    @Test
    void getStudentById() throws StudentDAOException, CityDAOException, UserDAOException {
        Student student = studentService.getStudentById(1L);
        assertEquals(student.getFirstname(), "student-01");
        assertEquals(student.getLastname(), "std-lastname01");
        assertEquals(student.getGender(), 'F');
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