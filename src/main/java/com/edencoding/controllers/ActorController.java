package com.edencoding.controllers;

import com.edencoding.dao.ActorDAO;
import com.edencoding.models.Actor;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ActorController {

    public TableView<Actor> actorTable;
    public Button deleteButton;
    public Button addButton;
    public TextField actorNameTextBox;
    public TableColumn<Actor, Integer> idColumn;
    public TableColumn<Actor, String> nameColumn;

    public void initialize() {
        //initialise table
        actorTable.setItems(ActorDAO.getActors());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("actor"));

        //set up buttons
        addButton.disableProperty().bind(Bindings.isEmpty(actorNameTextBox.textProperty()));
        deleteButton.disableProperty().bind(Bindings.isNull(actorTable.getSelectionModel().selectedItemProperty()));
    }

    public void handleDeleteActor(ActionEvent actionEvent) {
        ActorDAO.delete(actorTable.getSelectionModel().getSelectedItem().getId());
    }

    public void handleAddActor(ActionEvent actionEvent) {
        ActorDAO.insertActor(actorNameTextBox.getText());
    }
}
