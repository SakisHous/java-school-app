package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Public API of the
 * {@link ICityDAO} interface.
 *
 * @author Thanasis Chousiadas
 */
public class CityDAOImpl implements ICityDAO {

    /**
     * This method inserts a new record in the table Cities.
     *
     * @param city the {@link City} object of the model, represents the entity.
     * @return the inserted {@link City} object.
     * @throws CityDAOException if an error is occurred, the wrapper
     *                          exception to {@link SQLException} will be thrown.
     */
    @Override
    public City insert(City city) throws CityDAOException {
        String sql = "INSERT INTO CITIES (CITY) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, city.getCity());
            int n = ps.executeUpdate();
            if (n == 1) {
                return city;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CityDAOException("SQL Error in City Insert " + city);
        }
    }

    /**
     * This method updates a record of Cities table.
     *
     * @param city the {@link City} object of the model, represents the entity.
     * @return the inserted {@link City} object.
     * @throws CityDAOException if an error is occurred, this wrapper
     *                          exception to {@link SQLException} will be thrown.
     */
    @Override
    public City update(City city) throws CityDAOException {
        String sql = "UPDATE CITIES SET CITY = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, city.getCity());
            ps.setLong(2, city.getId());
            int n = ps.executeUpdate();

            if (n == 1) {
                return city;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CityDAOException("SQL Error in City Update " + city);
        }
    }

    /**
     * This method deletes a record from the City table with
     * the id given by the client.
     *
     * @param id the id provided by the client.
     * @throws CityDAOException if an error is occurred, this wrapper
     *                          exception to {@link SQLException} will be thrown.
     */
    @Override
    public void delete(Long id) throws CityDAOException {
        String sql = "DELETE FROM CITIES WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            int n = ps.executeUpdate();
            if (n == 1) {
                System.out.println("...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CityDAOException("SQL Error in City delete with id " + id);
        }
    }

    /**
     * This method returns all the records in the Cities table.
     *
     * @return an {@link ArrayList} with the {@link City} objects.
     * @throws CityDAOException if an error is occurred, this wrapper
     *                          exception to {@link SQLException} will be thrown.
     */
    @Override
    public List<City> getAll() throws CityDAOException {
        String sql = "SELECT * FROM CITIES";
        List<City> cities = new ArrayList<>();
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            rs = ps.executeQuery();
            while (rs.next()) {
                City city = new City(
                        rs.getLong("ID"),
                        rs.getString("CITY")
                );
                cities.add(city);
            }
            return cities;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CityDAOException("SQL Error in City, retrieving all cities");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * This method finds a city with an id from the
     * Cities table.
     *
     * @param id the id given by the client.
     * @return a {@link City} object.
     * @throws CityDAOException if an error is occurred, this wrapper
     *                          exception to {@link SQLException} will be thrown.
     */
    @Override
    public City getById(long id) throws CityDAOException {
        String sql = "SELECT * FROM CITIES WHERE ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            return new City(rs.getLong("ID"), rs.getString("CITY"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new CityDAOException("SQL Error in City with id = " + id);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public City getByName(String name) throws CityDAOException {
        String sql = "SELECT * FROM CITIES WHERE CITY = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, name);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return new City(rs.getLong("ID"), rs.getString("CITY"));
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new CityDAOException("SQL Error in Cities: Query City by name.");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
