package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.Teacher;

import java.util.List;

/**
 * This interface of DAO provides the Public API,
 * CRUD operations for the Teachers Table in the database.
 *
 * @author Thanasis Chousiadas
 */
public interface ITeacherDAO {
    Teacher insert(Teacher teacher) throws TeacherDAOException;
    Teacher update(Teacher teacher) throws TeacherDAOException;
    boolean delete(long id) throws TeacherDAOException;
    List<Teacher> getByLastname(String lastname) throws TeacherDAOException, SpecialityDAOException, UserDAOException;
    Teacher getById(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException;

    Teacher getByUserId(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException;
}
