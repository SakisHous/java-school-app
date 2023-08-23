package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.ISpecialityDAO;
import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dto.SpecialityInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.SpecialityUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.service.exceptions.SpecialityNotFoundException;

import java.util.List;

/**
 * This interface implements the Public API
 * of the {@link ISpecialityService} interface, for
 * the Service Layer of this application.
 * It implements services for CRUD operations in
 * {@link Speciality} objects.
 *
 * @author Thanasis Chousiadas
 */
public class SpecialityServiceImpl implements ISpecialityService {
    private final ISpecialityDAO specialtyDAO;

    /**
     * Dependency injection of {@link ISpecialityDAO} type
     * implementation in Data Access Object Layer.
     *
     * @param specialtyDAO an implementation of {@link ISpecialityDAO} interface.
     */
    public SpecialityServiceImpl(ISpecialityDAO specialtyDAO) {
        this.specialtyDAO = specialtyDAO;
    }

    /**
     * This method inserts a new speciality in the database.
     *
     * @param dto the Data Transfer Object with the data for insert the record.
     * @return the inserted {@link Speciality} entity.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public Speciality insertSpecialty(SpecialityInsertDTO dto) throws SpecialityDAOException {
        Speciality speciality = map(dto);

        return specialtyDAO.insert(speciality);
    }

    /**
     * This method updates an old speciality record with a new one.
     *
     * @param dto the Data Transfer Object with the data for update the record.
     * @return the updated {@link Speciality} object.
     * @throws SpecialityDAOException      handles errors propagated from DAO layer
     *                                     related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     * @throws SpecialityNotFoundException handles update or delete errors when the teacher
     *                                     does not exist.
     */
    @Override
    public Speciality updateSpecialty(SpecialityUpdateDTO dto) throws SpecialityDAOException, SpecialityNotFoundException {
        Speciality speciality = map(dto);

        if (specialtyDAO.getById(speciality.getId()) == null) {
            throw new SpecialityNotFoundException(speciality.getId());
        }

        return specialtyDAO.update(speciality);
    }

    /**
     * This method deletes a teacher with an id given
     * by the user.
     *
     * @param id the id given by the client.
     * @throws SpecialityDAOException      handles errors propagated from DAO layer
     *                                     related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     * @throws SpecialityNotFoundException handles update or delete errors when the teacher
     *                                     does not exist.
     */
    @Override
    public void deleteSpecialty(long id) throws SpecialityDAOException, SpecialityNotFoundException {
        Speciality speciality = specialtyDAO.getById(id);

        if (speciality == null) {
            throw new SpecialityNotFoundException("Specialty not found");
        }

        // TODO: Delete a Teacher if exists and after Specialty to avoid foreign key constraint violation
        specialtyDAO.delete(id);
    }

    /**
     * This method returns the specialities where their speciality name
     * begins with the parameter given by the client.
     *
     * @return an {@link java.util.ArrayList} object with {@link Speciality} objects.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public List<Speciality> getAllSpecialities() throws SpecialityDAOException {
        return specialtyDAO.getAll();
    }

    /**
     * This method returns a speciality with a certain id (primary key).
     *
     * @param id the id of the speciality.
     * @return a {@link Speciality} object.
     * @throws SpecialityDAOException handles errors propagated from DAO layer
     *                                related with {@link gr.aueb.cf.schoolappsoa.dao.SpecialityDAOImpl}.
     */
    @Override
    public Speciality getSpecialtyById(long id) throws SpecialityDAOException {
        return specialtyDAO.getById(id);
    }

    /**
     * This method maps the {@link SpecialityInsertDTO} object
     * to {@link Speciality} object for insert operation in the DB.
     *
     * @param dto a {@link SpecialityInsertDTO} object.
     * @return a {@link Speciality} object.
     */
    private Speciality map(SpecialityInsertDTO dto) {
        return new Speciality(null, dto.getSpeciality());
    }

    /**
     * This method maps the {@link SpecialityUpdateDTO} object
     * to {@link Speciality} object for update operation in the DB.
     *
     * @param dto a {@link SpecialityUpdateDTO} object.
     * @return a {@link Speciality} object.
     */
    private Speciality map(SpecialityUpdateDTO dto) {
        return new Speciality(dto.getId(), dto.getSpeciality());
    }
}
