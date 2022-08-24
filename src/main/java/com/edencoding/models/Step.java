package com.edencoding.models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Represents a single step in a wider <code>Task</code>
 */
public class Step {
    private final int id;
    private final StringProperty name;
    private final StringProperty description;
    private final ObjectProperty<LocalDate>  due;
    private final ObjectProperty<Actor> actor;
    private final IntegerProperty parent;


    public Step(int id, LocalDate due, String description, String name, Actor actor, int parent) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.due = new SimpleObjectProperty<>(due);
        this.actor = new SimpleObjectProperty<>(actor);
        this.parent = new SimpleIntegerProperty(parent);
    }

    public int getId() {
        return id;
    }

    public LocalDate getDue() {
        return due.get();
    }

    public ObjectProperty<LocalDate> dueProperty() {
        return due;
    }

    public void setDue(LocalDate due) {
        this.due.set(due);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Actor getActor() {
        return actor.get();
    }

    public ObjectProperty<Actor> actorProperty() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor.set(actor);
    }

    public int getParent() {
        return parent.get();
    }

    public IntegerProperty parentProperty() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent.set(parent);
    }
}
