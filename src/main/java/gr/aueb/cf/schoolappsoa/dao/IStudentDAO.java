package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.Student;

import java.util.List;

/**
 * This interface of DAO provides the Public API,
 * CRUD operations for Students Table in the database.
 *
 * @author Thanasis Chousiadas
 */
public interface IStudentDAO {
    Student insert(Student student) throws StudentDAOException;
    Student update(Student student) throws StudentDAOException;
    boolean delete(long id) throws StudentDAOException;
    List<Student> getByLastname(String lastname) throws StudentDAOException, CityDAOException, UserDAOException;
    Student getById(long id) throws StudentDAOException, CityDAOException, UserDAOException;

    Student getByUserId(long id) throws StudentDAOException, CityDAOException, UserDAOException;
}
