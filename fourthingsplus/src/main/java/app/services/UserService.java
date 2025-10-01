package app.services;

import app.entities.User;
import app.exceptions.DatabaseException;

public interface UserService {
    public User createUser(String userName, String password) throws DatabaseException;
    public User login(String userName, String password) throws DatabaseException;
    public boolean deleteUser(int userId) throws DatabaseException;
    public boolean updateUser(User user) throws DatabaseException;
}
