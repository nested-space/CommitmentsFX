package com.edencoding;

import com.edencoding.dao.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Launch extends Application {

    /**
     * The entry point for this application
     *
     * @param args commandline arguments, not currently handled
     */
    public static void main(String[] args) {
        if (Database.isOK()) {  //check for driver errors
            launch();
        } else {
            Platform.exit();
        }
    }

    @Override
    public void start(Stage stage) throws IOException {
        System.out.println("SETUP:\tLaunching");
        launchActorWindow(stage);
    }

    public void launchActorWindow(Stage stage) {
        System.out.println("SETUP:\tOpening Actor Window");

        try {
            Parent root = FXMLLoader.load(getClass().getResource("fxml/ActorEditWindow.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Actor Editor");
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
