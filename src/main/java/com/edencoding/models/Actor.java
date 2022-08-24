package com.edencoding.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a person responsible for a <code>Step</code>
 */
public class Actor {
    private final BooleanProperty isMe;
    private final StringProperty name;

    public Actor(boolean isMe, String name) {
        this.isMe = new SimpleBooleanProperty(isMe);
        this.name = new SimpleStringProperty(name);
    }

    public boolean isIsMe() {
        return isMe.get();
    }

    public BooleanProperty isMeProperty() {
        return isMe;
    }

    public void setIsMe(boolean isMe) {
        this.isMe.set(isMe);
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
}
