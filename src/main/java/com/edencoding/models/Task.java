package com.edencoding.models;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

/**
 * Represents a Task to be completed, with a number of <code>Step</code>s (1 to many).
 */
public class Task {

    private final int id;
    private final ReadOnlyStringProperty name;
    private final ReadOnlyStringProperty description;
    private final ReadOnlyStringProperty project;
    private final ReadOnlyStringProperty projectSubGroup;
    private final ReadOnlyIntegerProperty currentStep;
    private final ReadOnlyBooleanProperty complete;

    private final ReadOnlyObjectProperty<LocalDate> created;
    private final ReadOnlyObjectProperty<LocalDate> lastUpdated;

    private final ObservableList<String> stakeholders;
    private final ObservableList<Step> steps;

    public Task(int id, String name, String description, String project,
                String projectSubGroup, int currentStep, boolean complete,
                LocalDate created, LocalDate lastUpdated,
                List<Step> steps, List<String> stakeholders) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.project = new SimpleStringProperty(project);
        this.projectSubGroup = new SimpleStringProperty(projectSubGroup);
        this.currentStep = new SimpleIntegerProperty(currentStep);
        this.complete = new SimpleBooleanProperty(complete);
        this.created = new SimpleObjectProperty<>(created);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);

        this.steps = FXCollections.observableList(steps);
        this.stakeholders = FXCollections.observableArrayList(stakeholders);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public String getDescription() {
        return description.get();
    }

    public ReadOnlyStringProperty descriptionProperty() {
        return description;
    }

    public String getProject() {
        return project.get();
    }

    public ReadOnlyStringProperty projectProperty() {
        return project;
    }

    public String getProjectSubGroup() {
        return projectSubGroup.get();
    }

    public ReadOnlyStringProperty projectSubGroupProperty() {
        return projectSubGroup;
    }

    public int getCurrentStep() {
        return currentStep.get();
    }

    public ReadOnlyIntegerProperty currentStepProperty() {
        return currentStep;
    }

    public boolean isComplete() {
        return complete.get();
    }

    public ReadOnlyBooleanProperty completeProperty() {
        return complete;
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public ReadOnlyObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated.get();
    }

    public ReadOnlyObjectProperty<LocalDate> lastUpdatedProperty() {
        return lastUpdated;
    }

    public ObservableList<Step> getSteps() {
        return FXCollections.unmodifiableObservableList(steps);
    }

    public ObservableList<String> getStakeholders() {
        return FXCollections.unmodifiableObservableList(stakeholders);
    }
}
