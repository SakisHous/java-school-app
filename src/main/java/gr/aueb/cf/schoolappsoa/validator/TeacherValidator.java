package gr.aueb.cf.schoolappsoa.validator;


import gr.aueb.cf.schoolappsoa.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.TeacherUpdateDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * It is a utility class. No instances should
 * be provided.
 */
public class TeacherValidator {

    private TeacherValidator() {
    }

    /**
     * This utility method validates the firstname and lastname
     * of the {@link TeacherInsertDTO} object.
     *
     * @param dto a {@link TeacherInsertDTO} object.
     * @return a {@link HashMap} with the errors.
     */
    public static Map<String, String> validate(TeacherInsertDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 3 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "size");
        }

        if (dto.getFirstname().matches("^.*\\s+.*$")) {
            errors.put("firstname", "whitespaces");
        }

        if (dto.getLastname().matches("^.*\\s+.*$")) {
            errors.put("lastname", "whitespaces");
        }

        if (dto.getLastname().length() < 3 || dto.getLastname().length() > 32) {
            errors.put("lastname", "size");
        }

        return errors;
    }

    /**
     * This utility method validates the firstname and lastname
     * of the {@link TeacherUpdateDTO} object.
     *
     * @param dto a {@link TeacherUpdateDTO} object.
     * @return a {@link HashMap} with the errors.
     */
    public static Map<String, String> validate(TeacherUpdateDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto.getFirstname().length() < 3 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "size");
        }

        if (dto.getFirstname().matches("^.*\\s+.*$")) {
            errors.put("firstname", "whitespaces");
        }

        if (dto.getLastname().matches("^.*\\s+.*$")) {
            errors.put("lastname", "whitespaces");
        }

        if (dto.getLastname().length() < 3 || dto.getLastname().length() > 32) {
            errors.put("lastname", "size");
        }

        return errors;
    }
}
