package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.service.exceptions.SpecialityNotFoundException;

import java.util.List;

public interface ISpecialityService {
    Speciality insertSpecialty(SpecialityInsertDTO dto) throws SpecialityDAOException;

    Speciality updateSpecialty(SpecialityUpdateDTO dto) throws SpecialityDAOException, SpecialityNotFoundException;

    void deleteSpecialty(long id) throws SpecialityDAOException, SpecialityNotFoundException;

    List<Speciality> getAllSpecialities() throws SpecialityDAOException;

    Speciality getSpecialtyById(long id) throws SpecialityDAOException;
}
