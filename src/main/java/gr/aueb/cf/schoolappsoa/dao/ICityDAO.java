package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.model.City;

import java.util.List;

/**
 * This interface of DAO provides the Public API,
 * CRUD operations for Cities Table in the database.
 *
 * @author Thanasis Chousiadas
 */
public interface ICityDAO {
    City insert(City city) throws CityDAOException;
    City update(City city) throws CityDAOException;
    void delete(Long id) throws CityDAOException;
    List<City> getAllCities() throws CityDAOException;
    City getById(long id) throws CityDAOException;

    City getByName(String name) throws CityDAOException;
}
