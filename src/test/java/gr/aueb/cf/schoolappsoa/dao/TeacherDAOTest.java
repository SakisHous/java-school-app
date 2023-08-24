package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.dbutil.DBHelper;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This test class tests the Public API
 * implemented by the {@link TeacherDAOImpl}
 * class of Data Access Object Layer.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherDAOTest {
    private static ITeacherDAO teacherDAO;
    private static IUserDAO userDAO;
    private static ISpecialityDAO specialityDAO;

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
        Teacher teacher02 = new Teacher(2L, 101L, "Teacher-01", "Lastname-02", speciality01, user02);
        teacherDAO.insert(teacher02);
        Teacher teacher03 = new Teacher(3L, 102L, "Teacher-01", "Lastname-03", speciality02, user03);
        teacherDAO.insert(teacher03);
    }

    /**
     * Before each testing cycle this method deletes
     * all the records from the database and initializes the
     * dependencies.
     *
     * @throws SQLException handles errors for database access error
     *                      or other errors related with the database.
     */
    @BeforeAll
    public static void setUpClass() throws SQLException {
        teacherDAO = new TeacherDAOImpl();
        userDAO = new UserDAOImpl();
        specialityDAO = new SpecialityDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * Before each unit test, this method sets up the database in
     * the initial state.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
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

    /**
     * This method tests {@link TeacherDAOImpl#insert(Teacher)} for
     * the insert procedure of a new teacher.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void insert() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        Speciality speciality = specialityDAO.getById(4L);
        User user = userDAO.getById(4L);
        Teacher teacher = new Teacher();
        teacher.setSsn(12300L);
        teacher.setFirstname("new-teacher");
        teacher.setLastname("new-teacher-lastname");
        teacher.setTeacherSpeciality(speciality);
        teacher.setUser(user);
        teacherDAO.insert(teacher);

        List<Teacher> teachers = teacherDAO.getByLastname("new-teacher-lastname");

        assertEquals(teachers.size(), 1);
    }

    /**
     * This method tests {@link TeacherDAOImpl#update(Teacher)} for
     * the update procedure of a teacher.
     *
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void update() throws SpecialityDAOException, TeacherDAOException, UserDAOException {
        List<Speciality> specialities = specialityDAO.getAll();
        // updating the speciality of the first teacher
        Teacher teacher = teacherDAO.getById(1L);
        teacher.setTeacherSpeciality(specialities.get(3));
        teacherDAO.update(teacher);

        Teacher updatedTeacher = teacherDAO.getById(1L);

        assertEquals(updatedTeacher.getTeacherSpeciality().getSpeciality(), "Physics");
    }

    /**
     * This method tests {@link TeacherDAOImpl#delete(long)} for deleting
     * a teacher from the database.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void delete() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        Long id = 1L;
        teacherDAO.delete(id);

        Teacher teacher = teacherDAO.getById(1L);

        assertNull(teacher);
    }

    /**
     * This method tests {@link TeacherDAOImpl#getByLastname(String)} the list of teachers where their lastname
     * begins with the parameter's lastname.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void getByLastname() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        List<Teacher> teachers = teacherDAO.getByLastname("Lastname-");
        assertEquals(teachers.size(), 3);
    }

    /**
     * This method tests {@link TeacherDAOImpl#getById(long)} for retrieving
     * a teacher by id.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void getById() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        Teacher teacher = teacherDAO.getById(1L);
        assertNotNull(teacher);
    }

    /**
     * This method tests {@link TeacherDAOImpl#getByUserId(long)} for the
     * retrieving a teacher by user id foreign key.
     *
     * @throws TeacherDAOException    is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Teacher} Entity.
     * @throws SpecialityDAOException is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link Speciality} Entity.
     * @throws UserDAOException       is a wrapper exception to {@link SQLException}
     *                                and handles errors for {@link User} Entity.
     */
    @Test
    public void getByUserId() throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        Teacher teacher = teacherDAO.getByUserId(1L);

        assertEquals(teacher.getUser().getUsername(), "user-01");
    }
}