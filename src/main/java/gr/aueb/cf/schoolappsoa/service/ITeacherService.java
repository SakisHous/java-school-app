package gr.aueb.cf.schoolappsoa.service;

import gr.aueb.cf.schoolappsoa.dao.exceptions.SpecialityDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.TeacherDAOException;
import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.dto.TeacherInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.TeacherUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.Teacher;
import gr.aueb.cf.schoolappsoa.service.exceptions.TeacherNotFoundException;

import java.util.List;

public interface ITeacherService {
    Teacher insertTeacher(TeacherInsertDTO dto) throws TeacherDAOException, UserDAOException, SpecialityDAOException;

    Teacher updateTeacher(TeacherUpdateDTO dto) throws TeacherDAOException, TeacherNotFoundException, UserDAOException, SpecialityDAOException;

    boolean deleteTeacher(long id) throws TeacherDAOException, TeacherNotFoundException, SpecialityDAOException, UserDAOException;

    List<Teacher> getTeacherByLastname(String lastname) throws TeacherDAOException, UserDAOException, SpecialityDAOException;

    Teacher getTeacherById(long id) throws TeacherDAOException, UserDAOException, SpecialityDAOException;
}
