package gr.aueb.cf.schoolappsoa.service;


import gr.aueb.cf.schoolappsoa.dao.exceptions.*;
import gr.aueb.cf.schoolappsoa.dto.LoginDTO;
import gr.aueb.cf.schoolappsoa.dto.UserInsertDTO;
import gr.aueb.cf.schoolappsoa.dto.UserUpdateDTO;
import gr.aueb.cf.schoolappsoa.model.User;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserAlreadyExistsException;
import gr.aueb.cf.schoolappsoa.service.exceptions.UserNotFoundException;

import java.util.List;

public interface IUserService {
    User insertUser(UserInsertDTO dto) throws UserAlreadyExistsException, UserDAOException;

    User updateUser(UserUpdateDTO dto) throws UserNotFoundException, UserDAOException;

    void deleteUser(long id) throws UserNotFoundException, UserDAOException, StudentDAOException, CityDAOException, TeacherDAOException, SpecialityDAOException;

    //    List<User> getUserByUsername(String username) throws UserDAOException;
    User getUserById(long id) throws UserDAOException, UserNotFoundException;

    User getUserByUsername(String username) throws UserDAOException;

    List<User> getAllUsers() throws UserDAOException;

    List<User> getUsersByUsernameLike(String username) throws UserDAOException;

    boolean login(LoginDTO dto) throws UserDAOException;
}
