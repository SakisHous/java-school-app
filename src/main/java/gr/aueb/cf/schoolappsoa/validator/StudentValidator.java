package gr.aueb.cf.schoolappsoa.validator;

import gr.aueb.cf.schoolappsoa.dto.StudentInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.StudentUpdateDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Validator class for Students.
 * It is a utility class.
 * No instances should be provided.
 */
public class StudentValidator {

    /**
     * Private default constructor.
     */
    private StudentValidator() {
    }

    /**
     * This utility method validates the firstname and lastname
     * of the {@link StudentInsertDTO} object.
     *
     * @param dto a {@link StudentInsertDTO} object.
     * @return a {@link HashMap} with the errors.
     */
    public static Map<String, String> validate(StudentInsertDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 3 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "size");
        }
        if (dto.getFirstname().matches("^.*\\s.*$")) {
            errors.put("firstname", "whitespaces");
        }
        if (dto.getLastname().length() < 3 || dto.getLastname().length() > 32) {
            errors.put("lastname", "size");
        }
        if (dto.getLastname().matches("^.*\\s.*$")) {
            errors.put("lastname", "whitespaces");
        }
        return errors;
    }

    /**
     * This utility method validates the firstname and lastname
     * of the {@link StudentUpdateDTO} object.
     *
     * @param dto a {@link StudentUpdateDTO} object.
     * @return a {@link HashMap} with the errors.
     */
    public static Map<String, String> validate(StudentUpdateDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 3 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "size");
        }
        if (dto.getFirstname().matches("^.*\\s.*$")) {
            errors.put("firstname", "whitespaces");
        }
        if (dto.getLastname().length() < 3 || dto.getLastname().length() > 32) {
            errors.put("lastname", "size");
        }
        if (dto.getLastname().matches("^.*\\s.*$")) {
            errors.put("lastname", "whitespaces");
        }
        return errors;
    }
}
