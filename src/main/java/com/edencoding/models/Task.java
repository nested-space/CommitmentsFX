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
    private final StringProperty name;
    private final StringProperty description;
    private final ObservableList<String> stakeholders;
    private final StringProperty project;
    private final StringProperty projectSubGroup;
    private final ObservableList<Step> steps;
    private final IntegerProperty currentStep;
    private final BooleanProperty complete;
    private final ObjectProperty<LocalDate> created;
    private final ObjectProperty<LocalDate> lastUpdated;

    public Task(int id, String name, String description, String project,
                String projectSubGroup, int currentStep, boolean complete,
                LocalDate created, LocalDate lastUpdated) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.project = new SimpleStringProperty(project);
        this.projectSubGroup = new SimpleStringProperty(projectSubGroup);
        this.currentStep = new SimpleIntegerProperty(currentStep);
        this.complete = new SimpleBooleanProperty(complete);
        this.created = new SimpleObjectProperty<>(created);
        this.lastUpdated = new SimpleObjectProperty<>(lastUpdated);

        steps = FXCollections.observableArrayList();
        stakeholders = FXCollections.observableArrayList();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getProject() {
        return project.get();
    }

    public StringProperty projectProperty() {
        return project;
    }

    public void setProject(String project) {
        this.project.set(project);
    }

    public String getProjectSubGroup() {
        return projectSubGroup.get();
    }

    public StringProperty projectSubGroupProperty() {
        return projectSubGroup;
    }

    public void setProjectSubGroup(String projectSubGroup) {
        this.projectSubGroup.set(projectSubGroup);
    }

    public int getCurrentStep() {
        return currentStep.get();
    }

    public IntegerProperty currentStepProperty() {
        return currentStep;
    }

    public void setCurrentStep(int currentStep) {
        this.currentStep.set(currentStep);
    }

    public boolean isComplete() {
        return complete.get();
    }

    public BooleanProperty completeProperty() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete.set(complete);
    }

    public LocalDate getCreated() {
        return created.get();
    }

    public ObjectProperty<LocalDate> createdProperty() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created.set(created);
    }

    public LocalDate getLastUpdated() {
        return lastUpdated.get();
    }

    public ObjectProperty<LocalDate> lastUpdatedProperty() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated.set(lastUpdated);
    }

    public void addStep(Step step){
        steps.add(step);
    }

    public void removeStep(Step step){
        steps.remove(step);
    }

    public void addStakeholder(String name){
        stakeholders.add(name);
    }

    public void addStakeholders(List<String> names){
        stakeholders.addAll(names);
    }

    public void removeStakeholder(String name){
        stakeholders.remove(name);
    }
}
