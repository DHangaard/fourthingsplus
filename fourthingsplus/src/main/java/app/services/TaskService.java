package app.services;

import app.entities.Task;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.util.List;

public interface TaskService {

    public Task createTask(String name, int userId) throws DatabaseException;
    public List<Task> getTasks(int userId) throws DatabaseException;
    public boolean updateTask(Task task) throws DatabaseException;
    public boolean deleteTask(int taskId) throws DatabaseException;
}
