package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dto.CityInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.CityUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.service.exceptions.CityAlreadyExistsException;
import gr.aueb.cf.schoolappsoa.service.exceptions.CityNotFoundException;

import java.util.List;

/**
 * This interface declares the Public API for
 * the Service Layer of this application.
 * It declares services for CRUD operations in
 * {@link City} objects.
 *
 * @author Thanasis Chousiadas
 */
public interface ICityService {
    City insertCity(CityInsertDTO dto) throws CityDAOException, CityAlreadyExistsException;

    City updateCity(CityUpdateDTO dto) throws CityDAOException, CityNotFoundException;

    void deleteCity(long id) throws CityDAOException, CityNotFoundException;

    List<City> getAllCities() throws CityDAOException;

    City getCityById(long id) throws CityDAOException, CityNotFoundException;
}
