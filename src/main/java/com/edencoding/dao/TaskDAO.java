package com.edencoding.dao;

import com.edencoding.models.Step;
import com.edencoding.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskDAO {

    private static final String tableName = "Tasks";
    private static final String idColumn = "id";
    private static final String nameColumn = "name";
    private static final String descriptionColumn = "description";
    private static final String projectColumn = "project";
    private static final String projectSubGroupColumn = "projectSubGroup";
    private static final String currentStepColumn = "currentStep";
    private static final String completeColumn = "complete";
    private static final String createdColumn = "created";
    private static final String lastUpdatedColumn = "lastUpdated";


    private static final ObservableList<Task> tasks;

    static {
        tasks = FXCollections.observableArrayList();
        updateTasksFromDB();
    }

    private static ObservableList<Task> getTasks(){
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
                        rs.getDate(createdColumn).toLocalDate(),
                        rs.getDate(lastUpdatedColumn).toLocalDate(),
                        //TODO: get steps!,
                        //TODO: get stakeholders!!
                ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Tasks from database ");
            tasks.clear();
        }
    }
}
