package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.ICityDAO;
import gr.aueb.cf.schoolappsoa.dao.IStudentDAO;
import gr.aueb.cf.schoolappsoa.dao.IUserDAO;
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

import java.util.List;

/**
 * This interface implements the Public API
 * of the {@link IStudentService} interface, for
 * the Service Layer of this application.
 * It implements services for CRUD operations in
 * {@link Student} objects.
 *
 * @author Thanasis Chousiadas
 */
public class StudentServiceImpl implements IStudentService {
    private final IStudentDAO studentDAO;
    private final ICityDAO cityDAO;
    private final IUserDAO userDAO;

    /**
     * Dependencies injection of {@link IStudentDAO}, {@link ICityDAO} and {@link IUserDAO} type
     * implementations in Data Access Object Layer.
     *
     * @param studentDAO an implementation of {@link IStudentDAO} interface.
     * @param cityDAO    an implementation of {@link ICityDAO} interface.
     * @param userDAO    an implementation of {@link IUserDAO} interface.
     */
    public StudentServiceImpl(IStudentDAO studentDAO, ICityDAO cityDAO, IUserDAO userDAO)
    {
        this.studentDAO = studentDAO;
        this.cityDAO = cityDAO;
        this.userDAO = userDAO;
    }

    /**
     * This method inserts a new student in the database.
     *
     * @param dto the Data Transfer Object with the data for insert the record.
     * @return the inserted {@link Student} entity.
     * @throws StudentDAOException handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws CityDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public Student insertStudent(StudentInsertDTO dto) throws StudentDAOException, CityDAOException, UserDAOException
    {
        Student student = map(dto);

        return studentDAO.insert(student);
    }

    /**
     * This method updates an old student record with a new one.
     *
     * @param dto the Data Transfer Object with the data for update the record.
     * @return the updated {@link Student} object.
     * @throws StudentDAOException      handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws StudentNotFoundException handles update or delete errors when the student
     *                                  does not exist.
     * @throws CityDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public Student updateStudent(StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException
    {
        Student student = map(dto);

        if (studentDAO.getById(student.getId()) == null) {
            throw new StudentNotFoundException(student);
        }

        return studentDAO.update(student);
    }

    /**
     * This method deletes a student with an id given
     * by the user.
     *
     * @param id the id given by the user.
     * @return true if the student is deleted successfully,
     * otherwise false.
     * @throws StudentDAOException      handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws StudentNotFoundException handles update or delete errors when the student
     *                                  does not exist.
     * @throws CityDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public boolean deleteStudent(long id) throws StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException
    {
        Student student;
        boolean studentIsDeleted = false;

        student = studentDAO.getById(id);

        if (student == null) {
            throw new StudentNotFoundException("Student with id = " + id + " was not found");
        }

        if (studentDAO.delete(id)) {
            studentIsDeleted = true;
        }

        return studentIsDeleted;
    }

    /**
     * This method returns the students where their lastname
     * begins with the parameter given by the user.
     *
     * @param lastname the lastname for searching the students' lastname.
     * @return an {@link java.util.ArrayList} with {@link Student} objects.
     * @throws StudentDAOException handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws CityDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public List<Student> getStudentByLastname(String lastname) throws StudentDAOException, CityDAOException, UserDAOException
    {
        return studentDAO.getByLastname(lastname);
    }

    /**
     * This method returns a student with a certain id (primary key).
     *
     * @param id the id of the student.
     * @return a {@link Student} object.
     * @throws StudentDAOException handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws CityDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException    handles errors propagated from DAO layer
     *                             related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public Student getStudentById(long id) throws StudentDAOException, CityDAOException, UserDAOException
    {
        return studentDAO.getById(id);
    }

    /**
     * This method maps the {@link StudentInsertDTO} object
     * to {@link Student} object for insert operation in the DB.
     *
     * @param dto a {@link StudentInsertDTO} object.
     * @return a {@link Student} object.
     * @throws CityDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    private Student map(StudentInsertDTO dto) throws CityDAOException, UserDAOException
    {
        // converts java.util.Date to java.sql.Date
        java.sql.Date sqlBirthDate = DateUtil.toSQLDate(dto.getBirthDate());

        // make objects for Student model (it receives City object and User object)
        City city = cityDAO.getById(dto.getCityId());
        User user = userDAO.getById(dto.getUserId());

        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getGender(), sqlBirthDate, city, user);
    }

    /**
     * This method maps the {@link StudentUpdateDTO} object
     * to {@link Student} object for update operation in the DB.
     *
     * @param dto a {@link StudentUpdateDTO} object.
     * @return a {@link Student} object.
     * @throws CityDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    private Student map(StudentUpdateDTO dto) throws CityDAOException, UserDAOException
    {
        // converts java.util.Date to java.sql.Date
        java.sql.Date sqlBirthDate = DateUtil.toSQLDate(dto.getBirthDate());

        // make objects for Student model (it receives City object and User object)
        City city = cityDAO.getById(dto.getCityId());
        User user = userDAO.getById(dto.getUserId());

        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getGender(), sqlBirthDate, city, user);
    }
}
