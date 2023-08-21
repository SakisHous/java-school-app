package gr.aueb.cf.schoolappsoa.service.exceptions;


import gr.aueb.cf.schoolappsoa.model.Teacher;

/**
 * This exception class handles exceptions
 * for update or delete operations. If the
 * record to be updated  or the
 * record to be deleted does not exist.
 *
 * @author Thanasis Chousiadas
 */
public class TeacherNotFoundException extends Exception {
    private static final long serialVersionUID = 1L;

    /**
     * Overloaded constructor. It receives the object {@link Teacher} to be updated of deleted.
     *
     * @param teacher a {@link Teacher} object.
     */
    public TeacherNotFoundException(Teacher teacher) {
        super("Teacher with id = " + teacher.getId() + " was not found");
    }

    /**
     * Overloaded constructor that receives a custom message.
     *
     * @param s the message given by the client.
     */
    public TeacherNotFoundException(String s) {
        super(s);
    }
}
