package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.Speciality;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class implements the Public API of the
 * {@link ITeacherDAO} interface.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherDAOImpl implements ITeacherDAO {

    private final ISpecialityDAO specialtyDAO = new SpecialityDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();

    /**
     * This method inserts a new record in the Teachers table.
     *
     * @param teacher a {@link Teacher} object, to be inserted.
     * @return the inserted {@link Teacher} object.
     * @throws TeacherDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public Teacher insert(Teacher teacher) throws TeacherDAOException {
        String sql = "INSERT INTO TEACHERS (SSN, FIRSTNAME, LASTNAME, SPECIALITY_ID, USER_ID) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, teacher.getSsn());
            ps.setString(2, teacher.getFirstname());
            ps.setString(3, teacher.getLastname());
            ps.setLong(4, teacher.getTeacherSpeciality().getId());
            ps.setLong(5, teacher.getUser().getId());
            int n = ps.executeUpdate();
            if (n == 1) {
                return teacher;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error in Teacher insert " + teacher);
        }
    }

    /**
     * This method updates an old record in the Teachers table
     * with the new one.
     *
     * @param teacher a {@link Teacher} object.
     * @return the updated {@link Teacher} object.
     * @throws TeacherDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public Teacher update(Teacher teacher) throws TeacherDAOException {
        String sql = "UPDATE TEACHERS SET SSN = ?, FIRSTNAME = ?, LASTNAME = ?, SPECIALITY_ID = ?, USER_ID = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, teacher.getSsn());
            ps.setString(2, teacher.getFirstname());
            ps.setString(3, teacher.getLastname());
            ps.setLong(4, teacher.getTeacherSpeciality().getId());
            ps.setLong(5, teacher.getUser().getId());
            ps.setLong(6, teacher.getId());

            int n = ps.executeUpdate();
            if (n == 1) {
                return teacher;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error in Teacher Update " + teacher);
        }
    }

    /**
     * This method deletes a record from the Teachers table,
     * where the primary key is equal to the id given by the
     * client.
     *
     * @param id the primary key given by the client.
     * @return true if the row is deleted successfully,
     * otherwise false.
     * @throws TeacherDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public boolean delete(long id) throws TeacherDAOException {
        String sql = "DELETE FROM TEACHERS WHERE ID = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            int n = ps.executeUpdate();
            return n == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error: User delete with id = " + id);
        }
    }

    /**
     * This method returns an {@link ArrayList} with the records
     * of the Teachers table where lastname begins with the lastname
     * provided by the client.
     *
     * @param lastname the lastname for searching given by the client.
     * @return an {@link ArrayList} of {@link Teacher} objects where
     * their lastname field begins with the lastname parameter
     * given by the client.
     * @throws TeacherDAOException    for errors that occur during records retrieval
     *                                from Teachers table.
     * @throws SpecialityDAOException for errors that occur during records retrieval
     *                                from Specialities table. Teachers table has foreign key
     *                                with Specialities table.
     * @throws UserDAOException       for errors that occur during records retrieval
     *                                from Users table. Teachers table has foreign key
     *                                with Users table.
     */
    @Override
    public List<Teacher> getByLastname(String lastname) throws TeacherDAOException, SpecialityDAOException, UserDAOException {
        String sql = "SELECT * FROM TEACHERS WHERE LASTNAME LIKE ?";
        List<Teacher> teachers = new ArrayList<>();
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Speciality speciality = specialtyDAO.getById(rs.getLong("SPECIALITY_ID"));
                User user = userDAO.getById(rs.getLong("USER_ID"));

                Teacher teacher = new Teacher(
                        rs.getLong("ID"),
                        rs.getLong("SSN"),
                        rs.getString("FIRSTNAME"),
                        rs.getString("LASTNAME"),
                        speciality,
                        user
                );
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error: Could not retrieve teachers from DB.");
        } catch (SpecialityDAOException | UserDAOException e1) {
            throw e1;
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
     * This method returns the record where the primary key is
     * the same with the id given by the client.
     *
     * @param id the id provided by the client.
     * @return a {@link Teacher} with the id.
     * @throws TeacherDAOException    for errors that occur during records retrieval
     *                                from Teachers table.
     * @throws UserDAOException       for errors that occur during records retrieval
     *                                from Users table. Teachers table has foreign keys
     *                                with Cities table.
     * @throws SpecialityDAOException for errors that occur during records retrieval
     *                                from Specialities table. Teachers table has foreign key
     *                                with Users table.
     */
    @Override
    public Teacher getById(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException {
        String sql = "SELECT * FROM TEACHERS WHERE ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)
        ) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Speciality speciality = specialtyDAO.getById(rs.getLong("SPECIALITY_ID"));
            User user = userDAO.getById(rs.getLong("USER_ID"));
            return new Teacher(
                    rs.getLong("ID"),
                    rs.getLong("SSN"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    speciality,
                    user
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new TeacherDAOException("SQL Error: Teacher with id = " + id + " not found");
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
     * This method searches for the record in Students table where
     * the foreign key user_id is the same with the id given by the client
     * and returns this record.
     *
     * @param id the id given by the client.
     * @return a {@link Teacher}  object with the foreign key
     * the same with the id. Each student has a
     * unique user id.
     * @throws TeacherDAOException    for errors that occur during records retrieval
     *                                from Teachers table.
     * @throws UserDAOException       for errors that occur during records retrieval
     *                                from Users table. Teachers table has foreign key
     *                                with Users table.
     * @throws SpecialityDAOException for errors that occur during records retrieval
     *                                from Specialities table. Teachers table has foreign key
     *                                with Specialities table.
     */
    @Override
    public Teacher getByUserId(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException {
        String sql = "SELECT * FROM TEACHERS WHERE USER_ID = ?";
        ResultSet rs = null;
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }
            Speciality speciality = specialtyDAO.getById(rs.getLong("SPECIALITY_ID"));
            User user = userDAO.getById(rs.getLong("USER_ID"));
            return new Teacher(
                    rs.getLong("ID"),
                    rs.getLong("SSN"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    speciality,
                    user
            );
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new TeacherDAOException("SQL Error: Get Teacher by teacher_id = " + id);
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
}
