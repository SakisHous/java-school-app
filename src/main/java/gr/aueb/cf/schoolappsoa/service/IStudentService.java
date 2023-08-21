package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.StudentInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.StudentUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Student;
import gr.aueb.cf.schoolappsoa.service.exceptions.StudentNotFoundException;

import java.util.List;

/**
 * This interface declares the Public API for
 * the Service Layer of this application.
 * It declares services for CRUD operations in
 * {@link Student} objects.
 *
 * @author Thanasis Chousiadas
 */
public interface IStudentService {
    Student insertStudent(StudentInsertDTO dto) throws StudentDAOException, CityDAOException, UserDAOException;

    Student updateStudent(StudentUpdateDTO dto) throws StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException;

    boolean deleteStudent(long id) throws StudentDAOException, StudentNotFoundException, CityDAOException, UserDAOException;

    List<Student> getStudentByLastname(String lastname) throws StudentDAOException, CityDAOException, UserDAOException;

    Student getStudentById(long id) throws StudentDAOException, CityDAOException, UserDAOException;
}
