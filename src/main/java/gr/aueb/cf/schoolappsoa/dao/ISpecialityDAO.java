package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.model.Speciality;

import java.util.List;

/**
 * This interface of DAO provides the Public API,
 * CRUD operations for the Specialities Table in the database.
 *
 * @author Thanasis Chousiadas
 */
public interface ISpecialityDAO {
    Speciality insert(Speciality speciality) throws SpecialityDAOException;
    Speciality update(Speciality speciality) throws SpecialityDAOException;
    void delete(Long id) throws SpecialityDAOException;
    List<Speciality> getAllSpecialty() throws SpecialityDAOException;
    Speciality getById(Long id) throws SpecialityDAOException;
}
