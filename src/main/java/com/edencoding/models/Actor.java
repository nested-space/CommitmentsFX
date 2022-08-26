package com.edencoding.models;

import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Actor {

    private final ReadOnlyIntegerProperty id;
    private final ReadOnlyStringProperty actor;

    public Actor(int id, String actor) {
        this.id = new SimpleIntegerProperty(id);
        this.actor = new SimpleStringProperty(actor);
    }

    public int getId() {
        return id.get();
    }

    public ReadOnlyIntegerProperty idProperty() {
        return id;
    }

    public String getActor() {
        return actor.get();
    }

    public ReadOnlyStringProperty actorProperty() {
        return actor;
    }
}
