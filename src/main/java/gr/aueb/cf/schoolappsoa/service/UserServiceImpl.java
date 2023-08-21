package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.IStudentDAO;
import gr.aueb.cf.schoolappsoa.dao.ITeacherDAO;
import gr.aueb.cf.schoolappsoa.dao.IUserDAO;
import gr.aueb.cf.schoolappsoa.dao.exceptions.*;
import gr.aueb.cf.schoolappsoa.dto.LoginDTO;
import gr.aueb.cf.schoolappsoa.dto.UserInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.UserUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserNotFoundException;
import gr.aueb.cf.schoolappsoa.service.security.SecUtil;

import java.util.List;

/**
 * This interface implements the Public API
 * of the {@link IUserService} interface, for
 * the Service Layer of this application.
 * It implements services for CRUD operations in
 * {@link User} objects.
 *
 * @author Thanasis Chousiadas
 */
public class UserServiceImpl implements IUserService {
    private final IUserDAO userDAO;
    private final IStudentDAO studentDAO;
    private final ITeacherDAO teacherDAO;

    /**
     * Dependencies injection of {@link IUserDAO}, {@link IStudentDAO} and {@link ITeacherDAO} type
     * implementations in Data Access Object Layer.
     *
     * @param userDAO    an implementation of {@link IUserDAO} interface.
     * @param studentDAO an implementation of {@link IStudentDAO} interface.
     * @param teacherDAO an implementation of {@link ITeacherDAO} interface.
     */
    public UserServiceImpl(IUserDAO userDAO, IStudentDAO studentDAO, ITeacherDAO teacherDAO)
    {
        this.userDAO = userDAO;
        this.studentDAO = studentDAO;
        this.teacherDAO = teacherDAO;
    }

    /**
     * This method inserts a new teacher in the database.
     *
     * @param dto the Data Transfer Object with the data for insert the record.
     * @return the inserted {@link User} entity.
     * @throws UserAlreadyExistsException handles exceptions where a new user is inserted with
     *                                    username already exists in the DB. Usernames are unique.
     * @throws UserDAOException           handles errors propagated from DAO layer
     *                                    related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public User insertUser(UserInsertDTO dto) throws UserAlreadyExistsException, UserDAOException
    {
        User user;
        user = map(dto);

        // checks is the user is already exists
        User storedUser = userDAO.getByUsername(user.getUsername());
        if (storedUser != null) {
            throw new UserAlreadyExistsException(user);
        }

        return userDAO.insert(user);
    }

    /**
     * This method updates an old user record with a new one.
     *
     * @param dto the Data Transfer Object with the data for update the record.
     * @return the updated {@link User} object.
     * @throws UserNotFoundException handles errors when the user does not exist.
     * @throws UserDAOException      handles errors propagated from DAO layer
     *                               related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public User updateUser(UserUpdateDTO dto) throws UserNotFoundException, UserDAOException
    {
        User user;
        user = map(dto);

        if (userDAO.getById(user.getId()) == null) {
            throw new UserNotFoundException(user.getId());
        }

        return userDAO.update(user);
    }

    /**
     * This method deletes a teacher with an id given by the user.
     * If a user has a foreign key constraint with either a Student or
     * a Teacher, then this method cascades the deletion and deletes
     * the record from the corresponding table. After it deletes the user.
     *
     * @param id the id given by the client.
     * @throws UserNotFoundException  handles errors when the user does not exist.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws StudentDAOException    handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.StudentDAOImpl}.
     * @throws CityDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.CityDAOImpl}.
     * @throws TeacherDAOException    handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public void deleteUser(long id) throws UserNotFoundException, UserDAOException, StudentDAOException, CityDAOException, TeacherDAOException, SpecialityDAOException
    {
        User user;
        user = userDAO.getById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        // Search if there is Student with user_id -> id
        Student student = studentDAO.getByUserId(id);
        if (student != null) {
            studentDAO.delete(student.getId());
        }

        // Search if there is teacher with user_id -> id
        Teacher teacher = teacherDAO.getByUserId(id);
        if (teacher != null) {
            teacherDAO.delete(teacher.getId());
        }

        // deleting user after deleting the Student or Teacher if exists
        userDAO.delete(id);
    }

    /**
     * This method returns a user if exists where the username
     * is exactly the same with the username given by the client.
     *
     * @param username the username given by the client.
     * @return a {@link User} object.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public User getUserByUsername(String username) throws UserDAOException
    {
        return userDAO.getByUsername(username);
    }

    /**
     * This method returns a user with a certain id (primary key).
     *
     * @param id the id of the user.
     * @return a {@link User} object.
     * @throws UserDAOException      handles errors propagated from DAO layer
     *                               related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws UserNotFoundException handles errors when the user does not exist.
     */
    @Override
    public User getUserById(long id) throws UserDAOException, UserNotFoundException {
        User user;
        user = userDAO.getById(id);

        if (user == null) {
            throw new UserNotFoundException("Search Error: User with id: " + id + " was not found");
        }
        return user;
    }

    /**
     * This method returns all the users in the DB.
     *
     * @return an {@link java.util.ArrayList} object with {@link User} objects.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public List<User> getAllUsers() throws UserDAOException
    {
        return userDAO.getAll();
    }

    /**
     * This method returns the users where their username begins
     * with the username parameter given by the client.
     *
     * @param username the username given by the client.
     * @return an {@link java.util.ArrayList} with {@link User} objects.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public List<User> getUsersByUsernameLike(String username) throws UserDAOException
    {
        return userDAO.getByUsernameLike(username);
    }

    /**
     * This method checks if a {@link User} object exists in the database and authorize
     * the user to continue in the application.
     *
     * @param dto a {@link LoginDTO dto} object.
     * @return true if the user is authorized,
     * otherwise false.
     * @throws UserDAOException handles errors propagated from DAO layer
     *                          related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public boolean login(LoginDTO dto) throws UserDAOException {
        String hashedPasswd;
        User user;

        user = userDAO.getByUsername(dto.getUsername());
        if (user == null) {
            return false;
        }
        hashedPasswd = user.getPassword();

        return SecUtil.checkPassword(dto.getPassword(), hashedPasswd);
    }

    /**
     * This method maps the {@link UserInsertDTO} object
     * to {@link User} object for insert operation in the DB.
     *
     * @param dto a {@link UserInsertDTO} object.
     * @return a {@link User} object.
     */
    private User map(UserInsertDTO dto)
    {
        String hashedPasswd = SecUtil.hashPassword(dto.getPassword());
        return new User(null, dto.getUsername(), hashedPasswd);
    }

    /**
     * This method maps the {@link UserUpdateDTO} object
     * to {@link User} object for update operation in the DB.
     *
     * @param dto a {@link UserUpdateDTO} object.
     * @return a {@link User} object.
     */
    private User map(UserUpdateDTO dto)
    {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword());
    }
}
