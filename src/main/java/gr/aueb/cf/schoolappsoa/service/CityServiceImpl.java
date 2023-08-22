package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.ICityDAO;
import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dto.CityInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.CityUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.service.exceptions.CityAlreadyExistsException;
import gr.aueb.cf.schoolappsoa.service.exceptions.CityNotFoundException;

import java.util.List;

/**
 * This interface implements the Public API
 * of the {@link ICityService} interface, for
 * the Service Layer of this application.
 * It implements services for CRUD operations in
 * {@link City} objects.
 *
 * @author Thanasis Chousiadas
 */
public class CityServiceImpl implements ICityService {
    private final ICityDAO cityDAO;

    /**
     * Dependency injection of an {@link ICityDAO} implementation in Data Access Object Layer.
     *
     * @param cityDAO an implementation of {@link ICityDAO} interface.
     */
    public CityServiceImpl(ICityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    /**
     * This method inserts a new city in the database.
     *
     * @param dto the Data Transfer Object with the data for insert the record.
     * @return the inserted {@link City} entity.
     * @throws CityDAOException           handles errors propagated from DAO layer.
     * @throws CityAlreadyExistsException handles insert error where the city
     *                                    already exists.
     */
    @Override
    public City insertCity(CityInsertDTO dto) throws CityDAOException, CityAlreadyExistsException {
        City city = map(dto);

        // checks if the city is already exists
        City storedCity = cityDAO.getByName(city.getCity());

        if (storedCity != null) {
            throw new CityAlreadyExistsException(city);
        }

        return cityDAO.insert(city);
    }

    /**
     * This method updates an old city with a new one.
     *
     * @param dto the Data Transfer Object with the data for update the record.
     * @return the updated {@link City} entity.
     * @throws CityDAOException      handles errors propagated from DAO layer.
     * @throws CityNotFoundException handles update error where the city
     *                               does not exist.
     */
    @Override
    public City updateCity(CityUpdateDTO dto) throws CityDAOException, CityNotFoundException {
        City city = map(dto);

        if (cityDAO.getById(city.getId()) == null) {
            throw new CityNotFoundException(city);
        }

        return cityDAO.update(city);
    }

    /**
     * This method deletes a city from the database.
     *
     * @param id the primary key of the city.
     * @throws CityDAOException      handles errors propagated from DAO layer.
     * @throws CityNotFoundException handles delete error where the city
     *                               does not exist.
     */
    @Override
    public void deleteCity(long id) throws CityDAOException, CityNotFoundException {
        City city = cityDAO.getById(id);

        if (city == null) {
            throw new CityNotFoundException("Delete city with id = " + id + " was not found");
        }

        cityDAO.delete(id);
    }

    /**
     * This method return all the cities in the database.
     *
     * @return an {@link java.util.ArrayList} with {@link City} objects.
     * @throws CityDAOException handles errors propagated from DAO layer.
     */
    @Override
    public List<City> getAllCities() throws CityDAOException {
        return cityDAO.getAllCities();
    }

    /**
     * This method searches for a specific city with an id given
     * by the user and returns this city.
     *
     * @param id the primary key of the city.
     * @return a {@link City} object.
     * @throws CityDAOException      handles errors propagated from DAO layer.
     * @throws CityNotFoundException if the city does not exist, thrown this
     *                               exception.
     */
    @Override
    public City getCityById(long id) throws CityDAOException, CityNotFoundException {
        City city = cityDAO.getById(id);

        if (city == null) {
            throw new CityNotFoundException("City with id = " + id + " was not found");
        }

        return city;
    }

    /**
     * This method maps the {@link CityInsertDTO} object
     * to {@link City} object for insert operation in the DB.
     *
     * @param dto a {@link CityInsertDTO} object.
     * @return a {@link City} object.
     */
    private City map(CityInsertDTO dto) {
        return new City(null, dto.getCity());
    }

    /**
     * This method maps the {@link CityUpdateDTO} object
     * to {@link City} object for update operation in the DB.
     *
     * @param dto a {@link CityUpdateDTO} object.
     * @return a {@link City} object.
     */
    private City map(CityUpdateDTO dto) {
        return new City(dto.getId(), dto.getCity());
    }
}
