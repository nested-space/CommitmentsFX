package com.edencoding.controllers;

import com.edencoding.dao.TaskDAO;
import com.edencoding.models.Task;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TaskController {

    public TableView<Task> taskTable;
    public Button deleteButton;
    public Button addButton;
    public TableColumn<Task, Integer> idColumn;
    public TableColumn<Task, String> nameColumn;
    public TableColumn<Task, Integer> descriptionColumn;
    public TableColumn<Task, Integer> projectColumn;
    public TableColumn<Task, Integer> projectSubGroupColumn;
    public TextField taskNameTextBox;
    public TextField taskDescriptionTextBox;
    public TextField taskProjectTextBox;
    public TextField taskProjectSubGroupTextBox;


    public void initialize() {
        //initialise table
        taskTable.setItems(TaskDAO.getTasks());
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("project"));
        projectSubGroupColumn.setCellValueFactory(new PropertyValueFactory<>("projectSubGroup"));

        //set up buttons
        addButton.disableProperty().bind(Bindings.and(
                Bindings.and(Bindings.isEmpty(taskNameTextBox.textProperty()), Bindings.isEmpty(taskDescriptionTextBox.textProperty())),
                Bindings.and(Bindings.isEmpty(taskProjectTextBox.textProperty()), Bindings.isEmpty(taskProjectSubGroupTextBox.textProperty())))
        );
        deleteButton.disableProperty().bind(Bindings.isNull(taskTable.getSelectionModel().selectedItemProperty()));
    }

    public void handleDeleteTask(ActionEvent actionEvent) {
        TaskDAO.delete(taskTable.getSelectionModel().getSelectedItem().getId());
    }

    public void handleAddTask(ActionEvent actionEvent) {
        TaskDAO.insert(taskNameTextBox.getText(), taskDescriptionTextBox.getText(), taskProjectTextBox.getText(), taskProjectSubGroupTextBox.getText());
    }
}
