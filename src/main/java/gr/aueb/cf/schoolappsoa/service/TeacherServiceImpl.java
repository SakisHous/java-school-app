package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.ICityDAO;
import gr.aueb.cf.schoolappsoa.dao.ISpecialityDAO;
import gr.aueb.cf.schoolappsoa.dao.ITeacherDAO;
import gr.aueb.cf.schoolappsoa.dao.IUserDAO;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.exceptions.TeacherNotFoundException;

import java.util.List;

/**
 * This interface implements the Public API
 * of the {@link ITeacherService} interface, for
 * the Service Layer of this application.
 * It implements services for CRUD operations in
 * {@link Teacher} objects.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherServiceImpl implements ITeacherService {
    private final ITeacherDAO teacherDAO;
    private final ISpecialityDAO specialityDAO;
    private final IUserDAO userDAO;

    /**
     * Dependencies injection of {@link ITeacherDAO}, {@link ICityDAO} and {@link IUserDAO} type
     * implementations in Data Access Object Layer.
     *
     * @param teacherDAO    an implementation of {@link ITeacherDAO} interface.
     * @param specialityDAO an implementation of {@link ISpecialityDAO} interface.
     * @param userDAO       an implementation of {@link IUserDAO} interface.
     */
    public TeacherServiceImpl(ITeacherDAO teacherDAO, ISpecialityDAO specialityDAO, IUserDAO userDAO) {
        this.teacherDAO = teacherDAO;
        this.specialityDAO = specialityDAO;
        this.userDAO = userDAO;
    }

    /**
     * This method inserts a new teacher in the database.
     *
     * @param dto the Data Transfer Object with the data for insert the record.
     * @return the inserted {@link Teacher} entity.
     * @throws TeacherDAOException    handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public Teacher insertTeacher(TeacherInsertDTO dto) throws TeacherDAOException, UserDAOException, SpecialityDAOException {
        Teacher teacher = map(dto);

        return teacherDAO.insert(teacher);
    }

    /**
     * This method updates an old teacher record with a new one.
     *
     * @param dto the Data Transfer Object with the data for update the record.
     * @return the updated {@link Teacher} object.
     * @throws TeacherDAOException      handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws TeacherNotFoundException handles update or delete errors when the teacher
     *                                  does not exist.
     * @throws UserDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException   handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public Teacher updateTeacher(TeacherUpdateDTO dto) throws TeacherDAOException, TeacherNotFoundException, UserDAOException, SpecialityDAOException {
        Teacher teacher = map(dto);

        if (teacherDAO.getById(teacher.getId()) == null) {
            throw new TeacherNotFoundException(teacher);
        }

        return teacherDAO.update(teacher);
    }

    /**
     * This method deletes a teacher with an id given
     * by the user.
     *
     * @param id the id given by the user.
     * @return true if the row is deleted successfully,
     * otherwise false.
     * @throws TeacherDAOException      handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws TeacherNotFoundException handles update or delete errors when the teacher
     *                                  does not exist.
     * @throws SpecialityDAOException   handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     * @throws UserDAOException         handles errors propagated from DAO layer
     *                                  related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    @Override
    public boolean deleteTeacher(long id) throws TeacherDAOException, TeacherNotFoundException, SpecialityDAOException, UserDAOException {
        Teacher teacher;
        boolean teacherIsDeleted = false;

        teacher = teacherDAO.getById(id);
        if (teacher == null) {
            throw new TeacherNotFoundException("Teacher with id = " + id + " was not found");
        }
        if (teacherDAO.delete(id)) {
            teacherIsDeleted = true;
        }

        return teacherIsDeleted;
    }

    /**
     * This method returns the teachers where their lastname
     * begins with the parameter given by the client.
     *
     * @param lastname the lastname for searching the teachers' lastname.
     * @return an {@link java.util.ArrayList} with {@link Teacher} objects.
     * @throws TeacherDAOException    handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public List<Teacher> getTeacherByLastname(String lastname) throws TeacherDAOException, UserDAOException, SpecialityDAOException {
        return teacherDAO.getByLastname(lastname);
    }

    /**
     * This method returns a teacher with a certain id (primary key).
     *
     * @param id the id of the student.
     * @return a {@link Teacher} object.
     * @throws TeacherDAOException    handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.TeacherDAOImpl}.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public Teacher getTeacherById(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException {
        return teacherDAO.getById(id);
    }

    /**
     * This method maps the {@link TeacherInsertDTO} object
     * to {@link Teacher} object for insert operation in the DB.
     *
     * @param dto a {@link TeacherInsertDTO} object.
     * @return a {@link Teacher} object.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    private Teacher map(TeacherInsertDTO dto) throws UserDAOException, SpecialityDAOException {
        Speciality speciality;
        User user;

        speciality = specialityDAO.getById(dto.getSpecialityId());
        user = userDAO.getById(dto.getUserId());

        return new Teacher(null, dto.getSsn(), dto.getFirstname(), dto.getLastname(), speciality, user);
    }

    /**
     * This method maps the {@link TeacherUpdateDTO} object
     * to {@link Teacher} object for update operation in the DB.
     *
     * @param dto a {@link TeacherUpdateDTO} object.
     * @return a {@link Teacher} object.
     * @throws UserDAOException       handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.UserDAOImpl}.
     */
    private Teacher map(TeacherUpdateDTO dto) throws UserDAOException, SpecialityDAOException {
        Speciality speciality;
        User user;

        speciality = specialityDAO.getById(dto.getSpecialityId());
        user = userDAO.getById(dto.getUserId());

        return new Teacher(dto.getId(), dto.getSsn(), dto.getFirstname(), dto.getLastname(), speciality, user);
    }
}
