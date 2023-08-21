package gr.aueb.cf.schoolappsoa.service.exceptions;

import gr.aueb.cf.schoolappsoa.model.Student;

/**
 * This exception class handles exceptions
 * for update or delete operations. If the
 * record to be updated  or the
 * record to be deleted does not exist.
 *
 * @author Thanasis Chousiadas
 */
public class StudentNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives the object {@link Student} to be updated of deleted.
     *
     * @param student
     */
    public StudentNotFoundException(Student student) {
        super("Student with id = " + student.getId() + " was not found");
    }

    /**
     * Overloaded constructor that receives a custom message.
     *
     * @param s the message given by the client.
     */
    public StudentNotFoundException(String s) {
        super(s);
    }
}
