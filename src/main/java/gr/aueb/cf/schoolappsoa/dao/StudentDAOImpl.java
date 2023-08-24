package gr.aueb.cf.schoolappsoa.dao;

import gr.aueb.cf.schoolappsoa.dao.exceptions.CityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.StudentDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.City;
import gr.aueb.cf.schoolappsoa.model.Student;
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
 * {@link IStudentDAO} interface.
 *
 * @author Thanasis Chousiadas
 */
public class StudentDAOImpl implements IStudentDAO {
    private final ICityDAO cityDAO = new CityDAOImpl();
    private final IUserDAO userDAO = new UserDAOImpl();

    /**
     * This method inserts a new record in the Students table.
     *
     * @param student the {@link Student} object of the model to be inserted.
     * @return the inserted {@link Student} object.
     * @throws StudentDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public Student insert(Student student) throws StudentDAOException {
        String sql = "INSERT INTO STUDENTS (FIRSTNAME, LASTNAME, GENDER, BIRTH_DATE, CITY_ID, USER_ID) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getLastname());
            ps.setString(3, String.valueOf(student.getGender()));
            ps.setDate(4, student.getBirthDate());
            ps.setLong(5, student.getStudentCity().getId());
            ps.setLong(6, student.getUser().getId());
            int n = ps.executeUpdate();

            if (n >= 1) {
                return student;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StudentDAOException("SQL Error in Student insert " + student);
        }
    }

    /**
     * This method updates an old record in the Students table
     * with a new one.
     *
     * @param student the {@link Student} object.
     * @return the updated {@link Student} object.
     * @throws StudentDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public Student update(Student student) throws StudentDAOException {
        String sql = "UPDATE STUDENTS SET FIRSTNAME = ?, LASTNAME = ?, GENDER = ?, BIRTH_DATE = ?, CITY_ID = ?, USER_ID = ? WHERE ID = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getLastname());
            ps.setString(3, String.valueOf(student.getGender()));
            ps.setDate(4, student.getBirthDate());
            ps.setLong(5, student.getStudentCity().getId());
            ps.setLong(6, student.getUser().getId());
            ps.setLong(7, student.getId());
            int n = ps.executeUpdate();

            if (n >= 1) {
//                JOptionPane.showMessageDialog(null, "Successful Update", "Update", JOptionPane.PLAIN_MESSAGE);
                return student;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StudentDAOException("SQL Error in Student Update " + student);
        }
    }

    /**
     * This method deletes a record from Students table with an id given by
     * the client.
     *
     * @param id the primary key of the record.
     * @return true if the row is deleted successfully,
     * otherwise false.
     * @throws StudentDAOException if an error is occurred, this wrapper
     *                             exception to {@link SQLException} will be thrown.
     */
    @Override
    public boolean delete(long id) throws StudentDAOException {
        String sql = "DELETE FROM STUDENTS WHERE ID = ?";
        boolean studentIsDeleted = false;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);

            int n = ps.executeUpdate();
            if (n == 1) {
                studentIsDeleted = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new StudentDAOException("SQL Error: User delete with id = " + id);
        }
        return studentIsDeleted;
    }

    /**
     * This method returns an {@link ArrayList} with the records
     * of the Students table where lastname begins with the lastname
     * provided by the client.
     *
     * @param lastname the lastname for searching given by the client.
     * @return an {@link ArrayList} of {@link Student} objects where
     * their lastname field begins with the lastname parameter
     * given by the client.
     * @throws StudentDAOException for errors that occur during records retrieval
     *                             from Students table.
     * @throws CityDAOException    for errors that occur during records retrieval
     *                             from Cities table. Students table has foreign key
     *                             with Cities table.
     * @throws UserDAOException    for errors that occur during records retrieval
     *                             from Users table. Students table has foreign key
     *                             with Users table.
     */
    @Override
    public List<Student> getByLastname(String lastname) throws StudentDAOException, CityDAOException, UserDAOException {
        String sql = "SELECT * FROM STUDENTS WHERE LASTNAME LIKE ?";
        List<Student> students = new ArrayList<>();
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lastname + "%");
            rs = ps.executeQuery();

            while (rs.next()) {
                City city = cityDAO.getById(rs.getLong("CITY_ID"));
                User user = userDAO.getById(rs.getLong("USER_ID"));

                Student student = new Student();
                student.setId(rs.getLong("ID"));
                student.setFirstname(rs.getString("FIRSTNAME"));
                student.setLastname(rs.getString("LASTNAME"));
                student.setGender(rs.getString("GENDER").charAt(0));
                student.setBirthDate(rs.getDate("BIRTH_DATE"));
                student.setStudentCity(city);
                student.setUser(user);

                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StudentDAOException("");
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return students;
    }

    /**
     * This method returns the record where the primary key is
     * same with the id given by the client.
     *
     * @param id the id provided by the client.
     * @return a {@link Student} with the id.
     * @throws StudentDAOException for errors that occur during records retrieval
     *                             from Students table.
     * @throws CityDAOException    for errors that occur during records retrieval
     *                             from Cities table. Students table has foreign key
     *                             with Cities table.
     * @throws UserDAOException    for errors that occur during records retrieval
     *                             from Users table. Students table has foreign key
     *                             with Users table.
     */
    @Override
    public Student getById(long id) throws StudentDAOException, CityDAOException, UserDAOException {
        String sql = "SELECT * FROM STUDENTS WHERE ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }
            City city = cityDAO.getById(rs.getLong("CITY_ID"));
            User user = userDAO.getById(rs.getLong("USER_ID"));

            return new Student(
                    rs.getLong("ID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("GENDER").charAt(0),
                    rs.getDate("BIRTH_DATE"),
                    city,
                    user
            );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new StudentDAOException("SQL Error in Student retrieve with id = " + id);
        } catch (CityDAOException | UserDAOException e1) {
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
     * This method searches for the record in Students table where
     * the foreign key user_id is the same with the id given by the client
     * and returns this record.
     *
     * @param id the id given by the client.
     * @return a {@link Student} object with the foreign key
     * the same with the id. Each student has a
     * unique user id.
     * @throws StudentDAOException for errors that occur during records retrieval
     *                             from Students table.
     * @throws CityDAOException    for errors that occur during records retrieval
     *                             from Cities table. Students table has foreign key
     *                             with Cities table.
     * @throws UserDAOException    for errors that occur during records retrieval
     *                             from Users table. Students table has foreign key
     *                             with Users table.
     */
    @Override
    public Student getByUserId(long id) throws StudentDAOException, CityDAOException, UserDAOException {
        String sql = "SELECT * FROM STUDENTS WHERE USER_ID = ?";
        ResultSet rs = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            City city = cityDAO.getById(rs.getLong("CITY_ID"));
            User user = userDAO.getById(rs.getLong("USER_ID"));
            return new Student(
                    rs.getLong("ID"),
                    rs.getString("FIRSTNAME"),
                    rs.getString("LASTNAME"),
                    rs.getString("GENDER").charAt(0),
                    rs.getDate("BIRTH_DATE"),
                    city,
                    user
            );
        } catch (SQLException e1) {
            e1.printStackTrace();
            throw new StudentDAOException("SQL Error: Get Student by user_id = " + id);
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
