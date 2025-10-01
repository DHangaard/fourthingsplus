package app.services;

import app.entities.Task;
import app.exceptions.DatabaseException;
import app.persistence.TaskMapper;

import java.util.List;

public class TaskImpl implements TaskService {
    private TaskMapper taskMapper;

    public TaskImpl(TaskMapper taskMapper){
        this.taskMapper = taskMapper;
    }


    @Override
    public Task createTask(String name, int userId) throws DatabaseException {
        return taskMapper.createTask(name,userId);
    }

    @Override
    public List<Task> getTasks(int userId) throws DatabaseException {
        return taskMapper.getTasks(userId);
    }

    @Override
    public boolean updateTask(Task task) throws DatabaseException {
        return taskMapper.updateTask(task);
    }

    @Override
    public boolean deleteTask(int taskId) throws DatabaseException {
        return taskMapper.deleteTask(taskId);
    }
}
