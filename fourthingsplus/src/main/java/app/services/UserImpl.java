package app.services;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.UserMapper;

public class UserImpl implements UserService {
    private UserMapper userMapper;

    public UserImpl(UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(String userName, String password) throws DatabaseException {
        return userMapper.createUser(userName,password);
    }

    @Override
    public User login(String userName, String password) throws DatabaseException {
        return userMapper.login(userName,password);
    }

    @Override
    public boolean deleteUser(int userId) throws DatabaseException {
        return userMapper.deleteUser(userId);
    }

    @Override
    public boolean updateUser(User user) throws DatabaseException {
        return userMapper.updateUser(user);
    }
}
