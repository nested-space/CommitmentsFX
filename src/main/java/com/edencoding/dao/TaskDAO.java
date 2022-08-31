package com.edencoding.dao;

import com.edencoding.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Database access object for the Task entity
 * <p>
 * Business logic:
 * 1. When a Task is deleted, all associated Steps should be deleted too
 */
public class TaskDAO {

    private static final String tableName = "Tasks";
    private static final String idColumn = "id";
    private static final String nameColumn = "name";
    private static final String descriptionColumn = "description";
    private static final String projectColumn = "project";
    private static final String projectSubGroupColumn = "projectSubGroup";
    private static final String currentStepColumn = "currentStep";

    private static final String stepIDsColumn = "steps";
    private static final String completeColumn = "complete";
    private static final String createdColumn = "created";
    private static final String lastUpdatedColumn = "lastUpdated";

    private static final ObservableList<Task> tasks;

    static {
        tasks = FXCollections.observableArrayList();
        updateTasksFromDB();
    }

    public static ObservableList<Task> getTasks() {
        return FXCollections.unmodifiableObservableList(tasks);
    }

    private static void updateTasksFromDB() {

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            tasks.clear();
            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt(idColumn),
                        rs.getString(nameColumn),
                        rs.getString(descriptionColumn),
                        rs.getString(projectColumn),
                        rs.getString(projectSubGroupColumn),
                        rs.getInt(currentStepColumn),
                        rs.getBoolean(completeColumn),
                        convertStepStringToList(rs.getString(stepIDsColumn)),
                        LocalDate.parse(rs.getString(createdColumn)),
                        LocalDate.parse(rs.getString(lastUpdatedColumn))
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Tasks from database ");
            System.out.println(e.getMessage());
            tasks.clear();
        }
    }

    public static void insert(String name, String description,
                              String project, String projectSubGroup) {
        //update database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{nameColumn, descriptionColumn, projectColumn, projectSubGroupColumn, completeColumn, createdColumn, lastUpdatedColumn},
                new Object[]{name, description, project, projectSubGroup, false, LocalDate.now(), LocalDate.now()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.DATE, Types.DATE});

        //update cache
        tasks.add(new Task(id, name, description, project, projectSubGroup, -1, false, new ArrayList<>(), LocalDate.now(), LocalDate.now()));
    }

    public static void update(Task task) {
        //update database
        long rows = CRUDHelper.update(
                tableName,
                new String[]{nameColumn, descriptionColumn, projectColumn, projectSubGroupColumn, completeColumn, lastUpdatedColumn, stepIDsColumn, currentStepColumn},
                new Object[]{task.getName(), task.getDescription(), task.getProject(), task.getProjectSubGroup(), task.isComplete(), LocalDate.now()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.DATE,},
                idColumn,
                Types.INTEGER,
                task.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Task to be updated with id " + task.getId() + " didn't exist in database");

        //update cache
        Optional<Task> optionalTask = getTask(task.getId());
        optionalTask.ifPresentOrElse((oldTask) -> {
            tasks.remove(oldTask);
            tasks.add(task);
        }, () -> {
            throw new IllegalStateException("Task to be updated with id " + task.getId() + " didn't exist in database");
        });
    }

    public static void delete(int id) {
        //update database
        CRUDHelper.delete(tableName, id);

        //update cache
        Optional<Task> task = getTask(id);
        task.ifPresent(tasks::remove);
    }

    public static Optional<Task> getTask(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) return Optional.of(task);
        }
        return Optional.empty();
    }

    /* *************************************************************************************************************/
    /*                                              Private Utility Methods                                        */
    /* *************************************************************************************************************/

    private static List<Integer> convertStepStringToList(String string) {
        if (string == null) return new ArrayList<>();

        List<Integer> ids = new ArrayList<>();
        String[] idsAsStrings = string.split(",");

        for (String id : idsAsStrings) {
            ids.add(Integer.valueOf(id));
        }

        return ids;
    }

    private static String convertStepsStringToList(Task task) {
        StringBuilder idsAsString = new StringBuilder();
        for (Integer step : task.getStepIDs()) {
            idsAsString.append(step).append(",");
        }
        return idsAsString.toString();
    }
}
