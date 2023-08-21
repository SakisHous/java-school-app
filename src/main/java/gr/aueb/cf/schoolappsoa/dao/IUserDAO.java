package gr.aueb.cf.schoolappsoa.dao;


import gr.aueb.cf.schoolappsoa.dao.exceptions.UserDAOException;
import gr.aueb.cf.schoolappsoa.model.User;

import java.util.List;

/**
 * This interface of DAO provides the Public API,
 * CRUD operations of the Users Table in the database.
 *
 * @author Thanasis Chousiadas
 */
public interface IUserDAO {
    User insert(User user) throws UserDAOException;
    User update(User user) throws UserDAOException;
    void delete(long id) throws UserDAOException;
    List<User> getAll() throws UserDAOException;
    User getById(long id) throws UserDAOException;
    User getByUsername(String username) throws UserDAOException;
    List<User> getByUsernameLike(String username) throws UserDAOException;
}
