package com.edencoding.models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Represents a single step in a wider <code>Task</code>
 */
public class Step {
    private final int id;
    private final ReadOnlyStringProperty name;
    private final ReadOnlyStringProperty description;
    private final ReadOnlyObjectProperty<LocalDate>  due;

    private final ReadOnlyStringProperty responsible;
    private final ReadOnlyIntegerProperty parent;

    public Step(int id, String name, String description,
                String responsible, LocalDate due,
                int parent) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.due = new SimpleObjectProperty<>(due);
        this.responsible = new SimpleStringProperty(responsible);
        this.parent = new SimpleIntegerProperty(parent);
    }

    public int getId() {
        return id;
    }

    public LocalDate getDue() {
        return due.get();
    }

    public ReadOnlyObjectProperty<LocalDate> dueProperty() {
        return due;
    }

    public String getDescription() {
        return description.get();
    }

    public ReadOnlyStringProperty descriptionProperty() {
        return description;
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public String getActor() {
        return responsible.get();
    }

    public ReadOnlyStringProperty actorProperty() {
        return responsible;
    }

    public int getParent() {
        return parent.get();
    }

    public ReadOnlyIntegerProperty parentProperty() {
        return parent;
    }
}
