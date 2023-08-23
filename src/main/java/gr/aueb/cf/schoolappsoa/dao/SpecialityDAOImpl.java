package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Public API of the
 * {@link ISpecialityDAO} interface.
 *
 * @author Thanasis Chousiadas
 */
public class SpecialityDAOImpl implements ISpecialityDAO {

    /**
     * This method inserts a new record in the Specialities table.
     *
     * @param speciality the {@link Speciality} object of the model to be inserted.
     * @return the inserted {@link Speciality} object.
     * @throws SpecialityDAOException if an error is occurred, this wrapper
     *                                exception to {@link SQLException} will be thrown.
     */
    @Override
    public Speciality insert(Speciality speciality) throws SpecialityDAOException {
        String sql = "INSERT INTO SPECIALITIES (SPECIALITY) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, speciality.getSpecialty());
            int n = ps.executeUpdate();
            if (n == 1) {
                return speciality;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Specialty Insert " + speciality);
        }
    }

    /**
     * This method updates a old record of the Speciality table with
     * a new one.
     *
     * @param speciality the {@link Speciality} object of the model to be updated.
     * @return the inserted {@link Speciality} object.
     * @throws SpecialityDAOException if an error is occurred, this wrapper
     *                                exception to {@link SQLException} will be thrown.
     */
    @Override
    public Speciality update(Speciality speciality) throws SpecialityDAOException {
        String sql = "UPDATE SPECIALITIES SET SPECIALITY = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, speciality.getSpecialty());
            ps.setLong(2, speciality.getId());

            int n = ps.executeUpdate();
            if (n == 1) {
                return speciality;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Specialty Update " + speciality);
        }
    }

    /**
     * This method deletes a record from Speciality table, given by
     * the client.
     *
     * @param id the id provided by the client.
     * @throws SpecialityDAOException if an error is occurred, this wrapper
     *                                exception to {@link SQLException} will be thrown.
     */
    @Override
    public void delete(Long id) throws SpecialityDAOException {
        String sql = "DELETE FROM SPECIALITIES WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            int n = ps.executeUpdate();
            if (n >= 1) {
                System.out.println("...");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Specialty delete with id " + id);
        }
    }

    /**
     * This method returns all the records from the
     * Specialities table.
     *
     * @return an {@link ArrayList} with {@link Speciality} objects.
     * @throws SpecialityDAOException if an error is occurred, this wrapper
     *                                exception to {@link SQLException} will be thrown.
     */
    @Override
    public List<Speciality> getAllSpeciality() throws SpecialityDAOException {
        String sql = "SELECT * FROM SPECIALITIES";
        List<Speciality> specialties = new ArrayList<>();
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            rs = ps.executeQuery();
            while (rs.next()) {
                Speciality speciality = new Speciality(
                        rs.getLong("ID"),
                        rs.getString("SPECIALITY")
                );
                specialties.add(speciality);
            }
            return specialties;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpecialityDAOException("SQL Error in Specialty, retrieving all specialties");
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
     * This methods finds and returns a certain record from
     * Specialities table with the given id.
     *
     * @param id the id given by the client.
     * @return an {@link Speciality} object.
     * @throws SpecialityDAOException if an error is occurred, this wrapper
     *                                exception to {@link SQLException} will be thrown.
     */
    @Override
    public Speciality getById(Long id) throws SpecialityDAOException {
        String sql = "SELECT * FROM SPECIALITIES WHERE ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            return new Speciality(rs.getLong("ID"), rs.getString("SPECIALITY"));
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SpecialityDAOException("SQL Error: Speciality with id = " + id);
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
