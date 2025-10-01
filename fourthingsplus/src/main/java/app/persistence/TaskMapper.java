package app.persistence;

import app.entities.Task;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskMapper {

    private ConnectionPool connectionPool;

    public TaskMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Task createTask(String name, int userId) throws DatabaseException {
        String sql = "INSERT INTO task (name,done,user_id) VALUES (?,?,?)";
        Task task = null;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){

            ps.setString(1,name);
            ps.setBoolean(2,false);
            ps.setInt(3,userId);

            ResultSet idResultset = ps.getGeneratedKeys();
            if(idResultset.next()){
                int taskId = idResultset.getInt("task_id");
                task = new Task(taskId,name,false);
            }

        }catch (SQLException e){
            throw new DatabaseException("Couldn't create task");
        }

        return task;
    }


    public List<Task> getTasks(int userId) throws DatabaseException {
        String sql = "SELECT task_id, name, done FROM task WHERE user_id = ?";
        ArrayList<Task> tasks = new ArrayList<>();

        try(Connection connection = connectionPool.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            ps.setInt(1, userId);

            ResultSet taskResultset = ps.getGeneratedKeys();
            while (taskResultset.next()) {

                int taskId = taskResultset.getInt("task_id");
                String name = taskResultset.getString("name");
                boolean isDone = taskResultset.getBoolean("done");

                Task task = new Task(taskId, name, isDone);
                tasks.add(task);
            }
        } catch (SQLException e) {
            throw new DatabaseException("Could not get task(s)");
        }
        return tasks;
    }

    public boolean updateTask(Task task) throws DatabaseException {
        String sql = "UPDATE task SET done = ? WHERE task_id = ?";
        boolean result = false;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setBoolean(1,task.isDone());
            ps.setInt(2,task.getTaskId());

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 1){
                result = true;
            }

        }catch (SQLException e){
            throw new DatabaseException("Couldn't update task");
        }
        return result;
    }


    public boolean deleteTask(int taskId) throws DatabaseException {
        String sql = "DELETE FROM task WHERE task_id = ?";
        boolean result = false;

        try(Connection connection = connectionPool.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)){

            ps.setInt(1,taskId);

            int rowsAffected = ps.executeUpdate();
            if(rowsAffected == 1){
                result = true;
            }

        }catch (SQLException e){
            throw new DatabaseException("Couldn't delete task");
        }
        return result;
    }
}
