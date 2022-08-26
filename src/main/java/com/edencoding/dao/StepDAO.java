package com.edencoding.dao;

import com.edencoding.models.Step;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StepDAO {

    private static final String tableName = "Steps";
    private static final String idColumn = "id";
    private static final String nameColumn = "name";
    private static final String descriptionColumn = "description";
    private static final String dueDateColumn = "due";
    private static final String responsiblePerson = "responsible";
    private static final String parentTaskColumn = "parent";

    private static final ObservableList<Step> steps;

    static {
        steps = FXCollections.observableArrayList();
        updateStepsFromDB();
    }

    public static ObservableList<Step> getSteps() {
        return FXCollections.unmodifiableObservableList(steps);
    }

    private static void updateStepsFromDB() {

        String query = "SELECT * FROM " + tableName;

        try (Connection connection = Database.connect()) {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            steps.clear();
            while (rs.next()) {
                steps.add(new Step(
                        rs.getInt(idColumn),
                        rs.getString(nameColumn),
                        rs.getString(descriptionColumn),
                        rs.getString(responsiblePerson),
                        rs.getDate(dueDateColumn).toLocalDate(),
                        rs.getInt(parentTaskColumn)
                        ));
            }
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Steps from database ");
            steps.clear();
        }
    }

    public static void update(Step step) {
        //update database
        long rows = CRUDHelper.update(
                tableName,
                new String[]{nameColumn, descriptionColumn, responsiblePerson, dueDateColumn, parentTaskColumn},
                new Object[]{step.getName(), step.getDescription(), step.getActor(), step.getDue(), step.getParent()},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER},
                idColumn,
                Types.INTEGER,
                step.getId()
        );

        if (rows == 0)
            throw new IllegalStateException("Step to be updated with id " + step.getId() + " didn't exist in database");

        //update cache
        Optional<Step> optionalStep = getStep(step.getId());
        optionalStep.ifPresentOrElse((oldStep) -> {
            steps.remove(oldStep);
            steps.add(step);
        }, () -> {
            throw new IllegalStateException("Step to be updated with id " + step.getId() + " didn't exist in database");
        });
    }

    public static void insertStep(String name, String description,
                                    LocalDate due, String responsibleStep,
                                    int parentTask) {
        //update database
        int id = (int) CRUDHelper.create(
                tableName,
                new String[]{nameColumn, descriptionColumn, responsiblePerson, dueDateColumn, parentTaskColumn},
                new Object[]{name, description, due, responsibleStep, parentTask},
                new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.INTEGER});

        //update cache
        steps.add(new Step(
                id, name, description, responsibleStep, due, parentTask));
    }

    public static void delete(int id) {
        //update database
        CRUDHelper.delete(tableName, id);

        //update cache
        Optional<Step> step = getStep(id);
        step.ifPresent(steps::remove);

    }

    public static Optional<Step> getStep(int id) {
        for (Step step : steps) {
            if (step.getId() == id) return Optional.of(step);
        }
        return Optional.empty();
    }

}
