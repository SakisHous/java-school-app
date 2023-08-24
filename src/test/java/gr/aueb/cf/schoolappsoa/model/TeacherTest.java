package gr.aueb.cf.schoolappsoa.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * This unit test class tests the {@link Teacher}
 * Model class for Teacher Entity.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherTest {

    /**
     * This method tests the getters and setters
     * for each attribute of {@link Teacher} class.
     */
    @Test
    void gettersSetters() {
        User user = new User(1L, "user-01", "123456");
        Speciality speciality = new Speciality(1L, "Biology");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setSsn(123L);
        teacher.setFirstname("NewTeacher");
        teacher.setLastname("LastnameTeacher");
        teacher.setTeacherSpeciality(speciality);
        teacher.setUser(user);

        assertEquals(teacher.getId(), 1);
        assertEquals(teacher.getSsn(), 123L);
        assertEquals(teacher.getFirstname(), "NewTeacher");
        assertEquals(teacher.getLastname(), "LastnameTeacher");
        assertNotNull(teacher.getTeacherSpeciality());
        assertNotNull(teacher.getUser());
    }

    /**
     * This class tests the constructor of
     * {@link Teacher} class.
     */
    @Test
    void overloadedConstructor() {
        User user = new User(1L, "user-01", "123456");
        Speciality speciality = new Speciality(1L, "Biology");

        Teacher teacher = new Teacher(1L, 123L, "NewTeacher", "LastnameTeacher", speciality, user);

        assertEquals(teacher.getId(), 1);
        assertEquals(teacher.getSsn(), 123L);
        assertEquals(teacher.getFirstname(), "NewTeacher");
        assertEquals(teacher.getLastname(), "LastnameTeacher");
        assertNotNull(teacher.getTeacherSpeciality());
        assertNotNull(teacher.getUser());
    }
}
