package gr.aueb.cf.schoolappsoa.model;

import gr.aueb.cf.schoolappsoa.service.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This unit test class tests the {@link Student}
 * Model class for Student Entity.
 *
 * @author Thanasis Chousiadas
 */
public class StudentTest {

    @Test
    void gettersSetters() throws ParseException {
        User user = new User(1L, "user-01", "123456");
        City city = new City(1L, "Athens");

        Student student = new Student();
        student.setId(1L);
        student.setFirstname("student-01");
        student.setLastname("student-lastname01");
        student.setGender('F');
        java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("12-12-2000"));
        student.setBirthDate(sqlDate);
        student.setStudentCity(city);
        student.setUser(user);

        assertEquals(student.getId(), 1L);
        assertEquals(student.getFirstname(), "student-01");
        assertEquals(student.getLastname(), "student-lastname01");
        assertEquals(student.getGender(), 'F');
        assertEquals(DateUtil.toString(student.getBirthDate()), "12-12-2000");
        assertEquals(student.getStudentCity().getId(), 1L);
        assertEquals(student.getStudentCity().getCity(), "Athens");
        assertEquals(student.getUser().getId(), 1L);
        assertEquals(student.getUser().getUsername(), "user-01");
    }

    @Test
    void overloadedConstructor() throws ParseException {
        User user = new User(1L, "user-01", "123456");
        City city = new City(1L, "Athens");
        java.sql.Date sqlDate = DateUtil.toSQLDate(DateUtil.toDate("12-12-2000"));
        Student student = new Student(1L, "student-01", "student-lastname01", 'F', sqlDate, city, user);

        assertEquals(student.getId(), 1L);
        assertEquals(student.getFirstname(), "student-01");
        assertEquals(student.getLastname(), "student-lastname01");
        assertEquals(student.getGender(), 'F');
        assertEquals(DateUtil.toString(student.getBirthDate()), "12-12-2000");
        assertEquals(student.getStudentCity().getId(), 1L);
        assertEquals(student.getStudentCity().getCity(), "Athens");
        assertEquals(student.getUser().getId(), 1L);
        assertEquals(student.getUser().getUsername(), "user-01");
    }
}
