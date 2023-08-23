package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.*;
import gr.aueb.cf.schoolappsoa.dto.*;
import gr.aueb.cf.schoolappsoa.model.*;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class performs unit tests for
 * {@link UserServiceImpl} class at Service Layer.
 *
 * @author Thanasis Chousiadas
 */
public class UserServiceTest {
    private static final IUserDAO userDAO = new UserDAOImpl();
    private static final IStudentDAO studentDAO = new StudentDAOImpl();
    private static final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private static final ICityDAO cityDAO = new CityDAOImpl();
    private static final ISpecialityDAO specialityDAO = new SpecialityDAOImpl();
    private static IUserService userService;
    private static IStudentService studentService;
    private static ITeacherService teacherService;

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
        userService = new UserServiceImpl(userDAO, studentDAO, teacherDAO);
        studentService = new StudentServiceImpl(studentDAO, cityDAO, userDAO);
        teacherService = new TeacherServiceImpl(teacherDAO, specialityDAO, userDAO);
        DBHelper.eraseData();
    }

    /**
     * This method creates dummy users in the database for
     * testing.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    public static void createDummyUsers() throws UserDAOException {
        User user = new User();
        user.setUsername("user-01");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-02");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-03");
        user.setPassword("123456");
        userDAO.insert(user);

        user = new User();
        user.setUsername("user-04");
        user.setPassword("123456");
        userDAO.insert(user);
    }

    /**
     * This mehtod creates dummy cities in the database.
     *
     * @throws CityDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link City} Entity.
     */
    public static void createDummyCities() throws CityDAOException {
        // insert city for student
        City city = new City();
        city.setCity("Athens");
        cityDAO.insert(city);

        city = new City();
        city.setCity("Patras");
        cityDAO.insert(city);

        city = new City();
        city.setCity("Volos");
        cityDAO.insert(city);

        city = new City();
        city.setCity("Thessaloniki");
        cityDAO.insert(city);
    }

    /**
     * This method creates dummy specialities in the database.
     *
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     */
    public static void createDummySpecialities() throws SpecialityDAOException {
        Speciality speciality = new Speciality();
        speciality.setSpeciality("Maths");
        specialityDAO.insert(speciality);

        speciality = new Speciality();
        speciality.setSpeciality("Biology");
        specialityDAO.insert(speciality);

        speciality = new Speciality();
        speciality.setSpeciality("Chemistry");
        specialityDAO.insert(speciality);
    }

    /**
     * Before each unit test, this method sets up the database in
     * the initial state.
     *
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     * @throws CityDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link City} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     */
    @BeforeEach
    void setUp() throws UserDAOException, CityDAOException, SpecialityDAOException {
        createDummyUsers();
        createDummyCities();
        createDummySpecialities();
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
     * This method tests the {@link UserServiceImpl#insertUser(UserInsertDTO)} method
     * for the service of insert and retrieve a new user from the database.
     *
     * @throws UserAlreadyExistsException is an exception for handling errors
     *                                    for insertion a new user with the same
     *                                    username in the database with the username
     *                                    of a stored user. Username is unique.
     * @throws UserDAOException           is a wrapper exception to {@link SQLException}
     *                                    and handles errors for {@link User} Entity.
     */
    @Test
    public void insertAndGetUser() throws UserAlreadyExistsException, UserDAOException {
        UserInsertDTO dto = new UserInsertDTO();
        dto.setUsername("mock-user");
        dto.setPassword("123456");

        userService.insertUser(dto);

        User user = userService.getUserByUsername("mock-user");

        assertEquals(user.getUsername(), "mock-user");
    }

    /**
     * This method tests the prohibition of duplicate usernames
     * in the database.
     */
    @Test
    public void insertDuplicateUser() {
        UserInsertDTO dto = new UserInsertDTO(null, "user-01", "123456");
        assertThrows(UserAlreadyExistsException.class, () -> {
            userService.insertUser(dto);
        });
    }

    /**
     * This method tests the {@link UserServiceImpl#updateUser(UserUpdateDTO)} method
     * for the service of update an old user in the database.
     *
     * @throws UserNotFoundException an exception for handling errors
     *                               for updating a user that does not exist in
     *                               the database.
     * @throws UserDAOException      is a wrapper exception to {@link SQLException}
     *                               and handles errors for {@link User} Entity.
     */
    @Test
    public void updateUser() throws UserNotFoundException, UserDAOException {
        UserUpdateDTO dto = new UserUpdateDTO(1L, "UpdatedUser", "abcdef");
        userService.updateUser(dto);

        User user = userService.getUserById(1L);

        assertEquals(user.getUsername(), "UpdatedUser");
    }

    /**
     * This method tests the exception for updating
     * a non-existing user.
     */
    @Test
    public void updateNonExistingUser() {
        UserUpdateDTO dto = new UserUpdateDTO(10L, "UpdatedUser", "abcdef");

        assertThrows(UserNotFoundException.class, () -> {
            userService.updateUser(dto);
        });
    }

    /**
     * This method test the deletion from the database of a user that it
     * has not foreign keys in Teachers or Students tables.
     *
     * @throws UserNotFoundException  an exception for handling errors for deleting
     *                                or updating a user that does not exist in the database.
     * @throws StudentDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Student} Entity.
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     * @throws CityDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link City} Entity.
     */
    @Test
    public void deleteUserWithoutFK() throws UserNotFoundException, StudentDAOException, TeacherDAOException, SpecialityDAOException, UserDAOException, CityDAOException {
        Long id = 1L;
        userService.deleteUser(id);

        List<User> users = userService.getAllUsers();

        assertEquals(users.size(), 3);
    }

    /**
     * This method test the exception that is thrown
     * when deleting a non-existing user.
     */
    @Test
    public void deleteNonExistingUser() {
        Long id = 10L;

        assertThrows(UserNotFoundException.class, () -> {
            userService.deleteUser(id);
        });
    }

    /**
     * This method test the deletion from the database of a user that
     * has a foreign key with Students table.
     *
     * @throws ParseException         an exception that occurs when the date is not
     *                                formatting properly.
     * @throws StudentDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Student} Entity.
     * @throws CityDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link City} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     * @throws UserNotFoundException  an exception for handling errors for deleting
     *                                or updating a user that does not exist in the database.
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     */
    @Test
    public void deleteUserWithFKStudent() throws ParseException, StudentDAOException, CityDAOException, UserDAOException, UserNotFoundException, TeacherDAOException, SpecialityDAOException {
        StudentInsertDTO dto = new StudentInsertDTO();
        dto.setFirstname("student01");
        dto.setLastname("studentLast01");
        dto.setGender('M');
        String birthStr = "02-06-1997";
        Date birthDate = DateUtil.toDate(birthStr);
        dto.setBirthDate(birthDate);
        dto.setCityId(1L);
        dto.setUserId(1L);

        studentService.insertStudent(dto);
        // delete user with PK: 1
        Long id = 1L;

        userService.deleteUser(id);

        Student student = studentService.getStudentById(1L);
        assertNull(student);

        List<User> users = userService.getAllUsers();

        assertEquals(users.size(), 3);
    }

    /**
     * This method test the deletion from the database of a user that
     * has a foreign key with Teachers table.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     * @throws UserNotFoundException  an exception for handling errors for deleting
     *                                or updating a user that does not exist in the database.
     * @throws StudentDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Student} Entity.
     * @throws CityDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link City} Entity.
     */
    @Test
    public void deleteUserWithFKTeacher() throws TeacherDAOException, SpecialityDAOException, UserDAOException, UserNotFoundException, StudentDAOException, CityDAOException {
        Long id = 3L;

        TeacherInsertDTO dto = new TeacherInsertDTO();
        dto.setSsn(12345L);
        dto.setFirstname("teacher-01");
        dto.setLastname("teacher-lastname-01");
        dto.setSpecialityId(2L);
        dto.setUserId(id);

        teacherService.insertTeacher(dto);

        userService.deleteUser(id);
        Teacher teacher = teacherService.getTeacherById(1L);
        assertNull(teacher);

        List<User> users = userService.getAllUsers();
        assertEquals(users.size(), 3);
    }

    /**
     * Tests if the {@link UserServiceImpl#getUserByUsername(String)}
     * retrieves the right user.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    public void getUserByUsername() throws UserDAOException {
        User user = userService.getUserByUsername("user-03");
        assertNotNull(user);
    }

    /**
     * Tests if the {@link UserServiceImpl#getUserById(long)}
     * retrieves the right user.
     *
     * @throws UserNotFoundException an exception for handling errors for
     *                               retrieving a user that does not exist.
     * @throws UserDAOException      is a wrapper exception to {@link SQLException}
     *                               and handles errors for {@link User} Entity.
     */
    @Test
    public void getUserById() throws UserNotFoundException, UserDAOException {
        User user = userService.getUserById(4L);

        assertEquals(user.getUsername(), "user-04");
    }

    /**
     * Tests the {@link UserNotFoundException} for retrieving
     * a user that does not exist.
     */
    @Test
    public void getUserByIdNotFound() {
        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(10L);
        });
    }

    /**
     * This method tests the {@link UserServiceImpl#getAllUsers()}
     * method for retrieving all the users from the database.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    public void getAllUsers() throws UserDAOException {
        List<User> users = userService.getAllUsers();
        assertEquals(users.size(), 4);
    }

    /**
     * This method tests the {@link UserServiceImpl#getUsersByUsernameLike(String)}
     * method for retrieving users with username that begins with a given
     * parameter.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    public void getUsersByUsernameLike() throws UserDAOException {
        List<User> users = userService.getUsersByUsernameLike("user");
        assertEquals(users.size(), 4);
    }

    /**
     * This method tests the {@link UserServiceImpl#login(LoginDTO)} method
     * It inserts a new user and tests if this user can log in the application.
     *
     * @throws UserAlreadyExistsException is an exception for handling errors
     *                                    for insertion a new user with the same
     *                                    username in the database with the username
     *                                    of a stored user. Username is unique.
     * @throws UserDAOException           is a wrapper exception to {@link SQLException}
     *                                    and handles errors for {@link User} Entity.
     */
    @Test
    public void loginValidUser() throws UserAlreadyExistsException, UserDAOException {
        // User with hashed code
        UserInsertDTO insertDTO = new UserInsertDTO();
        insertDTO.setUsername("crypto-user");
        insertDTO.setPassword("123456");
        userService.insertUser(insertDTO);

        LoginDTO dto = new LoginDTO();
        dto.setUsername("crypto-user");
        dto.setPassword("123456");
        boolean isValid = userService.login(dto);

        assertTrue(isValid);
    }

    /**
     * This method tests for an non-existing user to have
     * access in the application.
     *
     * @throws UserDAOException is a wrapper exception to {@link SQLException}
     *                          and handles errors for {@link User} Entity.
     */
    @Test
    public void loginInvalidUser() throws UserDAOException {
        LoginDTO dto = new LoginDTO("wrong-user", "12345678");
        boolean isValid = userService.login(dto);

        assertFalse(isValid);
    }
}
