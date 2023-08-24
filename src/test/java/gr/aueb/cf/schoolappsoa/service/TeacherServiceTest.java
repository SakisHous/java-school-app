package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.*;
import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.exceptions.TeacherNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class performs unit tests for
 * {@link TeacherServiceImpl} class at Service Layer.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherServiceTest {
    private static final ITeacherDAO teacherDAO = new TeacherDAOImpl();
    private static final ISpecialityDAO specialityDAO = new SpecialityDAOImpl();
    private static final IUserDAO userDAO = new UserDAOImpl();
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
        teacherService = new TeacherServiceImpl(teacherDAO, specialityDAO, userDAO);
        DBHelper.eraseData();
    }

    @BeforeEach
    public void setUp() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
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

    @Test
    public void insertTeacher() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        TeacherInsertDTO dto = new TeacherInsertDTO();
        dto.setSsn(123L);
        dto.setFirstname("Teacher01");
        dto.setLastname("LastnameTeacher");
        dto.setSpecialityId(1L);
        dto.setUserId(3L);
        teacherService.insertTeacher(dto);

        List<Teacher> teachers = teacherService.getTeacherByLastname("");

        assertEquals(teachers.size(), 3);
    }

    @Test
    public void updateTeacher() throws TeacherNotFoundException, TeacherDAOException, SpecialityDAOException, UserDAOException {
        TeacherUpdateDTO dto = new TeacherUpdateDTO();
        dto.setId(1L);
        dto.setSsn(123L);
        dto.setFirstname("UpdatedName");
        dto.setLastname("UpdatedLast");
        dto.setSpecialityId(1L);
        dto.setUserId(1L);
        teacherService.updateTeacher(dto);
        Teacher teacher = teacherService.getTeacherById(1L);

        assertEquals(teacher.getFirstname(), "UpdatedName");
        assertEquals(teacher.getLastname(), "UpdatedLast");
    }

    @Test
    public void deleteTeacher() throws TeacherNotFoundException, TeacherDAOException, SpecialityDAOException, UserDAOException {
        Long id = 2L;
        teacherService.deleteTeacher(id);
        List<Teacher> teachers = teacherService.getTeacherByLastname("");
        assertEquals(teachers.size(), 1);
    }

    @Test
    public void getTeacherByLastname() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        List<Teacher> teachers = teacherService.getTeacherByLastname("Lastname-");

        assertEquals(teachers.size(), 2);
    }

    @Test
    public void getTeacherById() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        Teacher teacher = teacherService.getTeacherById(2L);

        assertEquals(teacher.getFirstname(), "Teacher-02");
    }

    /**
     * This method creates dummy records in the database, regarding
     * the Entities {@link User}, {@link Speciality} and {@link Teacher}.
     *
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     */
    public static void createDummyEntities() throws UserDAOException, SpecialityDAOException, TeacherDAOException {
        User user01 = new User(1L, "user-01", "123456");
        userDAO.insert(user01);
        User user02 = new User(2L, "user-02", "123456");
        userDAO.insert(user02);
        User user03 = new User(3L, "user-03", "123456");
        userDAO.insert(user03);
        User user04 = new User(4L, "user-04", "123456");
        userDAO.insert(user04);

        Speciality speciality01 = new Speciality(1L, "Maths");
        specialityDAO.insert(speciality01);
        Speciality speciality02 = new Speciality(2L, "Biology");
        specialityDAO.insert(speciality02);
        Speciality speciality03 = new Speciality(3L, "Chemistry");
        specialityDAO.insert(speciality03);
        Speciality speciality04 = new Speciality(4L, "Physics");
        specialityDAO.insert(speciality04);

        Teacher teacher01 = new Teacher(1L, 100L, "Teacher-01", "Lastname-01", speciality03, user01);
        teacherDAO.insert(teacher01);
        Teacher teacher02 = new Teacher(2L, 101L, "Teacher-02", "Lastname-02", speciality01, user02);
        teacherDAO.insert(teacher02);
    }
}